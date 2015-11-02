package com.ezee.dao.impl;

import java.util.List;

import com.ezee.dao.EzeeDebtAgeRuleDao;
import com.ezee.model.entity.EzeeDebtAgeRule;

/**
 * 
 * @author siborg
 *
 */
public class EzeeDebtAgeRuleDaoImpl extends EzeeBaseDaoImpl<EzeeDebtAgeRule> implements EzeeDebtAgeRuleDao {

	@Override
	public EzeeDebtAgeRule get(long id) {
		return super.get(id, EzeeDebtAgeRule.class);
	}

	@Override
	public List<EzeeDebtAgeRule> get() {
		return super.get(EzeeDebtAgeRule.class);
	}
}