module sokoban {
	exports persistentie;
	exports cui;
	exports main;
	exports domein;
	exports exceptions;

	requires java.sql;
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
	
	opens main to javafx.graphics,javafx.fxml;
	opens gui to javafx.graphics,javafx.fxml;
	
}