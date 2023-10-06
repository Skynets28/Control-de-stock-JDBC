package com.seb.jdbc.dao;

import com.seb.jdbc.modelo.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {
    private Connection con;
    public ProductoDAO(Connection con){
        this.con = con;
    }

    public void guardar(Producto producto) {
        try {

            final PreparedStatement statement = con.prepareStatement("INSERT INTO PRODUCTO (nombre, descripcion, cantidad, categoria_id) "
                            + "VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            try (statement) {

                ejecutaRegistro(statement, producto);
                System.out.println("COMMIT");
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void ejecutaRegistro(PreparedStatement statement, Producto producto) throws SQLException {

        statement.setString(1, producto.getNombre());
        statement.setString(2, producto.getDescripcion());
        statement.setInt(3, producto.getCantidad());
        statement.setInt(4, producto.getCategoriaId());
        statement.execute();

        final ResultSet resultSet = statement.getGeneratedKeys();

        try (resultSet){
            while(resultSet.next()){
                producto.setId(resultSet.getInt(1));
                System.out.println(String.format("Fue insertado el producto %s", producto));
            }
        }

    }

    public List<Producto> listar() {
        List<Producto> resultado = new ArrayList<>();

        try {

            final PreparedStatement statement = con.prepareStatement("SELECT id, nombre, descripcion, cantidad FROM producto");

            try(statement){
                statement.execute();

                final ResultSet resultSet = statement.getResultSet();
                try(resultSet){
                    while (resultSet.next()) {
                        Producto fila = new Producto(resultSet.getInt("id"),resultSet.getString("nombre"), resultSet.getString("descripcion"), resultSet.getInt("cantidad") );
                        resultado.add(fila);
                    }
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return resultado;
    }

    public int eliminar(Integer id){
        try {
            final PreparedStatement statement = con.prepareStatement("DELETE FROM PRODUCTO WHERE ID = ?");
            try(statement){
                statement.setInt(1, id);

                statement.execute();

                return statement.getUpdateCount();
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public int modificar(String nombre, String descripcion, Integer id, Integer cantidad){
        try {
            final PreparedStatement statement = con.prepareStatement("UPDATE producto SET "
                    + "nombre = ?"
                    + ", descripcion = ?"
                    + ", cantidad = ?"
                    + " WHERE id = ?");

            try(statement){
                statement.setString(1, nombre);
                statement.setString(2, descripcion);
                statement.setInt(3, cantidad);
                statement.setInt(4, id);
                statement.execute();
            }

            return statement.getUpdateCount();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public List<Producto> listar(Integer categoriaId) {
        List<Producto> resultado = new ArrayList<>();

        try {
            var querySelect = "SELECT id, nombre, descripcion, cantidad FROM producto WHERE CATEGORIA_ID = ?";
            System.out.println(querySelect);
            final PreparedStatement statement = con.prepareStatement(querySelect);

            try(statement){
                statement.setInt(1, categoriaId);
                statement.execute();

                final ResultSet resultSet = statement.getResultSet();
                try(resultSet){
                    while (resultSet.next()) {
                        Producto fila = new Producto(resultSet.getInt("id"),resultSet.getString("nombre"), resultSet.getString("descripcion"), resultSet.getInt("cantidad") );
                        resultado.add(fila);
                    }
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return resultado;
    }
}
