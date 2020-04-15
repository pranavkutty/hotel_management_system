import java.sql.*;
import java.util.Scanner;
import java.io.IOException;
import java.io.Console;

class Hotel{

	public static void main(String[] args) throws IOException, InterruptedException
	{

		int choice1=0,choice2=0;
		Scanner sc = new Scanner(System.in);
		while(choice1 != 3)
		{
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
					while(!user.equals("exit"))
					{
						System.out.println(":::::::::::::::::::Admin Login:::::::::::::::::::::");
						System.out.print("Username: ");
						user = sc.next();

						Console console = System.console();
						String pwd = new String(console.readPassword("Password: "));

						if(user.equals("admin") && pwd.equals("admin"))
						{
							new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();				
							System.out.println("Admin login successful");
						}
						else
						{
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
					break;
				}
				default:
				{
					System.out.println("Kindly select a valid option!");
					break;
				}
			}
		}
	}

}
