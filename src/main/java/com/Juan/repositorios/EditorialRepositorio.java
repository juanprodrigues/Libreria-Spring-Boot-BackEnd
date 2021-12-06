package com.Juan.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.Juan.entidades.Editorial;
@Repository 
public interface EditorialRepositorio extends JpaRepository<Editorial,String> {
	@Query("SELECT a FROM Editorial a WHERE a.nombre LIKE %?1%")
	List<Editorial> buscarPorEditorial(@Param("nombre") String nombre);
	
	@Query("SELECT l from Editorial l WHERE l.alta = true ")
	List<Editorial> buscarActivos();
	
	@Query("SELECT p from Editorial p WHERE (p.nombre LIKE :q) AND ( p.alta=1 ) ORDER BY p.nombre ASC")
	List<Editorial> listarPorNombre(@Param("q") String nombre);
}