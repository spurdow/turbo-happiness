package gtg.virus.gtpr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import gtg.virus.gtpr.adapters.ShelfAdapter;
import gtg.virus.gtpr.entities.Book;
import gtg.virus.gtpr.entities.Page;
import gtg.virus.gtpr.entities.Shelf;
import gtg.virus.gtpr.utils.Utilities;
import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class NavigationalShelfListViewActivity extends ActionBarActivity {

	private ListView mListView = null;
	
	public final static int MAX_SHELVES = 20;
	
	public final static int MAX_BOOKS = 3;

	private static final String TAG = NavigationalShelfListViewActivity.class.getSimpleName();
	
	protected DrawerLayout mDrawerLayout = null;
	
	protected ListView mDrawerList = null;
	
	protected String[] titles = {"Schedule","Find Books","Sync"};
	
	private ActionBarDrawerToggle mDrawerToggle = null;
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

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
	    mDrawerList = (ListView) findViewById(R.id.left_drawer);

	    // Set the adapter for the list view
	    mDrawerList.setAdapter(new ArrayAdapter<String>(this,
	                android.R.layout.simple_list_item_1, titles));
/*	    // Set the list's click listener
	    mDrawerList.setOnItemClickListener(new DrawerItemClickListener(){
	    	
	    });
	    */
	    mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getActionBar().setTitle(R.string.drawer_close);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActionBar().setTitle(R.string.drawer_open);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
		

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		
		HashMap<String , String > data = new HashMap<String , String>();
		if(Utilities.isExternalStorageReadable()){
			Utilities.walkdir(Environment.getExternalStorageDirectory(), data);
			Utilities.walkdir(Environment.getDataDirectory(), data);
		}
		for(Entry<String , String> d : data.entrySet()){
			Log.i(TAG,"Test " +  d.getKey() + " "  + d.getValue());
		}
		Log.i(TAG,data.size() + " test" );
	}

	
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
	    super.onPostCreate(savedInstanceState);
	    // Sync the toggle state after onRestoreInstanceState has occurred.
	    mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	    super.onConfigurationChanged(newConfig);
	    mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Pass the event to ActionBarDrawerToggle, if it returns
	    // true, then it has handled the app icon touch event
	    if (mDrawerToggle.onOptionsItemSelected(item)) {
	        return true;
	    }
	    // Handle your other action bar items...

	    return super.onOptionsItemSelected(item);
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
