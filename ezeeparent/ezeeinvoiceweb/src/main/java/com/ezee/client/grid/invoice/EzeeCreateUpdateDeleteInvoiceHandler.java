package com.ezee.client.grid.invoice;

import com.ezee.model.entity.EzeeInvoice;
import com.ezee.web.common.ui.crud.EzeeCreateUpdateDeleteEntityHandler;

public interface EzeeCreateUpdateDeleteInvoiceHandler extends EzeeCreateUpdateDeleteEntityHandler<EzeeInvoice> {

	void onCreatePaymentFromNewInvoice(EzeeInvoice invoice);
}
