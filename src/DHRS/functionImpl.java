package DHRS;

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
				localSocket.setSoTimeout(1000);
				localSocket.receive(receivePacket);
				String receive=new String(receivePacket.getData());
				GeneralMessage m=GeneralMessage.decode(receive);
				messages.set(Integer.valueOf(m.getValue(PropertyName.SERVERID))-1, m);

			}
		} catch (SocketTimeoutException e){


		} catch (IOException e) {

			e.printStackTrace();
		} 

		return messages;
	}

	public void sendsuspectedreport(MessageType t,int errorServer){
		GeneralMessage g= new GeneralMessage(t);
		g.setValue(PropertyName.SERVERID, String.valueOf(errorServer));
		String encode=g.encode();
		byte[] sendData =encode.getBytes();
		DatagramPacket sendPacket=new DatagramPacket(sendData,sendData.length,sequencerAddress);

		try {
			localSocket.send(sendPacket);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	public void preprocess(List<GeneralMessage> responds) {
		GeneralMessage m1=responds.get(0);
		GeneralMessage m2=responds.get(1);
		GeneralMessage m3=responds.get(2);
		int errorServer=0;
		if (m1==null) {
			errorServer=1;
		}
		if (m2==null) {
			errorServer=2;
		}
		if (m3==null) {
			errorServer=3;
		}
		if(errorServer>0){
			sendsuspectedreport(MessageType.REPORT_NO_RESPOND,errorServer);
		}

	}
	public String Process_RETCODE(List<GeneralMessage> responds) {
		int errorServer=0;
		long ID=Long.parseLong(responds.get(0).getValue(PropertyName.RETCODE));
		long ID2=Long.parseLong(responds.get(1).getValue(PropertyName.RETCODE));
		long ID3=Long.parseLong(responds.get(2).getValue(PropertyName.RETCODE));

		if(ID==ID2 && ID2==ID3 && ID==ID3){
			return "ok with all ID";
		}
		if(ID==ID2 && ID!=ID3){
			errorServer=3;
			return "ok with ID"+ID;
		}
		if(ID==ID3 && ID2!=ID3){
			errorServer=2;
			return "ok with ID"+ID;
		}
		if(ID2==ID3 && ID!=ID2){
			errorServer=1;
			return "ok with ID"+ID2;
		}
		if(errorServer>0){
			sendsuspectedreport(MessageType.REPORT_SUSPECTED_RESPOND,errorServer);
		}
		return "RETCODE Process Complete";
	}
	@Override
	public String reserveRoom(int GuestID, String hotel, String RoomType,
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
		preprocess(responds);
		Process_RETCODE(responds);
		return "ReserveRoom Process Complete";
	}

	@Override
	public String cancelRoom(int GuestID, String hotel, String RoomType,
			int checkindate, int checkoutdate) {
		GeneralMessage g= new GeneralMessage();
		g.setValue(PropertyName.GUESTID,String.valueOf(GuestID));
		g.setValue(PropertyName.HOTEL, hotel);
		g.setValue(PropertyName.ROOMTYPE, RoomType);
		g.setValue(PropertyName.CHECKINDATE,String.valueOf(checkindate));
		g.setValue(PropertyName.CHECKOUTDATE,String.valueOf(checkoutdate));
		List<GeneralMessage> responds = new ArrayList<GeneralMessage>();  
		responds = handleRequest(g);
		//return properties...
		preprocess(responds);
		Process_RETCODE(responds);
		return "CancelRoom Process Complete";


	}

	@Override
	public String checkAvailability(int GuestID, String Preferredhotel,
			String RoomType, int checkindate, int checkoutdate) {
		GeneralMessage g= new GeneralMessage();
		g.setValue(PropertyName.GUESTID,String.valueOf(GuestID));
		g.setValue(PropertyName.HOTEL, Preferredhotel);
		g.setValue(PropertyName.ROOMTYPE, RoomType);
		g.setValue(PropertyName.CHECKINDATE,String.valueOf(checkindate));
		g.setValue(PropertyName.CHECKOUTDATE,String.valueOf(checkoutdate));
		List<GeneralMessage> responds = new ArrayList<GeneralMessage>();  
		responds = handleRequest(g);
		//return properties...
		preprocess(responds);
		Long ID=Long.parseLong(responds.get(0).getValue(PropertyName.RESID));
		String Avaliability=responds.get(0).getValue(PropertyName.AVALIABLITY);
		if(ID>0){
			return "ok with ID"+ID+"\n"+Avaliability;
		}

		return "fail with ID"+ID;
	}

	@Override
	public String serviceReport(String hotel, int ServiceDate) {
		GeneralMessage g= new GeneralMessage();
		g.setValue(PropertyName.HOTEL, hotel);
		g.setValue(PropertyName.CHECKOUTDATE,String.valueOf(ServiceDate));
		List<GeneralMessage> responds = new ArrayList<GeneralMessage>();  
		responds = handleRequest(g);
		//return properties...
		Long ID=Long.parseLong(responds.get(0).getValue(PropertyName.RESID));
		String ServiceReport=responds.get(1).getValue(PropertyName.SERVICEREPORT);
		if(ID>0){
			return "ok with ID"+ID+"\n"+ServiceReport;
		}

		return "fail with ID"+ID;
	}

	@Override
	public String printSatus(String hotel, int Date) {
		GeneralMessage g= new GeneralMessage();
		g.setValue(PropertyName.HOTEL, hotel);
		g.setValue(PropertyName.DATE,String.valueOf(Date));
		List<GeneralMessage> responds = new ArrayList<GeneralMessage>();  
		responds = handleRequest(g);
		//return properties...
		Long ID=Long.parseLong(responds.get(0).getValue(PropertyName.RESID));
		String PrintSatus=responds.get(1).getValue(PropertyName.PRINTSTATUS);
		if(ID>0){
			return "ok with ID"+ID+"\n"+PrintSatus;
		}
		return "fail with ID"+ID;

	}

	@Override
	public String transferReservation(int GuestID, int ReservationID,
			String CurrentHotel, String OtherHotel) {
		GeneralMessage g= new GeneralMessage();
		g.setValue(PropertyName.GUESTID, String.valueOf(GuestID));
		g.setValue(PropertyName.RESID, String.valueOf(ReservationID));
		g.setValue(PropertyName.HOTEL, CurrentHotel);
		g.setValue(PropertyName.OTHERHOTEL, OtherHotel);
		List<GeneralMessage> responds = new ArrayList<GeneralMessage>();  
		responds = handleRequest(g);
		//return properties...
		Long ID=Long.parseLong(responds.get(0).getValue(PropertyName.RESID));
		if(ID>0){
			return "ok with ID"+ID+"transfer complete from "+CurrentHotel+" to "+OtherHotel+" requested by "+GuestID;
		}
		return "fail with ID"+ID;
	}

}
