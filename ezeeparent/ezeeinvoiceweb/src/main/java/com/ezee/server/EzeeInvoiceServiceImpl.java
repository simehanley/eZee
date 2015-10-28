package com.ezee.server;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezee.client.EzeeInvoiceService;
import com.ezee.model.entity.EzeeDatabaseEntity;
import com.ezee.server.dao.EzeeEntitiesDao;

/**
 * 
 * @author siborg
 *
 */
public class EzeeInvoiceServiceImpl extends AbstractRemoteService implements EzeeInvoiceService {

	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(EzeeInvoiceServiceImpl.class);

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
}