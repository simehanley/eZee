package com.ezee.dao;

import static com.ezee.common.EzeeCommonConstants.EMPTY_STRING;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ezee.model.entity.EzeeUser;

import junit.framework.TestCase;

/**
 * 
 * @author siborg
 *
 */
public class EzeeUserDaoTest extends AbstractEzeeDaoTest {

	@Autowired
	private EzeeUserDao userDao;

	@Test
	public void canPersistAnEzeeUser() {
		EzeeUser user = new EzeeUser("Simon", "Stanley", "sstanley", "password", "sime.stanley@coocoomail.com",
				new Date(), new Date());
		userDao.save(user);
		TestCase.assertNotNull(user.getId());
		TestCase.assertNotSame("password", user.getPassword());
		TestCase.assertTrue(userDao.geEncryptor().checkPassword("password", user.getPassword()));
	}

	@Test
	public void canDetermineExistenceOfAnExistingUser() {
		EzeeUser user = new EzeeUser("Simon", "Hanley", "hanleys", "password", "sime.@coocoomail.com", new Date(),
				new Date());
		userDao.save(user);
		EzeeUser existing = userDao.get("hanleys", EMPTY_STRING);
		TestCase.assertNotNull(existing);
	}
}
