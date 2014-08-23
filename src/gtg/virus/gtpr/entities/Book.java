package gtg.virus.gtpr.entities;

import java.util.List;

public class Book {
	
	private List<Page> pages;
	
	private String name;
	
	private String path;

	
	
	
	/**
	 * @param pages
	 * @param name
	 * @param path
	 */
	public Book(List<Page> pages, String name, String path) {
		super();
		this.pages = pages;
		this.name = name;
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
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	
	
	
}
