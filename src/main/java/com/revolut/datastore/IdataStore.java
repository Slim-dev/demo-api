package com.revolut.datastore;

import java.util.List;

public interface IdataStore<T> {
    T findById(String id);
    List<T> findAll();
    void add(Object data);
}
