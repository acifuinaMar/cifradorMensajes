package com.cifrador.controller;

import com.cifrador.dao.TokenDAO;
import com.cifrador.dao.HistorialDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/TokenEliminarServlet")
public class TokenEliminarServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Validar sesión
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("idUsuario") == null) {
            response.sendRedirect("login.html");
            return;
        }
        
        int idUsuario = (int) session.getAttribute("idUsuario");
        String idMensajeStr = request.getParameter("idMensaje");
        
        if (idMensajeStr == null || idMensajeStr.isEmpty()) {
            response.sendRedirect("HistorialServlet?error=" + 
                java.net.URLEncoder.encode("ID de mensaje no válido", "UTF-8"));
            return;
        }
        
        try {
            int idMensaje = Integer.parseInt(idMensajeStr);
            TokenDAO tokenDAO = new TokenDAO();
            boolean perteneceAlUsuario = tokenDAO.verificarPropiedadMensaje(idMensaje, idUsuario);
            
            if (!perteneceAlUsuario) {
                response.sendRedirect("HistorialServlet?error=" + 
                    java.net.URLEncoder.encode("No tienes permiso para eliminar este token", "UTF-8"));
                return;
            }
            
            boolean eliminado = tokenDAO.eliminarLogicoToken(idMensaje);
            
            if (eliminado) {
                // Registrar en historial x auditoria
                HistorialDAO historialDAO = new HistorialDAO();
                historialDAO.insertar(idUsuario, idMensaje, 4); 
                
                response.sendRedirect("HistorialServlet?mensaje=" + 
                    java.net.URLEncoder.encode("Token eliminado correctamente", "UTF-8"));
            } else {
                response.sendRedirect("HistorialServlet?error=" + 
                    java.net.URLEncoder.encode("No se pudo eliminar el token", "UTF-8"));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("HistorialServlet?error=" + 
                java.net.URLEncoder.encode("Error al eliminar: " + e.getMessage(), "UTF-8"));
        }
    }
}