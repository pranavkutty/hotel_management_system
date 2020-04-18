import java.sql.*;
import java.util.Scanner;
import java.util.Formatter;
import java.io.IOException;
import java.io.Console;

class Hotel{

	public static void main(String[] args) throws IOException, InterruptedException
	{
		try(
			//connection object
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentdb?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC","myuser","myuser");
			//statement executing object
			Statement stmt = conn.createStatement();
			){
			int choice1=0;
			Scanner sc = new Scanner(System.in);
			while(choice1 != 3)
			{
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
				int admin_choice=0;
				System.out.println(":::::::::::::::Welcome to Ustad Hotel:::::::::::::::");
				System.out.println(":::::::::::::::::::Login Portal:::::::::::::::::::::");
				System.out.println("1.	Admin Login");
				System.out.println("2.	Customer");
				System.out.println("3.	Exit");
				System.out.print("Please select an option(1-3): ");
				choice1 = sc.nextInt();
				switch(choice1)
				{
					case 1:
					{
						String user="";
						new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
						while(!user.equals("exit") && admin_choice != 4)
						{
							System.out.println(":::::::::::::::::::Admin Login:::::::::::::::::::::");
							System.out.print("Username: ");
							user = sc.next();

							Console console = System.console();
							String pwd = new String(console.readPassword("Password: "));

							if(user.equals("admin") && pwd.equals("admin"))
							{
								new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();				
								System.out.println("Admin login successful!");
								admin_choice = admin(sc,stmt);
							}
							else
							{
								new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
								System.out.println("Wrong credentials! Try again!");
							}
						}	
						break;
					}
					case 2:
					{
						System.out.println("Menu2:");
						break;
					}
					case 3:
					{
						System.out.println("Thank You!");
					}
						break;
					default:
					{
						System.out.println("Kindly select a valid option!");
						break;
					}
				}
			}
		}catch(SQLException ex){
			ex.printStackTrace();
		}
	}


	static int admin(Scanner sc,Statement stmt) throws IOException, InterruptedException
	{
		int admin_choice=0;
		try
		{
			while(admin_choice!=4)
			{
				System.out.println(":::::::::::::Admin Main Menu:::::::::::::: ");
				System.out.println("1.	View all Bookings");
				System.out.println("2.	Get details of a particular guest");
				System.out.println("3.	Free rooms");
				System.out.println("4.	Logout");
				System.out.print("Enter your choice (1-4):");
				admin_choice = sc.nextInt();
				
				switch(admin_choice)
				{
					case 1:
					{
						new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();						
						ResultSet rset = stmt.executeQuery("select * from hotel");
						System.out.println("Bookings:");
						System.out.println("+------------+-------------+------------+------------+------------+----------------+---------+");
						System.out.println("| BOOKING ID | NAME        | CHECK IN   | CHECK OUT  | MOBILE NO  | PAYMENT STATUS | ROOM_NO |");
						System.out.println("+------------+-------------+------------+------------+------------+----------------+---------+");
						int rowcount = 0;
						while(rset.next())
						{
							System.out.format("| %11d",rset.getInt("booking_id"));
							System.out.format("| %12s",rset.getString("name"));
							System.out.format("| %11s",rset.getString("check_in"));
							System.out.format("| %11s",rset.getString("check_out"));
							System.out.format("| %11s",rset.getString("mobile_no"));
							System.out.format("| %15s",rset.getString("payment_status"));
							System.out.format("| %8d|",rset.getInt("room_no"));
							System.out.println();
							++rowcount;
						}
						System.out.println("+------------+-------------+------------+------------+------------+----------------+---------+");
						System.out.println();
						System.out.println("Press Enter to continue....");
						sc.nextLine();
						sc.nextLine();
						new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
						break;
					}
					case 2:
					{
						new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
						System.out.print("Enter Booking ID: ");
						int booking_id = sc.nextInt();
						System.out.println("The details of booking ID "+booking_id+" is:");
						ResultSet rset = stmt.executeQuery("select * from hotel where booking_id = "+booking_id);
						System.out.println("+------------+-------------+------------+------------+------------+----------------+---------+");
						System.out.println("| BOOKING ID | NAME        | CHECK IN   | CHECK OUT  | MOBILE NO  | PAYMENT STATUS | ROOM_NO |");
						System.out.println("+------------+-------------+------------+------------+------------+----------------+---------+");
						int rowcount = 0;
						while(rset.next())
						{
							System.out.format("| %11d",rset.getInt("booking_id"));
							System.out.format("| %12s",rset.getString("name"));
							System.out.format("| %11s",rset.getString("check_in"));
							System.out.format("| %11s",rset.getString("check_out"));
							System.out.format("| %11s",rset.getString("mobile_no"));
							System.out.format("| %15s",rset.getString("payment_status"));
							System.out.format("| %8d|",rset.getInt("room_no"));
							System.out.println();
							++rowcount;
						}
						System.out.println("+------------+-------------+------------+------------+------------+----------------+---------+");
						System.out.println();
						System.out.println("Press Enter to continue....");
						sc.nextLine();
						sc.nextLine();
						new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
						break;
					}
					case 3:
					{
						//free rooms
						break;
					}
					case 4:
					{
						new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
						System.out.println("Logged out successfully");
						break;
					}
				}
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return admin_choice;
	}

}
