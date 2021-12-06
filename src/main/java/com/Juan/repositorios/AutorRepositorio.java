package com.Juan.repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Juan.entidades.Autor;


@Repository 
public interface AutorRepositorio extends JpaRepository<Autor, String> {
	@Query("SELECT a FROM Autor a WHERE a.nombre LIKE %?1%")
	List<Autor> buscarPorAutor(@Param("nombre") String nombre);
	
	@Query("SELECT l from Autor l WHERE l.alta = true ")
	List<Autor> buscarActivos();
	
	@Query("SELECT p from Autor p WHERE (p.nombre LIKE :q) AND ( p.alta=1 ) ORDER BY p.nombre ASC")
	List<Autor> listarPorNombre(@Param("q") String nombre);
}
