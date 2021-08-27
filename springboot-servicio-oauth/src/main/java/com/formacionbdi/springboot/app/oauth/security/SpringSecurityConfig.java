package com.formacionbdi.springboot.app.oauth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// Clase encargada de administrar la seguridad desde SpringSecurity

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	// 1. Inyectamos la interfaz UserDetailService del package service,
	// Spring se encargará de aplicar la clase que la implementa (UsuarioService)
	@Autowired
	private UserDetailsService usuarioService;
	
	// 2.Implementamos un método de encriptación en bcrypt
	// simplemente devuelve la instancia de la clase y se anota con Bean para que se 
	// guarde en el contenedor de Spring y poder aplicarlo en otra parte del microservicio
	// Se va a utilizar en la conf. del servidor de autorización
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// 3. Construimos en el autentication manager, anotamos con autowired para
	// conseguirlo, dentro del método, le aplicamos un método userDetail... y le pasamos
	// nuestro usuarioService, también le aplicamos la encrptación
	@Override
	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.usuarioService).passwordEncoder(passwordEncoder());
	}

	// 4. Añadimos el autentication manager como componente de Spring 
	// Se va a utilizar en la conf. del servidor de autorización
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}	

}
