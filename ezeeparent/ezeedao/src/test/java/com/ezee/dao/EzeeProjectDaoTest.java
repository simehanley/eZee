package com.ezee.dao;

import static com.ezee.model.entity.enums.EzeePaymentType.cash;
import static com.ezee.model.entity.enums.EzeeProjectItemType.expense;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ezee.model.entity.EzeePayee;
import com.ezee.model.entity.project.EzeeProject;
import com.ezee.model.entity.project.EzeeProjectItem;
import com.ezee.model.entity.project.EzeeProjectItemDetail;
import com.ezee.model.entity.project.EzeeProjectPayment;

import junit.framework.TestCase;

public class EzeeProjectDaoTest extends AbstractEzeeDaoTest<EzeeProject> {

	@Autowired
	private EzeePayeeDao payeeDao;

	@Autowired
	private EzeeProjectDao projectDao;

	private final EzeePayee resource = new EzeePayee();

	@Test
	@Override
	public void canPersist() {
		EzeeProject project = new EzeeProject();
		project.setName("NEW PROJECT");
		TestCase.assertNull(project.getId());
		projectDao.save(project);
		TestCase.assertNotNull(project.getId());
	}

	@Test
	@Override
	public void canEdit() {
		EzeeProject project = new EzeeProject();
		project.setName("NEW PROJECT");
		TestCase.assertNull(project.getId());
		projectDao.save(project);
		TestCase.assertNotNull(project.getId());
		TestCase.assertNull(project.getStartDate());
		project.setStartDate("23/10/1970");
		projectDao.save(project);
		EzeeProject persisted = projectDao.get(project.getId(), EzeeProject.class);
		TestCase.assertNotNull(persisted.getStartDate());
	}

	@Test
	@Override
	public void canDelete() {
		EzeeProject project = new EzeeProject();
		project.setName("NEW PROJECT");
		TestCase.assertNull(project.getId());
		projectDao.save(project);
		TestCase.assertNotNull(project.getId());
		projectDao.delete(project);
		EzeeProject persisted = projectDao.get(project.getId(), EzeeProject.class);
		TestCase.assertNull(persisted);
	}

	@Test
	public void canPersistProjectWithUnderlyingItem() {
		payeeDao.save(resource);
		EzeeProject project = new EzeeProject();
		project.setName("COMPLEX PROJECT");
		projectDao.save(project);
		project.addItem(new EzeeProjectItem("PRELIM", resource, null, null));
		project.addItem(new EzeeProjectItem("BUILD WORKS", resource, null, null));
		project.addItem(new EzeeProjectItem("BUILD SERVICES", resource, null, null));
		project.addItem(new EzeeProjectItem("EXTERNAL WORKS", resource, null, null));
		projectDao.save(project);
		EzeeProject persisted = projectDao.get(project.getId(), EzeeProject.class);
		TestCase.assertTrue(persisted.getItems().size() == 4);
	}

	@Test
	public void canEditProjectWithUnderlyingItem() {
		payeeDao.save(resource);
		EzeeProject project = new EzeeProject();
		project.setName("COMPLEX PROJECT");
		projectDao.save(project);

		EzeeProjectItem one = new EzeeProjectItem("PRELIM", resource, null, null);
		EzeeProjectItem two = new EzeeProjectItem("BUILD WORKS", resource, null, null);
		EzeeProjectItem three = new EzeeProjectItem("BUILD SERVICES", resource, null, null);
		EzeeProjectItem four = new EzeeProjectItem("EXTERNAL WORKS", resource, null, null);
		project.addItem(one);
		project.addItem(two);
		project.addItem(three);
		project.addItem(four);
		projectDao.save(project);
		project.getItems().remove(four);
		projectDao.save(project);
		EzeeProject persisted = projectDao.get(project.getId(), EzeeProject.class);
		TestCase.assertTrue(persisted.getItems().size() == 3);
	}

	@Test
	public void canDeleteProjectWithUnderlyingItem() {
		payeeDao.save(resource);
		EzeeProject project = new EzeeProject();
		project.setName("COMPLEX PROJECT");
		projectDao.save(project);
		project.addItem(new EzeeProjectItem("PRELIM", resource, null, null));
		project.addItem(new EzeeProjectItem("BUILD WORKS", resource, null, null));
		project.addItem(new EzeeProjectItem("BUILD SERVICES", resource, null, null));
		project.addItem(new EzeeProjectItem("EXTERNAL WORKS", resource, null, null));
		projectDao.save(project);
		projectDao.delete(project);
		EzeeProject persisted = projectDao.get(project.getId(), EzeeProject.class);
		TestCase.assertNull(persisted);
	}

	@Test
	public void canPersistProjectWithUnderlyingItemDetailAndPayments() {
		payeeDao.save(resource);
		EzeeProject project = new EzeeProject();
		project.setName("COMPLEX PROJECT");
		projectDao.save(project);
		EzeeProjectItem item = new EzeeProjectItem("BUILD WORKS", resource, null, null);
		item.addDetail(new EzeeProjectItemDetail("Demolition/EathWorks", expense, 100., 10., null, null));
		item.addDetail(new EzeeProjectItemDetail("Concrete", expense, 250., 10., null, null));
		item.addPayment(
				new EzeeProjectPayment("1/12/2015", cash, "Partial payment for concrete.", 200., 10., null, null));
		project.addItem(item);
		projectDao.save(project);
		EzeeProject persisted = projectDao.get(project.getId(), EzeeProject.class);
		TestCase.assertTrue(persisted.getItems().size() == 1);
		EzeeProjectItem persistedItem = persisted.getItems().iterator().next();
		TestCase.assertTrue(persistedItem.getDetails().size() == 2);
		TestCase.assertTrue(persistedItem.getPayments().size() == 1);
	}

	@Test
	public void canDeleteProjectWithUnderlyingItemDetailAndPayments() {
		payeeDao.save(resource);
		EzeeProject project = new EzeeProject();
		project.setName("COMPLEX PROJECT");
		projectDao.save(project);
		EzeeProjectItem item = new EzeeProjectItem("BUILD WORKS", resource, null, null);
		item.addDetail(new EzeeProjectItemDetail("Demolition/EathWorks", expense, 100., 10., null, null));
		item.addDetail(new EzeeProjectItemDetail("Concrete", expense, 250., 10., null, null));
		item.addPayment(
				new EzeeProjectPayment("1/12/2015", cash, "Partial payment for concrete.", 200., 10., null, null));
		project.addItem(item);
		projectDao.save(project);
		projectDao.delete(project);
		EzeeProject persisted = projectDao.get(project.getId(), EzeeProject.class);
		TestCase.assertNull(persisted);
	}
}