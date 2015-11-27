package DHRS;

public class Room {
	int No_of_room;
	String Type_of_room;
	boolean Rent_of_room;
	int Checkindate;
	int Checkoutdate;
	Guest Guests;
	public Room(int no_of_room,String type_of_room,boolean rent_of_room,
			int checkindate,int checkoutdate,Guest guests){
		       this.No_of_room=no_of_room;
		       this.Type_of_room=type_of_room;
		       this.Rent_of_room=rent_of_room;
		       this.Checkindate=checkindate;
		       this.Checkoutdate=checkoutdate;
		       this.Guests=guests;
	}
}
