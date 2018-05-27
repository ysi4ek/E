package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class Runner {

	public static void main(String[] args) {
	
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver registered");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver not found");
		}
		
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "1234");
			
			String adduserQuery = "INSERT INTO user (user_Id, userName, address, age) VALUES (3,'Alla', 'Malinavka', 24)";
			Statement statement1 = connection.createStatement();
			statement1.executeUpdate(adduserQuery);
			System.out.println("Book added");
			
			System.out.println(connection);
			String selectQuery = "SELECT * FROM user.user";
			
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(selectQuery);
			
			while (rs.next()) {
				//System.out.println("book_id: " + rs.getInt(1));
				System.out.println("userId: " + rs.getInt(1));
				System.out.println("userName: " + rs.getString(2));
				//System.out.println("author_id: " + rs.getInt(4));
				//System.out.println("genre: " + rs.getString(5));
			}
			
			String updateAmberQuery = "UPDATE books SET name = 'Test name' 	WHERE id =2";
			Statement statement2 = connection.createStatement();
			statement2.executeUpdate(updateAmberQuery);
			
			String sql = "UPDATE books SET name = 'UPDATED' WHERE id = ?";
			PreparedStatement prepstatement = connection.prepareStatement(sql);
			prepstatement.setInt(1, new Random().nextInt(3)+1);
			prepstatement.executeUpdate();
			
			String deleteBookQuery = "DELETE FROM books WHERE id=2";
			Statement stmnt = connection.createStatement();
			stmnt.executeQuery(deleteBookQuery);
			
		} catch (SQLException e){
			System.out.println();
		}

	}

}