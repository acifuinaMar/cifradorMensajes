
import com.cifrador.dao.UsuarioDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Jalo la info del form
        String email = request.getParameter("usuario"); 
        String password = request.getParameter("password");

        UsuarioDAO dao = new UsuarioDAO();
        int idUsuario = dao.validar(email, password);

        if (idUsuario != -1) {

            HttpSession session = request.getSession();
            session.setAttribute("idUsuario", idUsuario);

            response.sendRedirect("menu.html");

        } else {
            response.getWriter().println("<h1>Error: usuario o contraseña incorrectos</h1>");
            response.setHeader("Refresh", "2; URL=login.html");
        }
    }
}