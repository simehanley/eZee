package com.ezee.dao;

import static com.ezee.common.EzeeCommonConstants.EMPTY_STRING;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ezee.model.entity.lease.EzeeLease;
import com.ezee.model.entity.lease.EzeeLeaseCategory;
import com.ezee.model.entity.lease.EzeeLeasePremises;
import com.ezee.model.entity.lease.EzeeLeaseTenant;

import junit.framework.TestCase;

public class EzeeLeaseDaoTest extends AbstractEzeeDaoTest<EzeeLease> {

	private EzeeLeasePremises premises = new EzeeLeasePremises("PREMISES", EMPTY_STRING, EMPTY_STRING, EMPTY_STRING,
			EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING,
			EMPTY_STRING, EMPTY_STRING);
	private EzeeLeaseTenant tenant = new EzeeLeaseTenant("TENANT", EMPTY_STRING, EMPTY_STRING, EMPTY_STRING,
			EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING,
			EMPTY_STRING, EMPTY_STRING);

	private EzeeLeaseCategory category = new EzeeLeaseCategory("CATEGORY", EMPTY_STRING, EMPTY_STRING, EMPTY_STRING,
			EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING,
			EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING,
			EMPTY_STRING);

	@Autowired
	private EzeeLeaseDao dao;

	@Test
	@Override
	public void canPersist() {
		EzeeLease lease = new EzeeLease("23/10/2016", "23/10/2018", EMPTY_STRING, 100.0, "1,2,15", null, tenant,
				premises, category, null, false, false, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING);
		TestCase.assertNull(lease.getId());
		dao.save(lease);
		TestCase.assertNotNull(lease.getId());
		TestCase.assertNotNull(lease.getPremises().getId());
		TestCase.assertNotNull(lease.getTenant().getId());
		TestCase.assertNotNull(lease.getCategory().getId());
	}

	@Test
	@Override
	public void canEdit() {
		EzeeLease lease = new EzeeLease("23/10/2016", "23/10/2018", EMPTY_STRING, 100.0, "1,2,15", null, tenant,
				premises, category, null, false, false, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING);
		TestCase.assertNull(lease.getId());
		dao.save(lease);
		TestCase.assertNotNull(lease.getId());
		lease.setLeaseEnd("23/10/2019");
		dao.save(lease);
		EzeeLease persisted = dao.get(lease.getId(), EzeeLease.class);
		TestCase.assertEquals("23/10/2019", persisted.getLeaseEnd());
	}

	@Test
	@Override
	public void canDelete() {
		EzeeLease lease = new EzeeLease("23/10/2016", "23/10/2018", EMPTY_STRING, 100.0, "1,2,15", null, tenant,
				premises, category, null, false, false, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING);
		TestCase.assertNull(lease.getId());
		dao.save(lease);
		TestCase.assertNotNull(lease.getId());
		dao.delete(lease);
		EzeeLease persisted = dao.get(lease.getId(), EzeeLease.class);
		TestCase.assertNull(persisted);
	}
}