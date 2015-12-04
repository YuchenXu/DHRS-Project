package DHRS_FE;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import message.GeneralMessage;
import message.GeneralMessage.MessageType;
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
	public functionImpl() {

	}
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
		
		System.out.println ("Handling request:" + msg.encode());
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
		List<GeneralMessage> messages= new ArrayList<GeneralMessage> ();
		messages.add(null);
		messages.add(null);
		messages.add(null);


		try {

			for(int i=0;i<3;i++){
				localSocket.setSoTimeout(8000);
				localSocket.receive(receivePacket);
				String receive=new String(receivePacket.getData());
				GeneralMessage m=GeneralMessage.decode(receive);

				int serverID = Integer.valueOf(m.getValue(PropertyName.SERVERID));
				if (serverID >= 1 && serverID <= 3)
					messages.set(Integer.valueOf(m.getValue(PropertyName.SERVERID))-1, m);

			}
		} catch (SocketTimeoutException e){


		} catch (IOException e) {

			e.printStackTrace();
		} 
		
		System.out.println("Received responds:");
		for (GeneralMessage r : messages) 
			if (r!=null) {
				System.out.println ("------");
				System.out.println(r.encode());
			}

		return messages;
	}

	public void sendsuspectedreport(MessageType t,int errorServer){
		GeneralMessage g= new GeneralMessage(t);
		g.setValue(PropertyName.SERVERID, String.valueOf(errorServer));
		String encode=g.encode();
		byte[] sendData =encode.getBytes();
		DatagramPacket sendPacket;

		try {
			sendPacket=new DatagramPacket(sendData,sendData.length,sequencerAddress);

			localSocket.send(sendPacket);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	
	public GeneralMessage preprocess(List<GeneralMessage> responds) {
		GeneralMessage m1=responds.get(0);
		GeneralMessage m2=responds.get(1);
		GeneralMessage m3=responds.get(2);

		GeneralMessage mRet = null;

		int errorServer=0;
		if (m1==null) {
			errorServer=1;
			mRet=m2;
		}
		if (m2==null) {
			errorServer=2;
			mRet=m3;

		}
		if (m3==null) {
			errorServer=3;
			mRet=m1;
		}

		if(errorServer>0){
			sendsuspectedreport(MessageType.REPORT_NO_RESPOND,errorServer);
		}

		return mRet;
	}

	public String Process_RETCODE(List<GeneralMessage> responds) {
		int errorServer=0;
		long retID=0;

		String id = responds.get(0).getValue(PropertyName.RESID);
		long ID=Long.parseLong(id);
		long ID2=Long.parseLong(responds.get(1).getValue(PropertyName.RESID));
		long ID3=Long.parseLong(responds.get(2).getValue(PropertyName.RESID));

		if(ID==ID2 && ID2==ID3 && ID==ID3){
			retID = ID;
		}
		if(ID==ID2 && ID!=ID3){
			errorServer=3;
			retID = ID;
		}
		if(ID==ID3 && ID2!=ID3){
			errorServer=2;
			retID = ID;
		}
		if(ID2==ID3 && ID!=ID2){
			errorServer=1;
			retID = ID2;
		}

		if(errorServer>0){
			sendsuspectedreport(MessageType.REPORT_SUSPECTED_RESPOND,errorServer);
		}

		return "RETCODE Process Complete, ID:" + retID;
	}
	public String Process_String(GeneralMessage.PropertyName n,List<GeneralMessage> responds){
		int errorServer=0;
		String respond=responds.get(0).getValue(n);;
		String respond2=responds.get(1).getValue(n);
		String respond3=responds.get(2).getValue(n);
		if(respond.equals(respond2) && respond2.equals(respond3) && respond.equals(respond3)){
			return respond;
		}
		if(respond.equals(respond2) && !respond.equals(respond3)){
			errorServer=3;
			return respond;
		}
		if(respond.equals(respond3) && !respond2.equals(respond3)){
			errorServer=2;
			return respond;
		}
		if(respond2.equals(respond3) && !respond.equals(respond2)){
			errorServer=1;
			return respond2;
		}

		if(errorServer>0){
			sendsuspectedreport(MessageType.REPORT_SUSPECTED_RESPOND,errorServer);
		}
		return "Error";
	}

	@Override
	public String reserveRoom(int GuestID, String hotel, String RoomType,
			int checkindate, int checkoutdate) {
		//sent properties...
		GeneralMessage g= new GeneralMessage(MessageType.RESERVE);
		g.setValue(PropertyName.GUESTID,String.valueOf(GuestID));
		g.setValue(PropertyName.HOTEL, hotel);
		g.setValue(PropertyName.ROOMTYPE, RoomType);
		g.setValue(PropertyName.CHECKINDATE,String.valueOf(checkindate));
		g.setValue(PropertyName.CHECKOUTDATE,String.valueOf(checkoutdate));
		List<GeneralMessage> responds = new ArrayList<GeneralMessage>();  
		responds = handleRequest(g);

		//return properties...
		GeneralMessage m = preprocess(responds);

		if (m!=null) {
			return "RETCODE Process Complete, ID:" + m.getValue(PropertyName.RESID);
		} else
			return Process_RETCODE(responds);
	}

	@Override
	public String cancelRoom(int GuestID, String hotel, String RoomType,
			int checkindate, int checkoutdate) {
		GeneralMessage g= new GeneralMessage(MessageType.CANCEL);
		g.setValue(PropertyName.GUESTID,String.valueOf(GuestID));
		g.setValue(PropertyName.HOTEL, hotel);
		g.setValue(PropertyName.ROOMTYPE, RoomType);
		g.setValue(PropertyName.CHECKINDATE,String.valueOf(checkindate));
		g.setValue(PropertyName.CHECKOUTDATE,String.valueOf(checkoutdate));
		List<GeneralMessage> responds = new ArrayList<GeneralMessage>();  
		responds = handleRequest(g);
		//return properties...
		GeneralMessage m = preprocess(responds);

		if (m!=null) {
			return "RETCODE Process Complete, ID:" + m.getValue(PropertyName.RESID);
		} else
			return Process_RETCODE(responds);
	}





	@Override
	public String checkAvailability(int GuestID, String Preferredhotel,
			String RoomType, int checkindate, int checkoutdate) {
		GeneralMessage g= new GeneralMessage(MessageType.CHECKAVAILABILITY);
		g.setValue(PropertyName.GUESTID,String.valueOf(GuestID));
		g.setValue(PropertyName.HOTEL, Preferredhotel);
		g.setValue(PropertyName.ROOMTYPE, RoomType);
		g.setValue(PropertyName.CHECKINDATE,String.valueOf(checkindate));
		g.setValue(PropertyName.CHECKOUTDATE,String.valueOf(checkoutdate));
		List<GeneralMessage> responds = new ArrayList<GeneralMessage>();  
		responds = handleRequest(g);
		//return properties...
		GeneralMessage m = preprocess(responds);
		if (m!=null) {
			return m.getValue(PropertyName.AVALIABLITY);
		} else
			return Process_String(PropertyName.AVALIABLITY,responds);
	}



	@Override
	public String serviceReport(String hotel, int ServiceDate) {
		GeneralMessage g= new GeneralMessage(MessageType.SERVICEREPORT);
		g.setValue(PropertyName.HOTEL, hotel);
		g.setValue(PropertyName.CHECKOUTDATE,String.valueOf(ServiceDate));
		List<GeneralMessage> responds = new ArrayList<GeneralMessage>();  
		responds = handleRequest(g);
		//return properties...
		GeneralMessage m = preprocess(responds);
		if (m!=null) {
			return m.getValue(PropertyName.SERVICEREPORT);
		} else
			return Process_String(PropertyName.SERVICEREPORT,responds);
	}




	@Override
	public String printSatus(String hotel, int Date) {
		GeneralMessage g= new GeneralMessage(MessageType.STATUSREPORT);
		g.setValue(PropertyName.HOTEL, hotel);
		g.setValue(PropertyName.DATE,String.valueOf(Date));
		List<GeneralMessage> responds = new ArrayList<GeneralMessage>();  
		responds = handleRequest(g);
		//return properties...
		GeneralMessage m = preprocess(responds);
		if (m!=null) {
			return m.getValue(PropertyName.PRINTSTATUS);
		} else
			return Process_String(PropertyName.PRINTSTATUS,responds);
	}

	@Override
	public String transferReservation(int GuestID, int ReservationID,
			String CurrentHotel, String OtherHotel) {
		GeneralMessage g= new GeneralMessage(MessageType.TRANSFER);
		g.setValue(PropertyName.GUESTID, String.valueOf(GuestID));
		g.setValue(PropertyName.RESID, String.valueOf(ReservationID));
		g.setValue(PropertyName.HOTEL, CurrentHotel);
		g.setValue(PropertyName.OTHERHOTEL, OtherHotel);
		List<GeneralMessage> responds = new ArrayList<GeneralMessage>();  
		responds = handleRequest(g);
		//return properties...
		GeneralMessage m = preprocess(responds);

		if (m!=null) {
			return "RETCODE Process Complete, ID:" + m.getValue(PropertyName.RESID);
		} else
			return Process_RETCODE(responds);
	}

}
