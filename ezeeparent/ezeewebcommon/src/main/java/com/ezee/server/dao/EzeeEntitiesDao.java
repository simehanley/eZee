package com.ezee.server.dao;

import static com.ezee.common.collections.EzeeCollectionUtils.isEmpty;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

import org.hibernate.collection.spi.PersistentCollection;

import com.ezee.dao.EzeePaymentDao;
import com.ezee.model.entity.EzeeDatabaseEntity;
import com.ezee.model.entity.EzeeInvoice;
import com.ezee.model.entity.EzeePayment;
import com.ezee.model.entity.lease.EzeeLease;
import com.ezee.model.entity.project.EzeeProject;
import com.ezee.model.entity.project.EzeeProjectItem;

public class EzeeEntitiesDao {

	private final EzeeEntitiesDaoPostProcessor postprocessor = new EzeeEntitiesDaoPostProcessor();

	private final Map<Class<?>, EzeeEntityDao<?>> daoCache = new ConcurrentHashMap<>();

	public EzeeEntitiesDao(final List<EzeeEntityDao<?>> entityDaos) {
		for (EzeeEntityDao<?> dao : entityDaos) {
			daoCache.put(dao.getClazz(), dao);
		}
	}

	public <T extends EzeeDatabaseEntity> T getEntity(final Class<T> clazz, final long id) {
		if (containsDao(clazz)) {
			T entity = getDao(clazz).getDao().get(id, clazz);
			if (entity != null) {
				postprocessor.postProcessEntity(entity);
			}
			return entity;
		}
		return null;
	}

	public <T extends EzeeDatabaseEntity> List<T> getEntities(final Class<T> clazz) {
		if (containsDao(clazz)) {
			List<T> entities = getDao(clazz).getDao().get(clazz);
			if (!isEmpty(entities)) {
				for (T entity : entities) {
					postprocessor.postProcessEntity(entity);
				}
			}
			return entities;
		}
		return new ArrayList<>();
	}

	public <T extends EzeeDatabaseEntity> T saveEntity(final Class<T> clazz, final T entity) {
		if (containsDao(clazz)) {
			getDao(clazz).getDao().save(entity);
			postprocessor.postProcessEntity(entity);
			return entity;
		}
		return null;
	}

	public <T extends EzeeDatabaseEntity> T deleteEntity(final Class<T> clazz, final T entity) {
		if (containsDao(clazz)) {
			getDao(clazz).getDao().delete(entity);
			postprocessor.postProcessEntity(entity);
			return entity;
		}
		return null;
	}

	public List<EzeePayment> getOutstandingCheques(final Long premisesId) {
		EzeePaymentDao dao = (EzeePaymentDao) getDao(EzeePayment.class).getDao();
		List<EzeePayment> payments = dao.getOutstandingCheques(premisesId);
		if (!isEmpty(payments)) {
			for (EzeePayment payment : payments) {
				postprocessor.postProcessEntity(payment);
			}
		}
		return payments;
	}

	@SuppressWarnings("unchecked")
	private <T extends EzeeDatabaseEntity> EzeeEntityDao<T> getDao(final Class<T> clazz) {
		return (EzeeEntityDao<T>) daoCache.get(clazz);
	}

	private <T> boolean containsDao(final Class<T> clazz) {
		return daoCache.containsKey(clazz);
	}

	private static class EzeeEntitiesDaoPostProcessor {

		public <T extends EzeeDatabaseEntity> void postProcessEntity(final T entity) {
			if (entity instanceof EzeePayment) {
				postProcessPayment((EzeePayment) entity);
			} else if (entity instanceof EzeeInvoice) {
				postProcessInvoice((EzeeInvoice) entity);
			} else if (entity instanceof EzeeProject) {
				postProcessProject((EzeeProject) entity);
			} else if (entity instanceof EzeeLease) {
				postProcessLease((EzeeLease) entity);
			}
		}

		/**
		 * Post process {@link EzeePayment} to remove non serializable
		 * {@link PersistentCollection}
		 */
		private void postProcessPayment(final EzeePayment payment) {
			if (!isEmpty(payment.getInvoices())) {
				payment.setInvoices(new LinkedHashSet<>(payment.getInvoices()));
				for (EzeeInvoice invoice : payment.getInvoices()) {
					postProcessInvoice(invoice);
				}
			}
		}

		/**
		 * Post process {@link EzeeInvoice} to remove potentially large binary
		 * file object
		 */
		private void postProcessInvoice(EzeeInvoice invoice) {
			invoice.setFile(null);
		}

		/**
		 * Post process {@link EzeeProject} to remove non serializable
		 * {@link PersistentCollection}
		 */
		private void postProcessProject(final EzeeProject project) {
			if (project.getItems() != null) {
				project.setItems(new LinkedHashSet<>(project.getItems()));
				for (EzeeProjectItem item : project.getItems()) {
					if (item.getDetails() != null) {
						item.setDetails(new LinkedHashSet<>(item.getDetails()));
					}
					if (item.getPayments() != null) {
						item.setPayments(new LinkedHashSet<>(item.getPayments()));
					}
				}
			}
		}

		private void postProcessLease(final EzeeLease lease) {
			if (lease.getIncidentals() != null) {
				lease.setIncidentals(new HashSet<>(lease.getIncidentals()));
			}
			if (lease.getMetaData() != null) {
				lease.setMetaData(new TreeSet<>(lease.getMetaData()));
			}
			if (lease.getNotes() != null) {
				lease.setNotes(new TreeSet<>(lease.getNotes()));
			}
			if (lease.getFiles() != null) {
				lease.setFiles(new TreeSet<>(lease.getFiles()));
			}
			lease.setEdited(false);
		}
	}
}