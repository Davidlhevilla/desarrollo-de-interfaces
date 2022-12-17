package application;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Libros {
	private int id;
	private SimpleStringProperty titulo;
	private SimpleStringProperty editorial;
	private SimpleStringProperty autor;
	private SimpleIntegerProperty paginas;
	
	
	public Libros(String titulo, String editorial, String autor,
			int paginas) {
		super();
		this.titulo = new SimpleStringProperty(titulo);
		this.editorial = new SimpleStringProperty(editorial);
		this.autor = new SimpleStringProperty(autor);
		this.paginas = new SimpleIntegerProperty(paginas);
		
	}
	public Libros(int id,String titulo, String editorial, String autor,
			int paginas) {
		super();
		this.id = id;
		this.titulo = new SimpleStringProperty(titulo);
		this.editorial = new SimpleStringProperty(editorial);
		this.autor = new SimpleStringProperty(autor);
		this.paginas = new SimpleIntegerProperty(paginas);
		
	}

	
	
	
	public int getId() {
		return id;
	}
	public String getTitulo() {
		return titulo.get();
	}


	public void setTitulo(String titulo) {
		this.titulo = new SimpleStringProperty(titulo);
	}


	public String getEditorial() {
		return editorial.get();
	}


	public void setEditorial(String editorial) {
		this.editorial = new SimpleStringProperty(editorial);
	}


	public String getAutor() {
		return autor.get();
	}


	public void setAutor(String autor) {
		this.autor = new SimpleStringProperty(autor);
	}


	public int getPaginas() {
		return paginas.get();
	}


	public void setPegi(int paginas) {
		this.paginas = new SimpleIntegerProperty(paginas);
	}
	
}
