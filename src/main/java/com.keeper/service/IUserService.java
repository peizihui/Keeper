package com.keeper.service;

/*
 * Created by @GoodforGod on 6.04.2017.
 */

import com.keeper.model.dao.User;
import com.keeper.model.dto.*;
import com.keeper.util.Translator;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Default Comment
 */
public interface IUserService extends IModelService<User> {
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);

    Optional<User> getAuthorized();

    Optional<User> getByEmail(String email);
    Optional<User> getByPhone(String phone);

    Optional<User> removeByEmail(String email);
    Optional<User> removeByPhone(String phone);


    /*---ZONES---*/
    ZoneDTO getZone(Long userId);
    UserDTO createZone(Long userId, ZoneDTO zone);
    /*---END ZONES---*/

    /*---PICTURE---*/
    PictureDTO getPicture(Long userId);
    UserDTO setPicture(Long userId, PictureDTO picture);
    /*---END PICTURE---*/

    /*GEOPOINTS*/
    List<GeoUserDTO> getGeoPoints(Long userId);
    UserDTO addGeoPoint(Long id, GeoUserDTO geoPoint);
    UserDTO removeGeoPoint(Long userId, GeoUserDTO geoPoint);
    UserDTO removeGeoPointById(Long userId, Long geoPointId);
    /*---END GEOPOINTS---*/

    /*---ROUTES---*/
    List<RouteDTO> getRoutes(Long userId);
    UserDTO addRoute(Long userId, RouteDTO route);
    UserDTO removeRoute(Long userId, RouteDTO route);
    UserDTO removeRouteById(Long userId, Long routeId);
    /*---END ROUTES---*/

    /*---PARTICIPANTED TASKS---*/
/*    List<TaskDTO> getParticipantedTasks(Long userId);
    UserDTO addParticipantedTask(Long userId, TaskDTO task);
    UserDTO removeParticipantedTask(Long userId, TaskDTO task);
    UserDTO removeParticipantedTaskById(Long userId, Long taskId);*/
    /*---END PARTICIPANTED TASKS---*/
}
