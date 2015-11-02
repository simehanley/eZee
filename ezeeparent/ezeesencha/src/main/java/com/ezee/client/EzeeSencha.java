package com.ezee.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.GXT;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.state.client.CookieProvider;
import com.sencha.gxt.state.client.GridStateHandler;
import com.sencha.gxt.state.client.StateManager;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Resizable;
import com.sencha.gxt.widget.core.client.Resizable.Dir;
import com.sencha.gxt.widget.core.client.button.IconButton.IconConfig;
import com.sencha.gxt.widget.core.client.button.ToolButton;
import com.sencha.gxt.widget.core.client.event.CollapseEvent;
import com.sencha.gxt.widget.core.client.event.CollapseEvent.CollapseHandler;
import com.sencha.gxt.widget.core.client.event.ExpandEvent;
import com.sencha.gxt.widget.core.client.event.ExpandEvent.ExpandHandler;
import com.sencha.gxt.widget.core.client.form.SimpleComboBox;
import com.sencha.gxt.widget.core.client.grid.CellSelectionModel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.GridSelectionModel;
import com.sencha.gxt.widget.core.client.grid.GridView;
import com.sencha.gxt.widget.core.client.selection.CellSelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.CellSelectionChangedEvent.CellSelectionChangedHandler;
import com.sencha.gxt.widget.core.client.tips.QuickTip;

public class EzeeSencha implements IsWidget, EntryPoint {

	private static final StockProperties props = GWT.create(StockProperties.class);

	interface MyUiBinder extends UiBinder<ContentPanel, EzeeSencha> {
	}

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	private ContentPanel panel;

	@UiField(provided = true)
	ColumnModel<Stock> cm;

	@UiField(provided = true)
	ListStore<Stock> store;

	@UiField
	GridView<Stock> view;

	@UiField
	Grid<Stock> grid;

	@UiField
	SimpleComboBox<String> typeCombo;

	@UiField(provided = true)
	IconConfig toolButtonIcon = ToolButton.QUESTION;

	@Override
	public Widget asWidget() {
		if (panel == null) {
			ColumnConfig<Stock, String> nameCol = new ColumnConfig<Stock, String>(props.name(), 50, "Company");
			ColumnConfig<Stock, String> symbolCol = new ColumnConfig<Stock, String>(props.symbol(), 100, "Symbol");
			ColumnConfig<Stock, Double> lastCol = new ColumnConfig<Stock, Double>(props.last(), 75, "Last");
			ColumnConfig<Stock, Double> changeCol = new ColumnConfig<Stock, Double>(props.change(), 100, "Change");
			ColumnConfig<Stock, Date> lastTransCol = new ColumnConfig<Stock, Date>(props.lastTrans(), 100,
					"Last Updated");

			final NumberFormat number = NumberFormat.getFormat("0.00");
			changeCol.setCell(new AbstractCell<Double>() {
				@Override
				public void render(Context context, Double value, SafeHtmlBuilder sb) {
					String style = "style='color: " + (value < 0 ? "red" : "green") + "'";
					String v = number.format(value);
					sb.appendHtmlConstant("<span " + style + " qtitle='Change' qtip='" + v + "'>" + v + "</span>");
				}
			});

			lastTransCol.setCell(new DateCell(DateTimeFormat.getFormat("MM/dd/yyyy")));

			List<ColumnConfig<Stock, ?>> columns = new ArrayList<ColumnConfig<Stock, ?>>();
			columns.add(nameCol);
			columns.add(symbolCol);
			columns.add(lastCol);
			columns.add(changeCol);
			columns.add(lastTransCol);

			cm = new ColumnModel<Stock>(columns);

			store = new ListStore<Stock>(props.key());
			store.add(new Stock("Apple", "APP", 99.00, 99.01, new Date()));
			store.add(new Stock("MyOB", "MyOB", 99.00, 99.01, new Date()));
			store.add(new Stock("Microsoft", "MCRF", 99.00, 99.01, new Date()));

			// UiBinder instantiates the widgets
			panel = uiBinder.createAndBindUi(this);
			// panel.getHeader().setIcon(ExampleImages.INSTANCE.table());
			final Resizable resizable = new Resizable(panel, Dir.E, Dir.SE, Dir.S);
			panel.addExpandHandler(new ExpandHandler() {
				@Override
				public void onExpand(ExpandEvent event) {
					resizable.setEnabled(true);
				}
			});
			panel.addCollapseHandler(new CollapseHandler() {
				@Override
				public void onCollapse(CollapseEvent event) {
					resizable.setEnabled(false);
				}
			});

			typeCombo.add("Row");
			typeCombo.add("Cell");
			typeCombo.setValue("Row");
			// Change selection model on select, not value change which fires on
			// blur
			typeCombo.addSelectionHandler(new SelectionHandler<String>() {
				@Override
				public void onSelection(SelectionEvent<String> event) {
					boolean cell = event.getSelectedItem().equals("Cell");
					if (cell) {
						CellSelectionModel<Stock> c = new CellSelectionModel<Stock>();
						c.addCellSelectionChangedHandler(new CellSelectionChangedHandler<Stock>() {
							@Override
							public void onCellSelectionChanged(CellSelectionChangedEvent<Stock> event) {
							}
						});
						grid.setSelectionModel(c);
					} else {
						grid.setSelectionModel(new GridSelectionModel<Stock>());
					}
				}
			});

			view.setAutoExpandColumn(nameCol);

			new QuickTip(grid);

			// Stage manager, load the previous state
			GridStateHandler<Stock> state = new GridStateHandler<Stock>(grid);
			state.loadState();
		}

		return panel;
	}

	@Override
	public void onModuleLoad() {
		// State manager, initialize the state options
		StateManager.get().setProvider(new CookieProvider("/", null, null, GXT.isSecure()));

		RootPanel.get().add(asWidget());
	}

	// private FramedPanel panel;
	// private Stock stock;
	// private StockProperties properties = GWT.create(StockProperties.class);
	// private StockDriver driver = GWT.create(StockDriver.class);
	//
	// public void onModuleLoad() {
	// RootLayoutPanel.get().add(asWidget());
	// }
	//
	// interface MyUiBinder extends UiBinder<FramedPanel, EzeeSencha> {
	// }
	//
	// interface StockDriver extends SimpleBeanEditorDriver<Stock, EzeeSencha> {
	// }
	//
	// interface StockTemplate extends XTemplates {
	// @XTemplate(source = "EzeeSencha.html")
	// SafeHtml drawStock(Stock stock);
	// }
	//
	// private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
	//
	// @UiField
	// @Ignore
	// ComboBox<Stock> scb;
	// @UiField
	// CssFloatLayoutContainer inner;
	//
	// // editor fields
	// @UiField
	// TextField name;
	// @UiField
	// TextField symbol;
	// @UiField
	// DoubleField last;
	// @UiField
	// DoubleField change;
	// @UiField
	// DateField lastTrans;
	//
	// @UiField
	// @Ignore
	// HTML display;
	// @UiField(provided = true)
	// NumberFormat numberFormat = NumberFormat.getFormat("0.00");
	// @UiField(provided = true)
	// ListStore<Stock> stockStore;
	// @UiField(provided = true)
	// LabelProvider<Stock> stockLabelProvider = properties.nameLabel();
	// @UiField
	// HorizontalPanel hp;
	//
	// public Widget asWidget() {
	// if (panel == null) {
	// stockStore = new ListStore<Stock>(properties.key());
	// stockStore.add(new Stock("Apple", "APP", 99.01, 99.00, new Date()));
	// stockStore.add(new Stock("MyOB", "My", 99.01, 99.00, new Date()));
	//
	// stock = stockStore.get(0);
	//
	// panel = uiBinder.createAndBindUi(this);
	//
	// hp.setCellWidth(display, "200");
	//
	// last.addValidator(new MinNumberValidator<Double>(0D));
	//
	// symbol.addValidator(new RegExValidator("^[^a-z]+$", "Only uppercase
	// letters allowed"));
	// symbol.setAutoValidate(true);
	//
	// display.setHTML(getUpdatedPanel());
	//
	// panel.add(hp);
	//
	// driver.initialize(this);
	// scb.setValue(stock);
	// driver.edit(stock);
	// }
	//
	// return panel;
	// }
	//
	// @UiHandler("scb")
	// public void nameComboChange(SelectionEvent<Stock> event) {
	// symbol.clearInvalid();
	// change.clearInvalid();
	// last.clearInvalid();
	// lastTrans.clearInvalid();
	//
	// stock = event.getSelectedItem();
	// driver.edit(stock);
	// display.setHTML(getUpdatedPanel());
	// }
	//
	// @UiHandler("reset")
	// public void resetClicked(SelectEvent event) {
	// FormPanelHelper.reset(inner);
	// driver.edit(stock);
	// }
	//
	// @UiHandler("save")
	// public void saveClicked(SelectEvent event) {
	// stock = driver.flush();
	// if (driver.hasErrors()) {
	// new MessageBox("Please correct the errors before saving.").show();
	// return;
	// }
	// display.setHTML(getUpdatedPanel());
	// stockStore.update(stock);
	// }
	//
	// private SafeHtml getUpdatedPanel() {
	// StockTemplate template = GWT.create(StockTemplate.class);
	// return template.drawStock(stock);
	// }

	// public Widget asWidget() {
	// if (panel == null) {
	// SelectHandler selectHandler = new SelectHandler() {
	// @Override
	// public void onSelect(SelectEvent event) {
	// Info.display("Click", ((TextButton) event.getSource()).getText() + "
	// clicked");
	// }
	// };
	//
	// FramedPanel framedPanelStart = new FramedPanel();
	// // Align buttons to the start or left in ltr
	// framedPanelStart.setButtonAlign(BoxLayoutPack.START); // Left
	// framedPanelStart.setHeadingText("Button Aligning Example: " +
	// BoxLayoutPack.START);
	// framedPanelStart.setPixelSize(500, 150);
	// framedPanelStart.addStyleName("white-bg");
	// framedPanelStart.add(new HTML());
	// framedPanelStart.getElement().setMargins(new Margins(5));
	// framedPanelStart.addButton(new TextButton("Button 1", selectHandler));
	// framedPanelStart.addButton(new TextButton("Button 2", selectHandler));
	// framedPanelStart.addButton(new TextButton("Button 3", selectHandler));
	//
	// FramedPanel framedPanelCenter = new FramedPanel();
	// // Align buttons to the center
	// framedPanelCenter.setButtonAlign(BoxLayoutPack.CENTER); // Center
	// framedPanelCenter.setHeadingText("Button Aligning Example: " +
	// BoxLayoutPack.CENTER);
	// framedPanelCenter.setPixelSize(500, 150);
	// framedPanelCenter.addStyleName("white-bg");
	// framedPanelCenter.add(new HTML());
	// framedPanelCenter.getElement().setMargins(new Margins(5));
	// framedPanelCenter.addButton(new TextButton("Button 1", selectHandler));
	// framedPanelCenter.addButton(new TextButton("Button 2", selectHandler));
	// framedPanelCenter.addButton(new TextButton("Button 3", selectHandler));
	//
	// FramedPanel framedPanelEnd = new FramedPanel();
	// // Align buttons to the end or right in ltr
	// framedPanelEnd.setButtonAlign(BoxLayoutPack.END); // Right
	// framedPanelEnd.setHeadingText("Button Aligning Example: " +
	// BoxLayoutPack.END);
	// framedPanelEnd.setPixelSize(500, 150);
	// framedPanelEnd.addStyleName("white-bg");
	// framedPanelEnd.add(new HTML());
	// framedPanelEnd.getElement().setMargins(new Margins(5));
	// framedPanelEnd.addButton(new TextButton("Button 1", selectHandler));
	// framedPanelEnd.addButton(new TextButton("Button 2", selectHandler));
	// framedPanelEnd.addButton(new TextButton("Button 3", selectHandler));
	//
	// panel = new FlowLayoutContainer();
	// panel.add(framedPanelStart, new MarginData(10));
	// panel.add(framedPanelCenter, new MarginData(10));
	// panel.add(framedPanelEnd, new MarginData(10));
	// }
	//
	// return panel;
	// }
}
