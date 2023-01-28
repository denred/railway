package com.epam.redkin.railway.model.repository;

import java.sql.SQLException;

/**
 * @param <T> the entity of which will be operated
 * @author Denys Redkin
 * @apiNote Use this interface for extending in your DAO interfaces to implements standard CRUD operations
 */
public interface EntityDAO<T> {
    /**
     * Create entity in database
     *
     * @param entity which will be created in database
     * @return the id of the created entity
     */
    int create(T entity) throws RuntimeException, SQLException;

    /**
     * Return entity from database by the id
     *
     * @param id id of entity in database
     * @return requested entity
     */
    T getById(int id);

    /**
     * Update entity in database by id
     *
     * @param entity entity which necessary update
     * @return true if update successful and false otherwise
     */
    boolean update(T entity);

    /**
     * Delete entity in database by id
     *
     * @param id id of entity in database which will be deleted
     */
    void delete(int id);
}
