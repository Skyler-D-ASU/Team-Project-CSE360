/**
 * Required Libraries
 */
module FoundationsF26 {
	requires javafx.controls;
	requires java.sql;
	requires javafx.graphics;
	requires java.desktop;
	requires com.h2database;
	requires javafx.base;
	
	opens applicationMain to javafx.graphics, javafx.fxml;
}
