package com.ezee.server.report.excel.payer;

import com.ezee.model.entity.EzeePayer;
import com.ezee.server.report.excel.financialentity.EzeeFinancialEntityReportGenerator;

public abstract class EzeePayerReportGenerator<T extends EzeePayer> extends EzeeFinancialEntityReportGenerator<T> {
}