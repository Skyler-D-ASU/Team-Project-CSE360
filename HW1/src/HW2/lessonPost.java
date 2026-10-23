package HW2;

/*******
 * <p> Title: lessonPost Class. </p>
 * 
 * <p> Description: Creates a lessonPost object that stores basic components of a lessonPost,
 * 	   View constructor for more details.s</p>
 * 
 * <p> Copyright: Skyler DeLongchamp © 2026 </p>
 * 
 * @author Skyler DeLongchamp
 * 
 * @version 1.00		2026-10-5 Initial version
 *  
 */

public class lessonPost {
	/** Declares String Author */
	public String Author;
	/** Declares String postBody */
	public String postBody;
	/** Declares String postTitle */
	public String postTitle;
	/** Declares String timeEffort */
	public String timeEffort;
	/** Declares boolean timeEditable */
	public boolean timeEditable;
	/** Declares boolean bodyEditable */
	public boolean bodyEditable;
	/** Declares boolean titleEditable */
	public boolean titleEditable;
	/** Declares int ID */
	public int ID;
	/**
	 * Default Constructor
	 */
	public lessonPost() {}
	/**
	 * Constructor with inputs
	 * 
	 * @param Author stores author
	 * @param postTitle stores title of post
	 * @param postBody stores body of post
	 * @param timeEffort stores time effort data
	 * @param titleEditable stores bool if title is editable
	 * @param bodyEditable stores bool if body is editable
	 * @param timeEditable stores bool if time is editable
	 * @param ID unique INT identifier for a post
	 */
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
