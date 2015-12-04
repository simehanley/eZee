package com.ezee.client.grid.payment;

import static com.ezee.client.EzeeInvoiceWebConstants.PAYMENT_CRUD_HEADERS;
import static com.ezee.common.collections.EzeeCollectionUtils.isEmpty;
import static com.ezee.model.entity.enums.EzeePaymentType.cheque;
import static com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType.delete;
import static com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityType.update;

import java.util.Set;

import com.ezee.client.crud.payment.EzeeCreateUpdateDeletePayment;
import com.ezee.client.grid.invoice.EzeeInvoiceChangeListener;
import com.ezee.model.entity.EzeeInvoice;
import com.ezee.model.entity.EzeePayment;
import com.ezee.web.common.cache.EzeeEntityCache;
import com.ezee.web.common.ui.grid.EzeeGrid;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;

/**
 * 
 * @author siborg
 *
 */
public class EzeePaymentGrid extends EzeeGrid<EzeePayment> implements EzeePaymentCreationListener {

	private EzeeInvoiceChangeListener listener;

	public EzeePaymentGrid(final EzeeEntityCache cache) {
		super(cache);
	}

	protected void initGrid() {
		super.initGrid();
		model = new EzeePaymentGridModel();
		model.bind(grid);
	}

	@Override
	protected void initFilter() {
		super.initFilter();
		toolBar = new EzeePaymentGridToolBar(this, cache);
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
	public void deleteEntity() {
		EzeePayment entity = getSelected();
		if (entity != null) {
			new EzeeCreateUpdateDeletePayment(cache, this, entity, delete, null, cheque, PAYMENT_CRUD_HEADERS).show();
		}
	}

	@Override
	public void newEntity() {
		/* not implemented */
	}

	@Override
	public void editEntity() {
		EzeePayment entity = getSelected();
		if (entity != null) {
			new EzeeCreateUpdateDeletePayment(cache, this, entity, update, null, cheque, PAYMENT_CRUD_HEADERS).show();
		}
	}

	@Override
	public void onCreatePayment(final Set<EzeeInvoice> invoices) {
		if (!isEmpty(invoices)) {
			new EzeeCreateUpdateDeletePayment(cache, this, invoices, PAYMENT_CRUD_HEADERS).show();
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