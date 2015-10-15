package com.ezee.client.grid;

import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.ezee.model.entity.EzeeFinancialEntity;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;

public abstract class EzeeFinancialEntityGridModel<T extends EzeeFinancialEntity> extends EzeeGridModel<T> {

	protected static final String NAME = "Name";
	protected static final String ADDRESS_LINE_1 = "Address (Line 1)";
	protected static final String ADDRESS_LINE_2 = "Address (Line 2)";
	protected static final String SUBURB = "Suburb";
	protected static final String CITY = "City";
	protected static final String STATE = "State";
	protected static final String POST_CODE = "Post Code";
	protected static final String PHONE = "Phone";

	protected static final String NAME_WIDTH = "300px";
	protected static final String ADDRESS_LINE_1_WIDTH = "400px";
	protected static final String ADDRESS_LINE_2_WIDTH = "400px";
	protected static final String SUBURB_WIDTH = "250px";
	protected static final String CITY_WIDTH = "250px";
	protected static final String STATE_WIDTH = "100px";
	protected static final String POST_CODE_WIDTH = "100px";
	protected static final String PHONE_WIDTH = "200px";

	@Override
	protected Map<String, Column<T, ?>> createColumns(final DataGrid<T> grid) {
		Map<String, Column<T, ?>> columns = new HashMap<>();
		createTextColumn(columns, grid, NAME, NAME_WIDTH, true);
		createTextColumn(columns, grid, ADDRESS_LINE_1, ADDRESS_LINE_1_WIDTH, true);
		createTextColumn(columns, grid, ADDRESS_LINE_2, ADDRESS_LINE_2_WIDTH, true);
		createTextColumn(columns, grid, SUBURB, SUBURB_WIDTH, true);
		createTextColumn(columns, grid, CITY, CITY_WIDTH, true);
		createTextColumn(columns, grid, STATE, STATE_WIDTH, true);
		createTextColumn(columns, grid, POST_CODE, POST_CODE_WIDTH, false);
		createTextColumn(columns, grid, PHONE, PHONE_WIDTH, false);
		return columns;
	}

	@Override
	protected String resolveTextFieldValue(final String fieldName, final T entity) {
		switch (fieldName) {
		case NAME:
			return entity.getName();
		case ADDRESS_LINE_1:
			return entity.getAddressLineOne();
		case ADDRESS_LINE_2:
			return entity.getAddressLineTwo();
		case SUBURB:
			return entity.getSuburb();
		case CITY:
			return entity.getCity();
		case STATE:
			return entity.getState();
		case POST_CODE:
			return entity.getPostcode();
		case PHONE:
			return entity.getPhone();
		default:
			return null;
		}
	}

	@Override
	protected Date resolveDateFieldValue(final String fieldName, final T entity) {
		/* do nothing */
		return null;
	}

	@Override
	protected boolean resolveBooleanFieldValue(final String fieldName, final T entity) {
		/* do nothing */
		return false;
	}

	@Override
	protected void addComparators(final Map<String, Column<T, ?>> columns) {

		handler.setComparator(columns.get(NAME), new Comparator<T>() {

			@Override
			public int compare(final T one, final T two) {
				return one.getName().compareTo(two.getName());
			}
		});

		handler.setComparator(columns.get(ADDRESS_LINE_1), new Comparator<T>() {

			@Override
			public int compare(final T one, final T two) {
				return one.getAddressLineOne().compareTo(two.getAddressLineOne());
			}
		});

		handler.setComparator(columns.get(ADDRESS_LINE_2), new Comparator<T>() {

			@Override
			public int compare(final T one, final T two) {
				return one.getAddressLineTwo().compareTo(two.getAddressLineTwo());
			}
		});

		handler.setComparator(columns.get(SUBURB), new Comparator<T>() {

			@Override
			public int compare(final T one, final T two) {
				return one.getSuburb().compareTo(two.getSuburb());
			}
		});

		handler.setComparator(columns.get(CITY), new Comparator<T>() {

			@Override
			public int compare(final T one, final T two) {
				return one.getCity().compareTo(two.getCity());
			}
		});

		handler.setComparator(columns.get(STATE), new Comparator<T>() {

			@Override
			public int compare(final T one, final T two) {
				return one.getState().compareTo(two.getState());
			}
		});
	}

	@Override
	protected void addSortColumns(final DataGrid<T> grid, final Map<String, Column<T, ?>> columns) {
		grid.getColumnSortList().push(columns.get(NAME));
		grid.getColumnSortList().push(columns.get(ADDRESS_LINE_1));
		grid.getColumnSortList().push(columns.get(ADDRESS_LINE_2));
		grid.getColumnSortList().push(columns.get(SUBURB));
		grid.getColumnSortList().push(columns.get(CITY));
		grid.getColumnSortList().push(columns.get(STATE));
	}
}