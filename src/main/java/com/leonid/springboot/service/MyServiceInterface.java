package com.leonid.springboot.service;

import java.util.List;

public interface MyServiceInterface<T, S> {
    List<T> getAll();

    T findById(S s);

    S create(T t);

}
