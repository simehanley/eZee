package com.ezee.client.grid.payment;

import static com.ezee.client.crud.EzeeCreateUpdateDeleteEntityType.delete;
import static com.ezee.client.crud.EzeeCreateUpdateDeleteEntityType.update;
import static com.ezee.common.collections.EzeeCollectionUtils.isEmpty;
import static com.ezee.model.entity.enums.EzeePaymentType.cheque;

import java.util.Set;

import com.ezee.client.EzeeInvoiceServiceAsync;
import com.ezee.client.cache.EzeeInvoiceEntityCache;
import com.ezee.client.crud.payment.EzeeCreateUpdateDeletePayment;
import com.ezee.client.grid.EzeeGrid;
import com.ezee.client.grid.invoice.EzeeInvoiceChangeListener;
import com.ezee.model.entity.EzeeInvoice;
import com.ezee.model.entity.EzeePayment;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;

/**
 * 
 * @author siborg
 *
 */
public class EzeePaymentGrid extends EzeeGrid<EzeePayment> implements EzeePaymentCreationListener {

	private EzeeInvoiceChangeListener listener;

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
		toolBar = new EzeePaymentGridToolBar(this);
		filterpanel.add(toolBar);
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
			new EzeeCreateUpdateDeletePayment(service, cache, this, entity, delete, null, cheque).center();
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
			new EzeeCreateUpdateDeletePayment(service, cache, this, entity, update, null, cheque).center();
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
}