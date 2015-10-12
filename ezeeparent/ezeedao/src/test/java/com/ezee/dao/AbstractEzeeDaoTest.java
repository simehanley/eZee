package com.ezee.dao;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 
 * @author siborg
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:ezeedao-test-context.xml")
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public abstract class AbstractEzeeDaoTest {

	@Autowired
	private ApplicationContext ctx;

	public ApplicationContext getCtx() {
		return ctx;
	}
}
