package com.formacionbdi.springboot.app.usuarios;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.formacionbdi.springboot.app.commons.usuarios.models.entity.Role;
import com.formacionbdi.springboot.app.commons.usuarios.models.entity.Usuario;

@Configuration
/*Se implementa una interfaz RepositoryRestConfigurer para configurar aquellos proyectos que 
 * son automáticamente implementados mediante la anotación @RepositoryRestResource
 * en la capa dao*/
public class RepositoryConfig implements RepositoryRestConfigurer{

	//El objetivo de este método es ser capaces de exponer el id de cada usuario/ role
	// ya que por defecto con la implementación automática que hemos desarrollado no viene
	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
	config.exposeIdsFor(Usuario.class,Role.class);
	}

	
	
}
