package com.ezee.client.grid.payee;

import java.util.Comparator;
import java.util.Map;

import com.ezee.client.grid.EzeeFinancialEntityGridModel;
import com.ezee.model.entity.EzeePayee;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;

/**
 * 
 * @author siborg
 *
 */
public class EzeePayeeGridModel extends EzeeFinancialEntityGridModel<EzeePayee> {

	protected static final String BANK = "Bank";
	protected static final String ACCOUNT_NAME = "Account Name";
	protected static final String ACCOUNT_NUMBER = "Account No.";
	protected static final String ACCOUNT_BSB = "Bsb";

	protected static final String BANK_WIDTH = "250px";
	protected static final String ACCOUNT_NAME_WIDTH = "300px";
	protected static final String ACCOUNT_NUMBER_WIDTH = "200px";
	protected static final String ACCOUNT_BSB_WIDTH = "150px";

	@Override
	protected Map<String, Column<EzeePayee, ?>> createColumns(final DataGrid<EzeePayee> grid) {
		Map<String, Column<EzeePayee, ?>> columns = super.createColumns(grid);
		createTextColumn(columns, grid, BANK, BANK_WIDTH, true);
		createTextColumn(columns, grid, ACCOUNT_NAME, ACCOUNT_NAME_WIDTH, true);
		createTextColumn(columns, grid, ACCOUNT_NUMBER, ACCOUNT_NUMBER_WIDTH, false);
		createTextColumn(columns, grid, ACCOUNT_BSB, ACCOUNT_BSB_WIDTH, false);
		return columns;
	}

	@Override
	protected String resolveTextFieldValue(final String fieldName, final EzeePayee payee) {
		switch (fieldName) {
		case BANK:
			return payee.getBank();
		case ACCOUNT_NAME:
			return payee.getAccountName();
		case ACCOUNT_NUMBER:
			return payee.getAccountNumber();
		case ACCOUNT_BSB:
			return payee.getAccountBsb();
		default:
			return super.resolveTextFieldValue(fieldName, payee);
		}
	}

	@Override
	protected void addComparators(final Map<String, Column<EzeePayee, ?>> columns) {
		super.addComparators(columns);

		handler.setComparator(columns.get(BANK), new Comparator<EzeePayee>() {

			@Override
			public int compare(final EzeePayee one, final EzeePayee two) {
				return one.getBank().compareTo(two.getBank());
			}
		});

		handler.setComparator(columns.get(ACCOUNT_NAME), new Comparator<EzeePayee>() {

			@Override
			public int compare(final EzeePayee one, final EzeePayee two) {
				return one.getAccountName().compareTo(two.getAccountName());
			}
		});
	}

	@Override
	protected void addSortColumns(final DataGrid<EzeePayee> grid, final Map<String, Column<EzeePayee, ?>> columns) {
		super.addSortColumns(grid, columns);
		grid.getColumnSortList().push(columns.get(BANK));
		grid.getColumnSortList().push(columns.get(ACCOUNT_NAME));
	}
}