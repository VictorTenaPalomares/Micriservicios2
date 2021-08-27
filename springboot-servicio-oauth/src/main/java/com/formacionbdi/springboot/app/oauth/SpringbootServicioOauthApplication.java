package com.formacionbdi.springboot.app.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableEurekaClient
//@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class}) Lo hacemos esta vez desde el pom
@EnableFeignClients // Inyecta aquellas interfaces que declaran que son clientes Feign
public class SpringbootServicioOauthApplication implements CommandLineRunner{

	// Inyectamos el codificador de contraseñas
	@Autowired
	BCryptPasswordEncoder passwordEncode;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringbootServicioOauthApplication.class, args);
	}

	// método que sirve para ejecutar ciertas configuraciones inyectadas desde un bean cuando arranca la aplicacion
	@Override
	public void run(String... args) throws Exception {
		
		//Creamos una contraseña de texto plano
		String password="12345";
		
  		// creamos a partir de ella 4 contraseñas encriptadas y las imprimimos
		for (int i = 0; i < 4; i++) {
			String BCryptPassword=passwordEncode.encode(password);
			System.out.println(BCryptPassword);
		}
	}

}
