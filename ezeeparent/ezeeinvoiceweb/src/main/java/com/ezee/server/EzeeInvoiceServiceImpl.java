package com.ezee.server;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezee.client.EzeeInvoiceService;
import com.ezee.common.exception.EzeeException;
import com.ezee.model.entity.EzeeDatabaseEntity;
import com.ezee.model.entity.EzeeDebtAgeRule;
import com.ezee.model.entity.EzeePayment;
import com.ezee.server.dao.EzeeEntitiesDao;
import com.ezee.server.util.EzeeDueDateCalculator;

/**
 * 
 * @author siborg
 *
 */
public class EzeeInvoiceServiceImpl extends AbstractRemoteService implements EzeeInvoiceService {

	private static final Logger log = LoggerFactory.getLogger(EzeeInvoiceServiceImpl.class);

	private static final long serialVersionUID = -1330259669564042121L;

	@SuppressWarnings("unchecked")
	@Override
	public <T extends EzeeDatabaseEntity> List<T> getEntities(final String clazz) {
		try {
			return getSpringBean(EzeeEntitiesDao.class).getEntities((Class<T>) Class.forName(clazz));
		} catch (ClassNotFoundException e) {
			log.error("Unable to resolve class '" + clazz + "'.", e);
			throw new EzeeException("Unable to resolve class '" + clazz + "'.", e);
		} catch (Throwable t) {
			log.error("Error retireving entities.", t);
			throw new EzeeException("Error retireving entities.", t);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends EzeeDatabaseEntity> T saveEntity(final String clazz, final T entity) {
		try {
			return getSpringBean(EzeeEntitiesDao.class).saveEntity((Class<T>) Class.forName(clazz), entity);
		} catch (ClassNotFoundException e) {
			log.error("Unable to resolve class '" + clazz + "'.", e);
			throw new EzeeException("Unable to resolve class '" + clazz + "'.", e);
		} catch (Throwable t) {
			log.error("Error saving entity " + entity + ".", t);
			throw new EzeeException("Error saving entity " + entity + ".", t);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends EzeeDatabaseEntity> T deleteEntity(final String clazz, final T entity) {
		try {
			return getSpringBean(EzeeEntitiesDao.class).deleteEntity((Class<T>) Class.forName(clazz), entity);
		} catch (ClassNotFoundException e) {
			log.error("Unable to resolve class '" + clazz + "'.", e);
			throw new EzeeException("Unable to resolve class '" + clazz + "'.", e);
		} catch (Throwable t) {
			log.error("Error deleting entity " + entity + ".", t);
			throw new EzeeException("Error deleting entity " + entity + ".", t);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends EzeeDatabaseEntity> T getEntity(String clazz, long id) {
		try {
			return getSpringBean(EzeeEntitiesDao.class).getEntity((Class<T>) Class.forName(clazz), id);
		} catch (ClassNotFoundException e) {
			log.error("Unable to resolve class '" + clazz + "'.", e);
			throw new EzeeException("Unable to resolve class '" + clazz + "'.", e);
		} catch (Throwable t) {
			log.error("Error getting entity for id " + id + ".", t);
			throw new EzeeException("Error getting entity for id " + id + ".", t);
		}
	}

	@Override
	public List<EzeePayment> getOutstandingCheques(final Long premisesId) {
		return getSpringBean(EzeeEntitiesDao.class).getOutstandingCheques(premisesId);
	}

	@Override
	public String calculateDueDate(final EzeeDebtAgeRule rule, final String today) {
		return getSpringBean(EzeeDueDateCalculator.class).calculate(rule, today);
	}
}