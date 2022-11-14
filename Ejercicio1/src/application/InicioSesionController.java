package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class InicioSesionController {
	
	
	@FXML
	private TextField txtNombre;
	
	@FXML
	private PasswordField txtContrasena;
	
	@FXML
	private Button btnEnviar;
	@FXML
	private Label lblMensaje;
	
	@FXML
	public void mostrarMensaje(ActionEvent event) {
		lblMensaje.setText("Bienvenido a Cenec "+ txtNombre.getText());
		txtNombre.setText("");
		txtContrasena.setText("");
		
	}
	
}
