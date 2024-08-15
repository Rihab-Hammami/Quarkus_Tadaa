package com.neo.exp.services;

import com.neo.exp.entities.UserProfile;
import com.neo.exp.entities.UserPoints;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;

@ApplicationScoped
public class UserPointsService {

    @Inject
    EntityManager em;

    @Transactional
    public void addPoints(String userId, int points) {
        UserPoints userPoints = findUserPointsByUserId(userId);
        if (userPoints == null) {
            userPoints = new UserPoints();
            userPoints.setUser(findUserById(userId));
            userPoints.setPoints(points);
            em.persist(userPoints);
        } else {
            userPoints.setPoints(userPoints.getPoints() + points);
            em.merge(userPoints);
        }
    }

    public int getPoints(String userId) {
        UserPoints userPoints = findUserPointsByUserId(userId);
        return userPoints != null ? userPoints.getPoints() : 0;
    }

    @Transactional
    public void convertPoints(String userId, int points) {
        UserPoints userPoints = findUserPointsByUserId(userId);
        if (userPoints != null && userPoints.getPoints() >= points) {
            userPoints.setPoints(userPoints.getPoints() - points);
            em.merge(userPoints);
        } else {
            throw new IllegalArgumentException("Insufficient points");
        }
    }

    private UserPoints findUserPointsByUserId(String userId) {
        try {
            return em.createQuery("SELECT up FROM UserPoints up WHERE up.user.id = :userId", UserPoints.class)
                    .setParameter("userId", userId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    private UserProfile findUserById(String userId) {
        return em.find(UserProfile.class, userId);
    }
}
