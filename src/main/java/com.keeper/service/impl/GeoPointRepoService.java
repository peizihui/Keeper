package com.keeper.service.impl;

import com.keeper.model.dao.GeoPoint;
import com.keeper.repo.GeoPointRepository;
import com.keeper.service.IGeoPointService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by AlexVasil on 29.03.2017.
 *
 * @author AlexVasil
 *
 */
//@Service(value = "geoPointService")
public class GeoPointRepoService extends ModelRepoService<GeoPoint> implements IGeoPointService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GeoPointRepoService.class);

    private final GeoPointRepository repository;

    @Autowired
    public GeoPointRepoService(GeoPointRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<GeoPoint> addGeoPoints(Long ownerId, List<GeoPoint> geoPoints) {
        return null;
    }

    @Override
    public List<GeoPoint> getGeoPoints(Long ownerId, List<Long> geoPointsIds) {
        return null;
    }

    @Override
    public List<GeoPoint> getAllGeoPoints() {
        return null;
    }

    @Override
    public List<GeoPoint> updateGeoPoints(Long ownerId, List<GeoPoint> geoPoints) {
        return null;
    }

    @Override
    public void removeGeoPoints(Long ownerId, List<Long> geoPointsIds) {

    }
}
