package gtg.virus.gtpr.adapters;

import gtg.virus.gtpr.R;
import gtg.virus.gtpr.entities.Book;
import gtg.virus.gtpr.entities.Shelf;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

	
	public void addBook(Book b){
		if(!shelves.isEmpty()){
			Shelf shelf = shelves.get(shelves.size()-1);
			int sizeOfShelf = shelf.getBooks().size();
			if(sizeOfShelf < shelf.getMax()){
				shelf.addBook(b);
			}else{
				Shelf newShelf = new Shelf();
				newShelf.addBook(b);
				shelves.add(newShelf);
			}
		}else{
			Shelf shelf = new Shelf();
			shelf.addBook(b);
			shelves.add(shelf);
		}
		
		notifyDataSetChanged();
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
		ViewHolder viewHolder = null;
		
		viewHolder = new ViewHolder();
		convertView = inflater.inflate(R.layout.shelf_row, null);
		viewHolder.shelf_parent = (LinearLayout) convertView.findViewById(R.id.shelf_parent);
		Shelf shelf = (Shelf) getItem(position);
		if(shelf != null){
			int maxSize  = shelf.getBooks().size();
			Log.i("ShelfAdapter", "MAX SIZE " + maxSize);
			
			for(int i = 0 ; i < maxSize ; i++){
				Book b = shelf.getBook(i);
/*				if(i == 0){
					resId = R.id.shelf_1;
					b = shelf.getBook(i);
				}else if(i == 1){
					resId = R.id.shelf_2;
					b = shelf.getBook(i);
				}else if(i == 2){
					resId = R.id.shelf_3;
					b = shelf.getBook(i);
				}*/
				if(viewHolder.shelf_parent.getChildCount() >= shelf.getMax()) break;
				
				if(b != null){
					FrameLayout ff = (FrameLayout) inflater.inflate(R.layout.shelf_item, null);
					FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT , LayoutParams.WRAP_CONTENT);
					params.setMargins(50, 0, 50, 0);
					params.gravity = Gravity.LEFT;
					ff.setLayoutParams(params);
					
					TextView tv = (TextView) ff.findViewById(R.id.title);
					tv.setText(b.getTitle());
					viewHolder.shelf_parent.addView(ff, i);
				
				}
			}

		}
			
		
		return convertView;
	}
	
	
	private class ViewHolder{
		LinearLayout shelf_parent;
	}

}
