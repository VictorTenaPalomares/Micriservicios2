package com.formacionbdi.springboot.app.productos.models.service;

import java.util.List;

import com.formacionbdi.springboot.app.commons.models.entity.Producto;

public interface IProductoService {

	public List<Producto> findAll();
	public Producto findById(Long id);
	
    //crea un producto y lo devuelve
	public Producto saveReturn(Producto producto);
	
	// elimina a través del id del producto
	public void eliminaPorId(Long id);
	
		
	
}
