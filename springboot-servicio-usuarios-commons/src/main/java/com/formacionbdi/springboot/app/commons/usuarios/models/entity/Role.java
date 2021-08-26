package com.formacionbdi.springboot.app.commons.usuarios.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity // anotar que esta clase es una clase que va a estar mapeada a una tabla
@Table(name="roles") // es opcional, pero nos permite elegir un nombre diferente para la tabla
public class Role implements Serializable{

	@Id //llave primaria de la entidad, tiene que coincidir en nombre
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long id;
	
 	@Column(unique=true,length=30)
	private String nombre;
	
	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	private static final long serialVersionUID = 4467531611801172710L;

}
