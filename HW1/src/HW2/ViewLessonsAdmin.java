package HW2;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;


import java.util.List;

import database.Database;
import entityClasses.User;
import guiAdminHome.ViewAdminHome;


/*******
 * <p> Title: ViewContributorHome Class. </p>
 * 
 * <p> Description: The Java/FX-based Role1 Home Page.  The page is a stub for some role needed for
 * the application.  The widgets on this page are likely the minimum number and kind for other role
 * pages that may be needed.</p>
 * 
 * <p> Copyright: Lynn Robert Carter © 2025 </p>
 * 
 * @author Lynn Robert Carter
 * 
 * @version 1.00		2025-08-20 Initial version
 *  
 */

public class ViewLessonsAdmin {
	
	/*-*******************************************************************************************

	Attributes
	
	 */
	
	// These are the application values required by the user interface
	
	private static double width = applicationMain.FoundationsMain.WINDOW_WIDTH;
	private static double height = applicationMain.FoundationsMain.WINDOW_HEIGHT;


	// These are the widget attributes for the GUI. There are 3 areas for this GUI.
	
	// GUI Area 1: It informs the user about the purpose of this page, whose account is being used,
	// and a button to allow this user to update the account settings
	protected static Label label_PageTitle = new Label();
	protected static Label label_UserDetails = new Label();
	protected static Button button_UpdateThisUser = new Button("Account Update");
	
	// This is a separator and it is used to partition the GUI for various tasks
	protected static Line line_Separator1 = new Line(20, 95, width-20, 95);

	// GUI ARea 2: This is a stub, so there are no widgets here.  For an actual role page, this are
	// would contain the widgets needed for the user to play the assigned role.
	protected static Button button_NewPost = new Button("New Post");
	
	// Post display area
	private VBox LessonsBox = new VBox(15);
	private ScrollPane LessonsScroll = new ScrollPane(LessonsBox);
	
	// Self post
	private TextArea PostTitle = new TextArea();
	private TextArea PostBody = new TextArea();
	private TextArea timeTakenArea = new TextArea();
	
	// Alert for posts
	protected static Alert alertPostInvalid = new Alert(AlertType.INFORMATION);
	
	// This is a separator and it is used to partition the GUI for various tasks
	protected static Line line_Separator4 = new Line(20, 525, width-20,525);
	
	// GUI Area 3: This is last of the GUI areas.  It is used for quitting the application and for
	// logging out.
	protected static Button button_Logout = new Button("Logout");
	protected static Button button_Quit = new Button("Quit");

	// This is the end of the GUI objects for the page.
	
	// These attributes are used to configure the page and populate it with this user's information
	private static ViewLessonsAdmin theView;		// Used to determine if instantiation of the class
												// is needed

	// Reference for the in-memory database so this package has access
	private static Database theDatabase = applicationMain.FoundationsMain.database;

	protected static Stage theStage;			// The Stage that JavaFX has established for us	
	protected static Pane theRootPane;			// The Pane that holds all the GUI widgets
	protected static User theUser;				// The current logged in User
	

	private static Scene theViewContributorHomeScene;	// The shared Scene each invocation populates
	protected static final int theRole = 1;		// Admin: 1; Contributor: 2; Role2: 3

	/*-*******************************************************************************************

	Constructors
	
	 */


	/**********
	 * <p> Method: displayRole1Home(Stage ps, User user) </p>
	 * 
	 * <p> Description: This method is the single entry point from outside this package to cause
	 * the Role1 Home page to be displayed.
	 * 
	 * It first sets up every shared attributes so we don't have to pass parameters.
	 * 
	 * It then checks to see if the page has been setup.  If not, it instantiates the class, 
	 * initializes all the static aspects of the GIUI widgets (e.g., location on the page, font,
	 * size, and any methods to be performed).
	 * 
	 * After the instantiation, the code then populates the elements that change based on the user
	 * and the system's current state.  It then sets the Scene onto the stage, and makes it visible
	 * to the user.
	 * 
	 * @param ps specifies the JavaFX Stage to be used for this GUI and it's methods
	 * 
	 * @param user specifies the User for this GUI and it's methods
	 * 
	 */
	public static void displayLessonsHome(Stage ps, User user) {
		
		// Establish the references to the GUI and the current user
		theStage = ps;
		theUser = user;
		Database theDatabase = applicationMain.FoundationsMain.database;
		
		// If not yet established, populate the static aspects of the GUI
		theView = new ViewLessonsAdmin();		// Instantiate singleton if needed
		
		// Populate the dynamic aspects of the GUI with the data from the user and the current
		// state of the system.
		theDatabase.getUserAccountDetails(user.getUserName());
		applicationMain.FoundationsMain.activeHomePage = theRole;
		
		label_UserDetails.setText("User: " + theUser.getUserName());
				
		// Set the title for the window, display the page, and wait for the Admin to do something
		theStage.setTitle("CSE 360 Foundations: Admin Lessons Page");
		theStage.setScene(theViewContributorHomeScene);
		theStage.show();
		
		
	}
	
	/**********
	 * <p> Method: ViewRole1Home() </p>
	 * 
	 * <p> Description: This method initializes all the elements of the graphical user interface.
	 * This method determines the location, size, font, color, and change and event handlers for
	 * each GUI object.</p>
	 * 
	 * This is a singleton and is only performed once.  Subsequent uses fill in the changeable
	 * fields using the displayRole2Home method.</p>
	 * 
	 */
	private ViewLessonsAdmin() {
		refreshFeed();

		// Create the Pane for the list of widgets and the Scene for the window
		theRootPane = new Pane();
		theViewContributorHomeScene = new Scene(theRootPane, width, height);	// Create the scene
		
		// Set the title for the window
		
		// Populate the window with the title and other common widgets and set their static state
		
		// GUI Area 1
		label_PageTitle.setText("Lessons Home Page");
		setupLabelUI(label_PageTitle, "Arial", 28, width, Pos.CENTER, 0, 5);

		label_UserDetails.setText("User: " + theUser.getUserName());
		setupLabelUI(label_UserDetails, "Arial", 20, width, Pos.BASELINE_LEFT, 20, 55);
		
		setupButtonUI(button_UpdateThisUser, "Dialog", 18, 170, Pos.CENTER, 610, 45);
		button_UpdateThisUser.setOnAction((_) -> {ControllerLessons.performUpdate(); });
		
		// GUI Area 2
		// Will contain fields of threads etc
		
		setupButtonUI(button_NewPost, "Dialog", 18, 205, Pos.CENTER, 25, 350);
		
		button_NewPost.setOnAction((_) -> {Publish();});
		
		// Display Area
		LessonsBox.setPadding(new Insets(8, 8, 8, 0));
		LessonsScroll.setLayoutX(250);
		LessonsScroll.setLayoutY(100);
		LessonsScroll.setMinWidth(500);
		LessonsScroll.setMaxWidth(500);
		LessonsScroll.setMinHeight(400);
		LessonsScroll.setMaxHeight(400);
		LessonsScroll.setFitToWidth(true);
		
		// Input Area
		SetupPostBoxUI(PostTitle, PostBody, timeTakenArea);

		
		
		// GUI Area 3
        setupButtonUI(button_Logout, "Dialog", 18, 250, Pos.CENTER, 20, 540);
        button_Logout.setOnAction((_) -> {guiAdminHome.ControllerAdminHome.performLogout();});
        
        setupButtonUI(button_Quit, "Dialog", 18, 250, Pos.CENTER, 300, 540);
        button_Quit.setOnAction((_) -> {ControllerLessons.performQuit(); });

		// This is the end of the GUI initialization code
		
		// Place all of the widget items into the Root Pane's list of children
         theRootPane.getChildren().addAll(
			label_PageTitle, label_UserDetails, button_UpdateThisUser, line_Separator1,
	        line_Separator4, button_Logout, button_Quit, button_NewPost, LessonsBox, LessonsScroll,
	        PostBody, PostTitle, timeTakenArea);
}
	
	
	/*-********************************************************************************************

	Helper methods to reduce code length

	 */
	private static void SetupPostBoxUI(TextArea Title, TextArea Body, TextArea timeTakenArea ){
		Title.setMinWidth(100);
		Title.setMaxWidth(200);
		Title.setMinHeight(25);
		Title.setMaxHeight(25);
		Title.setLayoutX(10);
		Title.setLayoutY(100);
		Title.setPrefRowCount(8);
		Title.setWrapText(true);
		Title.setPromptText("<Insert Title Information here>");
		
		Body.setMinWidth(100);
		Body.setMaxWidth(200);
		Body.setLayoutX(10);
		Body.setLayoutY(135);
		Body.setPrefRowCount(8);
		Body.setWrapText(true);
		Body.setPromptText("<Insert lessons learned body information here>");
		

		timeTakenArea.setMinWidth(100);
		timeTakenArea.setMaxWidth(200);
		timeTakenArea.setMinHeight(25);
		timeTakenArea.setMaxHeight(25);
		timeTakenArea.setLayoutX(10);
		timeTakenArea.setLayoutY(300);
		timeTakenArea.setPrefRowCount(8);
		timeTakenArea.setWrapText(true);
		timeTakenArea.setPromptText("<Insert time taken data here in hours>");
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
	 */
	private static void setupLabelUI(Label l, String ff, double f, double w, Pos p, double x, 
			double y){
		l.setFont(Font.font(ff, f));
		l.setMinWidth(w);
		l.setAlignment(p);
		l.setLayoutX(x);
		l.setLayoutY(y);		
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
	 */
	private static void setupButtonUI(Button b, String ff, double f, double w, Pos p, double x, 
			double y){
		b.setFont(Font.font(ff, f));
		b.setMinWidth(w);
		b.setAlignment(p);
		b.setLayoutX(x);
		b.setLayoutY(y);		
	}
	private void Publish() {
		String Err[];
		
		String t = PostTitle.getText().trim();
		String b = PostBody.getText().trim();
		String tt = timeTakenArea.getText().trim();
		
		Err = publishingInputValidation(t, b, tt);
		
		if (Err != null) {
			alertPostInvalid.setTitle(Err[0]);
			alertPostInvalid.setHeaderText(Err[1]);
			alertPostInvalid.setContentText("Correct the Title and try again.");
			alertPostInvalid.showAndWait();
		} else {
			theDatabase.createPost(theDatabase.getCurrentUsername(), t, b, tt, true, true, true);
			PostTitle.clear();
			PostBody.clear();
			refreshFeed();
			}
	}
	
	private void refreshFeed() {
		LessonsBox.getChildren().clear();
		List<lessonPost> lPosts = theDatabase.getAllPosts();
		for (lessonPost LP : lPosts) {
			LessonsBox.getChildren().add(createNewPostBox(LP));
			}
	}
	private VBox createNewPostBox(lessonPost LP) {
		VBox newPost = new VBox(8);
		
		// IDENTIFYING LABELS
		Label AuthorDisplay = new Label("Author: ");
		Label TitleDisplay = new Label("Title: ");
		Label BodyDisplay = new Label("Body: ");
		Label TimeDisplay = new Label("Time/Effort: ");
		
		// POST LOCK BUTTONS
		Button button_LockPostTitle = new Button();
        setupButtonUI(button_LockPostTitle, "Dialog", 10, 15, Pos.CENTER, 30, 30);
        
        Button button_LockPostBody = new Button();
        setupButtonUI(button_LockPostBody, "Dialog", 10, 15, Pos.CENTER, 30, 30);
        
        Button button_LockPostTime = new Button();
        setupButtonUI(button_LockPostTime, "Dialog", 10, 15, Pos.CENTER, 30, 30);
        
		Button button_deletePost = new Button("Delete Post");
        setupButtonUI(button_deletePost, "Dialog", 10, 15, Pos.CENTER, 30, 30);
        
		//Title Row
		HBox titleRow = new HBox(8);
		if (LP.titleEditable) {
			TextArea title = new TextArea(LP.postTitle);
			title.getOnInputMethodTextChanged();
			title.textProperty().addListener((observableTitle, oldValueTitle, newValueTitle) -> {theDatabase.updateTitle(newValueTitle, LP.ID); });
			title.setMaxSize(200, 65);
			titleRow.getChildren().addAll(TitleDisplay, title, button_LockPostTitle);
		} else {Label title = new Label(LP.postTitle);
				titleRow.getChildren().addAll(TitleDisplay, title, button_LockPostTitle);}
	
			
		//Author Row
		HBox authorRow = new HBox(8);
		Label author = new Label(LP.Author);
		authorRow.getChildren().addAll(AuthorDisplay, author);
			
		//Body Row
		HBox bodyRow = new HBox(8);
		if (LP.bodyEditable) {
			TextArea body = new TextArea(LP.postBody);
			body.textProperty().addListener((observableBody, oldValueBody, newValueBody) -> {theDatabase.updateBody(newValueBody, LP.ID);});
			body.setMaxSize(200, 100);
			body.setWrapText(true);
			bodyRow.getChildren().addAll(BodyDisplay, body, button_LockPostBody);
		} else { Label body = new Label(LP.postBody);
				 body.setWrapText(true);
				 body.setMaxWidth(350);
				 bodyRow.getChildren().addAll(BodyDisplay, body,button_LockPostBody);}

			
		//Time Effort Row
		HBox timeEffortRow = new HBox(8);
		if (LP.timeEditable) {
			TextArea timeEffortTXT = new TextArea(LP.timeEffort);
			timeEffortTXT.textProperty().addListener((observableTETXT, oldValueTETXT, newValueTETXT) -> {theDatabase.updateTimeEffort(newValueTETXT, LP.ID);});
			timeEffortTXT.setMaxSize(200, 65);
			timeEffortRow.getChildren().addAll(TimeDisplay, timeEffortTXT, button_LockPostTime);
		} else {Label timeEffortTXT = new Label(LP.timeEffort);
				timeEffortRow.getChildren().addAll(TimeDisplay, timeEffortTXT, button_LockPostTime);}
		
		// Deletion Button
		
		
		if (theDatabase.isPostTimeEditable(LP.ID)) { button_LockPostTime.setOnAction((_) -> {theDatabase.updateTimeEditing(false, LP.ID); 
																							refreshFeed();});
													button_LockPostTime.setText("Unlock Time");
		
		} else {button_LockPostTime.setOnAction((_) -> {theDatabase.updateTimeEditing(true, LP.ID); 
														refreshFeed();});
														button_LockPostTime.setText("Lock Time");}
		
		if (theDatabase.isPostTitleEditable(LP.ID)) { button_LockPostTitle.setOnAction((_) -> {theDatabase.updateTitleEditing(false, LP.ID);  
																							   refreshFeed();});
																							   button_LockPostTitle.setText("Unlock Title");
		
		} else { button_LockPostTitle.setOnAction((_) -> {theDatabase.updateTitleEditing(true, LP.ID); 
														  refreshFeed();});
														  button_LockPostTitle.setText("Lock Title");}
		
		if (theDatabase.isPostBodyEditable(LP.ID)) { button_LockPostBody.setOnAction((_) -> {theDatabase.updateBodyEditing(false, LP.ID); 
																							 refreshFeed();});
		 																					 button_LockPostBody.setText("Unlock Body");
		} else { button_LockPostBody.setOnAction((_) -> {theDatabase.updateBodyEditing(true, LP.ID);  
														 refreshFeed();}); 
														 button_LockPostBody.setText("Lock Body"); }
	
	
		if (LP.titleEditable && LP.timeEditable && LP.bodyEditable) { button_deletePost.setOnAction((_) -> {theDatabase.deletePost(LP.ID); refreshFeed();});
		} else { button_deletePost.setText("No Permission To Delete Post");}
		
		titleRow.getChildren().add(button_deletePost);

		// UI LINES
		Line outer_line_Sep = new Line(bodyRow.getHeight()- 15, 95, 350, 95);
		Line inner_line_Sep = new Line(bodyRow.getHeight()- 15, 95, 125, 95);
			
		newPost.getChildren().addAll(authorRow,inner_line_Sep, titleRow, bodyRow, timeEffortRow, outer_line_Sep);
		
		return newPost;
	}
	private String[] publishingInputValidation(String title, String Body, String timeTaken) {
		// Input type refers to (0) Title, (1) Body, (2) time taken
		
		if (title.length() < 5) { return new String[] {"Invalid Title", "Title is less than 5 characters."};
		} else if (Body.length() > 35) { return new String[] {"Invalid Title", "Title is more than 35 characters."};
		} else if (Body.length() < 10) { return new String[] {"Invalid Body", "Body is less than 10 characters."}; 
		} else if (timeTaken.length() > 50) { return new String[] {"Invalid Body ", "Time taken is more than 50 characters."}; 
		} else {return null;}	
	}

}
