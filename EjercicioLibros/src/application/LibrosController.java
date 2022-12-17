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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class LibrosController {
	@FXML
	private TextField txtTitulo;
	@FXML
	private TextField txtAutor;
	@FXML
	private ChoiceBox<String> cbEditorial;
	@FXML
	private TextField txtPaginas;
	@FXML
	private Button botonAnadir;
	@FXML
	private Button botonBorrar;

	@FXML
	private TableView<Libros> tableLibros;
	@FXML
	private TableColumn<Libros, String> columTitulo;
	@FXML
	private TableColumn<Libros, String> columEditorial;
	@FXML
	private TableColumn<Libros, String> columAutor;
	@FXML
	private TableColumn<Libros, Integer> columPaginas;

	private ObservableList<Libros> listaLibro = FXCollections
			.observableArrayList(new Libros("El señor de los anillos", "Minotauro", "J.R.R. Tolkien", 1984));

	public ObservableList<String> editorial = FXCollections.observableArrayList("Minotauro", "Salamandra", "PLAZA & JANES", "Planeta");
	

	@FXML
	private void initialize() {
		cbEditorial.setItems(editorial);
		columTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
		columEditorial.setCellValueFactory(new PropertyValueFactory<>("editorial"));
		columAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));
		columPaginas.setCellValueFactory(new PropertyValueFactory<>("paginas"));
		
		ObservableList listaLibrosBD = getLibrosBD();
		tableLibros.setItems(listaLibrosBD);

	}

	@FXML
	public void anadirLibro(ActionEvent event) {
		
		if(!txtTitulo.getText().isBlank()&&!txtAutor.getText().isBlank()&&!cbEditorial.getSelectionModel().isEmpty()&&!txtPaginas.getText().isBlank()) {
		if (esNumero(txtPaginas.getText())) {
			Libros v = new Libros(txtTitulo.getText(), txtAutor.getText(),
					cbEditorial.getValue().toString(),Integer.parseInt(txtPaginas.getText()));

			listaLibro.add(v);

			txtTitulo.clear();
			txtAutor.clear();
			cbEditorial.getSelectionModel().clearSelection();
			txtPaginas.clear();
			
			
			//Nos conectamos a la BD
			DatabaseConnection dbConnection = new DatabaseConnection();
			Connection connection = dbConnection.getConnection();

				String query = "insert into libros (titulo, autor, editorial, paginas)" + " values(?,?,?,?)";
				try {
					PreparedStatement ps = connection.prepareStatement(query);
					ps.setString(1, v.getTitulo());
					ps.setString(2, v.getAutor());
					ps.setString(3, v.getEditorial());
					ps.setInt(4, v.getPaginas());
					ps.executeUpdate();
					connection.close();
					ObservableList listaVideojuegosBD = getLibrosBD();
					tableLibros.setItems(listaVideojuegosBD);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		} else {
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setTitle("Error al insertar");
			alerta.setHeaderText("No se ha introducido un numero en el campo de paginas");
			alerta.setContentText("Por favor introduzca un numero de paginas correcto");
			alerta.showAndWait();
			
			/**
			 * SI NO SE INTRODUCEN TODOS LOS DATOS SE MOSTRARÁ UNA ALERTA DE TIPO WARNING
			 * AVISANDO QUE FALTAN DATOS
			 */
		}
		}else {
			Alert alerta = new Alert(AlertType.WARNING);
			alerta.setTitle("No se puede insertar");
			alerta.setHeaderText("No se puede dejar ningún campo en blanco");
			alerta.setContentText("Por favor introduzca todos los campos correctamente");
			alerta.showAndWait();
		}
	}
	@FXML
	public void borrarLibro(ActionEvent event) {
			
		int indiceSeleccionado = tableLibros.getSelectionModel().getSelectedIndex();
		
		System.out.println("Índice a borrar: "+indiceSeleccionado);
		if(indiceSeleccionado <= -1) {
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setTitle("Error al borrar");
			alerta.setHeaderText("No se ha seleccionado ningun libro a borrar");
			alerta.setContentText("Por favor, selecciona un libro para borrarlo");
			alerta.showAndWait();
		}else {
			DatabaseConnection dbConnection = new DatabaseConnection();
			Connection connection = dbConnection.getConnection();
			
			
			
			
			 
			try {
				String query = "DELETE FROM libros WHERE id = ?";
				PreparedStatement ps = connection.prepareStatement(query);
				Libros libros =tableLibros.getSelectionModel().getSelectedItem();
				ps.setInt(1,libros.getId());
				ps.executeUpdate();
			
//			tableJuego.getItems().remove(indiceSeleccionado);
			tableLibros.getSelectionModel().clearSelection();
			
			
			
			//Actualizamos la tabla
			ObservableList listaLibrosBD = getLibrosBD();
			tableLibros.setItems(listaLibrosBD);
			
			connection.close();

			
		}
		 catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		}
		
	}
	private ObservableList<Libros> getLibrosBD(){
		/*
		 * Creamos la ObservableList donde almacenaremos
		 * los libros obtenidos de la BD
		 */
		ObservableList<Libros>listaLibrosBD = 
				FXCollections.observableArrayList();
		
		//Nos conectamos a la BD
		DatabaseConnection dbConnection = new DatabaseConnection();
		Connection connection = dbConnection.getConnection();
		
		String query = " select * from libros";
		
		
		//Cerramos la conexion
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Libros libros = new Libros(
						rs.getInt("id"),
						rs.getString("titulo"),
						rs.getString("editorial"),
						rs.getString("autor"),
						rs.getInt("paginas")
						);
				listaLibrosBD.add(libros);
			}
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return listaLibrosBD;
	}

//	@FXML
//	public void borrarLibro(ActionEvent event) {
//		System.out.println("Borrando un libro");
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