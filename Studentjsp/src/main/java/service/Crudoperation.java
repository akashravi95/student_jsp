package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Students;

public class Crudoperation {
    public static Connection getCon() 
    {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbbase", "root", "root");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }

    public static int save(Students stud) {
        int status = 0;
        try {
            Connection conn = getCon();
            String query = "INSERT INTO student (name, age, username, password, mail_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, stud.getName());
            stmt.setString(2, stud.getAge());
            stmt.setString(3, stud.getUsername());
            stmt.setString(4, stud.getPassword());
            stmt.setString(5, stud.getMail_id());
            status = stmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    public static List<Students> getAllStudents() {
        List<Students> studentsList = new ArrayList<>();
        try {
            Connection conn = getCon();
            String query = "SELECT * FROM student";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                String age = rs.getString("age");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String email = rs.getString("mail_id");
                int id = rs.getInt("id");
                Students student = new Students();
                student.setId(id);
                student.setName(name);
                student.setAge(age);
                student.setUsername(username);
                student.setPassword(password);
                student.setMail_id(email);
                studentsList.add(student);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentsList;
    }
    
    public static int login(String username, String password) {
        int userId = -1;
        
        // Perform database authentication
        try {
            Connection conn = getCon();
            // Prepare and execute the SQL statement
            String sql = "SELECT id FROM student WHERE username = ? AND password = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            
            // Check if the user exists and retrieve the user ID
            if (resultSet.next()) {
                userId = resultSet.getInt("id");
            }
            
            // Close the database resources
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return userId;
    }
    
    public static Students getUserInfo(String userId) {
        Students user = null;
        try {
            Connection conn = getCon();
            String query = "SELECT * FROM student WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                String age = rs.getString("age");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String email = rs.getString("mail_id");
                int id = rs.getInt("id");
              
                
                user = new Students();
                user.setName(name);
                user.setAge(age);
                user.setUsername(username);
                user.setPassword(password);
                user.setMail_id(email);
                user.setId(id);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
    
    public static int editUser(Students stud) {
        int status = 0;
        try {
            Connection conn = getCon();
            String query = "UPDATE student SET name=?, age=?, username=?, mail_id=? WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, stud.getName());
            stmt.setString(2, stud.getAge());
            stmt.setString(3, stud.getUsername());
            stmt.setString(4, stud.getMail_id());
            stmt.setInt(5, stud.getId());
            status = stmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }
    
    public static int deleteUser(int userId) {
        int status = 0;
        try {
            Connection conn = getCon();
            String query = "DELETE FROM student WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);
            status = stmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }
}
