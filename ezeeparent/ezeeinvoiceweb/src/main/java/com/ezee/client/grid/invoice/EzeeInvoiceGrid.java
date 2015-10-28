package com.ezee.client.grid.invoice;

import static com.ezee.client.crud.EzeeCreateUpdateDeleteEntityType.delete;
import static com.ezee.client.crud.EzeeCreateUpdateDeleteEntityType.update;
import static com.ezee.common.collections.EzeeCollectionUtils.isEmpty;

import java.util.HashSet;
import java.util.Set;

import com.ezee.client.EzeeInvoiceServiceAsync;
import com.ezee.client.cache.EzeeInvoiceEntityCache;
import com.ezee.client.crud.invoice.EzeeCreateUpdateDeleteInvoice;
import com.ezee.client.crud.invoice.EzeeUploadInvoiceForm;
import com.ezee.client.grid.EzeeGrid;
import com.ezee.client.grid.payment.EzeePaymentCreationListener;
import com.ezee.model.entity.EzeeInvoice;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.view.client.MultiSelectionModel;

/**
 * 
 * @author siborg
 *
 */
public class EzeeInvoiceGrid extends EzeeGrid<EzeeInvoice>
		implements EzeeInvoiceChangeListener, EzeeInvoiceUpLoaderListener {

	private EzeePaymentCreationListener listener;

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
		toolBar = new EzeeInvoiceGridToolBar(this);
		filterpanel.add(toolBar);
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
		menu.addItem("Upload Invoice", new Command() {
			@Override
			public void execute() {
				uploadInvoice();
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
	private void newPayment() {
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

	private void uploadInvoice() {
		EzeeUploadInvoiceForm uploadInvoice = new EzeeUploadInvoiceForm(getSelected(), this);
		uploadInvoice.center();
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
	public void invoieUploaded(final String filename) {
		EzeeInvoice selected = getSelected();
		selected.setFilename(filename);
		onSave(selected);
	}
}