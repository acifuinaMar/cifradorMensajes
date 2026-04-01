package com.cifrador.controller;

import com.cifrador.dao.UsuarioDAO;
import com.cifrador.model.Usuario;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "UsuarioServlet", urlPatterns = {"/UsuarioServlet"})
public class UsuarioServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // Jalo datos
        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Crear obj
        Usuario u = new Usuario();
        u.setNombre(nombre);
        u.setEmail(email);
        u.setPassword(password);

        // Guardar en bd
        UsuarioDAO dao = new UsuarioDAO();
        boolean resultado = dao.insertar(u);

        // avisar
        if (resultado) {
            out.println("<h1>Usuario registrado</h1>");
        } else {
            out.println("<h1>Error al registrar</h1>");
        }
    }
}