package gtg.virus.gtpr.entities;

import gtg.virus.gtpr.R;

import java.util.ArrayList;
import java.util.List;

public class Shelf {
	
	private List<Book> books;
	
	public final static int MAX = 3;

	/**
	 * @param books
	 */
	public Shelf() {
		super();
		this.books = new ArrayList<Book>(3);
	}
	
	
	public void addBook(Book b){
		books.add(b);
	}
	
	public Book getBook(int pos){
		return books.get(pos % MAX);
	}

	/**
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
	
	public int resId(){
		return R.layout.shelf_item;
	}
	
	
}
