package com.keeper.controllers.web;

/*
 * Created by @GoodforGod on 25.04.2017.
 */

import com.keeper.model.dao.User;
import com.keeper.model.dto.GeoPointDTO;
import com.keeper.model.dto.GeoUserDTO;
import com.keeper.model.dto.TaskDTO;
import com.keeper.service.impl.GeoPointService;
import com.keeper.service.impl.GeoUserService;
import com.keeper.service.impl.UserService;
import com.keeper.util.Translator;
import com.keeper.util.resolve.TemplateResolver;
import com.keeper.util.resolve.WebResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

/**
 * Default Comment
 */
@Controller
public class MapWebController {

    private final GeoUserService geoPointService;
    private final UserService userService;

    @Autowired
    public MapWebController(GeoUserService geoPointService, UserService userService) {
        this.geoPointService = geoPointService;
        this.userService = userService;
    }

    @RequestMapping(value = WebResolver.MAP, method = RequestMethod.GET)
    public ModelAndView mapGet(Model model) {
        ModelAndView modelAndView = new ModelAndView(TemplateResolver.MAP);

        GeoPointDTO geoPointDTO = new GeoPointDTO();

        modelAndView.addObject("geoPoint", geoPointDTO);

        return modelAndView;
    }

    @RequestMapping(value = WebResolver.MAP, method = RequestMethod.POST)
    public ModelAndView mapUpdate(Model model) {
        ModelAndView modelAndView = new ModelAndView(TemplateResolver.MAP);

//        modelAndView.addObject("user", userDTO);

        return modelAndView;
    }

    @RequestMapping(value = WebResolver.GEOPOINT_CREATE, method = RequestMethod.POST)
    public ModelAndView geoPointCreateForm(@Valid GeoUserDTO geoPointDTO, Model model) {
        ModelAndView modelAndView = new ModelAndView(TemplateResolver.MAP);

        Optional<User> user = userService.getAuthorized();

        System.out.println(geoPointDTO.getRadius());

        if(user.isPresent()) {
            geoPointDTO.setUserId(user.get().getId());
            geoPointService.add(Translator.toDAO(geoPointDTO));
//            userService.addGeoPoint(user.getId(), geoPointDTO);
            // БЫЛО, то что не закомменчено стало т.к. теперь GeoUserService & and should save geoPoints via GeoUserService, not UserService
        }

        modelAndView.addObject("geoPoint", geoPointDTO);

        return modelAndView;
    }
}
