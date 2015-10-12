package com.ezee.model.entity;

import static com.ezee.common.EzeeCommonConstants.ZERO_DBL;
import static com.ezee.common.collections.EzeeCollectionUtils.isEmpty;

import java.beans.Transient;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.ezee.model.entity.enums.EzeePaymentType;

/**
 * 
 * @author siborg
 *
 */
@Entity
@Table(name = "EZEE_PAYMENT")
public class EzeePayment extends EzeeDatabaseEntity {

	private static final long serialVersionUID = 607584929798342009L;

	@Column(name = "DATE")
	private Date paymentDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "TYPE")
	private EzeePaymentType type;

	@Column(name = "DESCRIPTION")
	private String paymentDescription;

	@OneToMany
	@JoinTable(name = "EZEE_PAYMENT_TO_INVOICE_MAPPING", joinColumns = @JoinColumn(name = "PAYMENT_ID") , inverseJoinColumns = @JoinColumn(name = "INVOICE_ID") )
	private Set<EzeeInvoice> invoices;

	public EzeePayment() {
		super();
	}

	public EzeePayment(final Date created, final Date updated) {
		super(created, updated);
	}

	public EzeePayment(final Long id, final Date created, final Date updated) {
		super(id, created, updated);
	}

	@Transient
	public final double getPaymentAmount() {
		if (!isEmpty(invoices)) {
			return invoices.stream().mapToDouble(invoice -> invoice.getInvoiceAmount()).sum();
		}
		return ZERO_DBL;
	}

	public final Date getPaymentDate() {
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

	public void addInvoice(final EzeeInvoice invoice) {
		if (isEmpty(invoices)) {
			invoices = new HashSet<>();
		}
		invoices.add(invoice);
	}

	public void removeInvoice(final EzeeInvoice invoice) {
		if (!isEmpty(invoices)) {
			invoices.remove(invoice);
		}
	}
}