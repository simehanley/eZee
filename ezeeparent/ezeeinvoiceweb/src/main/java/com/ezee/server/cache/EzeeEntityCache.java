package com.ezee.server.cache;

import java.util.HashMap;

import com.ezee.model.entity.EzeeDatabaseEntity;

/**
 * 
 * @author siborg
 *
 */
public class EzeeEntityCache<Key, T extends EzeeDatabaseEntity> extends HashMap<Key, T> {

	private static final long serialVersionUID = 5433230624742772044L;
}