package com.cifrador.dao;

import com.cifrador.model.Usuario;
import com.cifrador.util.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class UsuarioDAO {

    public boolean insertar(Usuario usuario) {
        String sql = "INSERT INTO usuario (nombre, email, password) VALUES (?, ?, ?)";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getPassword());

            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public int validar(String email, String password) {
        String sql = "SELECT id_usuario FROM usuario WHERE email = ? AND password = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("id_usuario"); // login correcto
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1; // login incorrecto
    }
}