package gtg.virus.gtpr;

import java.util.ArrayList;
import java.util.List;

import gtg.virus.gtpr.adapters.ShelfAdapter;
import gtg.virus.gtpr.entities.Book;
import gtg.virus.gtpr.entities.Page;
import gtg.virus.gtpr.entities.Shelf;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class NavigationalShelfListViewActivity extends ActionBarActivity {

	
	public final static int MAX_SHELVES = 20;
	
	public final static int MAX_BOOKS = 3;
	/* (non-Javadoc)
	 * @see android.support.v7.app.ActionBarActivity#onCreate(android.os.Bundle)
	 */
	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
				
/*		mListView = (ListView) findViewById(R.id.shelf_list_view);
		
		List<Shelf> shelves = new ArrayList<Shelf>();
		for(int i = 0 ; i < MAX_SHELVES ; i ++){
			Shelf shelf = new Shelf();
			for(int x = 0 ; x < MAX_BOOKS ; x++){
				List<Page> pages = generatePages();
				Book b = new Book(pages, "Book " + x + " Shelf " + i, "Author " + x, "Path " + x, null);
				shelf.addBook(b);
			}
			shelves.add(shelf);
		}
		
		ShelfAdapter shelfAdapter = new ShelfAdapter(this , shelves );
		mListView.setAdapter(shelfAdapter);
*/
		
		int numRow = 8;
		int numCol = 3;

		TableLayout tblLayout = (TableLayout) findViewById(R.id.tblLayout);

		for(int i = 0; i < numRow; i++) {
		    HorizontalScrollView HSV = new HorizontalScrollView(this);
		    HSV.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
		            LayoutParams.MATCH_PARENT));

		    TableRow tblRow = new TableRow(this);
		    tblRow.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 
		            LayoutParams.WRAP_CONTENT));
		    tblRow.setBackgroundResource(R.drawable.shelf);

		    for(int j = 0; j < numCol; j++) {
		        View v = LayoutInflater.from(this).inflate(R.layout.shelf_item, null);

		        tblRow.addView(v,j);
/*		        tblRow.setDividerDrawable(getResources().getDrawable(R.drawable.linear_layout_vertical_divider));
		        tblRow.setShowDividers(TableRow.SHOW_DIVIDER_MIDDLE);*/
		    }

		    HSV.addView(tblRow);
		    tblLayout.addView(HSV, i);
		}
		
	}
	
	private List<Page> generatePages(){
		int max_pages = 10;
		List<Page> pages = new ArrayList<Page>();
		for(int i = 0 ; i < max_pages ; i++ ){
			Page p = new Page(0, "Test Content in page " + i );
			pages.add(p);
		}
		return pages;
	}
	

}
