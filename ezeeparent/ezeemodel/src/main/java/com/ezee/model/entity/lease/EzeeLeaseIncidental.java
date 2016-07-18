package com.ezee.model.entity.lease;

import static com.ezee.common.numeric.EzeeNumericUtils.round;
import static com.ezee.model.entity.EzeeEntityConstants.NULL_ID;
import static com.ezee.model.entity.lease.EzeeLeaseConstants.MONTHS_PER_YEAR;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ezee.model.entity.EzeeDatabaseEntity;
import com.google.gwt.user.client.rpc.IsSerializable;

@Entity
@Table(name = "EZEE_LEASE_INCIDENTAL")
public class EzeeLeaseIncidental extends EzeeDatabaseEntity implements IsSerializable {

	private static final long serialVersionUID = 5895008407914823958L;

	/** name of the incidental **/
	@Column(name = "NAME")
	private String name;

	/** yearly amount of the incidental **/
	@Column(name = "AMOUNT")
	private double amount;

	/** tax rate applied to the incidental **/
	@Column(name = "TAX_RATE")
	private double taxRate;

	/** percentage increase (yearly) applied to the incidental **/
	@Column(name = "PERCENTAGE")
	private double percentage;

	/** myob account number **/
	@Column(name = "ACCOUNT_NUMBER")
	private String account;

	public EzeeLeaseIncidental() {
		super();
	}

	public EzeeLeaseIncidental(final String name, final double amount, final double taxRate, final double percentage,
			final String account) {
		this(NULL_ID, name, amount, taxRate, percentage, account);
	}

	public EzeeLeaseIncidental(final Long id, final String name, final double amount, final double taxRate,
			final double percentage, final String account) {
		super(id);
		this.name = name;
		this.amount = amount;
		this.taxRate = taxRate;
		this.percentage = percentage;
		this.account = account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getPercentage() {
		return percentage;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public final double getTaxRate() {
		return taxRate;
	}

	public final void setTaxRate(double taxRate) {
		this.taxRate = taxRate;
	}

	public final double monthlyAmount() {
		return round(getAmount() / MONTHS_PER_YEAR);
	}

	public final double monthlyGst() {
		return round(yearlyGst() / MONTHS_PER_YEAR);
	}

	public final double monthlyTotal() {
		return monthlyAmount() + monthlyGst();
	}

	public final double yearlyAmount() {
		return getAmount();
	}

	public final double yearlyGst() {
		return round(taxRate * yearlyAmount());
	}

	public final double yearlyTotalAmount() {
		return yearlyAmount() + yearlyGst();
	}

	@Override
	public String toString() {
		return "EzeeLeaseIncidental [name=" + name + ", amount=" + amount + ", percentage=" + percentage + "]";
	}
}