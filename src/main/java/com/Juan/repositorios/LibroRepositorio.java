package com.Juan.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Juan.entidades.Libro;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro, String>{
	@Query("SELECT l from Libro l WHERE l.alta = true ")
	List<Libro> buscarActivos();
	
	@Query("SELECT p from Libro p WHERE (p.titulo LIKE :q) AND ( p.alta=1 ) ORDER BY p.titulo ASC")
	List<Libro> listarPorNombre(@Param("q") String nombre);

}
