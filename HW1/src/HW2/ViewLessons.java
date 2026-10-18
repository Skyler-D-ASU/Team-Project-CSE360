package HW2;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;


import java.util.List;
import java.util.Optional;

import database.Database;
import entityClasses.User;


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

public class ViewLessons {
	
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
	private boolean currentlyUpdating = false;
	
	// Self post
	private TextArea PostTitle = new TextArea();
	private TextArea PostBody = new TextArea();
	private TextArea timeTakenArea = new TextArea();
	
	// Alert for posts
	protected static Alert alertPostInvalid = new Alert(AlertType.INFORMATION);
	protected static Alert alertDeletePost = new Alert(AlertType.CONFIRMATION);
	
	// This is a separator and it is used to partition the GUI for various tasks
	protected static Line line_Separator4 = new Line(20, 525, width-20,525);
	
	// GUI Area 3: This is last of the GUI areas.  It is used for quitting the application, 
	// logging out, and returning to the user's home page.
	protected static Button button_ReturnToHomePage = new Button("Return");
	protected static Button button_Logout = new Button("Logout");
	protected static Button button_Quit = new Button("Quit");

	// This is the end of the GUI objects for the page.
	
	// These attributes are used to configure the page and populate it with this user's information
	private static ViewLessons theView;		// Used to determine if instantiation of the class
												// is needed

	// Reference for the in-memory database so this package has access
	private static Database theDatabase = applicationMain.FoundationsMain.database;

	protected static Stage theStage;			// The Stage that JavaFX has established for us	
	protected static Pane theRootPane;			// The Pane that holds all the GUI widgets
	protected static User theUser;				// The current logged in User
	

	private static Scene theViewContributorHomeScene;	// The shared Scene each invocation populates
	protected static final int theRole = 2;		// Admin: 1; Contributor: 2; Role2: 3

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
		theView = new ViewLessons();		// Instantiate singleton if needed
		
		// Populate the dynamic aspects of the GUI with the data from the user and the current
		// state of the system.
		theDatabase.getUserAccountDetails(user.getUserName());
		applicationMain.FoundationsMain.activeHomePage = theRole;
		
		label_UserDetails.setText("User: " + theUser.getUserName());
				
		// Set the title for the window, display the page, and wait for the Admin to do something
		theStage.setTitle("CSE 360 Foundations: Contributor Lessons Page");
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
	private ViewLessons() {

		// Create the Pane for the list of widgets and the Scene for the window
		theRootPane = new Pane();
		theViewContributorHomeScene = new Scene(theRootPane, width, height);	// Create the scene
		
		// Populate the window with the title and other common widgets and set their static state
		
		theRootPane.setStyle("-fx-background-color: #1A1A1A");
		line_Separator1.setStroke(Color.WHITE);
		
		// GUI Area 1
		
		// Set the title for the window
		label_PageTitle.setText("Lessons Home Page");
		setupLabelUI(label_PageTitle, "Franklin Gothic Heavy", 28, width, Pos.CENTER, 0, 10, "white");

		label_UserDetails.setText("User: " + theUser.getUserName());
		setupLabelUI(label_UserDetails, "Franklin Gothic Demi", 20, width, Pos.BASELINE_LEFT, 20, 55, "#9885FF");
		
		setupButtonUI(button_UpdateThisUser, "Franklin Gothic Demi", 18, 170, Pos.CENTER, 610, 45, "white", "#0080FF");
		button_UpdateThisUser.setOnAction((_) -> {ControllerLessons.performUpdate(); });
		
		// GUI Area 2
		
		// Will contain fields of threads etc
		
		setupButtonUI(button_NewPost, "Franklin Gothic Demi", 18, 125, Pos.CENTER, 110, 450, "white", "#008537");
		
		button_NewPost.setOnAction((_) -> {Publish();
		refreshFeed();});
		
		// Display Area
		LessonsBox.setPadding(new Insets(8, 8, 8, 0));
		LessonsScroll.setLayoutX(350);
		LessonsScroll.setLayoutY(105);
		LessonsScroll.setMinWidth(400);
		LessonsScroll.setMaxWidth(400);
		LessonsScroll.setMinHeight(400);
		LessonsScroll.setMaxHeight(400);
		LessonsScroll.setFitToWidth(true);
		LessonsScroll.setStyle("-fx-background: #1A1A1A;");
		
		// Input Area
		SetupPostBoxUI(PostTitle, PostBody, timeTakenArea);
		
		PostTitle.setFont(Font.font("Franklin Gothic Book", 16));
		PostTitle.setStyle("-fx-prompt-text-fill: grey; -fx-text-fill: white;"
				+ " -fx-control-inner-background: #1A1A1A; -fx-border-color: grey; "
				+ "-fx-border-width: 2px;");
		
		PostBody.setFont(Font.font("Franklin Gothic Book", 16));
		PostBody.setStyle("-fx-prompt-text-fill: grey; -fx-text-fill: white;"
				+ " -fx-control-inner-background: #1A1A1A; -fx-border-color: grey; "
				+ "-fx-border-width: 2px;");
		
		timeTakenArea.setFont(Font.font("Franklin Gothic Book", 16));
		timeTakenArea.setStyle("-fx-prompt-text-fill: grey; -fx-text-fill: white;"
				+ " -fx-control-inner-background: #1A1A1A; -fx-border-color: grey; "
				+ "-fx-border-width: 2px;");
		
		// GUI Area 3
		
		line_Separator4.setStroke(Color.WHITE);
		
		setupButtonUI(button_ReturnToHomePage, "Franklin Gothic Demi", 18, 200, Pos.CENTER, 50, 540, "white", "#3B3B3B");
		button_ReturnToHomePage.setOnAction((_) -> {ControllerLessons.goToUserHomePage(theStage, theUser);}	);
		
        setupButtonUI(button_Logout, "Franklin Gothic Demi", 18, 200, Pos.CENTER, 300, 540, "white", "#3B3B3B");
        button_Logout.setOnAction((_) -> {ControllerLessons.performLogout(); });
        
        setupButtonUI(button_Quit, "Franklin Gothic Demi", 18, 200, Pos.CENTER, 550, 540, "white", "#3B3B3B");
        button_Quit.setOnAction((_) -> {ControllerLessons.performQuit(); });

		// This is the end of the GUI initialization code
		
		// Place all of the widget items into the Root Pane's list of children
         theRootPane.getChildren().addAll(
			label_PageTitle, label_UserDetails, button_UpdateThisUser, line_Separator1,
	        line_Separator4, button_ReturnToHomePage, button_Logout, button_Quit, 
	        button_NewPost, LessonsBox, LessonsScroll, PostBody, PostTitle, timeTakenArea);
         refreshFeed();
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
			timeTakenArea.clear();
			refreshFeed();
			}
	}
	
	private void refreshFeed() {
		LessonsBox.getChildren().clear();
		List<lessonPost> lPosts = theDatabase.getAllPosts();
		for (lessonPost LP : lPosts) {
			if (LP.Author.equals(theDatabase.getCurrentUsername())) {
				System.out.println("Author: " + LP.Author + " Currently Logged in user: " + theDatabase.getCurrentUsername());
				LessonsBox.getChildren().add(createNewPostBox(LP)); }
			}
	}
	
	private VBox createNewPostBox(lessonPost LP) {
		VBox newPost = new VBox(8);

		Label AuthorDisplay = new Label("Author: ");
		setupLabelUI(AuthorDisplay, "Franklin Gothic Medium", 18, 100, Pos.BASELINE_RIGHT, 0, 30, "#9885FF");
		Label TitleDisplay = new Label("Title: ");
		setupLabelUI(TitleDisplay, "Franklin Gothic Medium", 18, 100, Pos.BASELINE_RIGHT, 0, 30, "#9885FF");
		Label BodyDisplay = new Label("Body: ");
		setupLabelUI(BodyDisplay, "Franklin Gothic Medium", 18, 100, Pos.BASELINE_RIGHT, 0, 30, "#9885FF");
		Label TimeDisplay = new Label("Time/Effort: ");
		setupLabelUI(TimeDisplay, "Franklin Gothic Medium", 18, 100, Pos.BASELINE_RIGHT, 0, 30, "#9885FF");

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
		setupLabelUI(title, "Franklin Gothic Medium", 16, 250, Pos.BASELINE_LEFT, 105, 30, "white");
		title.setStyle("-fx-border-color: grey; -fx-border-width: 2px; -fx-padding: 10px; -fx-border-radius: 5px;");
		title.setMaxHeight(30);
		titleRow.getChildren().addAll(TitleDisplay, title);
		
		//if (LP.titleEditable) {
		//	TextArea title = new TextArea(LP.postTitle);
		//	
		//	title.getOnInputMethodTextChanged();
		//	title.textProperty().addListener((observableTitle, oldValueTitle, newValueTitle) -> {theDatabase.updateTitle(newValueTitle, LP.ID); });
		//	title.setMaxSize(250, 30);
		//	title.setWrapText(true);
		//	
		//	title.setFont(Font.font("Franklin Gothic Book", 16));
		//	title.setStyle("-fx-prompt-text-fill: grey; -fx-text-fill: white;"
		//			+ " -fx-control-inner-background: #1A1A1A; -fx-border-color: grey; "
		//			+ "-fx-border-width: 2px;");
		//	
		//	titleRow.getChildren().addAll(TitleDisplay, title);
		//} else {Label title = new Label(LP.postTitle);
		//		setupLabelUI(title, "Franklin Gothic Medium", 16, 250, Pos.BASELINE_LEFT, 105, 30, "white");
		//		title.setStyle("-fx-border-color: grey; -fx-border-width: 2px; -fx-padding: 10px; -fx-border-radius: 5px;");
		//		title.setMaxHeight(30);
		//		titleRow.getChildren().addAll(TitleDisplay, title);}
		//
			
		
		//Author Row
		HBox authorRow = new HBox(8);
		Label author = new Label(LP.Author);
		setupLabelUI(author, "Franklin Gothic Medium", 16, 250, Pos.BASELINE_LEFT, 105, 30, "white");
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
		body.setFont(Font.font("Franklin Gothic Book", 16));
		body.setStyle("-fx-prompt-text-fill: grey; -fx-text-fill: white;"
				+ " -fx-control-inner-background: #1A1A1A; -fx-border-color: grey; "
				+ "-fx-border-width: 2px;");
		body.setEditable(false);
		bodyRow.getChildren().addAll(BodyDisplay, body);
		
		//if (LP.bodyEditable) {
		//	TextArea body = new TextArea(LP.postBody);
		//	body.textProperty().addListener((observableBody, oldValueBody, newValueBody) -> {theDatabase.updateBody(newValueBody, LP.ID);});
		//	body.setMaxSize(250, 125);
		//	body.setWrapText(true);
		//	body.setFont(Font.font("Franklin Gothic Book", 16));
		//	body.setStyle("-fx-prompt-text-fill: grey; -fx-text-fill: white;"
		//			+ " -fx-control-inner-background: #1A1A1A; -fx-border-color: grey; "
		//			+ "-fx-border-width: 2px;");
		//	
		//	bodyRow.getChildren().addAll(BodyDisplay, body);
		//} else { TextArea body = new TextArea(LP.postBody);
		//	body.setMaxSize(250, 125);
		//	body.setWrapText(true);
		//	body.setFont(Font.font("Franklin Gothic Book", 16));
		//	body.setStyle("-fx-prompt-text-fill: grey; -fx-text-fill: white;"
		//		+ " -fx-control-inner-background: #1A1A1A; -fx-border-color: grey; "
		//		+ "-fx-border-width: 2px;");
		//	body.setEditable(false);
		//	bodyRow.getChildren().addAll(BodyDisplay, body);}
		//
			
		
		//Time Effort Row
		HBox timeEffortRow = new HBox(8);
		timeEffortRow.setAlignment(Pos.CENTER);
		
		Label timeEffort = new Label(LP.timeEffort);
		setupLabelUI(timeEffort, "Franklin Gothic Medium", 16, 250, Pos.BASELINE_LEFT, 105, 30, "white");
		timeEffort.setStyle("-fx-border-color: grey; -fx-border-width: 2px; -fx-padding: 10px; -fx-border-radius: 5px;");
		timeEffort.setMaxHeight(30);
		timeEffortRow.getChildren().addAll(TimeDisplay, timeEffort);
		
		//if (LP.timeEditable) {
		//	TextArea timeEffortTXT = new TextArea(LP.timeEffort);
		//	timeEffortTXT.textProperty().addListener((observableTETXT, oldValueTETXT, newValueTETXT) -> {theDatabase.updateTimeEffort(newValueTETXT, LP.ID);});
		//	timeEffortTXT.setMaxSize(250, 50);
		//	timeEffortTXT.setWrapText(true);
		//	
		//	timeEffortTXT.setFont(Font.font("Franklin Gothic Book", 16));
		//	timeEffortTXT.setStyle("-fx-prompt-text-fill: grey; -fx-text-fill: white;"
		//			+ " -fx-control-inner-background: #1A1A1A; -fx-border-color: grey; "
		//			+ "-fx-border-width: 2px;");
		//	
		//	timeEffortRow.getChildren().addAll(TimeDisplay, timeEffortTXT);
		//} else {Label timeEffortTXT = new Label(LP.timeEffort);
		//		setupLabelUI(timeEffortTXT, "Franklin Gothic Medium", 16, 250, Pos.BASELINE_LEFT, 105, 30, "white");
		//		timeEffortTXT.setStyle("-fx-border-color: grey; -fx-border-width: 2px; -fx-padding: 10px; -fx-border-radius: 5px;");
		//		timeEffortTXT.setWrapText(true);
		//		timeEffortTXT.setMaxWidth(250);
		//		timeEffortRow.getChildren().addAll(TimeDisplay, timeEffortTXT);}
		//
		
		
		
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
						
						titleEdit.setFont(Font.font("Franklin Gothic Book", 16));
						titleEdit.setStyle("-fx-prompt-text-fill: grey; -fx-text-fill: white;"
								+ " -fx-control-inner-background: #1A1A1A; -fx-border-color: grey; "
								+ "-fx-border-width: 2px;");
						titleRow.getChildren().clear();
						titleRow.getChildren().addAll(TitleDisplay, titleEdit);
						//refreshFeed();
					} else {
						// field is not editable
						Label cannotEdit = new Label("Field cannot be edited");
						setupLabelUI(cannotEdit, "Franklin Gothic Medium", 14, 250, Pos.BASELINE_LEFT, 105, 30, "#E00000");
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
						bodyEdit.setFont(Font.font("Franklin Gothic Book", 16));
						bodyEdit.setStyle("-fx-prompt-text-fill: grey; -fx-text-fill: white;"
								+ " -fx-control-inner-background: #1A1A1A; -fx-border-color: grey; "
								+ "-fx-border-width: 2px;");
						bodyRow.getChildren().clear();
						bodyRow.getChildren().addAll(BodyDisplay, bodyEdit);
						//refreshFeed();
					} else { 
						// field is not editable
						Label cannotEdit = new Label("Field cannot be edited");
						setupLabelUI(cannotEdit, "Franklin Gothic Medium", 14, 250, Pos.BASELINE_LEFT, 105, 30, "#E00000");
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
							
						timeEffortTXT.setFont(Font.font("Franklin Gothic Book", 16));
						timeEffortTXT.setStyle("-fx-prompt-text-fill: grey; -fx-text-fill: white;"
								+ " -fx-control-inner-background: #1A1A1A; -fx-border-color: grey; "
								+ "-fx-border-width: 2px;");
						timeEffortRow.getChildren().clear();
						timeEffortRow.getChildren().addAll(TimeDisplay, timeEffortTXT);
					} else {
						// field is not editable
						Label cannotEdit = new Label("Field cannot be edited");
						setupLabelUI(cannotEdit, "Franklin Gothic Medium", 14, 250, Pos.BASELINE_LEFT, 105, 30, "#E00000");
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
					setupLabelUI(titleDone, "Franklin Gothic Medium", 14, 250, Pos.BASELINE_LEFT, 105, 30, "white");
					titleDone.setStyle("-fx-border-color: grey; -fx-border-width: 2px; -fx-padding: 10px; -fx-border-radius: 5px;");
					titleDone.setMaxHeight(30);
					titleRow.getChildren().clear();
					titleRow.getChildren().addAll(TitleDisplay, titleDone);
					
					TextArea bodyDone = new TextArea(LP.postBody);
					bodyDone.setMaxSize(250, 125);
					bodyDone.setWrapText(true);
					bodyDone.setFont(Font.font("Franklin Gothic Book", 16));
					bodyDone.setStyle("-fx-prompt-text-fill: grey; -fx-text-fill: white;"
							+ " -fx-control-inner-background: #1A1A1A; -fx-border-color: grey; "
							+ "-fx-border-width: 2px;");
					bodyDone.setEditable(false);
					bodyRow.getChildren().clear();
					bodyRow.getChildren().addAll(BodyDisplay, bodyDone);
					
					Label timeEffortDone = new Label(LP.timeEffort);
					setupLabelUI(timeEffortDone, "Franklin Gothic Medium", 16, 250, Pos.BASELINE_LEFT, 105, 30, "white");
					timeEffortDone.setStyle("-fx-border-color: grey; -fx-border-width: 2px; -fx-padding: 10px; -fx-border-radius: 5px;");
					timeEffortDone.setMaxHeight(30);
					timeEffortRow.getChildren().clear();
					timeEffortRow.getChildren().addAll(TimeDisplay, timeEffortDone);
					
					refreshFeed();
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
					refreshFeed();
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
	
	private String[] publishingInputValidation(String title, String Body, String timeTaken) {
		// Input type refers to (0) Title, (1) Body, (2) time taken
		
		if (title.length() < 5) { return new String[] {"Invalid Title", "Title is less than 5 characters."};
		} else if (title.length() > 35) { return new String[] {"Invalid Title", "Title is more than 35 characters."};
		} else if (Body.length() < 10) { return new String[] {"Invalid Body", "Body is less than 10 characters."}; 
		} else if (timeTaken.length() > 50) { return new String[] {"Invalid Body ", "Time taken is more than 50 characters."}; 
		} else {return null;}	
	}

	
	/*********************************************************************************************

	Helper methods to reduce code length

	 *********************************************************************************************/

	/**********
	 * Private local method to initialize the standard fields for a label
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
	
	/**********
	 * Private local method to initialize the standard fields for a text field
	 */
	private void setupTextUI(TextField t, String ff, double f, double w, Pos p, double x, double y, boolean e){
		t.setFont(Font.font(ff, f));
		t.setMinWidth(w);
		t.setMaxWidth(w);
		t.setAlignment(p);
		t.setLayoutX(x);
		t.setLayoutY(y);		
		t.setEditable(e);
		t.setStyle("-fx-text-fill: white; -fx-background-color: #1A1A1A;"
				+ " -fx-border-color: grey; -fx-border-width: 2px; -fx-border-radius: 10px;");
	}
	
	/********** 
	 * Private local method to initialize the Post box UI
	 */
	private static void SetupPostBoxUI(TextArea Title, TextArea Body, TextArea timeTakenArea ){
		Title.setMinWidth(100);
		Title.setMaxWidth(250);
		Title.setMinHeight(25);
		Title.setMaxHeight(50);
		Title.setLayoutX(50);
		Title.setLayoutY(105);
		Title.setPrefRowCount(8);
		Title.setWrapText(true);
		Title.setPromptText("<Insert Title Information here>");
			
		Body.setMinWidth(100);
		Body.setMaxWidth(250);
		Body.setMinHeight(100);
		Body.setMaxHeight(125);
		Body.setLayoutX(50);
		Body.setLayoutY(165);
		Body.setPrefRowCount(8);
		Body.setWrapText(true);
		Body.setPromptText("<Insert lessons learned body information here>");
		
		timeTakenArea.setMinWidth(100);
		timeTakenArea.setMaxWidth(250);
		timeTakenArea.setMinHeight(25);
		timeTakenArea.setMaxHeight(50);
		timeTakenArea.setLayoutX(50);
		timeTakenArea.setLayoutY(300);
		timeTakenArea.setPrefRowCount(8);
		timeTakenArea.setWrapText(true);
		timeTakenArea.setPromptText("<Insert time taken data here in hours>");
	}
	
}
