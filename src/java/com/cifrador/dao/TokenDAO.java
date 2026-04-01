package com.cifrador.dao;

import com.cifrador.util.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TokenDAO {

    public void insertar(String token, int idMensaje) {

        String sql = "INSERT INTO token (valor_token, id_mensaje, fecha_expiracion) VALUES (?, ?, NOW() + INTERVAL '7 days')";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, token);
            ps.setInt(2, idMensaje);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public ResultSet buscarToken(String valor) {
        String sql = "SELECT * FROM token WHERE valor_token = ? AND (eliminado IS FALSE OR eliminado IS NULL)";
        try {
            Connection conn = Conexion.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, valor);
            return ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public void marcarUsadoYFecha(String token) {
        String sql = """
            UPDATE token 
            SET usado = true, fecha_uso = NOW()
            WHERE valor_token = ?
        """;
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, token);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean verificarPropiedadMensaje(int idMensaje, int idUsuario) {
        String sql = "SELECT m.id_mensaje FROM mensaje m " +
                     "INNER JOIN token t ON m.id_mensaje = t.id_mensaje " +
                     "WHERE m.id_mensaje = ? AND m.id_usuario = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idMensaje);
            ps.setInt(2, idUsuario);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean eliminarLogicoToken(int idMensaje) {
        String sql = "UPDATE token SET eliminado = TRUE, fecha_eliminacion = NOW() WHERE id_mensaje = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idMensaje);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    //Token eliminados (para auditoria)
    public ResultSet obtenerTokensEliminados(int idUsuario) {
        String sql = "SELECT t.*, m.texto_cifrado, t.fecha_eliminacion " +
                     "FROM token t " +
                     "INNER JOIN mensaje m ON t.id_mensaje = m.id_mensaje " +
                     "WHERE m.id_usuario = ? AND t.eliminado = TRUE " +
                     "ORDER BY t.fecha_eliminacion DESC";
        try {
            Connection conn = Conexion.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idUsuario);
            return ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}