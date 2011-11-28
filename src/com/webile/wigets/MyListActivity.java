package com.webile.wigets;

import java.util.ArrayList;

import android.R.color;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyListActivity extends ListActivity {
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("Scroll top");
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.v("MyListActivity", "Scrolling to top");
		setListAdapter(new WebileListAdapter(new MyAdapterDataSource1()));
		return true;
	}
	
	private ModifierGroup[] modifierGroups = new ModifierGroup[]{
			new ModifierGroup("Choose a Crust", new String[]{"Thin","Thick"}),
			new ModifierGroup("Choose a sauce", new String[]{"Tomato","Chilli"}),
	};
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new WebileListAdapter(new MyAdapterDataSource1()));
    }
    
    private class MyAdapterDataSource1 implements WebileListAdapter.AdapterDataSource {

    	LayoutInflater mLayoutInflater;

    	public MyAdapterDataSource1() {
    		mLayoutInflater = LayoutInflater.from(MyListActivity.this);
    	}

		@Override
		public int numberOfSections() {
			return 20;
		}

		@Override
		public int numberOfRowsInSection(int section) {
			return 20;
		}

		@Override
		public View getView(int section, int row, View convertView,
				ViewGroup parent) {
			if(convertView == null) {
				convertView = mLayoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
			}
			TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
			tv.setText("Cell "+(row+1));
			return convertView;
		}

		@Override
		public View getHeaderView(int section, View convertView,
				ViewGroup parent) {
			if(convertView == null) {
				convertView = new TextView(MyListActivity.this);
				convertView.setBackgroundColor(color.darker_gray);
			}
			TextView tv = (TextView)  convertView;
			tv.setText("Section "+section);
			return tv;
		}

		@Override
		public String getItemViewId(int section, int row) {
			return "CellIdentifier1";
		}

    }
    
    private class MyAdapterDataSource implements WebileListAdapter.AdapterDataSource {

    	LayoutInflater mLayoutInflater;

    	public MyAdapterDataSource() {
    		mLayoutInflater = LayoutInflater.from(MyListActivity.this);
    	}
    	
		@Override
		public int numberOfSections() {
			return modifierGroups.length;
		}

		@Override
		public int numberOfRowsInSection(int section) {
			// TODO Auto-generated method stub
			return modifierGroups[section].getModifers().size();
		}

		@Override
		public View getView(int section, int row, View convertView,
				ViewGroup parent) {
			if(convertView == null) {
				convertView = mLayoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
			}
			TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
			String modifier = modifierGroups[section].getModifers().get(row);
			tv.setText(modifier);
			return convertView;
		}

		@Override
		public String getItemViewId(int section, int row) {
			return "CellItemId1";
		}

		@Override
		public View getHeaderView(int section, View convertView,
				ViewGroup parent) {
			if(convertView == null) {
				convertView = new TextView(MyListActivity.this);
				convertView.setBackgroundColor(color.darker_gray);
			}
			TextView tv = (TextView)  convertView;
			tv.setText(modifierGroups[section].getTitle());
			return tv;
		}
    	
    }
}

class ModifierGroup {
	private String title;
	private ArrayList<String> modifers;
	
	
	public String getTitle() {
		return title + " ("+modifers.size()+")";
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public ArrayList<String> getModifers() {
		return modifers;
	}


	public void setModifers(ArrayList<String> modifers) {
		this.modifers = modifers;
	}


	public ModifierGroup(String title, String[] modifiers) {
		this.title = title;
		this.modifers = new  ArrayList<String>(modifiers.length);
		for (String string : modifiers) {
			this.modifers.add(string);
		}
	}
}