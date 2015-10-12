package com.ezee.model.entity;

import static com.ezee.model.entity.EzeeEntityConstants.NULL_ID;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * @author siborg
 *
 */
@Entity
@Table(name = "EZEE_INVOICE")
public class EzeeInvoice extends EzeeDatabaseEntity {

	private static final long serialVersionUID = -588454316909349202L;

	@Column(name = "INVOICE_ID")
	private String invoiceId;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "EZEE_INVOICE_TO_PAYEE_MAPPING", joinColumns = @JoinColumn(name = "INVOICE_ID") , inverseJoinColumns = @JoinColumn(name = "PAYEE_ID") )
	private EzeePayee payee;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "EZEE_INVOICE_TO_PAYER_MAPPING", joinColumns = @JoinColumn(name = "INVOICE_ID") , inverseJoinColumns = @JoinColumn(name = "PAYER_ID") )
	private EzeePayer payer;

	@Column(name = "AMOUNT")
	private double amount;

	@Column(name = "TAX")
	private double tax;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "TAXABLE")
	private boolean taxable = Boolean.TRUE;

	@Column(name = "PAID")
	private boolean paid = Boolean.FALSE;

	@Column(name = "DUE_DATE")
	private Date dateDue;

	@Column(name = "PAYMENT_DATE")
	private Date datePaid;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "EZEE_INVOICE_TO_DEBT_AGE_MAPPING", joinColumns = @JoinColumn(name = "INVOICE_ID") , inverseJoinColumns = @JoinColumn(name = "DEBT_AGE_RULE_ID") )
	private EzeeDebtAgeRule ageRule;

	public EzeeInvoice() {
		super();
	}

	public EzeeInvoice(final String invoiceId, final EzeePayee payee, final EzeePayer payer, final double amount,
			final double tax, final String description, final boolean taxable, final boolean paid, final Date dateDue,
			final Date datePaid, final Date created, final Date updated) {
		this(NULL_ID, invoiceId, payee, payer, amount, tax, description, taxable, paid, dateDue, datePaid, created,
				updated);
	}

	public EzeeInvoice(final Long id, final String invoiceId, final EzeePayee payee, final EzeePayer payer,
			final double amount, final double tax, final String description, final boolean taxable, final boolean paid,
			final Date dateDue, final Date datePaid, final Date created, final Date updated) {
		super(id, created, updated);
		this.invoiceId = invoiceId;
		this.payee = payee;
		this.payer = payer;
		this.amount = amount;
		this.tax = tax;
		this.description = description;
		this.taxable = taxable;
		this.paid = paid;
		this.dateDue = dateDue;
		this.datePaid = datePaid;
	}

	public final String getInvoiceId() {
		return invoiceId;
	}

	public final EzeePayee getPayee() {
		return payee;
	}

	public final EzeePayer getPayer() {
		return payer;
	}

	public final double getAmount() {
		return amount;
	}

	public final double getTax() {
		return tax;
	}

	public final String getDescription() {
		return description;
	}

	public final boolean isTaxable() {
		return taxable;
	}

	public final boolean isPaid() {
		return paid;
	}

	public final Date getDateDue() {
		return dateDue;
	}

	public final Date getDatePaid() {
		return datePaid;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public void setPayee(EzeePayee payee) {
		this.payee = payee;
	}

	public void setPayer(EzeePayer payer) {
		this.payer = payer;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setTaxable(boolean taxable) {
		this.taxable = taxable;
	}

	public void setPaid(boolean paid) {
		this.paid = paid;
	}

	public void setDateDue(Date dateDue) {
		this.dateDue = dateDue;
	}

	public void setDatePaid(Date datePaid) {
		this.datePaid = datePaid;
	}
}