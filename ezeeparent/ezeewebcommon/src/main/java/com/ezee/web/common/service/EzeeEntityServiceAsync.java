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

	<T extends EzeeDatabaseEntity> void getEntity(String clazz, long id, AsyncCallback<T> entity);

	<T extends EzeeDatabaseEntity> void saveEntity(String clazz, T entity, AsyncCallback<T> result);

	<T extends EzeeDatabaseEntity> void saveEntities(String clazz, List<T> entities, AsyncCallback<List<T>> result);

	<T extends EzeeDatabaseEntity> void deleteEntity(String clazz, T entity, AsyncCallback<T> result);

	<T extends EzeeDatabaseEntity> void getEntities(String clazz, AsyncCallback<List<T>> entities);
}