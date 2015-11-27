package DHRS;

public class Guest {
int Guestid;
Room Room;
public Guest(int guestID,Room room) {
	this.Guestid=guestID;
	this.Room=room;
}
public String toString(){
	
	return Guestid+"";
}
}
