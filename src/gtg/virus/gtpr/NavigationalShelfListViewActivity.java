package gtg.virus.gtpr;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.commonsware.cwac.merge.MergeAdapter;
import com.ipaulpro.afilechooser.utils.FileUtils;
import com.radaee.pdf.Document;
import com.radaee.pdf.Global;
import com.radaee.pdf.Matrix;
import com.radaee.pdf.Page;

import gtg.virus.gtpr.adapters.ShelfAdapter;
import gtg.virus.gtpr.adapters.TitleListAdapter;
import gtg.virus.gtpr.async.AppLaunchTask;
import gtg.virus.gtpr.async.AppLaunchTask.AppLaunchListener;
import gtg.virus.gtpr.async.BookCreatorTask;
import gtg.virus.gtpr.db.Item;
import gtg.virus.gtpr.db.ItemHelper;
import gtg.virus.gtpr.entities.PBook;
import gtg.virus.gtpr.entities.Menu;
import gtg.virus.gtpr.entities.Shelf;
import gtg.virus.gtpr.entities.User;
import gtg.virus.gtpr.utils.Utilities;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
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
import android.widget.Toast;
import static gtg.virus.gtpr.utils.Utilities.*;
public class NavigationalShelfListViewActivity extends ActionBarActivity {

	private ListView mListView = null;
	
	private Document mDoc = null;

	private static final String TAG = NavigationalShelfListViewActivity.class.getSimpleName();

	private static final int REQUEST_CHOOSER = 12345;
	
	protected DrawerLayout mDrawerLayout = null;
	
	protected ListView mDrawerList = null;
	
	protected String[] titles = {"Schedule","Find Books","Sync"};
	
	private ActionBarDrawerToggle mDrawerToggle = null;
	
	private MergeAdapter mMergeAdapter = null;
	
	private ShelfAdapter mShelfAdapter = null;
	
	private ExecutorService mService = Executors.newFixedThreadPool(100);
	
	private ItemHelper iHelper = null;
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
		
        iHelper = new ItemHelper(NavigationalShelfListViewActivity.this);
        
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		
		AppLaunchTask task = new AppLaunchTask(this);
		task.setListener(new AppLaunchListener(){

			@Override
			public void onStartTask() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onFinishTask() {
				// TODO Auto-generated method stub
				
				
				List<Item> items = iHelper.list();
				
				if(items.size() > 0){
					for(Item item : items){
						BookCreatorTask bookTask = new BookCreatorTask(NavigationalShelfListViewActivity.this, mShelfAdapter);
						bookTask.execute(item.getPath());
					}
					
				}else{
					Toast.makeText(NavigationalShelfListViewActivity.this, "You dont have pdf's yet", Toast.LENGTH_LONG).show();
/*					Map<String , String> map = new HashMap<String , String>();
					
					walkDir();*/
				}
			}
			
		});
		task.execute(null,null,null);
		
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
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		// TODO Auto-generated method stub
		this.getMenuInflater().inflate(R.menu.main_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Pass the event to ActionBarDrawerToggle, if it returns
	    // true, then it has handled the app icon touch event
	    if (mDrawerToggle.onOptionsItemSelected(item)) {
	        return true;
	    }
	    // Handle your other action bar items...
	    switch(item.getItemId()){
	    case R.id.menu_add: 
	    	 // Create the ACTION_GET_CONTENT Intent
	        Intent getContentIntent = FileUtils.createGetContentIntent();

	        Intent intent = Intent.createChooser(getContentIntent, "Select a file");
	        startActivityForResult(intent, REQUEST_CHOOSER);
	    	break;
	    }
	    return super.onOptionsItemSelected(item);
	}
	
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch (requestCode) {
        case REQUEST_CHOOSER:   
            if (resultCode == RESULT_OK) {
            	final Uri uri = data.getData();
            	new AsyncTask<Void, Void,PBook>(){

            		private ProgressDialog mProgress;
					@Override
					protected void onPreExecute() {
						// TODO Auto-generated method stub
						super.onPreExecute();
						mProgress = new ProgressDialog(NavigationalShelfListViewActivity.this);
						mProgress.setMessage("Please wait...");
						mProgress.setIndeterminate(true);
						mProgress.show();
					}

					@Override
					protected PBook doInBackground(Void... params) {
						// TODO Auto-generated method stub

						
		                // Get the File path from the Uri
		                String path = FileUtils.getPath(NavigationalShelfListViewActivity.this, uri);
		                PBook b = null;
		                Log.i(TAG, "Path " + path);
		                // Alternatively, use FileUtils.getFile(Context, Uri)
		                if (path != null && Utilities.isValideBook(path) && FileUtils.isLocal(path)) {
		                    if(Utilities.isPdf(path) && iHelper != null){
		                    	Item i = new Item();
		                    	i.setPath(path);
		                    	iHelper.add(i);
		                    	
		                    	
		                    	mDoc = new Document();
		                    	int index = mDoc.Open(path, "");
		 
		                    	if( index == 0){
			                    	String author = mDoc.GetMeta("Author");
			                    	String title = mDoc.GetMeta("Title");
			                    	String subject = mDoc.GetMeta("Subject");
			                    	Log.i(TAG,"Meta " + author + " " + title + " " + subject);
			                    	b = new PBook();
			                    	b.setAuthor(author);
			                    	b.setTitle(title);
			                    	Bitmap page0 = Utilities.renderPage(mDoc);
			                    	b.setPage0(page0);
		                    	}else if(index == -1){
		                    		makeText("Password needed");
		                    	}else if(index == -2){
		                    		makeText("Unknown Encryption");
		                    	}else if(index == -3){
		                    		makeText("Damage or Invalid Format");
		                    	}else if(index == -10){
		                    		makeText("Access Denied");
		                    	}
		                    	
		                    }else if(Utilities.isEpub(path)){
		                    	
		                    }
		                }else if(!Utilities.isValideBook(path)){
		                	makeText( "File is not a pdf/epub/txt");
		                }
	    
						return b;
					}
					
					private void makeText(final String msg){
						runOnUiThread(new Runnable(){

							@Override
							public void run() {
								// TODO Auto-generated method stub
								Toast.makeText(NavigationalShelfListViewActivity.this, msg, Toast.LENGTH_SHORT).show();
							}
							
						});
					}
					@Override
					protected void onPostExecute(PBook result) {
						// TODO Auto-generated method stub
						super.onPostExecute(result);
						if(result != null){
							mShelfAdapter.addBook(result);
						}
						mProgress.dismiss();
					}
					
					
            		
            	}.executeOnExecutor(mService, null,null,null);
	        }
            break;
    }
		super.onActivityResult(requestCode, resultCode, data);
	}


	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(mDoc != null){
			mDoc.Close();
			mDoc = null;
		}
		
		if(iHelper != null){
			iHelper = null;
		}
		
		Global.RemoveTmp();
	}
	

	
}
