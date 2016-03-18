package com.ezee.dao;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_CLASS;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ezee.model.entity.EzeeDatabaseEntity;

/**
 * 
 * @author siborg
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:ezeedao-test-context.xml")
@DirtiesContext(classMode = AFTER_CLASS)
public abstract class AbstractEzeeDaoTest<T extends EzeeDatabaseEntity> {

	@Autowired
	private ApplicationContext ctx;

	public ApplicationContext getCtx() {
		return ctx;
	}

	public abstract void canPersist();

	public abstract void canEdit();

	public abstract void canDelete();
}
