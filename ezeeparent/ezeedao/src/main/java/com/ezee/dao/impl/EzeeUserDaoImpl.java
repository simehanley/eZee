package com.ezee.dao.impl;

import static com.ezee.common.EzeeCommonConstants.ZERO;
import static com.ezee.common.collections.EzeeCollectionUtils.isEmpty;
import static com.ezee.common.string.EzeeStringUtils.hasLength;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.jasypt.util.password.PasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.ezee.dao.EzeeUserDao;
import com.ezee.model.entity.EzeeUser;

public class EzeeUserDaoImpl extends EzeeBaseDaoImpl<EzeeUser> implements EzeeUserDao {

	@Autowired
	private PasswordEncryptor encryptor;

	@Override
	public EzeeUser get(long id) {
		return super.get(id, EzeeUser.class);
	}

	@Override
	public List<EzeeUser> get() {
		return get(EzeeUser.class);
	}

	@Override
	@Transactional(propagation = REQUIRED, readOnly = false)
	public void save(final EzeeUser entity) {
		if (hasLength(entity.getPassword())) {
			entity.setPassword(encryptor.encryptPassword(entity.getPassword()));
		}
		super.save(entity);
	}

	@Override
	public PasswordEncryptor geEncryptor() {
		return encryptor;
	}

	@SuppressWarnings("unchecked")
	@Override
	public EzeeUser get(final String username, final String email) {
		DetachedCriteria criteria = DetachedCriteria.forClass(EzeeUser.class);
		criteria.add(Restrictions.or(Restrictions.eq("username", username), Restrictions.eq("email", email)));
		List<EzeeUser> users = (List<EzeeUser>) getHibernateTemplate().findByCriteria(criteria);
		if (!isEmpty(users)) {
			return users.get(ZERO);
		}
		return null;
	}
}