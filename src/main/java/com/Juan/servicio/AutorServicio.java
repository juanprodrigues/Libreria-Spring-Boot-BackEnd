package com.Juan.servicio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.Juan.entidades.Autor;
import com.Juan.entidades.Editorial;
import com.Juan.entidades.Libro;
import com.Juan.repositorios.AutorRepositorio;

@Service
public class AutorServicio {
	@Autowired
	private AutorRepositorio autorRepositorio;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Autor guardar(String nombre) throws Exception {
		Autor autor = new Autor();

		validad(nombre);
		autor.setNombre(nombre);
		autor.setAlta(true);

		return autorRepositorio.save(autor);
	}

	@Transactional(readOnly = true)
	public List<Autor> buscarPorNombre(String nombre) {
		return autorRepositorio.buscarPorAutor(nombre);
	}
	@Transactional(readOnly = true)
	public Autor obtenerAutor(String id) {
		return autorRepositorio.getOne(id);

	}

	@Transactional(readOnly = true)
	public List<Autor> listarTodos() {
		return autorRepositorio.findAll();
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Autor alta(String id) {

		Autor entidad = autorRepositorio.getOne(id);

		entidad.setAlta(true);
		return autorRepositorio.save(entidad);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Autor baja(String id) {

		Autor entidad = autorRepositorio.getOne(id);

		entidad.setAlta(false);
		return autorRepositorio.save(entidad);
	}
	@Transactional
	private void validad(String nombre) throws Exception {
		if (nombre.isEmpty()) {
			throw new Exception();
		}
	}
	@Transactional
	public List<Autor> listarPorNombre(String nombre) {

		return autorRepositorio.listarPorNombre("%" + nombre + "%");

	}
	@Transactional
	public List<Autor> listarActivos() {
		return autorRepositorio.buscarActivos();
	}
	
	
	
	@Transactional
	public Autor modificar(String id,String nombre) throws Exception {
//		validad(isbn, titulo, anio, ejemplares);

		Autor entidad = autorRepositorio.getOne(id);
		System.out.println(entidad.toString());

		entidad.setNombre(nombre);



		return autorRepositorio.save(entidad);

	}
	
	
	
	
	
	
	
	
}
