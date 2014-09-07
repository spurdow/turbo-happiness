package gtg.virus.gtpr.entities;

import java.util.List;

import android.graphics.Bitmap;

import com.radaee.pdf.Page;

public class Book {
	
	private List<Page> pages;
	
	private String title;
	
	private String author;
	
	private String path;

	private Object tag;
	
	private Bitmap page0;

	/**
	 * @param pages
	 * @param title
	 * @param author
	 * @param path
	 */
	public Book(List<Page> pages, String title, String author, String path , Object tag , Bitmap frontPage) {
		super();
		this.pages = pages;
		this.title = title;
		this.author = author;
		this.path = path;
		this.tag = tag;
		this.page0 = frontPage;
	}

	/**
	 * @return the pages
	 */
	public List<Page> getPages() {
		return pages;
	}

	/**
	 * @param pages the pages to set
	 */
	public void setPages(List<Page> pages) {
		this.pages = pages;
	}

	/**
	 * @return the name
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param name the name to set
	 */
	public void setTitle(String name) {
		this.title = name;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @return the tag
	 */
	public Object getTag() {
		return tag;
	}

	/**
	 * @param tag the tag to set
	 */
	public void setTag(Object tag) {
		this.tag = tag;
	}

	public Bitmap getPage0() {
		return page0;
	}

	public void setPage0(Bitmap page0) {
		this.page0 = page0;
	}
	

	
}
