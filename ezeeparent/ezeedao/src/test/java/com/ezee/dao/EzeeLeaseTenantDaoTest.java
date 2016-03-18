package com.ezee.dao;

import static com.ezee.common.EzeeCommonConstants.EMPTY_STRING;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ezee.model.entity.lease.EzeeLeaseTenant;

import junit.framework.TestCase;

public class EzeeLeaseTenantDaoTest extends AbstractEzeeDaoTest<EzeeLeaseTenant> {

	@Autowired
	private EzeeLeaseTenantDao dao;

	@Test
	@Override
	public void canPersist() {
		EzeeLeaseTenant tenant = new EzeeLeaseTenant("TEST_LEASE_TENANT", EMPTY_STRING, EMPTY_STRING, EMPTY_STRING,
				EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING,
				"23/10/1970", null);
		TestCase.assertNull(tenant.getId());
		dao.save(tenant);
		TestCase.assertNotNull(tenant.getId());
	}

	@Test
	@Override
	public void canEdit() {
		EzeeLeaseTenant tenant = new EzeeLeaseTenant("TEST_LEASE_TENANT", EMPTY_STRING, EMPTY_STRING, EMPTY_STRING,
				EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING,
				"23/10/1970", null);
		TestCase.assertNull(tenant.getId());
		dao.save(tenant);
		TestCase.assertNotNull(tenant.getId());
		tenant.setUpdated("15/11/2019");
		dao.save(tenant);
		EzeeLeaseTenant persisted = dao.get(tenant.getId(), EzeeLeaseTenant.class);
		TestCase.assertNotNull(persisted.getUpdated());
		TestCase.assertEquals("15/11/2019", persisted.getUpdated());
	}

	@Test
	@Override
	public void canDelete() {
		EzeeLeaseTenant tenant = new EzeeLeaseTenant("TEST_LEASE_TENANT", EMPTY_STRING, EMPTY_STRING, EMPTY_STRING,
				EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING,
				"23/10/1970", null);
		TestCase.assertNull(tenant.getId());
		dao.save(tenant);
		TestCase.assertNotNull(tenant.getId());
		dao.delete(tenant);
		EzeeLeaseTenant persisted = dao.get(tenant.getId(), EzeeLeaseTenant.class);
		TestCase.assertNull(persisted);
	}
}