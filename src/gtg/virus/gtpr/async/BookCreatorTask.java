package gtg.virus.gtpr.async;

import static gtg.virus.gtpr.utils.Utilities.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import nl.siegmann.epublib.domain.Author;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.epub.EpubReader;

import com.radaee.pdf.Document;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

		if(isPdf(params[0])){
			mDoc = new Document();
			mDoc.Open(params[0], "");
			Bitmap b = renderPage(mDoc);
			String title = mDoc.GetMeta("Title");
			String author = mDoc.GetMeta("Author");
			String subject = mDoc.GetMeta("Subject");
			String producer = mDoc.GetMeta("Producer");
			
			Log.i(TAG,"Title [" + title + "] Author [" + author + "]  Producer [" + producer + "]  Subject [" + subject +"]");
			newBook = new PBook(null, title, author, "Test", null,b);
			newBook.setPath(params[0]);
		}else if(isEpub(params[0])){
			EpubReader epubReader = new EpubReader();
        	Book epubBook = null;
        	try {
				epubBook = epubReader.readEpub(new FileInputStream(params[0]));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	if(epubBook != null){
        		newBook = new PBook();
        		final String title = epubBook.getTitle();
        		final String author = epubBook.getMetadata().getAuthors().get(0).toString();
        		List<String> authors = new ArrayList<String>();
        		for(Author auth : epubBook.getMetadata().getAuthors()){
        			authors.add(auth.getFirstname() + " " + auth.getLastname());
        		}
        		
        		newBook.setTitle(title);
        		newBook.setAuthor(author);
        		newBook.setAuthors(authors);
        		newBook.setPath(params[0]);
        		Bitmap page0 = null;
        		try {
					page0 = BitmapFactory.decodeStream(epubBook.getCoverImage().getInputStream());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		if(page0 != null){
        			newBook.setPage0(page0);
        		}	
        	}
		}
		return newBook;
	}



	@Override
	protected void onPostExecute(PBook result) {
		// TODO Auto-generated method stub
		if(result == null) return;
		super.onPostExecute(result);
		mAdapter.addBook(result);
		bookCache.put(result.getPath(), result);
		if(mDoc != null)
			mDoc.Close();

	}
	
	

}
