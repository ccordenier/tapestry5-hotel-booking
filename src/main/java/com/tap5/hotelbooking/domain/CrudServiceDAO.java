package com.tap5.hotelbooking.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.tapestry5.hibernate.annotations.CommitAfter;

/**
 * CrudServiceDAO interface.
 * 
 * @author karesti
 */
public interface CrudServiceDAO
{
    /**
     * @param <T>
     * @param t
     * @return
     */
    @CommitAfter
    <T> T create(T t);

    /**
     * @param <T>
     * @param t
     * @return
     */
    @CommitAfter
    <T> T update(T t);

    /**
     * @param <T>
     * @param <PK>
     * @param type
     * @param id
     */
    @CommitAfter
    <T, PK extends Serializable> void delete(Class<T> type, PK id);

    /**
     * @param <T>
     * @param <PK>
     * @param type
     * @param id
     * @return
     */
    <T, PK extends Serializable> T find(Class<T> type, PK id);

    /**
     * @param <T>
     * @param queryName
     * @return
     */
    <T> List<T> findWithNamedQuery(String queryName);

    /**
     * Find a query with parameters
     * 
     * @param <T>
     * @param queryName
     * @param params
     * @return resulting list
     */
    <T> List<T> findWithNamedQuery(String queryName, Map<String, Object> params);
}
