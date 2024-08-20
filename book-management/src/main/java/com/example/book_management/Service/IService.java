package com.example.book_management.Service;

import java.util.List;

public interface IService<T> {
    T add(T t);

    T update(T t);

    void delete(Long id);

    List<T> getAll();

    T getById(Long id);
}
