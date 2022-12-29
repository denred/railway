package com.epam.redkin.model.repository;

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
    int create(T entity);

    /**
     * Return entity from database by the id
     *
     * @param id id of entity in database
     * @return requested entity
     */
    T read(int id);

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
     * @return true if delete successful and false otherwise
     */
    boolean delete(int id);
}
