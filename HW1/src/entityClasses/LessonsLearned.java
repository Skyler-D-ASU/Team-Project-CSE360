package entityClasses;

import java.util.List;
import java.util.Optional;

import HW2.lessonPost;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import database.Database;


public class LessonsLearned {
	/** Default Constructor */
	public LessonsLearned() {}
	
	/** Is the visuals updating */
	boolean currentlyUpdating = false;
	
	/** Font Names */
	String fgm = "Franklin Gothic Medium";
	String fgb = "Franklin Gothic Book";
	
	/** Reference for the in-memory database so this package has access */
	private static Database theDatabase = applicationMain.FoundationsMain.database;
	
	/** delete post alert */
	protected static Alert alertDeletePost = new Alert(AlertType.CONFIRMATION);
	/** Invalid post alert */
	protected static Alert alertPostInvalid = new Alert(AlertType.INFORMATION);
	
	
	/**********
	 * <p> Method: publishingInputValidation(String Title, String Body, String timeTaken) </p>
	 * 
	 * <p> Description: Checks title, Body, and timeTaken to see if they're valid lengths</p>
	 * 
	 * <p> Reason for Implementation Style : Due to the fact we needed a simple input validation without
	 * specific checks, this checks for input lengths and returns a string containing the error title and error
	 * description </p>
	 * 
	 * @param Title string input for title validation
	 * @param Body string input for body validation
	 * @param timeTaken string input for timeTaken validation
	 * 
	 * @return string[] if the input is invalid, else returns null
	 */
	public String[] publishingInputValidation(String Title, String Body, String timeTaken) {
		// Input type refers to (0) Title, (1) Body, (2) time taken
		
		if (Title.length() < 5) { return new String[] {"Invalid Title", "Title is less than 5 characters."};
		} else if (Title.length() > 35) { return new String[] {"Invalid Title", "Title is more than 35 characters."};
		} else if (Body.length() < 10) { return new String[] {"Invalid Body", "Body is less than 10 characters."}; 
		} else if (timeTaken.length() > 50) { return new String[] {"Invalid Body ", "Time taken is more than 50 characters."}; 
		} else {return null;}	
	}
	
	/**********
	 * <p> Method: createNewPostBox(lessonPost LP) </p>
	 * 
	 * @param LP takes in a lessonPost object
	 * 
	 * <p> Description: setups up UI for displaying the information stored in a single LP in the form of a VBox
	 * 	   The display also changes based on the status of certain boolean from the LP object</p>
	 * 
	 * <p> TODO: </p>
	 * 
	 * <p> Reason for Implementation Style : Due to the length of this function to create individual boxes that can be displayed
	 * with their own unique attributes, it made sense to have it seperate from the other function as it's a piece of the refresh
	 * feed function as it iterates through all unique database ids to create a postbox for each individual ID</p>
	 * 
	 * @return newPost a vBox object containing all of the UI information
	 * 
	 * 
	 * 
	 */
	public VBox createNewPostBox(lessonPost LP, VBox LB) {
		VBox newPost = new VBox(8);

		Label AuthorDisplay = new Label("Author: ");
		setupLabelUI(AuthorDisplay, fgm, 18, 100, Pos.BASELINE_RIGHT, 0, 30, "#9885FF");
		Label TitleDisplay = new Label("Title: ");
		setupLabelUI(TitleDisplay, fgm, 18, 100, Pos.BASELINE_RIGHT, 0, 30, "#9885FF");
		Label BodyDisplay = new Label("Body: ");
		setupLabelUI(BodyDisplay, fgm, 18, 100, Pos.BASELINE_RIGHT, 0, 30, "#9885FF");
		Label TimeDisplay = new Label("Time/Effort: ");
		setupLabelUI(TimeDisplay, fgm, 18, 100, Pos.BASELINE_RIGHT, 0, 30, "#9885FF");

		Button button_updatePost = new Button("Update Post");
		setupButtonUI(button_updatePost, "Franklin Gothic Demi", 18, 125, Pos.CENTER, 30, 30, "white", "#008537");
		Button button_deletePost = new Button("Delete Post");
        setupButtonUI(button_deletePost, "Franklin Gothic Demi", 18, 125, Pos.CENTER, 30, 30, "white", "#008537");
        
        
        // fields all start as locked/labels until update button is pressed
        // then check if field is locked/unlocked and change to editable text box
        // when finished updating, click button and change back to un-editable labels
        
		//Title Row
		HBox titleRow = new HBox(8);
		titleRow.setAlignment(Pos.CENTER);
		
		Label title = new Label(LP.postTitle);
		setupLabelUI(title, fgm, 16, 250, Pos.BASELINE_LEFT, 105, 30, "white");
		title.setStyle("-fx-border-color: grey; -fx-border-width: 2px; -fx-padding: 10px; -fx-border-radius: 5px;");
		title.setMaxHeight(30);
		titleRow.getChildren().addAll(TitleDisplay, title);

		//Author Row
		HBox authorRow = new HBox(8);
		Label author = new Label(LP.Author);
		setupLabelUI(author, fgm, 16, 250, Pos.BASELINE_LEFT, 105, 30, "white");
		author.setStyle("-fx-border-color: grey; -fx-border-width: 2px; -fx-padding: 10px; -fx-border-radius: 5px;");
		author.setMaxHeight(30);
		authorRow.setAlignment(Pos.CENTER);
		authorRow.getChildren().addAll(AuthorDisplay, author);
			
		//Body Row
		HBox bodyRow = new HBox(8);
		bodyRow.setAlignment(Pos.CENTER);
		
		TextArea body = new TextArea(LP.postBody);
		body.setMaxSize(250, 125);
		body.setWrapText(true);
		body.setFont(Font.font(fgb, 16));
		body.setStyle("-fx-prompt-text-fill: grey; -fx-text-fill: white;"
				+ " -fx-control-inner-background: #1A1A1A; -fx-border-color: grey; "
				+ "-fx-border-width: 2px;");
		body.setEditable(false);
		bodyRow.getChildren().addAll(BodyDisplay, body);
		
		//Time Effort Row
		HBox timeEffortRow = new HBox(8);
		timeEffortRow.setAlignment(Pos.CENTER);
		
		Label timeEffort = new Label(LP.timeEffort);
		setupLabelUI(timeEffort, fgm, 16, 250, Pos.BASELINE_LEFT, 105, 30, "white");
		timeEffort.setStyle("-fx-border-color: grey; -fx-border-width: 2px; -fx-padding: 10px; -fx-border-radius: 5px;");
		timeEffort.setMaxHeight(30);
		timeEffortRow.getChildren().addAll(TimeDisplay, timeEffort);
		
		// Top Row Buttons
		HBox buttonRow = new HBox(8);
		buttonRow.setAlignment(Pos.CENTER);
		buttonRow.getChildren().addAll(button_updatePost, button_deletePost);
		
		// Update
		if (LP.titleEditable || LP.timeEditable || LP.bodyEditable) {
			button_updatePost.setOnAction((_) -> {
				if (!currentlyUpdating) {
					button_updatePost.setText("Finish Updating");
					currentlyUpdating = true;
					if (LP.titleEditable) {
						TextArea titleEdit = new TextArea(LP.postTitle);
						titleEdit.getOnInputMethodTextChanged();
						titleEdit.textProperty().addListener((observableTitle, oldValueTitle, newValueTitle) -> {theDatabase.updateTitle(newValueTitle, LP.ID); });
						titleEdit.setMaxSize(250, 30);
						titleEdit.setWrapText(true);
						
						titleEdit.setFont(Font.font(fgb, 16));
						titleEdit.setStyle("-fx-prompt-text-fill: grey; -fx-text-fill: white;"
								+ " -fx-control-inner-background: #1A1A1A; -fx-border-color: grey; "
								+ "-fx-border-width: 2px;");
						titleRow.getChildren().clear();
						titleRow.getChildren().addAll(TitleDisplay, titleEdit);
						//refreshFeed();
					} else {
						// field is not editable
						Label cannotEdit = new Label("Field cannot be edited");
						setupLabelUI(cannotEdit, fgm, 14, 250, Pos.BASELINE_LEFT, 105, 30, "#E00000");
						cannotEdit.setStyle("-fx-text-fill: #E00000; -fx-border-color: grey; -fx-border-width: 2px; -fx-padding: 10px; -fx-border-radius: 5px;");
						cannotEdit.setMaxSize(250, 30);
						titleRow.getChildren().clear();
						titleRow.getChildren().addAll(TitleDisplay, cannotEdit);
						//refreshFeed();
					}
					
					if (LP.bodyEditable) {
						TextArea bodyEdit = new TextArea(LP.postBody);
						bodyEdit.textProperty().addListener((observableBody, oldValueBody, newValueBody) -> {theDatabase.updateBody(newValueBody, LP.ID);});
						bodyEdit.setMaxSize(250, 125);
						bodyEdit.setWrapText(true);
						bodyEdit.setFont(Font.font(fgb, 16));
						bodyEdit.setStyle("-fx-prompt-text-fill: grey; -fx-text-fill: white;"
								+ " -fx-control-inner-background: #1A1A1A; -fx-border-color: grey; "
								+ "-fx-border-width: 2px;");
						bodyRow.getChildren().clear();
						bodyRow.getChildren().addAll(BodyDisplay, bodyEdit);
						//refreshFeed();
					} else { 
						// field is not editable
						Label cannotEdit = new Label("Field cannot be edited");
						setupLabelUI(cannotEdit, fgm, 14, 250, Pos.BASELINE_LEFT, 105, 30, "#E00000");
						cannotEdit.setStyle("-fx-text-fill: #E00000; -fx-border-color: grey; -fx-border-width: 2px; -fx-padding: 10px; -fx-border-radius: 5px;");
						cannotEdit.setMaxSize(250, 30);
						bodyRow.getChildren().clear();
						bodyRow.getChildren().addAll(BodyDisplay, cannotEdit);
						//refreshFeed();
					}
					
					if (LP.timeEditable) {
						TextArea timeEffortTXT = new TextArea(LP.timeEffort);
						timeEffortTXT.textProperty().addListener((observableTETXT, oldValueTETXT, newValueTETXT) -> {theDatabase.updateTimeEffort(newValueTETXT, LP.ID);});
						timeEffortTXT.setMaxSize(250, 50);
						timeEffortTXT.setWrapText(true);
							
						timeEffortTXT.setFont(Font.font(fgb, 16));
						timeEffortTXT.setStyle("-fx-prompt-text-fill: grey; -fx-text-fill: white;"
								+ " -fx-control-inner-background: #1A1A1A; -fx-border-color: grey; "
								+ "-fx-border-width: 2px;");
						timeEffortRow.getChildren().clear();
						timeEffortRow.getChildren().addAll(TimeDisplay, timeEffortTXT);
					} else {
						// field is not editable
						Label cannotEdit = new Label("Field cannot be edited");
						setupLabelUI(cannotEdit, fgm, 14, 250, Pos.BASELINE_LEFT, 105, 30, "#E00000");
						cannotEdit.setStyle("-fx-text-fill: #E00000; -fx-border-color: grey; -fx-border-width: 2px; -fx-padding: 10px; -fx-border-radius: 5px;");
						cannotEdit.setMaxSize(250, 30);
						timeEffortRow.getChildren().clear();
						timeEffortRow.getChildren().addAll(TimeDisplay, cannotEdit);
						//refreshFeed();
					}
					
				} else {
					// when done updating, change all the info to un-editable again
					currentlyUpdating = false;
					
					Label titleDone = new Label(LP.postTitle);
					setupLabelUI(titleDone, fgm, 14, 250, Pos.BASELINE_LEFT, 105, 30, "white");
					titleDone.setStyle("-fx-border-color: grey; -fx-border-width: 2px; -fx-padding: 10px; -fx-border-radius: 5px;");
					titleDone.setMaxHeight(30);
					titleRow.getChildren().clear();
					titleRow.getChildren().addAll(TitleDisplay, titleDone);
					
					TextArea bodyDone = new TextArea(LP.postBody);
					bodyDone.setMaxSize(250, 125);
					bodyDone.setWrapText(true);
					bodyDone.setFont(Font.font(fgb, 16));
					bodyDone.setStyle("-fx-prompt-text-fill: grey; -fx-text-fill: white;"
							+ " -fx-control-inner-background: #1A1A1A; -fx-border-color: grey; "
							+ "-fx-border-width: 2px;");
					bodyDone.setEditable(false);
					bodyRow.getChildren().clear();
					bodyRow.getChildren().addAll(BodyDisplay, bodyDone);
					
					Label timeEffortDone = new Label(LP.timeEffort);
					setupLabelUI(timeEffortDone, fgm, 16, 250, Pos.BASELINE_LEFT, 105, 30, "white");
					timeEffortDone.setStyle("-fx-border-color: grey; -fx-border-width: 2px; -fx-padding: 10px; -fx-border-radius: 5px;");
					timeEffortDone.setMaxHeight(30);
					timeEffortRow.getChildren().clear();
					timeEffortRow.getChildren().addAll(TimeDisplay, timeEffortDone);
					
					refreshFeed(LB);
				}
			});
			
		} else {
			button_updatePost.setText("Cannot Update Post");
		}
		
		
		// Delete
		if (LP.titleEditable && LP.timeEditable && LP.bodyEditable && LP.Author.equals(theDatabase.getCurrentUsername())) {
			newPost.getChildren().add(buttonRow);
			button_deletePost.setOnAction((_) -> {
				alertDeletePost.setTitle("Delete Post");
				alertDeletePost.setHeaderText("You are about to permanently remove this lesson!");
				alertDeletePost.setContentText("Are you sure you want to delete this post?");
				Optional<ButtonType> result = alertDeletePost.showAndWait();
				if (result.isPresent() && result.get() == ButtonType.OK) {
					theDatabase.deletePost(LP.ID); 
					refreshFeed(LB);
				}});
		} else {
			button_deletePost.setText("Cannot Delete Post");
			newPost.getChildren().add(buttonRow);
		}
		
		
		
		// UI LINES
		Line outer_line_Sep = new Line(bodyRow.getHeight()- 15, 95, 350, 95);
		Line inner_line_Sep = new Line(bodyRow.getHeight()- 15, 95, 175, 95);
		outer_line_Sep.setStroke(Color.DODGERBLUE);
		inner_line_Sep.setStroke(Color.DODGERBLUE);	
		
		newPost.getChildren().addAll(inner_line_Sep, authorRow, titleRow, bodyRow, timeEffortRow, outer_line_Sep);
		newPost.setAlignment(Pos.CENTER);
		
		return newPost;
	}
	
	public VBox refreshFeed(VBox LB) {
		LB.getChildren().clear();
		List<lessonPost> lPosts = theDatabase.getAllPosts();
		for (lessonPost LP : lPosts) {
			if (LP.Author.equals(theDatabase.getCurrentUsername())) {
				System.out.println("Author: " + LP.Author + " Currently Logged in user: " + theDatabase.getCurrentUsername());
				LB.getChildren().add(createNewPostBox(LP, LB)); }
			}
		return LB;
	}
	
	/**********
	 * <p> Method: Publish() </p>
	 * 
	 * <p> Description: This method cleans all of the text, and attempts to send it to the database
	 * 	   after passing it through the publishingInputValidation(t, b, tt) method.  Then it pushes
	 *     to database and calls refreshFeed()</p>
	 * 
	 */
	public VBox Publish(TextArea PT, TextArea PB, TextArea TTA, VBox LB) {
		String Err[];
		
		String t = PT.getText().trim();
		String b = PB.getText().trim();
		String tt = TTA.getText().trim();
		
		Err = publishingInputValidation(t, b, tt);
		
		if (Err != null) {
			alertPostInvalid.setTitle(Err[0]);
			alertPostInvalid.setHeaderText(Err[1]);
			alertPostInvalid.setContentText("Correct the Title and try again.");
			alertPostInvalid.showAndWait();
		} else {
			theDatabase.createPost(theDatabase.getCurrentUsername(), t, b, tt, true, true, true);
			PT.clear();
			PB.clear();
			TTA.clear();
			LB = refreshFeed(LB);
		}
		return LB;
	}
	
	/**********
	 * Private local method to initialize the standard fields for a label
	 * 
	 * @param l		The Label object to be initialized
	 * @param ff	The font to be used
	 * @param f		The size of the font to be used
	 * @param w		The width of the Button
	 * @param p		The alignment (e.g. left, centered, or right)
	 * @param x		The location from the left edge (x axis)
	 * @param y		The location from the top (y axis)
	 * @param c		the text fill style
	 */
	
	private static void setupLabelUI(Label l, String ff, double f, double w, Pos p, double x, double y, String c){
		l.setFont(Font.font(ff, f));
		l.setMinWidth(w);
		l.setAlignment(p);
		l.setLayoutX(x);
		l.setLayoutY(y);	
		l.setStyle("-fx-text-fill: " + c);
	}

	/**********
	 * Private local method to initialize the standard fields for a button
	 * 
	 * @param b		The Button object to be initialized
	 * @param ff	The font to be used
	 * @param f		The size of the font to be used
	 * @param w		The width of the Button
	 * @param p		The alignment (e.g. left, centered, or right)
	 * @param x		The location from the left edge (x axis)
	 * @param y		The location from the top (y axis)
	 * @param tc	The text color of the button
	 * @param bc	The background color of the button
	 */
	private static void setupButtonUI(Button b, String ff, double f, double w, Pos p, double x, double y, String tc, String bc){
		b.setFont(Font.font(ff, f));
		b.setMinWidth(w);
		b.setAlignment(p);
		b.setLayoutX(x);
		b.setLayoutY(y);
		b.setStyle("-fx-text-fill: " + tc + "; -fx-background-color: " + bc + "; -fx-background-radius: 10px;");
	}

}
