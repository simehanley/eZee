package com.ezee.web.common.service;

import java.util.List;

import com.ezee.model.entity.EzeeDatabaseEntity;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * 
 * @author siborg
 *
 */
public interface EzeeEntityServiceAsync {

	<T extends EzeeDatabaseEntity> void getEntity(String clazz, long id, AsyncCallback<List<T>> entities);

	<T extends EzeeDatabaseEntity> void saveEntity(String clazz, T entity, AsyncCallback<T> result);

	<T extends EzeeDatabaseEntity> void deleteEntity(String clazz, T entity, AsyncCallback<T> result);

	<T extends EzeeDatabaseEntity> void getEntities(String clazz, AsyncCallback<List<T>> entity);
}
