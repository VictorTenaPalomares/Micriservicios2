package com.formacionbdi.springboot.app.oauth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

// Esta clase es un servidor de autorización que se encarga de todo el proceso del login
// y de procesar todo lo relacionado con el token a través de JWT

@Configuration 
@EnableAuthorizationServer //1 importante esta anotación
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{

	//2 procede del bean de la clase SpringSecurityConfig
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	//3 idem
	@Autowired
	private AuthenticationManager authenticationManager;

	// para dar seguridad a los endpoint
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("permitAll()")
		.checkTokenAccess("isAuthenticated()") // se encarga de validar el token
		;
	}

	// Aquí se configuran las aplicaciones clientes que van a interactuar con nuestro servicio segurizado
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

		clients.inMemory() //Almacenamos los clientes en memoria
		.withClient("frontendapp") //dándoles un identificador
		.secret(passwordEncoder.encode("12345")) // una contraseña encriptada con passworEncoder
		.scopes("read", "write") // configuramos el alcance, es decir permiso de lectura, escritura, en fin...
		.authorizedGrantTypes("password","redirect_uri","refresh_token")//tipo de concesión que va a tener nuestra autenticacion , en este caso un sistema de login tradicional
		.accessTokenValiditySeconds(3600)// configurar el tiempo de validez de acceso al token
		.refreshTokenValiditySeconds(3600) // configurar el tiempo de validez de refresco del token
		.and()
//		.withClient("androidapp") 
//		.secret(passwordEncoder.encode("12345")) 
//		.scopes("")                                           ASÍ PONDRÍAMOS A OTRO CLIENTE QUE CONSUMA NUESTRO SERVICIO
//		.authorizedGrantTypes("password","refresh_token")
//		.accessTokenValiditySeconds(3600)
//		.refreshTokenValiditySeconds(3600)
		;
	
	}

	//endpoint=/oauth/token
	//4 registramos el authenticationManager en el authenticationServer 
	//y el token storage que es de tipo jwt, 
	//tambien el accestokenconverter que guarda los datos del usuario en el token
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
	
		// registrar el autenticationManager, configurar el accestoken converter
		// creando un método personalizado que modela la configuración Jwt,
		// configurar el token storage que se encarga de guardar el token con los daros del accestokenconverter
		endpoints.authenticationManager(authenticationManager)
		.tokenStore(tokenStore())
		.accessTokenConverter(accessTokenConverter());
	}

	// Conversión del token recibido aportando un código secreto
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter tokenConverter=new JwtAccessTokenConverter();
		// agregamos código secreto para firmar el token este código servirá para validar el token
		// por ejemplo en el gateway
		// y así poder acceder a recursos protegidos del servicio, o no...
		tokenConverter.setSigningKey("vaya_codigo_mas_secreto_aeiou");		
		return tokenConverter;
	}
	
	// Para poder almacenar el token necesitamos el componente que se encarga de convertir el token
		@Bean
		public JwtTokenStore tokenStore() {
			return new JwtTokenStore(accessTokenConverter());
		}
	
	
	
}
