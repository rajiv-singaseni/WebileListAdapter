package com.webile.wigets;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import android.database.DataSetObserver;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class WebileListAdapter extends BaseAdapter {
	
	private String TAG = "WebileListAdapter";
	ArrayList<String> mRegisteredViewTypes;
	AdapterDataSource dataSource;
	
	//volatile data for quick access.
	int mNumberOfSections;
	ArrayList<Integer> mNumberOfRowsInSections;
	int mCount;
	private void constructVolatileData() {
		//do we even need this ??
		mCount = 0;
		mNumberOfSections = dataSource.numberOfSections();
		mNumberOfRowsInSections = new ArrayList<Integer>(mNumberOfSections);
		for(int section=0; section<mNumberOfSections; section++) {
			int numberOfRowsInCurrentSection = dataSource.numberOfRowsInSection(section);
			mNumberOfRowsInSections.add(new Integer(numberOfRowsInCurrentSection));
			mCount += 1+ numberOfRowsInCurrentSection;
			for(int j=0; j<numberOfRowsInCurrentSection; j++) {
				String viewTypeId = dataSource.getItemViewId(section, j);
				//do we already have this in our array;
				if(mRegisteredViewTypes.contains(viewTypeId)) {
					mRegisteredViewTypes.add(viewTypeId);
				}
			}
		}
		
	}
	
	public class IndexPath {
		public int row;
		public int section;
		private boolean header;
		public IndexPath() {
			header = false;
			section = -1;
			row = -1;
		}
		@Override
		public String toString() {
			return "IndexPath [row=" + row + ", section=" + section
					+ ", header=" + header + "]";
		}
	}

	private IndexPath translate(int position) {
		IndexPath ip = new IndexPath();
		for (int section=0; section< mNumberOfSections; section ++) {
			if(position == 0) {
				ip.header = true;
				ip.section = section;
				break;
			} 
			position--;
			int numberOfRowsInCurrentSection = mNumberOfRowsInSections.get(section).intValue();
			if(position < numberOfRowsInCurrentSection) {
				ip.section = section;
				ip.row = position;
				break;
			}
			position-= numberOfRowsInCurrentSection;
		}

		return ip;
	}
	
	public WebileListAdapter(AdapterDataSource dataSource) {
				
		this.dataSource = dataSource;
		mRegisteredViewTypes = new ArrayList<String>();
		mRegisteredViewTypes.add("HeaderViewTypeId");
		
		constructVolatileData();
		// TODO Auto-generated constructor stub
		this.registerDataSetObserver(new DataSetObserver() {
			@Override
			public void onChanged() {
				super.onChanged();
				//populate the meta data.
				constructVolatileData();
			}
			
			@Override
			public void onInvalidated() {
				// TODO Auto-generated method stub
				super.onInvalidated();
			}
		});
	}
		
	@Override
	public int getCount() {
		return mCount;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
//		IndexPath indexPath = translate(position);
		
		return null;
	}

	@Override
	public int getItemViewType(int position) {
		IndexPath indexPath = translate(position);
		String viewTypeId = dataSource.getItemViewId(indexPath.section, indexPath.row);
		return mRegisteredViewTypes.indexOf(viewTypeId);
	}
	
	@Override
	public int getViewTypeCount() {
		return mRegisteredViewTypes.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		IndexPath indexPath = translate(position);
		Log.v(TAG, "Position "+position+" translated to "+indexPath);
		if(indexPath.header) {
			return dataSource.getHeaderView(indexPath.section, convertView, parent);
		} else {
			return dataSource.getView(indexPath.section, indexPath.row, convertView, parent);
		}
	}
	
	public interface AdapterDataSource {
		public int numberOfSections();
		public int numberOfRowsInSection(int section);
		public View getView(int section, int row, View convertView, ViewGroup parent);
		public View getHeaderView(int section, View convertView, ViewGroup parent);
		public String getItemViewId(int section, int row);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

}
