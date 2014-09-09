package gtg.virus.gtpr.async;

import static gtg.virus.gtpr.utils.Utilities.renderPage;

import com.radaee.pdf.Document;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import gtg.virus.gtpr.adapters.ShelfAdapter;
import gtg.virus.gtpr.entities.PBook;
import gtg.virus.gtpr.utils.Utilities;

public class BookCreatorTask extends AsyncTask<String, Void , PBook>{

	private static final String TAG = BookCreatorTask.class.getSimpleName();

	private Context mContext;
	
	private ShelfAdapter mAdapter;
	
	private Document mDoc;
	
	
	
	public BookCreatorTask(Context mContext, ShelfAdapter mAdapter) {
		super();
		this.mContext = mContext;
		this.mAdapter = mAdapter;
	}



	@Override
	protected PBook doInBackground(String... params) {
		// TODO Auto-generated method stub
		PBook newBook = null;
		if(Utilities.isPdf(params[0])){
			mDoc = new Document();
			mDoc.Open(params[0], "");
			Bitmap b = renderPage(mDoc);
			String title = mDoc.GetMeta("Title");
			String author = mDoc.GetMeta("Author");
			String subject = mDoc.GetMeta("Subject");
			String producer = mDoc.GetMeta("Producer");
			
			Log.i(TAG,"Title [" + title + "] Author [" + author + "]  Producer [" + producer + "]  Subject [" + subject +"]");
			newBook = new PBook(null, title, author, "Test", null,b);
		}else if(Utilities.isEpub(params[0])){
			
		}
		return newBook;
	}



	@Override
	protected void onPostExecute(PBook result) {
		// TODO Auto-generated method stub
		if(result == null) return;
		super.onPostExecute(result);
		mAdapter.addBook(result);
		mDoc.Close();

	}
	
	

}
