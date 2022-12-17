package application;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Cliente {
	private int id;
	private SimpleStringProperty nombre;
	private SimpleIntegerProperty edad;
	private SimpleStringProperty sexo;
	
	
	
	public Cliente(String nombre, int edad, String sexo) {
		super();
		this.nombre = new SimpleStringProperty(nombre);
		this.edad = new SimpleIntegerProperty(edad);
		this.sexo = new SimpleStringProperty(sexo);
		
		
	}
	
	
	
	


	public Cliente(int id,String nombre, int edad, String sexo) {
		super();
		this.id = id;
		this.nombre = new SimpleStringProperty(nombre);
		this.edad = new SimpleIntegerProperty(edad);
		this.sexo = new SimpleStringProperty(sexo);
		
		
	}






	
	public int getId() {
		return id;
	}


	public String getNombre() {
		return nombre.get();
	}


	public void setNombre(String nombre) {
		this.nombre = new SimpleStringProperty(nombre);
	}
	
	


	public int getEdad() {
		return edad.get();
	}


	public void setEdad(int edad) {
		this.edad = new SimpleIntegerProperty(edad);
	}


	public String getSexo() {
		return sexo.get();
	}


	public void setSexo(String sexo) {
		this.sexo = new SimpleStringProperty(sexo);
	}


	
	
}
