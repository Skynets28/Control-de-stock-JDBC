package com.seb.jdbc.controller;

import com.seb.jdbc.dao.CategoriaDAO;
import com.seb.jdbc.factory.ConnectionFactory;
import com.seb.jdbc.modelo.Categoria;

import java.util.List;

public class CategoriaController {

    private CategoriaDAO categoriaDao;

    public CategoriaController(){
        var factory = new ConnectionFactory();
        this.categoriaDao = new CategoriaDAO(factory.recuperaConexion());
    }

	public List<Categoria> listar() {
		// TODO
		return categoriaDao.listar();
	}

    public List<Categoria> cargaReporte() {
        // TODO
        return this.categoriaDao.listarConProductos();
    }

}
