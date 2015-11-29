package DHRS;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import message.GeneralMessage;
import message.GeneralMessage.PropertyName;

public class functionImpl extends functionPOA {
	HashMap<Integer, Room> reservationinfo=new HashMap<Integer, Room>();
	ArrayList<Room> Roominfo = new ArrayList<Room>();
	HashMap<Integer, Guest> reservationcheck=new HashMap<Integer, Guest>();
	DatagramSocket localSocket;
	int ReservavtionID=0;
	String hroominfo="";
	String serviceReport="";
	String printstaue="";
	String transferinfo="";
	String hotel="";
	InetSocketAddress sequencerAddress;
	public void start_FE(int port,InetSocketAddress sequencerAddress){
		 this.sequencerAddress=sequencerAddress;
		 try {
			 localSocket = new DatagramSocket(port);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	 // handle the request specified by msg
    // open a local socket,
    //  send the request to sequencer, and receive results. (as list of message)
    // and finally close the local socket
    private List<GeneralMessage> handleRequest (GeneralMessage msg){
    	String sentence=msg.encode();
    	byte[] sendData = new byte[1024];
    	byte[] receiveData =new byte[1024];
        sendData = sentence.getBytes();
        DatagramPacket sendPacket;
        try {
        	sendPacket=new DatagramPacket(sendData,sendData.length,sequencerAddress);
			localSocket.send(sendPacket);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        List<GeneralMessage> messages= new ArrayList<GeneralMessage>();
        try {
        	
        	for(int i=0;i<3;i++){
			localSocket.receive(receivePacket);
			String receive=new String(receivePacket.getData());
			messages.add(GeneralMessage.decode(receive));
			
        	}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
        
    	return messages;
    }

	
	public functionImpl(int a,int b,int c,String hotel){
		this.hotel=hotel;
		for(int i=0;i<a;i++){
			Roominfo.add(new Room(a,"Single",false,0,0,null));
		}
		for(int i=0;i<b;i++){
			Roominfo.add(new Room(b,"Double",false,0,0,null));
		}
		for(int i=0;i<c;i++){
			Roominfo.add(new Room(c,"Family",false,0,0,null));
		}
	}
	private void RoomnumAdown(String RoomType){
		for(int i=0;i<Roominfo.size();i++){
			if(Roominfo.get(i).Type_of_room.equals(RoomType)){
				Roominfo.get(i).No_of_room--;
			}
		}
	}
	private String checkroom(String Roomtype){
		int rent=0;
		int Available=0;
		for(int i=0;i<Roominfo.size();i++){
			if(Roominfo.get(i).Type_of_room.equals(Roomtype)){
				Available=Roominfo.get(i).No_of_room;
				if(Roominfo.get(i).Rent_of_room){
					rent++;
				}
			}
		}
		//Available=Available-rent;
		return "Available "+Available+" Rent: "+rent;

	}
	private void RoomnumAup(String RoomType){
		for(int i=0;i<Roominfo.size();i++){
			if(Roominfo.get(i).Type_of_room==RoomType){
				Roominfo.get(i).No_of_room++;
			}
		}
	}

	@Override
	public void reserveRoom(int GuestID, String hotel, String RoomType,
			int checkindate, int checkoutdate) {
		//sent properties...
		GeneralMessage g= new GeneralMessage();
		g.setValue(PropertyName.GUESTID,String.valueOf(GuestID));
		g.setValue(PropertyName.HOTEL, hotel);
		g.setValue(PropertyName.ROOMTYPE, RoomType);
		g.setValue(PropertyName.CHECKINDATE,String.valueOf(checkindate));
		g.setValue(PropertyName.CHECKOUTDATE,String.valueOf(checkoutdate));
		List<GeneralMessage> responds = new ArrayList<GeneralMessage>();  
		responds = handleRequest(g);
		//return properties...
		Long ID=Long.parseLong(responds.get(0).getValue(PropertyName.RETCODE));
		
		for(int i=0;i<Roominfo.size();i++){
			if(Roominfo.get(i).Type_of_room.equals(RoomType)){
				if(!Roominfo.get(i).Rent_of_room){
					Roominfo.get(i).Guests=new Guest(GuestID,Roominfo.get(i));
					Roominfo.get(i).Checkindate=checkindate;
					Roominfo.get(i).Checkoutdate=checkoutdate;
					Roominfo.get(i).Rent_of_room=true;
					RoomnumAdown(RoomType);
					reservationinfo.put(GuestID, Roominfo.get(i));
					ReservavtionID++;
					reservationcheck.put(ReservavtionID, Roominfo.get(i).Guests);
					System.out.println("ResertionRoom Successful!GuesrID: "+GuestID+ "With ReservavtionID: "+ReservavtionID);
					break;
				}
			}
		}

	}

	@Override
	public void cancelRoom(int GuestID, String hotel, String RoomType,
			int checkindate, int checkoutdate) {
		if(reservationinfo.containsKey(GuestID)){
			for(int i=0;i<Roominfo.size();i++){
				if(Roominfo.get(i).Type_of_room.equals(RoomType)&&Roominfo.get(i).Checkindate==checkindate&&
						Roominfo.get(i).Checkoutdate==checkoutdate){

					Roominfo.get(i).Rent_of_room=false;
					Roominfo.get(i).Guests=null;
					Roominfo.get(i).Checkindate=0;
					Roominfo.get(i).Checkoutdate=0;
					RoomnumAup(RoomType);
					reservationinfo.remove(GuestID);
		
				}
			}
		}

	}

	@Override
	public String checkAvailability(int GuestID, String Preferredhotel,
			String RoomType, int checkindate, int checkoutdate) {
		hroominfo+="Preferred Hotel: "+checkroom(RoomType)+"\n";
		return hroominfo;
	}

	@Override
	public String serviceReport(String hotel, int ServiceDate) {
		serviceReport="";
		for(int i=0;i<Roominfo.size();i++){
			if(Roominfo.get(i).Checkoutdate==ServiceDate){
				serviceReport +=i+1+"RoomType: "+Roominfo.get(i).Type_of_room+","+"Checkindate: "+Roominfo.get(i).Checkindate+"Checkoutdate: "+Roominfo.get(i).Checkoutdate+"\n";
				System.out.println(serviceReport);
			}
		}

		return serviceReport;
	}

	@Override
	public String printSatus(String hotel, int Date) {
		printstaue="";
		for(int i=0;i<Roominfo.size();i++){
			printstaue+=i+1+". "+"RoomType: "+Roominfo.get(i).Type_of_room+"Rent: "+Roominfo.get(i).Rent_of_room;
			if(Roominfo.get(i).Guests!=null){
			printstaue+="Checkindate: "+Roominfo.get(i).Checkindate+"Checkoutdate: "+Roominfo.get(i).Checkoutdate+"Rent GuestID: "+Roominfo.get(i).Guests.toString()+"\n";
			}else{
			printstaue+="Not Rent\n";
			}
		}
		return printstaue;
		
	}

	@Override
	public String transferReservation(int GuestID, int ReservationID,
			String CurrentHotel, String OtherHotel) {
		if(reservationcheck.containsKey(ReservationID)){
			for(int i=0;i<Roominfo.size();i++){
				if(Roominfo.get(i).Guests.Guestid==GuestID){
					transferinfo+=Roominfo.get(i).Guests.Guestid+",";
					transferinfo+=OtherHotel+",";
					transferinfo+=Roominfo.get(i).Type_of_room+",";
					transferinfo+=Roominfo.get(i).Checkindate+",";
					transferinfo+=Roominfo.get(i).Checkoutdate;
					Roominfo.get(i).Rent_of_room=false;
					Roominfo.get(i).Guests=null;
					Roominfo.get(i).Checkindate=0;
					Roominfo.get(i).Checkoutdate=0;
					RoomnumAup(Roominfo.get(i).Type_of_room);
					reservationinfo.remove(GuestID);
//					cancelRoom(GuestID,CurrentHotel,Roominfo.get(i).Type_of_room,Roominfo.get(i).Checkindate,
//							Roominfo.get(i).Checkoutdate);
					break;
				}
			}
		}
         return transferinfo;
	}

}
