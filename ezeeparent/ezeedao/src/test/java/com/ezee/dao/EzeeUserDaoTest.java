package com.ezee.dao;

import static com.ezee.common.EzeeCommonConstants.EMPTY_STRING;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ezee.model.entity.EzeeUser;

import junit.framework.TestCase;

/**
 * 
 * @author siborg
 *
 */
public class EzeeUserDaoTest extends AbstractEzeeDaoTest<EzeeUser> {

	@Autowired
	private EzeeUserDao userDao;

	@Override
	@Test
	public void canPersist() {
		EzeeUser user = new EzeeUser("Simon", "Stanley", "sstanley", "password", "sime.stanley@coocoomail.com",
				"5/11/2015", null);
		userDao.save(user);
		TestCase.assertNotNull(user.getId());
		TestCase.assertNotSame("password", user.getPassword());
		TestCase.assertTrue(userDao.geEncryptor().checkPassword("password", user.getPassword()));
	}

	@Override
	@Test
	public void canEdit() {
		EzeeUser user = new EzeeUser("Simon", "Stanley", "sstanley", "password", "sime.stanley@coocoomail.com",
				"5/11/2015", null);
		userDao.save(user);
		user.setFirstname("Jerry");
		userDao.save(user);
		EzeeUser persisted = userDao.get(user.getId(), EzeeUser.class);
		TestCase.assertEquals("Jerry", persisted.getFirstname());
	}

	@Override
	@Test
	public void canDelete() {
		EzeeUser user = new EzeeUser("Simon", "Stanley", "sstanley", "password", "sime.stanley@coocoomail.com",
				"5/11/2015", null);
		userDao.save(user);
		EzeeUser persisted = userDao.get(user.getId(), EzeeUser.class);
		TestCase.assertNotNull(persisted);
		userDao.delete(persisted);
		persisted = userDao.get(user.getId(), EzeeUser.class);
		TestCase.assertNull(persisted);
	}

	@Test
	public void canDetermineExistenceOfAnExistingUser() {
		EzeeUser user = new EzeeUser("Simon", "Hanley", "hanleys", "password", "sime.@coocoomail.com", "5/11/2015",
				null);
		userDao.save(user);
		EzeeUser existing = userDao.get("hanleys", EMPTY_STRING);
		TestCase.assertNotNull(existing);
	}
}