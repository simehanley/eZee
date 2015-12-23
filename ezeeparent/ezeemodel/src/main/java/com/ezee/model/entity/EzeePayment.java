package com.ezee.model.entity;

import static com.ezee.common.EzeeCommonConstants.ZERO_DBL;
import static com.ezee.common.collections.EzeeCollectionUtils.isEmpty;
import static com.ezee.model.entity.EzeeEntityConstants.NULL_ID;
import static com.ezee.model.entity.EzeePaymentUtils.getInvoiceNumbers;

import java.beans.Transient;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.ezee.model.entity.enums.EzeePaymentType;
import com.ezee.model.entity.filter.EzeeDateFilterable;
import com.ezee.model.entity.filter.EzeeStringFilterable;
import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * 
 * @author siborg
 *
 */
@NamedNativeQueries({
		@NamedNativeQuery(name = "deleteInvoiceMappingsSql", query = "delete from EZEE_PAYMENT_TO_INVOICE_MAPPING where PAYMENT_ID = :id"),
		@NamedNativeQuery(name = "selectOutstandingChequesByPremisesSql", query = "select DISTINCT EZEE_PAYMENT.ID, EZEE_PAYMENT.PAYMENT_DATE, EZEE_PAYMENT.PAYMENT_TYPE, EZEE_PAYMENT.DESCRIPTION, EZEE_PAYMENT.CREATED, EZEE_PAYMENT.UPDATED, EZEE_PAYMENT.CHEQUE_NUMBER, EZEE_PAYMENT.CHEQUE_PRESENTED from EZEE_PAYMENT, EZEE_INVOICE, EZEE_PAYER, EZEE_PAYMENT_TO_INVOICE_MAPPING, EZEE_INVOICE_TO_PAYER_MAPPING where PAYMENT_TYPE = 'cheque' AND CHEQUE_PRESENTED = false AND EZEE_PAYMENT.ID = EZEE_PAYMENT_TO_INVOICE_MAPPING.PAYMENT_ID AND EZEE_INVOICE.ID = EZEE_PAYMENT_TO_INVOICE_MAPPING.INVOICE_ID AND EZEE_INVOICE.ID = EZEE_INVOICE_TO_PAYER_MAPPING.INVOICE_ID AND EZEE_PAYER.ID = EZEE_INVOICE_TO_PAYER_MAPPING.PAYER_ID AND EZEE_PAYER.ID = :payerId", resultClass = EzeePayment.class),
		@NamedNativeQuery(name = "selectOutstandingChequesSql", query = "select * from EZEE_PAYMENT where PAYMENT_TYPE = 'cheque' AND CHEQUE_PRESENTED = false", resultClass = EzeePayment.class) })
@Entity
@Table(name = "EZEE_PAYMENT")
public class EzeePayment extends EzeeDatabaseEntity
		implements IsSerializable, EzeeStringFilterable, EzeeDateFilterable {

	private static final long serialVersionUID = 607584929798342009L;

	@Column(name = "PAYMENT_DATE")
	private String paymentDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "PAYMENT_TYPE")
	private EzeePaymentType type;

	@Column(name = "DESCRIPTION")
	private String paymentDescription;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "EZEE_PAYMENT_TO_INVOICE_MAPPING", joinColumns = @JoinColumn(name = "PAYMENT_ID") , inverseJoinColumns = @JoinColumn(name = "INVOICE_ID") )
	private Set<EzeeInvoice> invoices;

	@Column(name = "CHEQUE_NUMBER")
	private String chequeNumber;

	@Column(name = "CHEQUE_PRESENTED")
	private boolean chequePresented;

	public EzeePayment() {
		super();
	}

	public EzeePayment(final String created, final String updated) {
		this(NULL_ID, created, updated);
	}

	public EzeePayment(final Long id, final String created, final String updated) {
		super(id, created, updated);
	}

	@Transient
	public final double getPaymentAmount() {
		double payment = ZERO_DBL;
		if (!isEmpty(invoices)) {
			for (EzeeInvoice invoice : invoices) {
				payment += invoice.getInvoiceAmount();
			}
		}
		return payment;
	}

	public final String getPaymentDate() {
		return paymentDate;
	}

	public final EzeePaymentType getType() {
		return type;
	}

	public final String getPaymentDescription() {
		return paymentDescription;
	}

	public final Set<EzeeInvoice> getInvoices() {
		return invoices;
	}

	public final void setInvoices(Set<EzeeInvoice> invoices) {
		this.invoices = invoices;
	}

	public void addInvoice(final EzeeInvoice invoice) {
		if (isEmpty(invoices)) {
			invoices = new HashSet<EzeeInvoice>();
		}
		invoices.add(invoice);
	}

	public void removeInvoice(final EzeeInvoice invoice) {
		if (!isEmpty(invoices)) {
			invoices.remove(invoice);
		}
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public void setType(EzeePaymentType type) {
		this.type = type;
	}

	public void setPaymentDescription(String paymentDescription) {
		this.paymentDescription = paymentDescription;
	}

	public String getChequeNumber() {
		return chequeNumber;
	}

	public void setChequeNumber(String chequeNumber) {
		this.chequeNumber = chequeNumber;
	}

	public boolean isChequePresented() {
		return chequePresented;
	}

	public void setChequePresented(boolean chequePresented) {
		this.chequePresented = chequePresented;
	}

	@Override
	public String toString() {
		return "EzeePayment [paymentDate=" + paymentDate + ", type=" + type + "]";
	}

	@Override
	public String filterDate() {
		return getPaymentDate();
	}

	@Override
	public String filterString() {
		return getInvoiceNumbers(this);
	}
}