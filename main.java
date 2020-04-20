import java.sql.*;
import java.util.Scanner;
import java.util.Formatter;
import java.io.IOException;
import java.io.Console;
import java.time.*;
import java.time.temporal.*;

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
							System.out.println("Type exit in 'username' to return to main-menu");
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
						customer(sc,stmt);
						break;
					}
					case 3:
					{
						System.out.println("Thank You! Please visit again!");
						break;
					}
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

	static void customer(Scanner sc,Statement stmt) throws IOException, InterruptedException
	{
		int id_counter =0,room_counter=0;
		try
		{		
			ResultSet rset = stmt.executeQuery("Select booking_id,room_no from hotel");
			while(rset.next())
			{
				id_counter = rset.getInt("booking_id");
				room_counter = rset.getInt("room_no");
			}
			int customer_choice = 0;

			while(customer_choice != 3)
			{
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
				System.out.println(":::::::::::::Customer Main Menu::::::::::::::");
				System.out.println("1.	Book rooms");
				System.out.println("2.	Complete payment");
				System.out.println("3.	Exit");
				customer_choice = sc.nextInt();
				switch(customer_choice)
				{
					case 1:
					{
						new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
						LocalDate cust_check_in;
						System.out.println("Kindly enter the details below to complete the booking process: ");
						id_counter += 1;
						room_counter += 1;
						System.out.print("Enter your name: ");
						sc.nextLine();
						String cust_name = sc.nextLine();
						System.out.print("Are you checking in today?(y/n) ");
						char ch = sc.next().charAt(0);
						if(ch == 'y'||ch== 'Y')
						{
							cust_check_in = LocalDate.now();
							System.out.println(cust_check_in+" is set as the check in date");
						}
						else
						{
							System.out.print("Enter your check in date:(yyyy-mm-dd) ");
							sc.nextLine();
							String cust_check_in_str = sc.nextLine();
							cust_check_in = LocalDate.parse(cust_check_in_str);
							System.out.println(cust_check_in+" is set as the check in date");
						}

						System.out.print("Enter the no. of days of stay: ");
						int cust_days = sc.nextInt();
						LocalDate cust_check_out = cust_check_in.plusDays(cust_days);
						System.out.println(cust_check_out+" is set as check out date");

						System.out.print("Enter your mobile no: ");
						sc.nextLine();
						String cust_mob = sc.nextLine();

						String query = "insert into hotel(booking_id,name,check_in,check_out,mobile_no,room_no) values("+id_counter+",'"+cust_name+"','"+cust_check_in+"','"+cust_check_out+"','"+cust_mob+"',"+room_counter+")";

						stmt.executeUpdate(query);
						System.out.println("Your booking is successful!\nYour booking ID is "+id_counter+" do remember it!\nKindly complete the payment from 'Customer Main Menu' before check out\nPress Enter to continue...");
						sc.nextLine();
						break;
					}
					case 2:
					{
						new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
						System.out.println(":::::::::Payment Portal::::::::");
						System.out.print("Enter your booking_id: ");
						int cust_id = sc.nextInt();
						System.out.print("Enter your name: ");
						sc.nextLine();
						String cust_name = sc.nextLine();
						rset = stmt.executeQuery("select * from hotel where booking_id="+cust_id);
						rset.next();
						if(rset.getInt("booking_id") == cust_id && rset.getString("name").equalsIgnoreCase(cust_name))
						{
							new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
							System.out.println("Kindly verify your details:");
							System.out.println("+------------+-------------+------------+------------+------------+----------------+---------+");
							System.out.println("| BOOKING ID | NAME        | CHECK IN   | CHECK OUT  | MOBILE NO  | PAYMENT STATUS | ROOM_NO |");
							System.out.println("+------------+-------------+------------+------------+------------+----------------+---------+");
							System.out.format("| %11d",rset.getInt("booking_id"));
							System.out.format("| %12s",rset.getString("name"));
							System.out.format("| %11s",rset.getString("check_in"));
							System.out.format("| %11s",rset.getString("check_out"));
							System.out.format("| %11s",rset.getString("mobile_no"));
							System.out.format("| %15s",rset.getString("payment_status"));
							System.out.format("| %8d|",rset.getInt("room_no"));
							System.out.println();	
							System.out.println("+------------+-------------+------------+------------+------------+----------------+---------+");
							System.out.print("\nDo you want to proceed with payment?(y/n): ");
							char ch = sc.next().charAt(0);
							if(ch == 'y'||ch == 'Y')
							{
								stmt.executeUpdate("update hotel set payment_status='PAID' where booking_id = "+cust_id);
								System.out.println("Payment successful\nPress enter to continue...");
								sc.nextLine();
								sc.nextLine();
							}
							else
							{
								System.out.println("Payment cancelled");
								sc.nextLine();
								sc.nextLine();
							}
						}
						else
						{
							System.out.println("User not found...Try Again!");
							sc.nextLine();
						}

						break;
					}
					default:
					{
						System.out.println("Please enter a valid option!");
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
			while(admin_choice!=3)
			{
				System.out.println(":::::::::::::Admin Main Menu::::::::::::::");
				System.out.println("1.	View all Bookings");
				System.out.println("2.	Get details of a particular guest");
			//	System.out.println("3.	Free rooms");
				System.out.println("3.	Logout");
				System.out.print("Enter your choice (1-3):");
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
						new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
						System.out.println("Logged out successfully");
						break;
					}
					default:
					{
						System.out.println("Please enter a valid option!");
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
