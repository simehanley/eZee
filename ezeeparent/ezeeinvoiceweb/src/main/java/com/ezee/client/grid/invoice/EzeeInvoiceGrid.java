package com.ezee.client.grid.invoice;

import static com.ezee.client.EzeeInvoiceWebConstants.INVOICE_CRUD_HEADERS;
import static com.ezee.common.EzeeCommonConstants.EMPTY_STRING;
import static com.ezee.common.collections.EzeeCollectionUtils.isEmpty;
import static com.ezee.common.string.EzeeStringUtils.hasLength;
import static com.ezee.web.common.EzeeWebCommonConstants.ENTITY_SERVICE;
import static com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType.delete;
import static com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType.update;
import static com.ezee.web.common.ui.dialog.EzeeMessageDialog.showNew;
import static com.ezee.web.common.ui.utils.EzeeCursorUtils.showDefaultCursor;
import static com.ezee.web.common.ui.utils.EzeeCursorUtils.showWaitCursor;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ezee.client.crud.invoice.EzeeCreateUpdateDeleteInvoice;
import com.ezee.client.crud.invoice.EzeeUploadInvoiceForm;
import com.ezee.client.grid.payment.EzeePaymentCreationListener;
import com.ezee.model.entity.EzeeInvoice;
import com.ezee.web.common.cache.EzeeEntityCache;
import com.ezee.web.common.ui.grid.EzeeGrid;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.view.client.MultiSelectionModel;

/**
 * 
 * @author siborg
 *
 */
public class EzeeInvoiceGrid extends EzeeGrid<EzeeInvoice>
		implements EzeeInvoiceChangeListener, EzeeInvoiceUpLoaderListener, EzeeCreateUpdateDeleteInvoiceHandler {

	private static final Logger log = Logger.getLogger("EzeeInvoiceGrid");

	private EzeePaymentCreationListener listener;

	public EzeeInvoiceGrid(final EzeeEntityCache cache) {
		super(cache);
	}

	@Override
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
		menu.addSeparator();
		menu.addItem("Make Payment", new Command() {
			@Override
			public void execute() {
				newPayment();
				contextMenu.hide();
			}
		});
		menu.addSeparator();
		menu.addItem("Upload Invoice File", new Command() {
			@Override
			public void execute() {
				uploadInvoiceFile();
				contextMenu.hide();
			}
		});
		menu.addItem("Delete Invoice File", new Command() {
			@Override
			public void execute() {
				deleteInvoiceFile();
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
	public void deleteEntity() {
		EzeeInvoice entity = getSelected();
		if (entity.getDatePaid() == null) {
			if (entity != null) {
				new EzeeCreateUpdateDeleteInvoice(cache, this, entity, delete, EMPTY_STRING, INVOICE_CRUD_HEADERS)
						.show();
			}
		} else {
			showNew("Cannot Delete", "Cannot delete an invoice that has been paid.  Delete underlying payment first.");
		}
	}

	@Override
	public void newEntity() {
		new EzeeCreateUpdateDeleteInvoice(cache, this, EMPTY_STRING, INVOICE_CRUD_HEADERS).show();
	}

	@Override
	public void editEntity() {
		EzeeInvoice entity = getSelected();
		if (entity != null) {
			new EzeeCreateUpdateDeleteInvoice(cache, this, entity, update, EMPTY_STRING, INVOICE_CRUD_HEADERS).show();
		}
	}

	public void newSupplierInvoice(final String supplierName) {
		new EzeeCreateUpdateDeleteInvoice(cache, this, supplierName, INVOICE_CRUD_HEADERS).show();
	}

	@SuppressWarnings("unchecked")
	public void newPayment() {
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
			} else {
				showNew("New Payment", "No invoices selected.  Please selct at least one invoice to pay.");
			}
		}
	}

	/** reverting this for the time being - may reinstate if necessary **/
	@SuppressWarnings("unused")
	private Set<EzeeInvoice> getInvoicesToPay() {
		List<EzeeInvoice> invoices = model.getHandler().getList();
		Set<EzeeInvoice> toPay = new HashSet<>();
		if (!isEmpty(invoices)) {
			for (EzeeInvoice invoice : invoices) {
				if (invoice.getDatePaid() == null && invoice.isPay()) {
					toPay.add(invoice);
				}
			}
		}
		return toPay;
	}

	private void uploadInvoiceFile() {
		EzeeInvoice entity = getSelected();
		if (entity != null) {
			EzeeUploadInvoiceForm uploadInvoice = new EzeeUploadInvoiceForm(getSelected(), this);
			uploadInvoice.center();
		}
	}

	private void deleteInvoiceFile() {
		final EzeeInvoice entity = getSelected();
		if (entity != null && hasLength(entity.getFilename())) {
			showWaitCursor();
			final String invoiceName = entity.getFilename();
			entity.setFilename(null);
			ENTITY_SERVICE.saveEntity(EzeeInvoice.class.getName(), entity, new AsyncCallback<EzeeInvoice>() {
				@Override
				public void onFailure(Throwable caught) {
					showDefaultCursor();
					String message = "Failed to delete invoice " + invoiceName + " for invoice '" + entity
							+ "'.  See log for details.";
					log.log(Level.SEVERE, message, caught);
					showNew("Error", message);
				}

				@Override
				public void onSuccess(final EzeeInvoice result) {
					showDefaultCursor();
					onSave(result);
				}
			});
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
	public void invoiceUploaded(final String filename) {
		EzeeInvoice selected = getSelected();
		if (selected != null) {
			selected.setFilename(filename);
			onSave(selected);
		}
	}

	@Override
	public void onCreatePaymentFromNewInvoice(final EzeeInvoice invoice) {
		listener.onCreatePayment(Collections.singleton(invoice));
	}
}