package Client;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


import org.omg.CORBA.ORB;

import DHRS.function;
import DHRS.functionHelper;

public class DHRS_Client {
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
        BufferedReader br1=new BufferedReader(new FileReader("ior1.txt"));
        BufferedReader br2=new BufferedReader(new FileReader("ior2.txt"));
        BufferedReader br3=new BufferedReader(new FileReader("ior3.txt"));
        String ior1=br1.readLine();
        String ior2=br2.readLine();
        String ior3=br3.readLine();
        br1.close();br2.close();br3.close();
        org.omg.CORBA.Object o1=orb.string_to_object(ior1);
        org.omg.CORBA.Object o2=orb.string_to_object(ior2);
        org.omg.CORBA.Object o3=orb.string_to_object(ior3);
        function H1=functionHelper.narrow(o1);
        function H2=functionHelper.narrow(o2);
        function H3=functionHelper.narrow(o3);
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
					if(hotel.equals("H1")){
						H1.reserveRoom(guestID, hotel, roomtype, checkindate, checkoutdate);
						System.out.println(guestID+"reserved! with "+roomtype+" "+checkindate+" to "+checkoutdate+" in "+hotel);
					}
					if(hotel.equals("H2")){
						H2.reserveRoom(guestID, hotel, roomtype, checkindate, checkoutdate);
						System.out.println(guestID+"reserved! with "+roomtype+" "+checkindate+" to "+checkoutdate+" in "+hotel);	
					}
					if(hotel.equals("H3")){
						H3.reserveRoom(guestID, hotel, roomtype, checkindate, checkoutdate);
						System.out.println(guestID+"reserved! with "+roomtype+" "+checkindate+" to "+checkoutdate+" in "+hotel);			
					}
					break;
					
//				case 11:
//
//						H1.reserveRoom(123, "H1", "Single", 20151117, 20151120);
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
					if(hotel2.equals("H1")){
						H1.cancelRoom(guestID2, hotel2, roomtype2, checkindate2, checkoutdate2);
						System.out.println(guestID2+"Canceled! with "+roomtype2+" "+checkindate2+" to "+checkoutdate2+" in "+hotel2);
					}
					if(hotel2.equals("H2")){
						H2.cancelRoom(guestID2, hotel2, roomtype2, checkindate2, checkoutdate2);
						System.out.println(guestID2+"Canceled! with "+roomtype2+" "+checkindate2+" to "+checkoutdate2+" in "+hotel2);	
					}
					if(hotel2.equals("H3")){
						H3.cancelRoom(guestID2, hotel2, roomtype2, checkindate2, checkoutdate2);
						System.out.println(guestID2+"Canceled! with "+roomtype2+" "+checkindate2+" to "+checkoutdate2+" in "+hotel2);			
					}
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
					if(hotel3.equals("H1")){
						String c=H1.checkAvailability(guestID3, hotel3, roomtype3, checkindate3, checkoutdate3);
						System.out.println(c);
					}
					if(hotel3.equals("H2")){
						String c=H2.checkAvailability(guestID3, hotel3, roomtype3, checkindate3, checkoutdate3);
						System.out.println(c);	
					}
					if(hotel3.equals("H3")){
						String c=H3.checkAvailability(guestID3, hotel3, roomtype3, checkindate3, checkoutdate3);
						System.out.println(c);			
					}
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
					if(hotel4.equals("H1")){
						transinfo="";
						transinfo+=H1.transferReservation(guestID4, ReservationID, hotel4, hotel5);
						info=transinfo.split(",");
						int id=Integer.parseInt(info[0]);
						String otherhotel=info[1];
						String roomtype1=info[2];
						int checkindate1=Integer.parseInt(info[3]);
						int checkoutdate1=Integer.parseInt(info[4]);
						if(otherhotel.equals("H2")){
							H2.reserveRoom(id, otherhotel, roomtype1, checkindate1, checkoutdate1);
							System.out.println(id+"reserved! with "+roomtype1+" "+checkindate1+" to "+checkoutdate1+" in "+otherhotel);	
						}
						if(otherhotel.equals("H3")){
							H3.reserveRoom(id, otherhotel, roomtype1, checkindate1, checkoutdate1);
							System.out.println(id+"reserved! with "+roomtype1+" "+checkindate1+" to "+checkoutdate1+" in "+otherhotel);	
						}	
					}
					if(hotel4.equals("H2")){
						transinfo="";
						transinfo+=H2.transferReservation(guestID4, ReservationID, hotel4, hotel5);
						info2=transinfo.split(",");
						int id2=Integer.parseInt(info[0]);
						String otherhotel2=info[1];
						String roomtype5=info[2];
						int checkindate5=Integer.parseInt(info[3]);
						int checkoutdate5=Integer.parseInt(info[4]);
						if(otherhotel2.equals("H1")){
							H1.reserveRoom(id2, otherhotel2, roomtype5, checkindate5, checkoutdate5);
							System.out.println(id2+"reserved! with "+roomtype5+" "+checkindate5+" to "+checkoutdate5+" in "+otherhotel2);	
						}
						if(otherhotel2.equals("H3")){
							H3.reserveRoom(id2, otherhotel2, roomtype5, checkindate5, checkoutdate5);
							System.out.println(id2+"reserved! with "+roomtype5+" "+checkindate5+" to "+checkoutdate5+" in "+otherhotel2);	
						}	
					}
					if(hotel4.equals("H3")){
						transinfo="";
						transinfo+=H2.transferReservation(guestID4, ReservationID, hotel4, hotel5);
						info3=transinfo.split(",");
						int id3=Integer.parseInt(info[0]);
						String otherhotel3=info[1];
						String roomtype6=info[2];
						int checkindate6=Integer.parseInt(info[3]);
						int checkoutdate6=Integer.parseInt(info[4]);
						if(otherhotel3.equals("H1")){
							H1.reserveRoom(id3, otherhotel3, roomtype6, checkindate6, checkoutdate6);
							System.out.println(id3+"reserved! with "+roomtype6+" "+checkindate6+" to "+checkoutdate6+" in "+otherhotel3);	
						}
						if(otherhotel3.equals("H2")){
							H3.reserveRoom(id3, otherhotel3, roomtype6, checkindate6, checkoutdate6);
							System.out.println(id3+"reserved! with "+roomtype6+" "+checkindate6+" to "+checkoutdate6+" in "+otherhotel3);
						}	
					}
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
					if(hotel4.equals("H1")){
						String report= H1.serviceReport(hotel4, date);
						System.out.println(report);	
					}
					if(hotel4.equals("H2")){
						String report= H2.serviceReport(hotel4, date);
						System.out.println(report);
					}
					if(hotel4.equals("H3")){
						String report= H3.serviceReport(hotel4, date);
						System.out.println(report);
					}
					break;
			   case 2:
				   System.out.println("Please enter the hotel you want to manage:(H1,H2,H3)");
				   String hotel5=keyboard.next();
				   System.out.println("Please enter the Date you want to check:");
				   int date2=keyboard.nextInt();
				   System.out.println("Your entry is Hotel: "+hotel5+" ServiceDate: "+date2);
					if(hotel5.equals("H1")){
						String report= H1.printSatus(hotel5, date2);
						System.out.println(report);	
					}
					if(hotel5.equals("H2")){
						String report= H2.printSatus(hotel5, date2);
						System.out.println(report);
					}
					if(hotel5.equals("H3")){
						String report= H3.printSatus(hotel5, date2);
						System.out.println(report);
					}
					break;
			   default:
					System.out.println("Invalid Input, please try again.");
				}
			}
		}
        
	}

}
