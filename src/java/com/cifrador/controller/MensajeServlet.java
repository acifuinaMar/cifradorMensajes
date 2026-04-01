package com.cifrador.controller;

import com.cifrador.dao.HistorialDAO;
import com.cifrador.dao.MensajesDAO;
import com.cifrador.dao.TokenDAO;
import com.cifrador.model.Mensaje;
import com.cifrador.util.Cifrado;
import com.cifrador.util.GenerarToken;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.util.Base64;

@WebServlet("/MensajeServlet")
public class MensajeServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();

        try {
            HttpSession session = request.getSession(false);
            
            // veo si hay sesión activa
            if (session == null || session.getAttribute("idUsuario") == null) {
                response.sendRedirect("login.html");
                return;
            }
            
            int idUsuario = (int) session.getAttribute("idUsuario");
            
            // Jalo el texto
            String texto = request.getParameter("texto");
            
            if (texto == null || texto.trim().isEmpty()) {
                response.sendRedirect("cifrar.html?error=" + 
                    java.net.URLEncoder.encode("El mensaje no puede estar vacío", "UTF-8"));
                return;
            }

            // cifrar
            Cifrado c = new Cifrado();
            c.generarLlaves(2048);
            String clavePrivada = Base64.getEncoder().encodeToString(c.getPrivateKey().getEncoded());
            String textoCifrado = c.cifrar(texto);

            // guardar
            Mensaje m = new Mensaje();
            m.setTextoCifrado(textoCifrado);
            m.setIdUsuario(idUsuario);
            m.setIdEstado(1);
            m.setClavePrivada(clavePrivada);

            MensajesDAO mensajeDAO = new MensajesDAO();
            int idMensaje = mensajeDAO.insertar(m);
            HistorialDAO historialDAO = new HistorialDAO();
            historialDAO.insertar(idUsuario, idMensaje, 1);

            // crear tokencillo
            String token = GenerarToken.generarToken();

            TokenDAO tokenDAO = new TokenDAO();
            tokenDAO.insertar(token, idMensaje);
            response.sendRedirect("cifrar.html?token=" + token);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("cifrar.html?error=" + 
                java.net.URLEncoder.encode("Error al cifrar: " + e.getMessage(), "UTF-8"));
        }
    }
}