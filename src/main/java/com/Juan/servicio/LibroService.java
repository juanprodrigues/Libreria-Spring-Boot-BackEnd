package com.Juan.servicio;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Juan.entidades.Autor;
import com.Juan.entidades.Editorial;
import com.Juan.entidades.Libro;
import com.Juan.repositorios.AutorRepositorio;
import com.Juan.repositorios.EditorialRepositorio;
import com.Juan.repositorios.LibroRepositorio;

import javassist.expr.NewArray;

@Service
public class LibroService {

	@Autowired
	private AutorRepositorio autorRepositorio;
	@Autowired
	private EditorialRepositorio editorialRepositorio;
	@Autowired
	private LibroRepositorio libroRepositorio;

	@Transactional
	public Libro guardarLibro(Long isbn, String titulo, Integer anio, Integer ejemplares, String autor,
			String editorial) throws Exception {

		validad(isbn, titulo, anio, ejemplares, autor, editorial);
		Libro libro = new Libro();
		libro.setIsbn(isbn);
		libro.setTitulo(titulo);
		libro.setAnio(anio);
		libro.setEjemplares(ejemplares);
		libro.setAlta(true);
		libro.setAutor(autorRepositorio.getById(autor));
		libro.setEditorial(editorialRepositorio.getById(editorial));

		return libroRepositorio.save(libro);

	}

	public void validad(Long isbn, String titulo, Integer anio, Integer ejemplares, String autor, String editorial)
			throws Exception {

		if (isbn == null) {
			throw new Exception("nulo isbn");
		}
		if (titulo == null || titulo.isEmpty()) {
			throw new Exception();
		}
		if (anio == null) {
			throw new Exception();
		}
		if (ejemplares == null) {
			throw new Exception();
		}
		if (autor == null || autor.isEmpty()) {
			throw new Exception();
		}
		if (editorial == null || editorial.isEmpty()) {
			throw new Exception();
		}
	}
	public void validad(Long isbn, String titulo, Integer anio, Integer ejemplares)
			throws Exception {

		if (isbn == null ) {
			throw new Exception("Esta nulo isbn");
		}
		if (titulo == null || titulo.isEmpty() || titulo.contains("  ") ){
			throw new Exception("esta vacio titulo");
		}
		if (anio == null ) {
			throw new Exception("esta error año");
		}
		if (ejemplares == null ) {
			throw new Exception("ejenplares pa");
		}

	}


	@Transactional
	public Autor guardarAutor(String nombre) {

		Autor autor = new Autor();

		autor.setNombre(nombre);
		autor.setAlta(true);

		return autorRepositorio.save(autor);
	}

	@Transactional
	public Editorial guardarEditorial(String nombre) {

		Editorial editorial = new Editorial();

		editorial.setNombre(nombre);
		editorial.setAlta(true);

		return editorialRepositorio.save(editorial);
	}

	@Transactional
	public List<Libro> listarActivos() {
		return libroRepositorio.buscarActivos();
	}

	@Transactional
	public List<Libro> listarTodos() {

		return libroRepositorio.findAll();
	}

	// para ajax

	public List<Libro> listarPorNombre(String nombre) {

		return libroRepositorio.listarPorNombre("%" + nombre + "%");

	}

	public Libro obtenerLibro(String id) {
		return libroRepositorio.getOne(id);

	}

	public Libro actualizarLibro(Libro libro) throws Exception {
		String autor = libro.getAutor().getNombre();
		String editorial = libro.getEditorial().getNombre();
		validad(libro.getIsbn(), libro.getTitulo(), libro.getAnio(), libro.getEjemplares(), autor, editorial);

		return libroRepositorio.save(libro);
	}

	@Transactional
	public Libro modificar(String id, Long isbn, String titulo, Integer anio, Integer ejemplares) throws Exception {
		validad(isbn, titulo, anio, ejemplares);

		Libro entidad = libroRepositorio.getOne(id);
		System.out.println(entidad.toString());
//		Autor a = entidad.getAutor();
//		Editorial e = entidad.getEditorial();
//		
//		a.setNombre(autor);
//		e.setNombre(editorial);
//		System.out.println(a.toString());
//		System.out.println(e.toString());
		entidad.setIsbn(isbn);
		entidad.setTitulo(titulo);
		entidad.setAnio(anio);
		entidad.setEjemplares(ejemplares);
//		entidad.setEditorial(e);
//		entidad.setAutor(a);

		return libroRepositorio.save(entidad);

	}
	
    @Transactional
    public Libro alta(String id) {

    	Libro entidad = libroRepositorio.getOne(id);

        entidad.setAlta(true);
        return libroRepositorio.save(entidad);
    }

    @Transactional
    public Libro baja(String id) {

    	Libro entidad = libroRepositorio.getOne(id);

        entidad.setAlta(false);
        return libroRepositorio.save(entidad);
    }
	

}
