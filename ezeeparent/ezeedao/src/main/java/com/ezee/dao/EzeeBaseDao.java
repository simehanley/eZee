package com.ezee.dao;

import static com.ezee.model.entity.EzeeEntityConstants.NULL_ID;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.ezee.model.entity.EzeeDatabaseEntity;

/**
 * 
 * @author siborg
 *
 */
public class EzeeBaseDao<T extends EzeeDatabaseEntity> extends HibernateDaoSupport {

	@Transactional(propagation = REQUIRED, readOnly = false)
	public void save(final T entity) {
		if (entity != null) {
			entity.setUpdated(new Date());
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
		if (id != NULL_ID && entityClazz != null) {
			return getHibernateTemplate().get(entityClazz, id);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<T> get(final Class<T> entity) {
		if (entity != null) {
			DetachedCriteria criteria = DetachedCriteria.forClass(entity);
			return (List<T>) getHibernateTemplate().findByCriteria(criteria);
		}
		return null;
	}
}