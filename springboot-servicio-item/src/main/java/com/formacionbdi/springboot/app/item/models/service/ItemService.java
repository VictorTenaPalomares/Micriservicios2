package com.formacionbdi.springboot.app.item.models.service;

import java.util.List;

import com.formacionbdi.springboot.app.commons.models.entity.Producto;
import com.formacionbdi.springboot.app.item.models.Item;

public interface ItemService {

	public List<Item> findAll();
	public Item findById(Long id, Integer cantidad);
	
	public Producto save (Producto producto); // guarda producto y lo devuelve
	
	public Producto update(Producto producto, Long id); // actualiza el producto a través de su id
	
	public void delete(Long id); // elimina a través del id
}
