package Locking_design;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/Signupverify")
public class Signupverify extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String Fullname = request.getParameter("fullname");
        String Username = request.getParameter("username");
        String Email = request.getParameter("email");
        String Mobilenumber = request.getParameter("mobilenumber");
        String Password= request.getParameter("password");
        String Confirmpassword = request.getParameter("confirmpassword");
        PrintWriter writer = response.getWriter();
        System.out.println("Mobile Number: " + Mobilenumber);
        System.out.println("Confirm Password: " + Confirmpassword);

        try {
            // JDBC URL, username, and password
            String url = "jdbc:mysql://localhost:3306/locking?user=root&password=pavan@9900";

            // SQL query to insert values into the table
            String query = "INSERT INTO locking.register(fullname, username, email, mobilenumber, password, confirmpassword) VALUES (?, ?, ?, ?, ?, ?)";

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url);

            // Prepare the statement
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, Fullname);
            ps.setString(2, Username);
            ps.setString(3, Email);
            ps.setString(4, Mobilenumber);
            ps.setString(5, Password);
            ps.setString(6, Confirmpassword);

            // Execute the query
            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                // Insert successful
                RequestDispatcher rd = request.getRequestDispatcher("signin.html");
                rd.include(request, response);

            } else {
                // Insert failed
                RequestDispatcher rd = request.getRequestDispatcher("Signup.html");
                rd.include(request, response);
            }

            // Close the resources
            ps.close();
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
