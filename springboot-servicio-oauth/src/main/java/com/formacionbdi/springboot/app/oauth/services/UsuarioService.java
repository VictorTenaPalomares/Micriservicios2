package com.formacionbdi.springboot.app.oauth.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.formacionbdi.springboot.app.commons.usuarios.models.entity.Usuario;
import com.formacionbdi.springboot.app.oauth.clients.UsuarioFeignClient;

/*Clase destinada a implementar la lógica de negocio para la autenticación del usuario
 * implementa una interfaz especial propia de spring security que implementa un método*/
@Service
public class UsuarioService  implements UserDetailsService{
	

	// Para registrar mensajes en nuetro log;
	private Logger log=LoggerFactory.getLogger(UsuarioService.class);
	
	@Autowired
	private UsuarioFeignClient client; 

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		// Convertimos al usuario que nos viene del otro microservicio
		Usuario usuario =client.findByUsername(username);
		
		// validamos en caso de null lanzamos ex e imprimimos en el log
		if (usuario==null) {
			log.error("Error en el login, no existe el usuario "+username+" en el sistema");
			throw new UsernameNotFoundException("Error en el login, no existe el usuario "+username+" en el sistema");
		}
		
		/*Este método devuelve un UserDetails que es una interfaz, entonces,
		 * para que esa devolucion se haga, hay que retornar un objeto de una clase que 
		 * la implemente. En este caso User de spring security. A su vez la clase User
		 * tiene un constructor que como último parámetro acepta un lista de otra interfaz
		 * por lo tanto hay que buscar la clase correspondiente que la implemente
		 * GrantedAuthority->SimpleGrantedAuthority. Cada rol lo instanciamos y al final
		 * lo convertimos todo a una lista por medio de streams de Java 8.*/
		
		List<GrantedAuthority> authorities=usuario
			.getRoles()
			.stream()
			.map(role->new SimpleGrantedAuthority(role.getNombre()))
			.peek(authority->log.info("Role: "+ authority.getAuthority()))
			.collect(Collectors.toList());
		
		log.info("Usuario autenticado: " + username);
		
		return new User(usuario.getUsername(),
				usuario.getPassword(),
				usuario.getEnabled(),
				true,
				true,
				true,
				authorities);
	}

}
