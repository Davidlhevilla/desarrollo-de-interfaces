package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Ejercicio1 extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.show();
            primaryStage.setTitle("Programa de Aleja");
            Image anotherIcon = new Image("file:archivo.png");
    		primaryStage.getIcons().add(anotherIcon);



            StackPane panel1 = new StackPane();
            Rectangle r1 = new Rectangle(400,400,Color.DARKGREEN);
            Rectangle r2 = new Rectangle(300,300,Color.GREEN);
            Rectangle r3 = new Rectangle(200,200,Color.LIGHTGREEN);

            panel1.getChildren().addAll(r1,r2,r3);
            Scene scene = new Scene(panel1,400,400);
            primaryStage.setScene(scene);
            primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
