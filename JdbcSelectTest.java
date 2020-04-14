import java.sql.*;

public class JdbcSelectTest{
	public static void main(String[] args)
	{
		try(
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentdb?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC","myuser","myuser");
			//connection
			Statement stmt = conn.createStatement(); //create statement
			){

			String strSelect = "select id, name from hotel";
			System.out.println("The SQL statement is: "+ strSelect + "\n");

			ResultSet rset = stmt.executeQuery(strSelect);

			System.out.println("The records selected are: ");
			int rowcount = 0;
			while(rset.next()){
				int id = rset.getInt("id");
				String name = rset.getString("name");
				System.out.println(id+"		"+name);
				++rowcount;
			}
			System.out.println("Total no. of records: "+rowcount);
		}catch(SQLException ex){
			ex.printStackTrace();
		} 

	}
}