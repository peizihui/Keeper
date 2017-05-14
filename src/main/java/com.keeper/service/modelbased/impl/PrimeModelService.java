package com.keeper.service.modelbased.impl;

/*
 * Created by @GoodforGod on 7.04.2017.
 */

import com.keeper.service.modelbased.IPrimeModelService;
import com.keeper.util.Validator;
import com.keeper.util.resolvers.ErrorMessageResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Basic model with ID as a LONG type PARAMETER!
 */
@Service
public class PrimeModelService<T> implements IPrimeModelService<T> {

    protected final Logger LOGGER = LoggerFactory.getLogger(PrimeModelService.class);

    protected JpaRepository<T, Long> primeRepository;

    protected T getEmpty() {
        return null;
    }

    protected List<T> getEmptyList() {
        return Collections.emptyList();
    }

    protected boolean invalidId(Long id, String msg) {
        if(!Validator.isIdValid(id)) {
            LOGGER.warn(msg);
            return true;
        }
        return false;
    }

    protected boolean invalidModel(T model, String msg) {
        if(model == null) {
            LOGGER.warn(msg);
            return true;
        }
        return false;
    }

    protected boolean invalidListModel(List<T> model, String msg) {
        if(model == null || model.isEmpty()) {
            LOGGER.warn(msg);
            return true;
        }
        return false;
    }

    @Override
    public boolean exists(Long id) {
        return (Validator.isIdValid(id)) && primeRepository.exists(id);
    }

    @Override
    public Optional<T> get(Long id) {
        if(invalidId(id, ErrorMessageResolver.GET_NULLABLE_ID))
            return Optional.empty();

        return Optional.of(primeRepository.findOne(id));
    }

    @Override
    public Optional<List<T>> getAll() {
        return Optional.of(primeRepository.findAll());
    }

    @Override
    public Optional<List<T>> getAllByIds(List<Long> ids) {
        return Optional.of(primeRepository.findAll(ids));
    }

    // Ask what to return, exception or empty/null in case of NULL
    @Transactional
    @Override
    public Optional<T> save(T model) {
        if(invalidModel(model, ErrorMessageResolver.CREATE_MODEL_NULLABLE))
            return Optional.empty();

        return Optional.of(primeRepository.save(model));
    }

    @Transactional
    @Override
    public Optional<List<T>> save(List<T> model) {
        if(invalidListModel(model, ErrorMessageResolver.CREATE_MODEL_NULLABLE))
            return Optional.empty();

        return Optional.of(primeRepository.save(model));
    }

    // Ask what to return, exception or empty/null in case of NULL
    @Transactional
    @Override
    public Optional<T> update(T model) {
        if(invalidModel(model, ErrorMessageResolver.UPDATE_MODEL_NULLABLE))
            return Optional.empty();

        return Optional.of(primeRepository.save(model));
    }

    @Transactional
    @Override
    public void remove(Long id) {
        if(invalidId(id, ErrorMessageResolver.REMOVE_NULLABLE_ID))
            throw new NullPointerException(ErrorMessageResolver.NULLABLE_MODEL + "DELETE");
        primeRepository.delete(id);
    }
}