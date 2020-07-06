package com.leonid.springboot.service;

import com.leonid.springboot.exception.EntityException;

import java.util.List;

public interface DataBaseServiceInterface<T, S> {
    List<T> getAll();

    T findById(S s) throws EntityException;

    S create(T t);

}
