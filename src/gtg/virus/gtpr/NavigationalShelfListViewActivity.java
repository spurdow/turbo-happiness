package gtg.virus.gtpr;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.commonsware.cwac.merge.MergeAdapter;
import com.radaee.pdf.Document;
import com.radaee.pdf.Global;
import com.radaee.pdf.Matrix;
import com.radaee.pdf.Page;

import gtg.virus.gtpr.adapters.ShelfAdapter;
import gtg.virus.gtpr.adapters.TitleListAdapter;
import gtg.virus.gtpr.async.BookCreatorTask;
import gtg.virus.gtpr.entities.Book;
import gtg.virus.gtpr.entities.Menu;
import gtg.virus.gtpr.entities.Shelf;
import gtg.virus.gtpr.entities.User;
import gtg.virus.gtpr.utils.Utilities;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import static gtg.virus.gtpr.utils.Utilities.*;
public class NavigationalShelfListViewActivity extends ActionBarActivity {

	private ListView mListView = null;
	
	private Document mDoc = null;
	
/*	public final static int MAX_SHELVES = 20;
	
	public final static int MAX_BOOKS = 3;*/

	private static final String TAG = NavigationalShelfListViewActivity.class.getSimpleName();
	
	protected DrawerLayout mDrawerLayout = null;
	
	protected ListView mDrawerList = null;
	
	protected String[] titles = {"Schedule","Find Books","Sync"};
	
	private ActionBarDrawerToggle mDrawerToggle = null;
	
	private MergeAdapter mMergeAdapter = null;
	
	private ShelfAdapter mShelfAdapter = null;
	
	private ExecutorService mService = Executors.newFixedThreadPool(100);
	/* (non-Javadoc)
	 * @see android.support.v7.app.ActionBarActivity#onCreate(android.os.Bundle)
	 */
	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Global.Init( this );
		setContentView(R.layout.activity_main);
				
		mListView = (ListView) findViewById(R.id.shelf_list_view);
		/*
		
		for(int i = 0 ; i < MAX_SHELVES ; i ++){
			Shelf shelf = new Shelf();
			for(int x = 0 ; x < MAX_BOOKS ; x++){
				List<Page> pages = generatePages();
				Book b = new Book(pages, "Shelf " + i + " Book " + x, "Author " + x, "Path " + x, null);
				shelf.addBook(b);
			}
			shelves.add(shelf);
		}*/
		List<Shelf> shelves = new ArrayList<Shelf>();
		mShelfAdapter = new ShelfAdapter(this , shelves );
		mListView.setAdapter(mShelfAdapter);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
	    mDrawerList = (ListView) findViewById(R.id.left_drawer);

	    // merge adapter
	    mMergeAdapter = new MergeAdapter();
	    
	    final TextView userName = new TextView(this);
	    User user = Utilities.getUser(this);
	    
	    if(user != null)
	    	userName.setText(user.getFullname());
	    else
	    	userName.setText("Not yet available.");
	    mMergeAdapter.addView(userName);
	    // main menu
	    addMainMenu();
	    
	    // settings
	    addSettings();

	    // Set the adapter for the list view
	    mDrawerList.setAdapter(mMergeAdapter);
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
		Log.i(TAG, "External " + Environment.getExternalStorageDirectory().toString());
		Log.i(TAG, "Others " + Environment.getRootDirectory().toString());

		if(Utilities.isExternalStorageReadable()){

			//Utilities.walkdir(Environment.getExternalStorageDirectory(), data);
			//Utilities.walkdir(Environment.getDataDirectory(), data);
			
		}
		// temporary
		File storageDir = new File("/storage/sdcard1");
		if(storageDir.exists()){
			if(storageDir.canRead() ){
				Utilities.walkdir(storageDir, data);
			}
		}
		

		for(final Entry<String , String> d : data.entrySet()){
			
			new BookCreatorTask(this, mShelfAdapter).execute(d.getValue());
		
		}
		
		
	}
	
	

	private void addMainMenu(){

		final String mainMenu = "Main Menu";
		final LayoutInflater inflater = LayoutInflater.from(this);
		final View v = inflater.inflate(R.layout.layout_preference_section_header, null);
		final TextView txtView = (TextView) v.findViewById(R.id.list_item_section_text);
		txtView.setText(mainMenu);
		mMergeAdapter.addView(v);
		List<Menu> mMenu = new ArrayList<Menu>();
		mMenu.add(new Menu(BitmapFactory.decodeResource(getResources(),R.drawable.ic_audio_play) , "Bookmarked Books"));
		mMenu.add(new Menu(BitmapFactory.decodeResource(getResources(),R.drawable.ic_audio_play) , "Annotated Books"));
		mMenu.add(new Menu(BitmapFactory.decodeResource(getResources(),R.drawable.ic_audio_play) , "Audio Books"));
		mMenu.add(new Menu(BitmapFactory.decodeResource(getResources(),R.drawable.ic_menu_calendar) , "Schedule Books"));

		
		final TitleListAdapter mAdapter = new TitleListAdapter(this, mMenu);
		mMergeAdapter.addAdapter(mAdapter);
	
	}
	
	private void addSettings(){

		final String settings = "Settings";
		final LayoutInflater inflater = LayoutInflater.from(this);
		final View v = inflater.inflate(R.layout.layout_preference_section_header, null);
		final TextView txtView = (TextView) v.findViewById(R.id.list_item_section_text);
		txtView.setText(settings);
		mMergeAdapter.addView(v);
		List<Menu> mMenu = new ArrayList<Menu>();
		mMenu.add(new Menu(BitmapFactory.decodeResource(getResources(),R.drawable.ic_action_settings) , "Settings"));
		mMenu.add(new Menu(BitmapFactory.decodeResource(getResources(),R.drawable.ic_menu_help) , "Help"));
		final TitleListAdapter mAdapter = new TitleListAdapter(this, mMenu);
		mMergeAdapter.addAdapter(mAdapter);
	
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
	
	
/*	
	private List<Page> generatePages(){
		int max_pages = 10;
		List<Page> pages = new ArrayList<Page>();
		for(int i = 0 ; i < max_pages ; i++ ){
			Page p = new Page(0, "Test Content in page " + i );
			pages.add(p);
		}
		return pages;
	}*/

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(mDoc != null){
			mDoc.Close();
			mDoc = null;
		}
		Global.RemoveTmp();
	}
	

	
}
