package com.ezee.client.main;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Resizable;
import com.sencha.gxt.widget.core.client.Resizable.Dir;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.CollapseEvent;
import com.sencha.gxt.widget.core.client.event.CollapseEvent.CollapseHandler;
import com.sencha.gxt.widget.core.client.event.ExpandEvent;
import com.sencha.gxt.widget.core.client.event.ExpandEvent.ExpandHandler;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.GridView;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

public class BasicGrid extends Composite {
	// Basic model for each row in the grid
	public class NameModel {

		private int id;
		private String firstname;
		private String lastname;
		private String user;

		public NameModel(int id, String firstname, String lastname, String user) {
			super();
			this.id = id;
			this.firstname = firstname;
			this.lastname = lastname;
			this.user = user;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getFirstname() {
			return firstname;
		}

		public void setFirstname(String firstname) {
			this.firstname = firstname;
		}

		public String getLastname() {
			return lastname;
		}

		public void setLastname(String lastname) {
			this.lastname = lastname;
		}

		public String getUser() {
			return user;
		}

		public void setUser(String user) {
			this.user = user;
		}

	}

	// Property access definitions for the values in the NameModel
	public interface GridProperties extends PropertyAccess<NameModel> {
		ModelKeyProvider<NameModel> id();

		ValueProvider<NameModel, String> user();

		ValueProvider<NameModel, String> firstname();

		ValueProvider<NameModel, String> lastname();
	}

	// Setup the property access definitions for the values for the grid columns
	public static GridProperties gridProperties = GWT.create(GridProperties.class);

	public BasicGrid() {
		// Setup the ListStore.
		ListStore<NameModel> listStore = new ListStore<NameModel>(gridProperties.id());

		listStore.add(new NameModel(1, "James", "Kirk", "jkirk"));
		listStore.add(new NameModel(2, "Mr", "Spock", "mspock"));
		listStore.add(new NameModel(3, "Mr", "Blah", "blah"));

		// Setup the grid columns
		ColumnConfig<NameModel, String> userCol = new ColumnConfig<NameModel, String>(gridProperties.user(), 100,
				"User");
		ColumnConfig<NameModel, String> firstnameCol = new ColumnConfig<NameModel, String>(gridProperties.firstname(),
				100, "Firstname");
		ColumnConfig<NameModel, String> lastnameCol = new ColumnConfig<NameModel, String>(gridProperties.lastname(),
				100, "Lastname");

		List<ColumnConfig<NameModel, ?>> columns = new ArrayList<ColumnConfig<NameModel, ?>>();
		columns.add(firstnameCol);
		columns.add(lastnameCol);
		columns.add(userCol);
		ColumnModel<NameModel> columnModel = new ColumnModel<NameModel>(columns);

		// Setup the grid view for styling
		GridView<NameModel> gridView = new GridView<NameModel>();
		//gridView.setAutoFill(true);
		gridView.setAutoExpandColumn(userCol);

		// Setup the grid
		Grid<NameModel> grid = new Grid<NameModel>(listStore, columnModel, gridView);
		// Setup a size if not depending on the parent container to give it a
		// size.
		// grid.setPixelSize(300, 200);

		// grid.setSize("100%", "100%");

		HorizontalLayoutContainer container = new HorizontalLayoutContainer();
		container.setSize("100%", "100%");
		container.add(grid);

		VerticalLayoutContainer con = new VerticalLayoutContainer();
		con.add(grid, new VerticalLayoutData(1, 1));

		ContentPanel panel = new ContentPanel();
		panel.setHeadingText("Basic Grid");
		panel.setPixelSize(600, 300);
		// panel.setSize("100%", "100%");
		panel.addStyleName("margin-10");

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
		panel.setWidget(con);

		initWidget(panel);
	}
}