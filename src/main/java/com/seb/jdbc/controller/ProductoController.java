package com.seb.jdbc.controller;

import com.seb.jdbc.factory.ConnectionFactory;
import com.seb.jdbc.modelo.Categoria;
import com.seb.jdbc.modelo.Producto;
import com.seb.jdbc.dao.ProductoDAO;

import java.util.List;

public class ProductoController {
	private ProductoDAO productoDAO;

	public ProductoController(){
		this.productoDAO = new ProductoDAO(new ConnectionFactory().recuperaConexion());
	}


	public int modificar(String nombre, String descripcion, Integer id, Integer cantidad) {
		// TODO
		return productoDAO.modificar(nombre, descripcion, id, cantidad);
	}

	public int eliminar(Integer id) {
		// TODO
		return productoDAO.eliminar(id);
	}

	public List<Producto> listar() {
		// TODO
		return productoDAO.listar();
	}

	public List<Producto> listar(Categoria categoria){
		return productoDAO.listar(categoria.getId());
	}

    public void guardar(Producto producto, Integer categoriaId) {
		producto.setCategoriaId(categoriaId);
		productoDAO.guardar(producto);
	}

}
