package com.ezee.dao.impl;

import static com.ezee.model.entity.EzeeEntityConstants.NULL_ID;
import static java.util.Collections.emptyList;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;
import static org.springframework.util.CollectionUtils.isEmpty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.hql.internal.CollectionSubqueryFactory;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.ezee.dao.EzeeBaseDao;
import com.ezee.model.entity.EzeeDatabaseEntity;
import com.ezee.model.entity.filter.EzeeEntityFilter;

/**
 * 
 * @author siborg
 *
 */
public abstract class EzeeBaseDaoImpl<T extends EzeeDatabaseEntity> extends HibernateDaoSupport
		implements EzeeBaseDao<T> {

	@Transactional(propagation = REQUIRED, readOnly = false)
	public void merge(final T entity) {
		if (entity != null) {
			getHibernateTemplate().merge(entity);
		}
	}

	@Transactional(propagation = REQUIRED, readOnly = false)
	public void save(final T entity) {
		if (entity != null) {
			getHibernateTemplate().saveOrUpdate(entity);
		}
	}

	@Transactional(propagation = REQUIRED, readOnly = false)
	public void delete(final T entity) {
		if (entity != null && entity.getId() != NULL_ID) {
			getHibernateTemplate().delete(entity);
		}
	}

	public T get(final long id, final Class<T> entityClazz) {
		if (entityClazz != null) {
			return getHibernateTemplate().get(entityClazz, id);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<T> get(final Class<T> entity) {
		if (entity != null) {
			DetachedCriteria criteria = DetachedCriteria.forClass(entity);
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			return (List<T>) getHibernateTemplate().findByCriteria(criteria);
		}
		return null;
	}

	@Override
	public List<T> get(final EzeeEntityFilter<T> filter, final Class<T> entityClazz) {
		List<T> all = get(entityClazz);
		if (!isEmpty(all)) {
			if (filter != null) {
				List<T> filtered = new ArrayList<>();
				for (T entity : all) {
					if (filter.include(entity)) {
						filtered.add(entity);
					}
				}
				return filtered;
			}
			return all;
		}
		return emptyList();
	}

	protected void deleteMappings(final T entity, final String mappingQuery) {
		getSessionFactory().getCurrentSession().getNamedQuery(mappingQuery).setLong("id", entity.getId())
				.executeUpdate();
	}

	public abstract T get(long id);

	public abstract List<T> get();
}