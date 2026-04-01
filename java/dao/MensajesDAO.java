package com.cifrador.dao;

import com.cifrador.model.Mensaje;
import com.cifrador.util.Conexion;

import java.sql.*;

public class MensajesDAO {

    public int insertar(Mensaje mensaje) {

        String sql = "INSERT INTO mensaje (texto_cifrado, id_usuario, id_estado, clave_privada) VALUES (?, ?, ?, ?) RETURNING id_mensaje;";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, mensaje.getTextoCifrado());
            ps.setInt(2, mensaje.getIdUsuario());
            ps.setInt(3, mensaje.getIdEstado());
            ps.setString(4, mensaje.getClavePrivada());

            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("id_mensaje");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }
    
    public ResultSet obtenerPorId(int idMensaje) {
        String sql = "SELECT * FROM mensaje WHERE id_mensaje = ?";

        try {
            Connection conn = Conexion.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idMensaje);

            return ps.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    public void actualizarEstado(int idMensaje, int idEstado) {
        String sql = "UPDATE mensaje SET id_estado = ? WHERE id_mensaje = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idEstado);
            ps.setInt(2, idMensaje);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void actualizarFechaLectura(int idMensaje) {
        String sql = "UPDATE mensaje SET fecha_lectura = NOW() WHERE id_mensaje = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idMensaje);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}