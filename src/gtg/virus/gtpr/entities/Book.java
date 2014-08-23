package gtg.virus.gtpr.entities;

import java.util.List;

public class Book {
	
	private List<Page> pages;
	
	private String title;
	
	private String author;
	
	private String path;

	
	
	

	/**
	 * @param pages
	 * @param title
	 * @param author
	 * @param path
	 */
	public Book(List<Page> pages, String title, String author, String path) {
		super();
		this.pages = pages;
		this.title = title;
		this.author = author;
		this.path = path;
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
	
	
	
}
