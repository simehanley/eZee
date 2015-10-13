package com.ezee.server.cache;

import static org.springframework.util.CollectionUtils.isEmpty;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ezee.dao.EzeeBaseDao;
import com.ezee.model.entity.EzeeDatabaseEntity;
import com.ezee.model.entity.EzeeDebtAgeRule;
import com.ezee.model.entity.EzeeInvoice;
import com.ezee.model.entity.EzeePayee;
import com.ezee.model.entity.EzeePayer;
import com.ezee.model.entity.EzeePayment;
import com.ezee.server.dao.EzeeDaoFactory;

/**
 * 
 * @author siborg
 *
 */
public class EzeeEntitiesCache {

	private final Logger log = LoggerFactory.getLogger(EzeeEntitiesCache.class);

	@Autowired
	private EzeeDaoFactory daoFactory;

	private final EzeeEntityCache<Long, EzeePayer> payers = new EzeeEntityCache<>();
	private final EzeeEntityCache<Long, EzeePayee> payees = new EzeeEntityCache<>();
	private final EzeeEntityCache<Long, EzeePayment> payments = new EzeeEntityCache<>();
	private final EzeeEntityCache<Long, EzeeInvoice> invoices = new EzeeEntityCache<>();
	private final EzeeEntityCache<Long, EzeeDebtAgeRule> debtrules = new EzeeEntityCache<>();

	@PostConstruct
	public void init() {
		log.info("Initialising ezee entities cache.");
		loadEntities(daoFactory.getPayerDao(), EzeePayer.class);
		loadEntities(daoFactory.getPayeeDao(), EzeePayee.class);
		loadEntities(daoFactory.getPaymentDao(), EzeePayment.class);
		loadEntities(daoFactory.getInvoiceDao(), EzeeInvoice.class);
		loadEntities(daoFactory.getDebtAgeRuleDao(), EzeeDebtAgeRule.class);
		log.info("Ezee entities cache fully initialised.");
	}

	public final <T extends EzeeDatabaseEntity> T get(final long id, final Class<T> clazz) {
		return getCache(clazz).get(id);
	}

	public <T extends EzeeDatabaseEntity> void put(final Class<T> clazz, final T entity) {
		getCache(clazz).put(entity.getId(), entity);
	}

	public <T extends EzeeDatabaseEntity> T remove(final long id, final Class<T> clazz) {
		return getCache(clazz).remove(id);
	}

	@SuppressWarnings("unchecked")
	private <Key, T extends EzeeDatabaseEntity> void loadEntities(final EzeeBaseDao<T> dao, Class<T> clazz) {
		List<T> entities = dao.get(clazz);
		EzeeEntityCache<Key, T> cache = getCache(clazz);
		if (!isEmpty(entities)) {
			entities.forEach(entity -> cache.put((Key) entity.getId(), entity));
		}
	}

	@SuppressWarnings("unchecked")
	private <Key, T extends EzeeDatabaseEntity> EzeeEntityCache<Key, T> getCache(final Class<T> clazz) {

		if (clazz.equals(EzeePayer.class)) {
			return (EzeeEntityCache<Key, T>) payers;
		} else if (clazz.equals(EzeePayee.class)) {
			return (EzeeEntityCache<Key, T>) payees;
		} else if (clazz.equals(EzeePayment.class)) {
			return (EzeeEntityCache<Key, T>) payments;
		} else if (clazz.equals(EzeeInvoice.class)) {
			return (EzeeEntityCache<Key, T>) invoices;
		} else {
			return (EzeeEntityCache<Key, T>) debtrules;
		}
	}
}