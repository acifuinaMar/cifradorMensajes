package com.cifrador.controller;

import com.cifrador.dao.HistorialDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.ResultSet;

@WebServlet("/HistorialServlet")
public class HistorialServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // Validar sesión
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("idUsuario") == null) {
            response.sendRedirect("login.html");
            return;
        }

        int idUsuario = (int) session.getAttribute("idUsuario");
        HistorialDAO dao = new HistorialDAO();
        ResultSet rs = dao.obtenerHistorialCompleto(idUsuario);
        
        // Verificar si hay mensajes de nive/error
        String mensajeExito = request.getParameter("mensaje");
        String mensajeError = request.getParameter("error");

        out.println("<!DOCTYPE html>");
        out.println("<html lang='es'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Mi Historial - Cifrador</title>");
        out.println("<link rel='stylesheet' href='styles.css'>");
        out.println("</head>");
        out.println("<body>");
        
        out.println("<div class='container'>");
        out.println("<div class='historial-box'>");
        
        // Mostrar mensajes de nice/error
        if (mensajeExito != null) {
            out.println("<div class='alert-success'>" + mensajeExito + "</div>");
        }
        if (mensajeError != null) {
            out.println("<div class='alert-error'>" + mensajeError + "</div>");
        }
        
        out.println("<h1>Mi Historial de Mensajes</h1>");
        
        out.println("<div class='tabla-responsive'>");
        out.println("<table class='tabla-historial'>");
        out.println("<thead>");
        out.println("<tr>");
        out.println("<th>ID</th>");
        out.println("<th>Token</th>");
        out.println("<th>Estado Token</th>");
        out.println("<th>Preview Mensaje</th>");
        out.println("<th>Estado Lectura</th>");
        out.println("<th>Fecha Lectura</th>");
        out.println("<th>Acciones</th>");
        out.println("</tr>");
        out.println("</thead>");
        out.println("<tbody>");

        try {
            boolean hayRegistros = false;
            while (rs != null && rs.next()) {
                hayRegistros = true;
                int idMensaje = rs.getInt("id_mensaje");
                String textoCifrado = rs.getString("texto_cifrado");
                int idEstado = rs.getInt("id_estado");
                java.sql.Timestamp fechaLectura = rs.getTimestamp("fecha_lectura");
                String valorToken = rs.getString("valor_token");
                boolean usado = rs.getBoolean("usado");
                boolean eliminado = rs.getBoolean("eliminado");
                java.sql.Timestamp fechaExpiracion = rs.getTimestamp("fecha_expiracion");
                
                // estado del token
                String estadoToken = "";
                String estadoTokenClase = "";
                if (eliminado) {
                    estadoToken = "Eliminado";
                    estadoTokenClase = "token-eliminado";
                } else if (usado) {
                    estadoToken = "Usado";
                    estadoTokenClase = "token-usado";
                } else if (fechaExpiracion != null && fechaExpiracion.before(new java.util.Date())) {
                    estadoToken = "Expirado";
                    estadoTokenClase = "token-expirado";
                } else {
                    estadoToken = "Activo";
                    estadoTokenClase = "token-activo";
                }
                
                // estado de lectura del mensaje
                String estadoLectura = "";
                String estadoLecturaClase = "";
                switch(idEstado) {
                    case 1:
                        estadoLectura = "No leído";
                        estadoLecturaClase = "estado-activo";
                        break;
                    case 2:
                        estadoLectura = "Leído";
                        estadoLecturaClase = "estado-leido";
                        break;
                    case 3:
                        estadoLectura = "Expirado";
                        estadoLecturaClase = "estado-expirado";
                        break;
                    default:
                        estadoLectura = "Desconocido";
                        estadoLecturaClase = "estado-desconocido";
                }
                
                String fechaLecturaStr = (fechaLectura != null) ? fechaLectura.toString() : "—";
                String previewToken = valorToken != null ? valorToken : "No disponible";
                String previewMensaje = textoCifrado.length() > 50 ? 
                    textoCifrado.substring(0, 50) + "..." : textoCifrado;
                
                out.println("<tr>");
                out.println("<td data-label='ID'>" + idMensaje + "</td>");
                out.println("<td data-label='Token' class='token-cell' title='" + (valorToken != null ? valorToken : "") + "'>" + previewToken + "</td>");
                out.println("<td data-label='Estado Token'><span class='" + estadoTokenClase + "'>" + estadoToken + "</span></td>");
                out.println("<td data-label='Preview Mensaje' class='mensaje-cell' title='" + textoCifrado + "'>" + previewMensaje + "</td>");
                out.println("<td data-label='Estado Lectura'><span class='" + estadoLecturaClase + "'>" + estadoLectura + "</span></td>");
                out.println("<td data-label='Fecha Lectura'>" + fechaLecturaStr + "</td>");
                out.println("<td data-label='Acciones' class='acciones-cell'>");
                
                // Botón eliminar si y solo si el token está activo
                if (!eliminado && !usado && (fechaExpiracion == null || !fechaExpiracion.before(new java.util.Date()))) {
                    out.println("<form action='TokenEliminarServlet' method='POST' class='form-eliminar' onsubmit='return confirmarEliminacion(" + idMensaje + ")'>");
                    out.println("<input type='hidden' name='idMensaje' value='" + idMensaje + "'>");
                    out.println("<button type='submit' class='btn-eliminar' title='Eliminar token (no podrá usarse para descifrar)'>");
                    out.println("🗑️ Eliminar");
                    out.println("</button>");
                    out.println("</form>");
                } else {
                    out.println("<span class='sin-accion'>—</span>");
                }
                
                out.println("</td>");
                out.println("</tr>");
            }
            
            if (!hayRegistros) {
                out.println("<tr>");
                out.println("<td colspan='7' class='sin-registros'>");
                out.println("<div class='mensaje-vacio'>");
                out.println("No se ha cifrado ningún mensaje");
                out.println("<br><br>");
                out.println("<a href='cifrar.html' class='btn-principal'>Cifrar el primer mensaje</a>");
                out.println("</div>");
                out.println("</td>");
                out.println("</tr>");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<tr>");
            out.println("<td colspan='7' class='error-tabla'>");
            out.println("Error al cargar el historial: " + e.getMessage());
            out.println("</td>");
            out.println("</tr>");
        }

        out.println("</tbody>");
        out.println("</table>");
        out.println("</div>");
        
        out.println("<div class='botones-historial'>");
        out.println("<a href='menu.html' class='btn-secundario'>Volver al Menú</a>");
        out.println("<a href='cifrar.html' class='btn-principal'>Cifrar Nuevo Mensaje</a>");
        out.println("</div>");
        
        out.println("</div>");
        out.println("</div>");
        
        // confirmar eliminación
        out.println("<script>");
        out.println("function confirmarEliminacion(idMensaje) {");
        out.println("    return confirm('¿Estás seguro de que quieres eliminar este token?\\n\\nUna vez eliminado, no podrá usarse para descifrar el mensaje.\\n\\n¿Deseas continuar?');");
        out.println("}");
        out.println("</script>");
        
        out.println("</body>");
        out.println("</html>");
    }
}