package com.ezee.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.ezee.model.entity.enums.EzeeDebtAgeRuleIntervalType;

/**
 * 
 * @author siborg
 *
 */
@Entity
@Table(name = "EZEE_DEBT_AGE_RULE")
public class EzeeDebtAgeRule extends EzeeDatabaseEntity {

	private static final long serialVersionUID = 1967457003204614100L;

	@Column(name = "NAME")
	private String name;

	@Column(name = "EOM")
	private boolean eom = Boolean.TRUE;

	@Column(name = "INTERVAL")
	private int interval;

	@Enumerated(EnumType.STRING)
	@Column(name = "INTERVAL_TYPE")
	private EzeeDebtAgeRuleIntervalType type;
}