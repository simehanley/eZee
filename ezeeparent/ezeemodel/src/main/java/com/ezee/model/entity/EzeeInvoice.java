package com.ezee.model.entity;

import static com.ezee.model.entity.EzeeEntityConstants.NULL_ID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ezee.model.entity.enums.EzeeInvoiceClassification;
import com.ezee.model.entity.filter.EzeeDateFilterable;
import com.ezee.model.entity.filter.EzeeStringFilterable;
import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * 
 * @author siborg
 *
 */
@NamedNativeQueries({
		@NamedNativeQuery(name = "deletePayeeMappingsSql", query = "delete from EZEE_INVOICE_TO_SUPPLIER_MAPPING where INVOICE_ID = :id"),
		@NamedNativeQuery(name = "deletePayerMappingsSql", query = "delete from EZEE_INVOICE_TO_PAYER_MAPPING where INVOICE_ID = :id"),
		@NamedNativeQuery(name = "deleteAgeDebtMappingsSql", query = "delete from EZEE_INVOICE_TO_DEBT_AGE_MAPPING where INVOICE_ID = :id") })
@Entity
@Table(name = "EZEE_INVOICE")
public class EzeeInvoice extends EzeeTaxableEntity implements IsSerializable, EzeeStringFilterable, EzeeDateFilterable {

	private static final long serialVersionUID = -588454316909349202L;

	@Column(name = "INVOICE_ID")
	private String invoiceId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinTable(name = "EZEE_INVOICE_TO_SUPPLIER_MAPPING", joinColumns = @JoinColumn(name = "INVOICE_ID") , inverseJoinColumns = @JoinColumn(name = "PAYEE_ID") )
	private EzeeSupplier supplier;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinTable(name = "EZEE_INVOICE_TO_PAYER_MAPPING", joinColumns = @JoinColumn(name = "INVOICE_ID") , inverseJoinColumns = @JoinColumn(name = "PAYER_ID") )
	private EzeePremises premises;

	@Column(name = "AMOUNT")
	private double amount;

	@Column(name = "TAX")
	private double tax;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "MANUAL_TAX")
	private boolean manualtax = Boolean.TRUE;

	@Column(name = "REVERSE_TAX")
	private boolean reversetax = Boolean.FALSE;

	@Column(name = "INVOICE_DATE")
	private String invoiceDate;

	@Column(name = "DUE_DATE")
	private String dateDue;

	@Column(name = "PAYMENT_DATE")
	private String datePaid;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinTable(name = "EZEE_INVOICE_TO_DEBT_AGE_MAPPING", joinColumns = @JoinColumn(name = "INVOICE_ID") , inverseJoinColumns = @JoinColumn(name = "DEBT_AGE_RULE_ID") )
	private EzeeDebtAgeRule ageRule;

	@Enumerated(EnumType.STRING)
	@Column(name = "CLASSIFICATION")
	private EzeeInvoiceClassification classification;

	@Column(name = "FILENAME")
	private String filename;

	@Lob
	@Column(name = "FILE")
	private byte[] file;

	@Transient
	private boolean pay = false;

	public EzeeInvoice() {
		super();
	}

	public EzeeInvoice(final String invoiceId, final EzeeSupplier supplier, final EzeePremises premises,
			final double amount, final double tax, final String description, final boolean manualtax,
			final String invoiceDate, final String dateDue, final String datePaid, final String created,
			final String updated, final EzeeInvoiceClassification classification) {
		this(NULL_ID, invoiceId, supplier, premises, amount, tax, description, manualtax, invoiceDate, dateDue,
				datePaid, created, updated, classification);
	}

	public EzeeInvoice(final Long id, final String invoiceId, final EzeeSupplier supplier, final EzeePremises premises,
			final double amount, final double tax, final String description, final boolean manualtax,
			final String invoiceDate, final String dateDue, final String datePaid, final String created,
			final String updated, final EzeeInvoiceClassification classification) {
		super(id, created, updated);
		this.invoiceId = invoiceId;
		this.supplier = supplier;
		this.premises = premises;
		this.amount = amount;
		this.tax = tax;
		this.description = description;
		this.manualtax = manualtax;
		this.invoiceDate = invoiceDate;
		this.dateDue = dateDue;
		this.datePaid = datePaid;
		this.classification = classification;
	}

	@Transient
	public final double getInvoiceAmount() {
		return getAmount() + getTax();
	}

	public final String getInvoiceId() {
		return invoiceId;
	}

	public final EzeeSupplier getSupplier() {
		return supplier;
	}

	public final EzeePremises getPremises() {
		return premises;
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

	public final boolean isManualTax() {
		return manualtax;
	}

	public final String getDateDue() {
		return dateDue;
	}

	public final String getDatePaid() {
		return datePaid;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public void setSupplier(EzeeSupplier supplier) {
		this.supplier = supplier;
	}

	public void setPremises(EzeePremises premises) {
		this.premises = premises;
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

	public void setManualTax(boolean manualtax) {
		this.manualtax = manualtax;
	}

	public void setDateDue(String dateDue) {
		this.dateDue = dateDue;
	}

	public void setDatePaid(String datePaid) {
		this.datePaid = datePaid;
	}

	public final EzeeDebtAgeRule getAgeRule() {
		return ageRule;
	}

	public void setAgeRule(EzeeDebtAgeRule ageRule) {
		this.ageRule = ageRule;
	}

	public final EzeeInvoiceClassification getClassification() {
		return classification;
	}

	public void setClassification(EzeeInvoiceClassification classification) {
		this.classification = classification;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	public final String getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public final boolean isReverseTax() {
		return reversetax;
	}

	public void setReverseTax(boolean reversetax) {
		this.reversetax = reversetax;
	}

	@Override
	public String toString() {
		return "EzeeInvoice [invoiceId=" + invoiceId + "]";
	}

	@Override
	public String filterDate() {
		return getInvoiceDate();
	}

	@Override
	public String filterString() {
		return invoiceId;
	}

	public final boolean isPay() {
		return pay;
	}

	public final void setPay(boolean pay) {
		this.pay = pay;
	}

	@Transient
	@Override
	public double getNet() {
		return getAmount();
	}

	@Transient
	@Override
	public double getGross() {
		return getInvoiceAmount();
	}

	@Override
	public void setNet(double net) {
		setAmount(net);
	}
}