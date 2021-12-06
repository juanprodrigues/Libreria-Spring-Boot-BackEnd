package com.Juan.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.Juan.entidades.Autor;
import com.Juan.entidades.Editorial;
import com.Juan.entidades.Libro;
import com.Juan.repositorios.EditorialRepositorio;

@Service
public class EditorialServicio {
	
	@Autowired
	private EditorialRepositorio editorialRepositorio;
	
	private void validad(String nombre) throws Exception {
		if (nombre.isEmpty()) {
			throw new Exception();
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Editorial guardar(String nombre) throws Exception {
		Editorial editorial = new Editorial();

		validad(nombre);
		editorial.setNombre(nombre);
		editorial.setAlta(true);

		return editorialRepositorio.save(editorial);
	}
	

	@Transactional(readOnly = true)
	public List<Editorial> listarTodos() {
		return editorialRepositorio.findAll();
	}
	
	

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Editorial alta(String id) {

		Editorial entidad = editorialRepositorio.getOne(id);

		entidad.setAlta(true);
		return editorialRepositorio.save(entidad);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Editorial baja(String id) {

		Editorial entidad = editorialRepositorio.getOne(id);

		entidad.setAlta(false);
		return editorialRepositorio.save(entidad);
	}
	
	public List<Editorial> listarPorNombre(String nombre) {

		return editorialRepositorio.listarPorNombre("%" + nombre + "%");

	}
	@Transactional
	public List<Editorial> listarActivos() {
		return editorialRepositorio.buscarActivos();
	}

	
	@Transactional(readOnly = true)
	public Editorial obtenerEditorial(String id) {
		return editorialRepositorio.getOne(id);

	}
	@Transactional
	public Editorial modificar(String id,String nombre) throws Exception {
//		validad(isbn, titulo, anio, ejemplares);

		Editorial entidad = editorialRepositorio.getOne(id);
		System.out.println(entidad.toString());

		entidad.setNombre(nombre);



		return editorialRepositorio.save(entidad);

	}
	
	
	
	
	
	
	
	
	
	

}
