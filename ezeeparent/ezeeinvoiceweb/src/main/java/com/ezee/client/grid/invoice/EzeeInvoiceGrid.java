package com.ezee.client.grid.invoice;

import static com.ezee.client.crud.EzeeCreateUpdateDeleteEntityType.delete;
import static com.ezee.client.crud.EzeeCreateUpdateDeleteEntityType.update;
import static com.ezee.client.css.EzeeInvoiceDefaultResources.INSTANCE;
import static com.ezee.common.EzeeCommonConstants.EMPTY_STRING;
import static com.ezee.common.collections.EzeeCollectionUtils.isEmpty;

import java.util.HashSet;
import java.util.Set;

import com.ezee.client.EzeeInvoiceServiceAsync;
import com.ezee.client.cache.EzeeInvoiceEntityCache;
import com.ezee.client.crud.invoice.EzeeCreateUpdateDeleteInvoice;
import com.ezee.client.grid.EzeeGrid;
import com.ezee.client.grid.payment.EzeePaymentCreationListener;
import com.ezee.model.entity.EzeeInvoice;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.view.client.MultiSelectionModel;

/**
 * 
 * @author siborg
 *
 */
public class EzeeInvoiceGrid extends EzeeGrid<EzeeInvoice> implements EzeeInvoiceChangeListener {

	private EzeePaymentCreationListener listener;

	private TextBox invoiceNumberText;

	private TextBox supplierText;

	private TextBox premisesText;

	public EzeeInvoiceGrid(final EzeeInvoiceServiceAsync service, final EzeeInvoiceEntityCache cache) {
		super(service, cache);
	}

	protected void initGrid() {
		super.initGrid();
		MultiSelectionModel<EzeeInvoice> selectModel = new MultiSelectionModel<>();
		grid.setSelectionModel(selectModel);
		model = new EzeeInvoiceGridModel();
		model.bind(grid);
	}

	@Override
	protected void initFilter() {
		super.initFilter();
		Label invoiceNumberLabel = new Label("Invoice(s)");
		invoiceNumberLabel.setStyleName(INSTANCE.css().gwtLabelMedium());
		invoiceNumberText = new TextBox();
		invoiceNumberText.setStyleName(INSTANCE.css().gwtTextBoxLarge());
		Label supplierLabel = new Label("Supplier");
		supplierLabel.setStyleName(INSTANCE.css().gwtLabelMedium());
		supplierText = new TextBox();
		supplierText.setStyleName(INSTANCE.css().gwtTextBoxMedium());
		Label premisesLabel = new Label("Premises");
		premisesLabel.setStyleName(INSTANCE.css().gwtLabelMedium());
		premisesText = new TextBox();
		premisesText.setStyleName(INSTANCE.css().gwtTextBoxMedium());
		filterpanel.add(invoiceNumberLabel);
		filterpanel.add(invoiceNumberText);
		filterpanel.add(supplierLabel);
		filterpanel.add(supplierText);
		filterpanel.add(premisesLabel);
		filterpanel.add(premisesText);
		KeyPressHandler filterHandler = new EzeeFilterKeyPressHandler();
		invoiceNumberText.addKeyPressHandler(filterHandler);
		supplierText.addKeyPressHandler(filterHandler);
		premisesText.addKeyPressHandler(filterHandler);
		initRefreshButton();
		initClearButton();
	}

	protected MenuBar createContextMenu() {
		MenuBar menu = super.createContextMenu();
		menu.addItem("Payment", new Command() {
			@Override
			public void execute() {
				newPayment();
				contextMenu.hide();
			}
		});
		return menu;
	}

	@Override
	public String getGridClass() {
		return EzeeInvoice.class.getName();
	}

	@Override
	protected void deleteEntity() {
		EzeeInvoice entity = getSelected();
		if (entity != null) {
			new EzeeCreateUpdateDeleteInvoice(service, cache, this, entity, delete).center();
		}
	}

	@Override
	protected void newEntity() {
		new EzeeCreateUpdateDeleteInvoice(service, cache, this).center();
	}

	@Override
	protected void editEntity() {
		EzeeInvoice entity = getSelected();
		if (entity != null) {
			new EzeeCreateUpdateDeleteInvoice(service, cache, this, entity, update).center();
		}
	}

	@SuppressWarnings("unchecked")
	protected void newPayment() {
		if (listener != null) {
			MultiSelectionModel<EzeeInvoice> model = (MultiSelectionModel<EzeeInvoice>) grid.getSelectionModel();
			Set<EzeeInvoice> selected = model.getSelectedSet();
			if (!isEmpty(selected)) {
				Set<EzeeInvoice> unpaid = new HashSet<>();
				for (EzeeInvoice invoice : selected) {
					if (invoice.getDatePaid() == null) {
						unpaid.add(invoice);
					}
				}
				listener.onCreatePayment(unpaid);
			}
		}
	}

	@Override
	public void onInvoicesChanged(final Set<EzeeInvoice> invoices) {
		if (!isEmpty(invoices)) {
			for (EzeeInvoice invoice : invoices) {
				onSave(invoice);
			}
		}
	}

	public final EzeePaymentCreationListener getListener() {
		return listener;
	}

	public void setListener(final EzeePaymentCreationListener listener) {
		this.listener = listener;
	}

	@Override
	protected void clearFilter() {
		invoiceNumberText.setText(EMPTY_STRING);
		supplierText.setText(EMPTY_STRING);
		premisesText.setText(EMPTY_STRING);
		filter = null;
		loadEntities();
	}

	@Override
	protected EzeeInvoiceFilter createFilter() {
		return new EzeeInvoiceFilter(invoiceNumberText.getText(), supplierText.getText(), premisesText.getText());
	}
}