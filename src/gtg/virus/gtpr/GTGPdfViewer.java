package gtg.virus.gtpr;

import com.google.gson.Gson;
import com.radaee.pdf.Document;
import com.radaee.pdf.Global;
import com.radaee.pdf.Page.Annotation;
import com.radaee.reader.PDFReader;
import com.radaee.reader.PDFReader.PDFReaderListener;
import com.radaee.view.PDFVPage;

import gtg.virus.gtpr.entities.PBook;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ProgressBar;
import static gtg.virus.gtpr.utils.Utilities.*;

public class GTGPdfViewer extends ActionBarActivity implements PDFReaderListener{

	private static final String TAG = GTGPdfViewer.class.getSimpleName();

	private PBook mBook = null;
	
	private Document mDoc = new Document();
	
	private PDFReader mReader = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		
		
		setContentView(R.layout.pdf_main_layout);
		
		
		mReader = (PDFReader) findViewById(R.id.pdf_reader_view);
		
		
		Bundle extras = getIntent().getExtras();
		if(extras != null){
			String gsonExtra =  extras.getString(PIN_EXTRA_PBOOK);
			
			mBook = new Gson().fromJson(gsonExtra, PBook.class);
		}
		
		

		mDoc.Open(mBook.getPath(), null);
		mReader.PDFOpen(mDoc, false, this);
		
	}
	
	

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onResume();

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if(mDoc != null){
			mDoc.Close();
		}
		
		if(mReader != null){
			mReader.PDFClose();
		}
	
	}
	@Override
	public void onBackPressed() {
		super.onBackPressed();

		finish();
	}
	

	@Override
	public void OnPageModified(int pageno) {
		// TODO Auto-generated method stub
		Log.i(TAG, "OnPageModified " + pageno);
	}

	@Override
	public void OnPageChanged(int pageno) {
		// TODO Auto-generated method stub
		Log.i(TAG, "OnPageChanged " + pageno);
	}

	@Override
	public void OnAnnotClicked(PDFVPage vpage, Annotation annot) {
		// TODO Auto-generated method stub
		Log.i(TAG, "OnAnnotClicked");
	}

	@Override
	public void OnSelectEnd(String text) {
		// TODO Auto-generated method stub
		Log.i(TAG, "OnSelectEnd " + text);
	}

	@Override
	public void OnOpenURI(String uri) {
		// TODO Auto-generated method stub
		Log.i(TAG, "OnOpenURI " + uri);
	}

	@Override
	public void OnOpenJS(String js) {
		// TODO Auto-generated method stub
		Log.i(TAG, "OnOpenJS " + js);
	}

	@Override
	public void OnOpenMovie(String path) {
		// TODO Auto-generated method stub
		Log.i(TAG, "OnOpenMovie " + path);
	}

	@Override
	public void OnOpenSound(int[] paras, String path) {
		// TODO Auto-generated method stub
		Log.i(TAG, "OnOpenSound " + path);
	}

	@Override
	public void OnOpenAttachment(String path) {
		// TODO Auto-generated method stub
		Log.i(TAG, "OnOpenAttachment " + path);
	}

	@Override
	public void OnOpen3D(String path) {
		// TODO Auto-generated method stub
		Log.i(TAG, "OnOpen3D " + path);
	}
	
	
	
	
	
	
}
