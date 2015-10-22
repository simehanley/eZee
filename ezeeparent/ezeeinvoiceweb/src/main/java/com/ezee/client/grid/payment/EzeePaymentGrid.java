package com.ezee.client.grid.payment;

import static com.ezee.client.crud.EzeeCreateUpdateDeleteEntityType.delete;
import static com.ezee.client.crud.EzeeCreateUpdateDeleteEntityType.update;
import static com.ezee.client.css.EzeeInvoiceDefaultResources.INSTANCE;
import static com.ezee.common.EzeeCommonConstants.EMPTY_STRING;
import static com.ezee.common.collections.EzeeCollectionUtils.isEmpty;
import static com.ezee.common.web.EzeeFromatUtils.getDateBoxFormat;

import java.util.Set;

import com.ezee.client.EzeeInvoiceServiceAsync;
import com.ezee.client.cache.EzeeInvoiceEntityCache;
import com.ezee.client.crud.payment.EzeeCreateUpdateDeletePayment;
import com.ezee.client.grid.EzeeGrid;
import com.ezee.client.grid.invoice.EzeeInvoiceChangeListener;
import com.ezee.model.entity.EzeeInvoice;
import com.ezee.model.entity.EzeePayment;
import com.ezee.model.entity.filter.EzeeEntityFilter;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.datepicker.client.DateBox;

/**
 * 
 * @author siborg
 *
 */
public class EzeePaymentGrid extends EzeeGrid<EzeePayment> implements EzeePaymentCreationListener {

	private EzeeInvoiceChangeListener listener;

	private DateBox dtFrom;

	private DateBox dtTo;

	private TextBox invoiceNumberText;

	public EzeePaymentGrid(final EzeeInvoiceServiceAsync service, final EzeeInvoiceEntityCache cache) {
		super(service, cache);
	}

	protected void initGrid() {
		super.initGrid();
		model = new EzeePaymentGridModel();
		model.bind(grid);
	}

	@Override
	protected void initFilter() {
		super.initFilter();
		Label dtFromLabel = new Label("From");
		dtFromLabel.setStyleName(INSTANCE.css().gwtLabelSmall());
		dtFrom = new DateBox();
		dtFrom.setStyleName(INSTANCE.css().gwtDateBoxSmall());
		dtFrom.setFormat(getDateBoxFormat());
		Label dtToLabel = new Label("To");
		dtToLabel.setStyleName(INSTANCE.css().gwtLabelSmall());
		dtTo = new DateBox();
		dtTo.setStyleName(INSTANCE.css().gwtDateBoxSmall());
		dtTo.setFormat(getDateBoxFormat());
		Label invoiceLabel = new Label("Invoice(s)");
		invoiceLabel.setStyleName(INSTANCE.css().gwtLabelMedium());
		invoiceNumberText = new TextBox();
		invoiceNumberText.setStyleName(INSTANCE.css().gwtTextBoxLarge());
		filterpanel.add(dtFromLabel);
		filterpanel.add(dtFrom);
		filterpanel.add(dtToLabel);
		filterpanel.add(dtTo);
		filterpanel.add(invoiceLabel);
		filterpanel.add(invoiceNumberText);
		KeyPressHandler filterHandler = new EzeeFilterKeyPressHandler();
		invoiceNumberText.addKeyPressHandler(filterHandler);
		initRefreshButton();
		initClearButton();
	}

	protected MenuBar createContextMenu() {
		MenuBar menu = new MenuBar(true);
		menu.setAnimationEnabled(true);
		menu.addItem("Edit", new Command() {
			@Override
			public void execute() {
				editEntity();
				contextMenu.hide();
			}
		});
		menu.addItem("Delete", new Command() {
			@Override
			public void execute() {
				deleteEntity();
				contextMenu.hide();
			}
		});
		return menu;
	}

	@Override
	public String getGridClass() {
		return EzeePayment.class.getName();
	}

	@Override
	protected void deleteEntity() {
		EzeePayment entity = getSelected();
		if (entity != null) {
			new EzeeCreateUpdateDeletePayment(service, cache, this, entity, delete, null).center();
		}
	}

	@Override
	protected void newEntity() {
		/* not implemented */
	}

	@Override
	protected void editEntity() {
		EzeePayment entity = getSelected();
		if (entity != null) {
			new EzeeCreateUpdateDeletePayment(service, cache, this, entity, update, null).center();
		}
	}

	@Override
	public void onCreatePayment(final Set<EzeeInvoice> invoices) {
		if (!isEmpty(invoices)) {
			new EzeeCreateUpdateDeletePayment(service, cache, this, invoices).center();
		}
	}

	@Override
	public void onSave(final EzeePayment payment) {
		super.onSave(payment);
		if (listener != null) {
			listener.onInvoicesChanged(payment.getInvoices());
		}
	}

	@Override
	public void onDelete(final EzeePayment payment) {
		super.onDelete(payment);
		if (listener != null) {
			listener.onInvoicesChanged(payment.getInvoices());
		}
	}

	public final EzeeInvoiceChangeListener getListener() {
		return listener;
	}

	public void setListener(final EzeeInvoiceChangeListener listener) {
		this.listener = listener;
	}

	@Override
	protected void clearFilter() {
		dtFrom.setValue(null);
		dtTo.setValue(null);
		invoiceNumberText.setText(EMPTY_STRING);
		filter = null;
		loadEntities();
	}

	@Override
	protected EzeeEntityFilter<EzeePayment> createFilter() {
		return new EzeePaymentFilter(invoiceNumberText.getText(), dtFrom.getValue(), dtTo.getValue());
	}
}