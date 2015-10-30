package com.ezee.server;

import static com.ezee.client.EzeeInvoiceWebConstants.EZEE_INVOICE_VERSION_PROPERTIES;
import static com.ezee.client.EzeeInvoiceWebConstants.EZEE_INVOICE_WEB_VERSION;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezee.client.EzeeInvoiceService;
import com.ezee.model.entity.EzeeDatabaseEntity;
import com.ezee.model.entity.EzeePayment;
import com.ezee.server.dao.EzeeEntitiesDao;
import com.ezee.server.util.EzeeInvoicePropertyLoader;

/**
 * 
 * @author siborg
 *
 */
public class EzeeInvoiceServiceImpl extends AbstractRemoteService implements EzeeInvoiceService {

	private static final Logger log = LoggerFactory.getLogger(EzeeInvoiceServiceImpl.class);

	private static final long serialVersionUID = -1330259669564042121L;

	private final EzeeInvoicePropertyLoader properties = new EzeeInvoicePropertyLoader(EZEE_INVOICE_VERSION_PROPERTIES);

	@SuppressWarnings("unchecked")
	@Override
	public <T extends EzeeDatabaseEntity> List<T> getEntities(final String clazz) {
		try {
			return getSpringBean(EzeeEntitiesDao.class).getEntities((Class<T>) Class.forName(clazz));
		} catch (ClassNotFoundException e) {
			log.error("Unable to resolve class '" + clazz + "'.", e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends EzeeDatabaseEntity> T saveEntity(final String clazz, final T entity) {
		try {
			return getSpringBean(EzeeEntitiesDao.class).saveEntity((Class<T>) Class.forName(clazz), entity);
		} catch (ClassNotFoundException e) {
			log.error("Unable to resolve class '" + clazz + "'.", e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends EzeeDatabaseEntity> T deleteEntity(final String clazz, final T entity) {
		try {
			return getSpringBean(EzeeEntitiesDao.class).deleteEntity((Class<T>) Class.forName(clazz), entity);
		} catch (ClassNotFoundException e) {
			log.error("Unable to resolve class '" + clazz + "'.", e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends EzeeDatabaseEntity> T getEntity(String clazz, long id) {
		try {
			return getSpringBean(EzeeEntitiesDao.class).getEntity((Class<T>) Class.forName(clazz), id);
		} catch (ClassNotFoundException e) {
			log.error("Unable to resolve class '" + clazz + "'.", e);
		}
		return null;
	}

	@Override
	public List<EzeePayment> getOutstandingCheques(final Long premisesId) {
		return getSpringBean(EzeeEntitiesDao.class).getOutstandingCheques(premisesId);
	}

	@Override
	public String getVersion() {
		return properties.getProperty(EZEE_INVOICE_WEB_VERSION);
	}
}