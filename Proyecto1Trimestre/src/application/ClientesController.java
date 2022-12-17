package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

public class ClientesController {
	@FXML
	private TextField txtNombre;
	@FXML
	private TextField txtEdad;
	@FXML
	private ChoiceBox<String> cbSexo;

	@FXML
	private Button botonAnadir;
	@FXML
	private Button botonEliminar;

	@FXML
	private TableView<Cliente> tableCliente;
	@FXML
	private TableColumn<Cliente, String> columNombre;
	@FXML
	private TableColumn<Cliente, Integer> columEdad;
	@FXML
	private TableColumn<Cliente, String> columSexo;

	private ObservableList<Cliente> listaCliente = FXCollections
			.observableArrayList(new Cliente("David", 29, "Hombre"));

	public ObservableList<String> sexo = FXCollections.observableArrayList("Hombre", "Mujer", "Otro");
	public ObservableList<String> ejercicio = FXCollections.observableArrayList("");

	@FXML
	private void initialize() {
		cbSexo.setItems(sexo);
		columNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		columEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
		columSexo.setCellValueFactory(new PropertyValueFactory<>("sexo"));

		ObservableList listaClienteBD = getClienteBD();

		tableCliente.setItems(listaCliente);
		tableCliente.setItems(listaClienteBD);
//		ObservableList listaClienteBD1 = anadirVideo();
//		tableCliente.setItems(listaClienteBD1);

	}

	@FXML
	public void anadirCliente(ActionEvent event) {

		if (!txtNombre.getText().isBlank() && !txtEdad.getText().isBlank() && !cbSexo.getSelectionModel().isEmpty()) {
			if (esNumero(txtEdad.getText())) {
				Cliente c = new Cliente(txtNombre.getText(), Integer.parseInt(txtEdad.getText()),
						cbSexo.getValue().toString());

				listaCliente.add(c);

				txtNombre.clear();
				txtEdad.clear();
				cbSexo.getSelectionModel().clearSelection();

				// Nos conectamos a la BD
				DatabaseConnection dbConnection = new DatabaseConnection();
				Connection connection = dbConnection.getConnection();

				String query = "insert into cliente (nombre, edad, sexo)" + " values(?,?,?)";
				try {
					PreparedStatement ps = connection.prepareStatement(query);
					ps.setString(1, c.getNombre());
					ps.setInt(2, c.getEdad());
					ps.setString(3, c.getSexo());
					ps.executeUpdate();
					connection.close();
					ObservableList listaClienteBD = getClienteBD();
					tableCliente.setItems(listaClienteBD);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				Alert alerta = new Alert(AlertType.ERROR);
				alerta.setTitle("Error al insertar");
				alerta.setHeaderText("No se ha introducido un numero en el campo edad");
				alerta.setContentText("Por favor introduzca una edad correcto");
				alerta.showAndWait();

				/**
				 * SI NO SE INTRODUCEN TODOS LOS DATOS SE MOSTRARÁ UNA ALERTA DE TIPO WARNING
				 * AVISANDO QUE FALTAN DATOS
				 */
			}
		} else {
			Alert alerta = new Alert(AlertType.WARNING);
			alerta.setTitle("No se puede insertar");
			alerta.setHeaderText("No se puede dejar ningún campo en blanco");
			alerta.setContentText("Por favor introduzca todos los campos correctamente");
			alerta.showAndWait();
		}
	}

	@FXML
	public void borrarCliente(ActionEvent event) {

		int indiceSeleccionado = tableCliente.getSelectionModel().getSelectedIndex();

		System.out.println("Índice a borrar: " + indiceSeleccionado);
		if (indiceSeleccionado <= -1) {
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setTitle("Error al borrar");
			alerta.setHeaderText("No se ha seleccionado ningun cliente a borrar");
			alerta.setContentText("Por favor, selecciona un cliente para borrarlo");
			alerta.showAndWait();
		} else {

			DatabaseConnection dbConnection = new DatabaseConnection();
			Connection connection = dbConnection.getConnection();

			try {
				String query = "DELETE FROM cliente WHERE id = ?";
				PreparedStatement ps = connection.prepareStatement(query);
				Cliente cliente = tableCliente.getSelectionModel().getSelectedItem();
				ps.setInt(1, cliente.getId());
				ps.executeUpdate();

//			tableJuego.getItems().remove(indiceSeleccionado);
				tableCliente.getSelectionModel().clearSelection();

				// Actualizamos la tabla
				ObservableList listaClienteBD = getClienteBD();
				tableCliente.setItems(listaClienteBD);

				connection.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	private ObservableList<Cliente> getClienteBD() {
		/*
		 * Creamos la ObservableList donde almacenaremos los libros obtenidos de la BD
		 */
		ObservableList<Cliente> listaClienteBD = FXCollections.observableArrayList();

		// Nos conectamos a la BD
		DatabaseConnection dbConnection = new DatabaseConnection();
		Connection connection = dbConnection.getConnection();

		String query = " select * from cliente";

		// Cerramos la conexion
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Cliente cliente = new Cliente(rs.getInt("id"),rs.getString("nombre"), rs.getInt("edad"), rs.getString("sexo")

				);
				listaClienteBD.add(cliente);
			}
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return listaClienteBD;
	}

//	@FXML
//	public void borrarVideojuego(ActionEvent event) {
//		System.out.println("Borrando un videojuego");
//	}

	public boolean esNumero(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}