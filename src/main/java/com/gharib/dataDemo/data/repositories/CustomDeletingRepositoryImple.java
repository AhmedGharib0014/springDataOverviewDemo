package com.gharib.dataDemo.data.repositories;


import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

@Repository
public class CustomDeletingRepositoryImple implements   CustomDeletingRepository{

    private final EntityManager entityManager;

    public CustomDeletingRepositoryImple(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void deleteByOrigin(String origin) {
        entityManager.createQuery("DELETE FROM Flight WHERE origin=?")
            .setParameter(1,origin)
            .executeUpdate();

    }
}
