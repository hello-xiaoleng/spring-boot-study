package com.xiaoleng.admin.domain.repository;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseRepository<T> {

    @PersistenceContext(name = "masterPersistenceUnit")
    protected EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Transactional
    public void persist(T domainObject) {
        entityManager.persist(domainObject);
    }

    public T load(long id) {
        return entityManager.find(getDomainClass(), id);
    }

    public T load(String id) {
        return entityManager.find(getDomainClass(), id);
    }

    @Transactional
    public T update(T domainObject) {
        return entityManager.merge(domainObject);
    }

    @Transactional
    public void remove(T domainObject) {
        entityManager.remove(domainObject);
    }

    @SuppressWarnings("unchecked")
    public Class<T> getDomainClass() {
        return (Class<T>) (((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    @SuppressWarnings("unchecked")
    public T findFirst(String query, Object... parameters) {
        Query queryObject = entityManager.createQuery(query).setMaxResults(1);
        for (int i = 0; i < parameters.length; i++) {
            queryObject.setParameter(i + 1, parameters[i]);
        }
        List result = queryObject.getResultList();
        return result.isEmpty() ? null : (T) result.get(0);
    }

    public T lock(T domainObject, LockModeType type) {
        entityManager.flush();
        entityManager.lock(domainObject, type);
        entityManager.refresh(domainObject);
        return domainObject;
    }

    public interface StatusCheck<T> {
        void check(T domainObject);
    }

    public T lock(T domainObject, LockModeType type, StatusCheck<T> statusCheck) {
        T lockedObject = this.lock(domainObject, type);
        if (null != statusCheck) {
            statusCheck.check(lockedObject);
        }
        return lockedObject;
    }

    public T lockAndLoad(Object primaryKey,
                         LockModeType lockMode) {
        return entityManager.find(getDomainClass(), primaryKey, lockMode);
    }

    public T lockRefreshAndLoad(long primaryKey, LockModeType type) {
        T domainObject = this.load(primaryKey);
        entityManager.flush();
        entityManager.lock(domainObject, type);
        entityManager.refresh(domainObject);
        return domainObject;
    }

    public void flush() {
        entityManager.flush();
    }

    public void clear() {
        entityManager.clear();
    }

    public void refresh(Object entity) {
        entityManager.refresh(entity);
    }

    public <C> C getSingleResult(TypedQuery<C> entity) {
        List<C> resultList = entity.getResultList();
        if (resultList.isEmpty()) {
            return null;
        }
        return resultList.get(0);
    }

    @SuppressWarnings("unchecked")
    public <C> C getQuerySingleResult(Query query) {
        List<C> resultList = query.getResultList();
        if (resultList.isEmpty()) {
            return null;
        }
        return resultList.get(0);
    }

    public List<T> findAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(getDomainClass());
        Root<T> rootEntry = cq.from(getDomainClass());
        CriteriaQuery<T> all = cq.select(rootEntry);
        TypedQuery<T> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
    }

    protected <T extends Enum> List<String> getEnumStringList(List<T> enums) {
        List<String> enumStrings = new ArrayList<>();
        for (T item : enums) {
            enumStrings.add(item.name());
        }
        return enumStrings;
    }

    @Transactional
    public void batchInsert(List<T> entitys) {
        int batchSize = 30;
        for (int i = 0; i < entitys.size(); i++) {
            entityManager.persist(entitys.get(i));
            if (i % batchSize == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
    }

    @Transactional
    public void batchUpdate(List<T> entitys) {
        int batchSize = 30;
        for (int i = 0; i < entitys.size(); i++) {
            entityManager.merge(entitys.get(i));
            if (i % batchSize == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
    }
}
