package com.phishingrod.repositories;

public interface EntityRepository<E, D>
{
    <T> T findById(long id, Class<T> type);


}
