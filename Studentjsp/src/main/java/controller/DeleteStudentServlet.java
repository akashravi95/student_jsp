package controller;
import service.Crudoperation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet("/DeleteStudentServlet")
public class DeleteStudentServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        if (id != null) {
            int studentId = Integer.parseInt(id);
            int status = Crudoperation.deleteUser(studentId);
            if (status > 0) {
                // Deletion successful
                response.sendRedirect("viewstudents.jsp"); // Redirect to the view students page
            } else {
                // Deletion failed
                response.getWriter().println("Failed to delete the student.");
            }
        } else {
            response.getWriter().println("Invalid student ID.");
        }
    }

}