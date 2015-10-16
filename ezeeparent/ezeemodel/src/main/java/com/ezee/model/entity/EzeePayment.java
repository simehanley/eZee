package com.ezee.model.entity;

import static com.ezee.common.EzeeCommonConstants.ZERO_DBL;
import static com.ezee.common.collections.EzeeCollectionUtils.isEmpty;

import java.beans.Transient;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
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

	@Column(name = "PAYMENT_DATE")
	private Date paymentDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "PAYMENT_TYPE")
	private EzeePaymentType type;

	@Column(name = "DESCRIPTION")
	private String paymentDescription;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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
		double payment = ZERO_DBL;
		if (!isEmpty(invoices)) {
			for (EzeeInvoice invoice : invoices) {
				payment += invoice.getInvoiceAmount();
			}
		}
		return payment;
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
}