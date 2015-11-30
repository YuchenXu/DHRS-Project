package Client;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


import org.omg.CORBA.ORB;

import DHRS.function;
import DHRS.functionHelper;

public class Client {
	static Scanner keyboard = new Scanner(System.in);
	public static void showMenu()
	{
		System.out.println("\n****Welcome to DHRS****\n");
		System.out.println("Please select the version of Client:");
		System.out.println("1. Guest");
		System.out.println("2. Manager");
	}
	public static void showSubMenu(int enter){
		if(enter==1){
			System.out.println("\n****Welcome to Guest****\n");
			System.out.println("1. ReserveRoom");
			System.out.println("2. CancelRoom");
			System.out.println("3. CheckAvailability");
			System.out.println("4. TransferReservation");
		}
		if (enter==2){
			System.out.println("\n****Welcome to Manager****\n");
			System.out.println("1. ServiceReport");
			System.out.println("2. PrintSatus");
		}
		if(enter!=1&&enter!=2){
			System.out.println("Invalid entry!!! Please try again!!!");
			showMenu();
		}
	}
	
	public static void main(String[] args) throws IOException {
		ORB orb=ORB.init(args,null);
        BufferedReader br=new BufferedReader(new FileReader("ior.txt"));
        String ior=br.readLine();
        br.close();
        org.omg.CORBA.Object o=orb.string_to_object(ior);
        function FE=functionHelper.narrow(o);
        int userChoice=0;
        while(true){
			Boolean valid = false;
			// Enforces a valid integer input.
			showMenu();
			System.out.println("Please enter your choice: ");
			int choice=keyboard.nextInt();
			showSubMenu(choice);
			while(!valid){
				try{
					userChoice=keyboard.nextInt();
					valid=true;
				}
				catch(Exception e)
				{
					System.out.println("Invalid Input, please enter an Integer");
					valid=false;
					keyboard.next();

				}
			}
			if(choice==1){
				switch(userChoice){
				case 1:
					System.out.println("Please enter your Guest ID:");
					int guestID=keyboard.nextInt();
					System.out.println("Please enter your hotel to reserve(H1,H2,H3):");
					String hotel=keyboard.next();
					keyboard.nextLine();
					System.out.println("Please enter the room type you want:(Single,Double,Family)");
					String roomtype=keyboard.nextLine();
					System.out.println("Please enter your Checkindate:");
					int checkindate=keyboard.nextInt();
					System.out.println("Please enter your Checkoutdate:");
					int checkoutdate=keyboard.nextInt();
					System.out.println("Your entry is GuestID:"+guestID+" Hotel: "+hotel+" Roomtype: "+roomtype+
							" Checkindate: "+checkindate+" Checkoutdate: "+checkoutdate);
					FE.reserveRoom(guestID, hotel, roomtype, checkindate, checkoutdate);
					break;
					
//				case 11:
//
//						FE.reserveRoom(123, "H1", "Single", 20151117, 20151120);
//						//System.out.println(guestID+"reserved! with "+roomtype+" "+checkindate+" to "+checkoutdate+" in "+hotel);
//					
//				
//					break;
					
				case 2: 
					System.out.println("Please enter your Guest ID:");
					int guestID2=keyboard.nextInt();
					System.out.println("Please enter the hotel you reserved(H1,H2,H3):");
					String hotel2=keyboard.next();
					keyboard.nextLine();
					System.out.println("Please enter the room type you have:");
					String roomtype2=keyboard.nextLine();
					System.out.println("Please enter your Checkindate:");
					int checkindate2=keyboard.nextInt();
					System.out.println("Please enter your Checkoutdate:");
					int checkoutdate2=keyboard.nextInt();
					System.out.println("Your entry is GuestID:"+guestID2+" Hotel: "+hotel2+" Roomtype: "+roomtype2+
							" Checkindate: "+checkindate2+" Checkoutdate: "+checkoutdate2);
					FE.cancelRoom(guestID2, hotel2, roomtype2, checkindate2, checkoutdate2);
					break;
				case 3:
					System.out.println("Please enter your Guest ID:");
					int guestID3=keyboard.nextInt();
					System.out.println("Please enter the hotel you Preferred(H1,H2,H3):");
					String hotel3=keyboard.next();
					keyboard.nextLine();
					System.out.println("Please enter the room type you preferred:");
					String roomtype3=keyboard.nextLine();
					System.out.println("Please enter your Checkindate:");
					int checkindate3=keyboard.nextInt();
					System.out.println("Please enter your Checkoutdate:");
					int checkoutdate3=keyboard.nextInt();
					System.out.println("Your entry is GuestID:"+guestID3+" Hotel: "+hotel3+" Roomtype: "+roomtype3+
							" Checkindate: "+checkindate3+" Checkoutdate: "+checkoutdate3);
					FE.checkAvailability(guestID3, hotel3, roomtype3, checkindate3, checkoutdate3);
					break;
				case 4:
					String[] info=new String[5];
					String[] info2=new String[5];
					String[] info3=new String[5];
					String transinfo="";
					System.out.println("Please enter your Guest ID:");
					int guestID4=keyboard.nextInt();
					System.out.println("Please enter your ReservationID: ");
					int ReservationID=keyboard.nextInt();
					System.out.println("Please enter current hotel you reserved in:(H1,H2,H3)");
					String hotel4=keyboard.next();
					keyboard.nextLine();
					System.out.println("Please enter OtherHotel you want to reserve:(H1,H2,H3)");
					String hotel5=keyboard.next();
					keyboard.nextLine();
					System.out.println("Your entry is GuestID:"+guestID4+"ReservationID: "+ReservationID+"Current Hotel: "+hotel4+"OtherHotel: "
							+hotel5);
				    FE.transferReservation(guestID4, ReservationID, hotel4, hotel5);
					break;
				default:
					System.out.println("Invalid Input, please try again.");
				}
			}
			if(choice==2){
				switch(userChoice){
				case 1:
					System.out.println("Please enter the hotel you want to manage:(H1,H2,H3)");
					String hotel4=keyboard.next();
					System.out.println("Please enter ServiceDate you want to check:");
					int date=keyboard.nextInt();
					System.out.println("Your entry is Hotel: "+hotel4+" ServiceDate: "+date);
					FE.serviceReport(hotel4, date);
					break;
			   case 2:
				   System.out.println("Please enter the hotel you want to manage:(H1,H2,H3)");
				   String hotel5=keyboard.next();
				   System.out.println("Please enter the Date you want to check:");
				   int date2=keyboard.nextInt();
				   System.out.println("Your entry is Hotel: "+hotel5+" ServiceDate: "+date2);
					FE.printSatus(hotel5, date2);
					break;
			   default:
					System.out.println("Invalid Input, please try again.");
				}
			}
		}
        
	}

}
