package gtg.virus.gtpr.adapters;

import gtg.virus.gtpr.R;
import gtg.virus.gtpr.entities.Book;
import gtg.virus.gtpr.entities.Shelf;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ShelfAdapter extends BaseAdapter {

	private Context mContext;
	
	private List<Shelf> shelves;
	
	private LayoutInflater inflater = null;
	
		
	
	/**
	 * @param mContext
	 * @param books
	 * @param inflater
	 */
	public ShelfAdapter(Context mContext, List<Shelf> shelves,
			LayoutInflater inflater) {
		super();
		this.mContext = mContext;
		this.shelves = shelves;
		this.inflater = LayoutInflater.from(mContext);
	}

	public ShelfAdapter(
			Context context,
			List<Shelf> shelves2) {
		// TODO Auto-generated constructor stub
		this(context, shelves2, null);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return shelves.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return shelves.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView == null){
			convertView = inflater.inflate(R.layout.shelf_row, null	);
			convertView.setBackgroundResource(R.drawable.shelf);
		}
		
		
		
		return convertView;
	}
	


}
