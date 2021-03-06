package com.formacionbdi.springboot.app.productos.controllers;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.formacionbdi.springboot.app.commons.models.entity.Producto;
import com.formacionbdi.springboot.app.productos.models.service.IProductoService;

@RestController
public class ProductoController {
	
	@Autowired
	private Environment env;
	
	@Value("${server.port}")
	private Integer port;
	
	@Autowired
	private IProductoService productoService;
	
	@GetMapping("/listar")
	public List<Producto> listar(){
		return productoService.findAll().stream().map(producto ->{
			//producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
			producto.setPort(port);
			return producto;
		}).collect(Collectors.toList());
	}
	
	@GetMapping("/ver/{id}")
	public Producto detalle(@PathVariable Long id) throws InterruptedException {
		
		if(id.equals(10L)) {
			throw new IllegalStateException("Producto no encontrado!");
		}
		
		if(id.equals(7L)) {
			TimeUnit.SECONDS.sleep(5L);
		}
		
		Producto producto = productoService.findById(id);
		//producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
		producto.setPort(port);
				
		return producto;
	}
	
	@PostMapping("/crear") //Endpoint para peticiones de tipo post 
	@ResponseStatus(HttpStatus.CREATED) // Cuando se ejecute devolver?? 201s
	public Producto crear(@RequestBody Producto producto) { //Este m??todo crea, persiste y devuelve un producto que recibe como JSON en el cuerpo de la petici??n, siempre que los par??metros JSON coincidan con los que lo forman en la BBDD
		return productoService.saveReturn(producto);
		
	}
	
	
	@PutMapping("/editar/{id}") //Endpoint para el replace del crud, peticiones tipo put
	@ResponseStatus(HttpStatus.CREATED)
	public Producto editar(@RequestBody Producto producto,@PathVariable Long id) {
		
		// Creamos un producto dentro de este m??todo con el que recuperamos a trav??s del id
		Producto productoDb=productoService.findById(id);
	    //Al producto que recuperamos le cambiamos el nombre y el precio a trav??s de los m??todos getter y setter
		productoDb.setNombre(producto.getNombre());// El nombre lo recibimos del objeto json por par??metro
		productoDb.setPrecio(producto.getPrecio()); // idem
		
		return productoService.saveReturn(productoDb); // llamamos al m??todo save return pero le pasamos el objeto que hemos modificado dentro de este m??todo
	}
	
	@DeleteMapping("/eliminar/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void eliminar(@PathVariable Long id) {
		productoService.eliminaPorId(id);
	}
	
	
	
	
	

}
