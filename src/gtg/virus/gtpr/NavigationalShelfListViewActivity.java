package gtg.virus.gtpr;

import java.util.ArrayList;
import java.util.List;

import gtg.virus.gtpr.adapters.ShelfAdapter;
import gtg.virus.gtpr.entities.Book;
import gtg.virus.gtpr.entities.Page;
import gtg.virus.gtpr.entities.Shelf;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;


public class NavigationalShelfListViewActivity extends ActionBarActivity {

	private ListView mListView = null;
	
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
				
		mListView = (ListView) findViewById(R.id.shelf_list_view);
		
		List<Shelf> shelves = new ArrayList<Shelf>();
		for(int i = 0 ; i < MAX_SHELVES ; i ++){
			Shelf shelf = new Shelf();
			for(int x = 0 ; x < MAX_BOOKS ; x++){
				List<Page> pages = generatePages();
				Book b = new Book(pages, "Shelf " + i + " Book " + x, "Author " + x, "Path " + x, null);
				shelf.addBook(b);
			}
			shelves.add(shelf);
		}
		
		ShelfAdapter shelfAdapter = new ShelfAdapter(this , shelves );
		mListView.setAdapter(shelfAdapter);

		
		
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
