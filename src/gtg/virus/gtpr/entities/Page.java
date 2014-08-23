package gtg.virus.gtpr.entities;

public class Page {
		
	private int bookmark;
	
	private String content;

	
	
	
	/**
	 * @param no
	 * @param bookmark
	 * @param content
	 */
	public Page(int bookmark, String content) {
		super();
		this.bookmark = bookmark;
		this.content = content;
	}


	/**
	 * @return the bookmark
	 */
	public int getBookmark() {
		return bookmark;
	}

	/**
	 * @param bookmark the bookmark to set
	 */
	public void setBookmark(int bookmark) {
		this.bookmark = bookmark;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
	
	
}
