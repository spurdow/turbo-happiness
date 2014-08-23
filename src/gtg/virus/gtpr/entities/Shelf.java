package gtg.virus.gtpr.entities;

import gtg.virus.gtpr.R;

import java.util.ArrayList;
import java.util.List;

import android.view.View;

public class Shelf {
	
	private List<Book> books;
	
	private int max;
	
	public final static int MAX = 3;

	private View mView = null;
	/**
	 * @param books
	 */
	public Shelf() {
		super();
		this.books = new ArrayList<Book>();
		this.max = MAX;
	}
	
	
	public void addBook(Book b){
		books.add(b);
	}
	
	public Book getBook(int pos){
		return books.get(pos);
	}

	/**dv
	 * @return the books
	 */
	public List<Book> getBooks() {
		return books;
	}

	/**
	 * @param books the books to set
	 */
	public void setBooks(List<Book> books) {
		this.books = books;
	}
	
	
	
	/**
	 * @return the max
	 */
	public int getMax() {
		return max;
	}


	/**
	 * @param max the max to set
	 */
	public void setMax(int max) {
		this.max = max;
	}
	
	
	


	/**
	 * @return the mView
	 */
	public View getmView() {
		return mView;
	}


	/**
	 * @param mView the mView to set
	 */
	public void setmView(View mView) {
		this.mView = mView;
	}


	public int resId(){
		return R.layout.shelf_item;
	}
	
	
}
