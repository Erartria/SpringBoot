package com.leonid.springboot.service;

import com.leonid.springboot.exception.NotFoundEntityWithID;

import java.util.List;

public interface DataBaseServiceInterface<T, S> {
    List<T> getAll();

    T findById(S s) throws NotFoundEntityWithID;

    S create(T t);

}
