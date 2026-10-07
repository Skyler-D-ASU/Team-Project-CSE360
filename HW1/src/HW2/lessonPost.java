package HW2;

public class lessonPost {
	public String Author;
	public String postBody;
	public String postTitle;
	public String timeEffort;
	public boolean timeEditable;
	public boolean bodyEditable;
	public boolean titleEditable;
	public int ID;
	
	public lessonPost() {}
	
	public lessonPost(String Author, String postTitle, String postBody, String timeEffort, boolean titleEditable, boolean bodyEditable, boolean timeEditable, int ID) {
		this.ID = ID;
		this.Author = Author;
		this.postBody = postBody;
		this.postTitle = postTitle;
		this.timeEffort = timeEffort;
		this.titleEditable = titleEditable;
		this.bodyEditable = bodyEditable;
		this.timeEditable = timeEditable;
	}
}
