package com.epam.redkin.railway.model.validator;

public interface Validator<T>{
    String isValid(T entity);
}
