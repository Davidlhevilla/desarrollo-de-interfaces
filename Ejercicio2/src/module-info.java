module Ejercicio2 {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
	exports application;
	
	
	
	opens application to javafx.graphics, javafx.fxml, javafx.base;
	
	
	
}
