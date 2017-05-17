package com.keeper.controllers.web;

/*
 * Created by @GoodforGod on 25.04.2017.
 */

import com.keeper.model.dao.Task;
import com.keeper.model.dao.User;
import com.keeper.model.dto.CommentDTO;
import com.keeper.model.dto.TaskDTO;
import com.keeper.model.util.SimpleGeoPoint;
import com.keeper.service.core.ISubscription;
import com.keeper.service.core.impl.SubscriptionService;
import com.keeper.service.modelbased.impl.CommentService;
import com.keeper.service.modelbased.impl.TaskService;
import com.keeper.service.modelbased.impl.UserService;
import com.keeper.util.ModelTranslator;
import com.keeper.util.Validator;
import com.keeper.util.resolvers.TemplateResolver;
import com.keeper.util.resolvers.WebResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

/**
 * Default Comment
 */
@Controller
public class TaskWebController {

    private static final Logger logger = LoggerFactory.getLogger(TaskWebController.class);

    private final TaskService taskService;
    private final UserService userService;
    private final ISubscription subsService;
    private final CommentService commentService;

    private final String MSG = "msg";

    @Autowired
    public TaskWebController(TaskService taskService,
                             UserService userService,
                             SubscriptionService subsService,
                             CommentService commentService) {
        this.taskService = taskService;
        this.userService = userService;
        this.subsService = subsService;
        this.commentService = commentService;
    }

    /**
     * Task View Template
     */
    @RequestMapping(value = WebResolver.TASK + "/{id}", method = RequestMethod.GET)
    public ModelAndView taskGet(@PathVariable(value = "id") Long taskId, Model model) {
        ModelAndView modelAndView = new ModelAndView(TemplateResolver.TASK);

        Optional<User> user = userService.getAuthorized();
        if(user.isPresent()){
            if(Validator.isIdValid(taskId)) {
                Optional<Task> daoTask = taskService.get(taskId);
                if(daoTask.isPresent()) {
                    modelAndView.addObject("user", ModelTranslator.toDTO(user.get()));
                    TaskDTO taskDTO = subsService.fillSubs(user.get().getId(),
                                                        ModelTranslator.toDTO(daoTask.get()));
                    modelAndView.addObject("task", taskDTO);
                    modelAndView.addObject("comment", new CommentDTO(user.get().getId(), taskDTO.getId(), user.get().getName()));
                    subsService.viewTask(user.get().getId(), daoTask.get().getId());
                    return modelAndView;
                }
                else modelAndView.addObject(MSG, "No Such Task!");
            }
            else modelAndView.addObject(MSG, "Invalid Task ID!");
        }
        else modelAndView.addObject(MSG, "ReLogin First!");

        modelAndView.setViewName(TemplateResolver.redirect(TemplateResolver.FEED));

        return modelAndView;
    }

    @RequestMapping(value = WebResolver.TASK + "/{id}", method = RequestMethod.DELETE)
    public ModelAndView taskDelete(@PathVariable(value = "id") Long taskId, Model model) {
        ModelAndView modelAndView = new ModelAndView(TemplateResolver.redirect(TemplateResolver.FEED));

        Optional<User> user = userService.getAuthorized();
        if(user.isPresent()) {
            if(Validator.isIdValid(taskId)) {
                try {
                    taskService.remove(taskId);
                } catch (Exception e) {
                    modelAndView.addObject(MSG, "No Such Task!");
                }
            }
            else modelAndView.addObject(MSG, "Invalid Task ID!");
        }
        else modelAndView.addObject(MSG, "ReLogin First");

        return modelAndView;
    }


    /**
     * Task Update/Create Form Template
     */
    @RequestMapping(value = WebResolver.TASK_FORM, method = RequestMethod.GET)
    public ModelAndView taskCreateForm(@RequestParam(value = "id", required = false) Long id,
                                       @RequestParam(value = "lat", required = false) String latitude,
                                       @RequestParam(value = "lng", required = false) String longitude,
                                       @RequestParam(value = "radius", required = false) Integer radius, Model model) {
        ModelAndView modelAndView = new ModelAndView(TemplateResolver.TASK_FORM);

        Optional<User> user = userService.getAuthorized();

        if(user.isPresent()) {
            TaskDTO dto = new TaskDTO();

            if(latitude != null && longitude != null && radius != null)
                dto.setGeo(new SimpleGeoPoint(latitude, longitude, radius));

            dto.setTopicStarterId(user.get().getId());
            if(Validator.isIdValid(id)) {
                Optional<Task> updateTask = taskService.get(id);

                if(updateTask.isPresent())
                    dto = ModelTranslator.toDTO(updateTask.get());
            }
            modelAndView.addObject("user", user);
            modelAndView.addObject("task", dto);
            return modelAndView;
        }
        else modelAndView.addObject(MSG, "ReLogin First!");

        modelAndView.setViewName(TemplateResolver.redirect(TemplateResolver.FEED));

        return modelAndView;
    }

    @RequestMapping(value = WebResolver.TASK_FORM, method = RequestMethod.POST)
    public ModelAndView taskUpdateOrCreate(@Valid TaskDTO task, Model model) {
        ModelAndView modelAndView = new ModelAndView(TemplateResolver.TASK);

        Optional<User> user = userService.getAuthorized();

        if(user.isPresent()) {
            task.setTopicStarterId(user.get().getId());

            // RADIUS SHOULD BE MORE THAT 0 ALWAYS TO SAVE
            if(task.getGeo() == null)
                task.setGeo(new SimpleGeoPoint("0.", "0.", 15));

            try {
                if (Validator.isIdValid(task.getId()))
                    taskService.updateDTO(task);
                else
                    taskService.saveDTO(task);

                modelAndView.addObject("user", user.get());
                modelAndView.addObject("task", task);
                return modelAndView;
            }
            catch (Exception e) {
                modelAndView.addObject(MSG, "No Such Task!");
                modelAndView.setViewName(TemplateResolver.FEED);
            }
        }
        else modelAndView.addObject(MSG, "ReLogin First!");

        modelAndView.setViewName(TemplateResolver.redirect(TemplateResolver.FEED));

        return modelAndView;
    }

    @RequestMapping(value = WebResolver.TASK_COMMENT, method = RequestMethod.POST)
    public ModelAndView taskAddComment(@Valid TaskDTO task,
                                       @Valid CommentDTO comment,
                                       Model model) {
        ModelAndView modelAndView = new ModelAndView(TemplateResolver.redirect(TemplateResolver.TASK + "/" + task.getId()));

        try {
            commentService.saveDTO(comment);
        } catch (Exception e) {
            logger.warn("ERROR ON SAVING COMMENT");
        }

        return modelAndView;
    }

    /**
     * Task Subscribe Mapping
     */
    @RequestMapping(value = WebResolver.TASK_SUBS, method = RequestMethod.POST)
    public ModelAndView taskSubscribe(@RequestParam(value = "id") Long taskId, Model model) {
        ModelAndView modelAndView = new ModelAndView(TemplateResolver.redirect(WebResolver.FEED));

        Optional<User> user = userService.getAuthorized();
        if(user.isPresent() && Validator.isIdValid(taskId)) {
            try {
                subsService.subscribe(user.get().getId(), taskId);
            } catch (Exception e) {
                logger.warn(e.getMessage() + " | " + "You can't Subscribe to " + taskId);
                modelAndView.addObject(MSG, "You can't Subscribe");
            }
        }

        return modelAndView;
    }

    @RequestMapping(value = WebResolver.TASK_SUBS, method = RequestMethod.DELETE)
    public ModelAndView taskUnSubscribe(@RequestParam(value = "id") Long taskId, Model model) {
        ModelAndView modelAndView = new ModelAndView(TemplateResolver.redirect(WebResolver.FEED));

        Optional<User> user = userService.getAuthorized();
        if(user.isPresent() && Validator.isIdValid(taskId)) {
            try {
                subsService.unSubscribe(user.get().getId(), taskId);
            } catch (Exception e) {
                logger.warn(e.getMessage() + " | " + "You are not Subscribed to " + taskId);
                modelAndView.addObject(MSG, "You are not Subscribed to " + taskId);
            }
        }

        return modelAndView;
    }
}
