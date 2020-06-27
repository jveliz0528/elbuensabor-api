package com.delivery.demo.specifications;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class SearchSpecification <E> {
    public Specification<E> findByProperty(String propertyName, String propertyValue) {
        return new Specification<E>() {
            public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query,
                                         CriteriaBuilder builder) {
                return builder.like(root.get(propertyName).as(String.class), "%" + propertyValue + "%");
            }
        };
    }

    public Specification<E> findByUid(String uidValue) {
        return new Specification<E>() {
            public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query,
                                         CriteriaBuilder builder) {
                return builder.like(root.get("uid").as(String.class), uidValue);
            }
        };
    }

    public Specification<E> isNotDeleted() {
        return new Specification<E>() {
            public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query,
                                         CriteriaBuilder builder) {
                return builder.equal(root.get("eliminado"), false);
            }
        };
    }

    public Specification<E> isNotHidden() {
        return new Specification<E>() {
            public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query,
                                         CriteriaBuilder builder) {
                return builder.equal(root.get("oculto"), false);
            }
        };
    }

    public Specification<E> esBebida() {
        return new Specification<E>() {
            public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query,
                                         CriteriaBuilder builder) {
                return builder.equal(root.get("esInsumo"), false);
            }
        };
    }

    public Specification<E> rubroPadre(String filter) {
        return new Specification<E>() {
            public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query,
                                         CriteriaBuilder builder) {
                return builder.like(root.join("rubro").join("rubroPadre").get("denominacion"), "%"+ filter +"%");
            }
        };
    }

    public Specification<E> findByForeignId(String columnName, Long id){
        return new Specification<E>() {
            public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query,
                                         CriteriaBuilder builder) {
                return builder.equal(root.join(columnName).get("id"), id);
            }
        };
    }

    public Specification<E> findByForeignAttribute(String columnName, String propertyName, String propertyValue) {
        return new Specification<E>() {
            public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query,
                                         CriteriaBuilder builder) {
                return builder.like(root.join(columnName).get(propertyName), "%" + propertyValue + "%");
            }
        };
    }

    public Specification<E> findByEstado(String propertyValue) {
        return new Specification<E>() {
            public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query,
                                         CriteriaBuilder builder) {
                return builder.like(root.join("estado").get("denominacion"), "%" + propertyValue + "%");
            }
        };
    }

    public Specification<E> findBetweenDates(String fechaInicio, String fechaFin){
        return  new Specification<E>() {
            public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                return builder.between(root.get("fecha"), fechaInicio, fechaFin);
            }
        };
    }

}
