//package com.dungnt.repository;
//
//import com.dungnt.entity.Config;
//import io.quarkus.hibernate.orm.panache.PanacheRepository;
//import jakarta.enterprise.context.ApplicationScoped;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//
//import java.util.List;
//
//@ApplicationScoped
//public class ConfigRepository implements PanacheRepository<Config> {
//    @PersistenceContext
//    EntityManager em;
//
//    public EntityManager getEntityManager() {
//        return em;
//    }
//
//    public String findByParamNameAndIsActive(String paramName) {
//        return String.valueOf(getEntityManager()
//                .createQuery("Select c.paramValue from Config c WHERE c.paramName = :paramName and c.isActive = 1")
//                .setParameter("paramName", paramName)
//                .setMaxResults(1)
//                .getSingleResult());
//
//    }
//}
