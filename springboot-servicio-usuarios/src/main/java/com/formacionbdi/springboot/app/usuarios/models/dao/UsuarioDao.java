package com.formacionbdi.springboot.app.usuarios.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.formacionbdi.springboot.app.commons.usuarios.models.entity.Usuario;

@RepositoryRestResource(path="usuarios") // todo automático
public interface UsuarioDao extends PagingAndSortingRepository<Usuario, Long>{

	// Es muy importante el nombre del método porque repercute en la consulta a realizar
	// usar ayuda IDE
	@RestResource(path="buscar-username") //personalizando el endpoint
	public Usuario findByUsername(@Param(value = "username") String username); // personalizando el nombre del parámetro par el endpoint

	
	/*Es lo mismo que arriba pero en versión JPA*/
	@Query("select u from Usuario u where u.username=?1")
	public Usuario obtenerPorUsername(String username);
	
	
	
	/*O también mediante SQL nativo*/
//	@Query(value = "La consulta en SQL nativo",nativeQuery = true)
//	public Usuario obtenerPorUsername(String username);
	
	
}
