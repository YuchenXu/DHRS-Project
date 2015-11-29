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
		DatagramPacket sendPacket=new DatagramPacket(sendData,sendData.length,sequencerAddress);
		try {
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


	@Override
	public String reserveRoom(int GuestID, String hotel, String RoomType,
			int checkindate, int checkoutdate) {
		//sent properties...
		GeneralMessage g= new GeneralMessage();
		g.setValue("GuestID",String.valueOf(GuestID));
		g.setValue("hotel", hotel);
		g.setValue("RoomType", RoomType);
		g.setValue("checkindate",String.valueOf(checkindate));
		g.setValue("checkoutdate",String.valueOf(checkoutdate));
		List<GeneralMessage> responds = new ArrayList<GeneralMessage>();  
		responds = handleRequest(g);
		//return properties...
		Long ID=Long.parseLong(responds.get(0).getValue("ID"));
		if(ID>0){
			return "ok with ID"+ID;
		}
		return "fail with ID"+ID;
	}

	@Override
	public String cancelRoom(int GuestID, String hotel, String RoomType,
			int checkindate, int checkoutdate) {
		GeneralMessage g= new GeneralMessage();
		g.setValue("GuestID",String.valueOf(GuestID));
		g.setValue("hotel", hotel);
		g.setValue("RoomType", RoomType);
		g.setValue("checkindate",String.valueOf(checkindate));
		g.setValue("checkoutdate",String.valueOf(checkoutdate));
		List<GeneralMessage> responds = new ArrayList<GeneralMessage>();  
		responds = handleRequest(g);
		//return properties...
		Long ID=Long.parseLong(responds.get(0).getValue("ID"));
		if(ID>0){
			return "ok with ID"+ID;
		}
		return "fail with ID"+ID;

	}


	@Override
	public String checkAvailability(int GuestID, String Preferredhotel,
			String RoomType, int checkindate, int checkoutdate) {
		GeneralMessage g= new GeneralMessage();
		g.setValue("GuestID",String.valueOf(GuestID));
		g.setValue("Preferredhotel", Preferredhotel);

		hroominfo+="Preferred Hotel: "+"\n";
		return hroominfo;
	}

	@Override
	public String serviceReport(String hotel, int ServiceDate) {
		serviceReport="";
	

		return serviceReport;
	}

	@Override
	public String printSatus(String hotel, int Date) {
		printstaue="";
	
		return printstaue;

	}

	@Override
	public String transferReservation(int GuestID, int ReservationID,
			String CurrentHotel, String OtherHotel) {

		return transferinfo;
	}

}
