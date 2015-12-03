package com.ezee.server.dao;

import static com.ezee.common.collections.EzeeCollectionUtils.isEmpty;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.hibernate.collection.spi.PersistentCollection;

import com.ezee.dao.EzeePaymentDao;
import com.ezee.model.entity.EzeeDatabaseEntity;
import com.ezee.model.entity.EzeeInvoice;
import com.ezee.model.entity.EzeePayment;

public class EzeeEntitiesDao {

	private final EzeeEntitiesDaoPostProcessor postprocessor = new EzeeEntitiesDaoPostProcessor();

	private final Map<Class<?>, EzeeEntityDao<?>> daoCache = new ConcurrentHashMap<>();

	public EzeeEntitiesDao(final List<EzeeEntityDao<?>> entityDaos) {
		for (EzeeEntityDao<?> dao : entityDaos) {
			daoCache.put(dao.getClazz(), dao);
		}
	}

	public <T extends EzeeDatabaseEntity> T getEntity(final Class<T> clazz, final long id) {
		T entity = getDao(clazz).getDao().get(id, clazz);
		if (entity != null) {
			postprocessor.postProcessEntity(entity);
		}
		return entity;
	}

	public <T extends EzeeDatabaseEntity> List<T> getEntities(final Class<T> clazz) {
		List<T> entities = getDao(clazz).getDao().get(clazz);
		if (!isEmpty(entities)) {
			for (T entity : entities) {
				postprocessor.postProcessEntity(entity);
			}
		}
		return entities;
	}

	public <T extends EzeeDatabaseEntity> T saveEntity(final Class<T> clazz, final T entity) {
		getDao(clazz).getDao().save(entity);
		postprocessor.postProcessEntity(entity);
		return entity;
	}

	public <T extends EzeeDatabaseEntity> T deleteEntity(final Class<T> clazz, final T entity) {
		getDao(clazz).getDao().delete(entity);
		postprocessor.postProcessEntity(entity);
		return entity;
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

	private static class EzeeEntitiesDaoPostProcessor {

		public <T extends EzeeDatabaseEntity> void postProcessEntity(final T entity) {
			if (entity instanceof EzeePayment) {
				postProcessPayment((EzeePayment) entity);
			} else if (entity instanceof EzeeInvoice) {
				postProcessInvoice((EzeeInvoice) entity);
			}
		}

		/**
		 * Post process {@link EzeePayment} to remove non serializable
		 * {@link PersistentCollection}
		 */
		public void postProcessPayment(final EzeePayment payment) {
			if (!isEmpty(payment.getInvoices())) {
				payment.setInvoices(new HashSet<>(payment.getInvoices()));
				for (EzeeInvoice invoice : payment.getInvoices()) {
					postProcessInvoice(invoice);
				}
			}
		}

		/**
		 * Post process {@link EzeeInvoice} to remove potentially large binary
		 * file object
		 */
		public void postProcessInvoice(EzeeInvoice invoice) {
			invoice.setFile(null);
		}
	}
}