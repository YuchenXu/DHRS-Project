package message;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GeneralMessage {
String request="";

	public enum MessageType {
		RESERVE,
		CANCEL,
		CHECKAVAILABILITY,
		STATUSREPORT,
		SERVICEREPORT,
		TRANSFER,
		RESPOND,    // respond message from hotel server
		REPORT_SUSPECTED_RESPOND,   
		// reporting a suspected respond from a hotel server
		REPORT_NO_RESPOND,  
		// reporting an issue of no response from a hotel server.
		ADD_SERVER,
		RMV_SERVER,
		PAUSE,
		RESUME
	};
	
	public enum PropertyName {
		GUESTID,
		HOTEL,
		ROOMTYPE,
		CHECKINDATE,
		CHECKOUTDATE,
		AVALIABLITY,
		SERVICEREPORT,
		DATE,
		PRINTSTATUS,
		OTHERHOTEL,
		RESID,
		RATE,
		SERVERID,
		RETCODE
	}
	
	//defult construct
	public GeneralMessage(){
		
	}
	// construct an empty UDP request, with message type as defined
	public GeneralMessage(MessageType  type ){
		  request+= type+"\n";
	}
	// set value for a property
	public void setValue (PropertyName property, String value){
		request+= property.toString() +":"+value+"\n";
	}
	// set list of values for a property
	public void setValueList (PropertyName property, List<String> values){
		request+= property.toString() +":";
		for(int i=0;i<values.size();i++){
			request+=values.get(i)+",";
		}
		
		request+= "\n";
		
	}
	// get a value from a property name. Null if property not found        
	public String getValue (PropertyName property){
		String Return=null;
		String[] split=request.split("\n");
		for(String s:split){
			String[] split2=s.split(":");
			if (split2.length>1 && split2[0].equals(property.toString())){
				Return=split2[1];
				break;
			}
			
		}
	
		return Return;
	}
	// get a list of values from a property name. Null if property not found
	// values are sepearted by comma ��,��
	public List<String> getValueList (PropertyName property){
		List<String> Return=new ArrayList<String>();
		String[] split=request.split("\n");
		for(String s:split){
			String[] split2=s.split(":");
			if (split2.length>1 && split2[0].equals(property.toString())){
				
				Return=Arrays.asList(split2[1].split(","));
				
				break;
			}
			
		}
		return Return;
	}
	// encode message in a format as below
	// <Request_type>
	// <propertname>:<value>
	// <popertyname>:<value>,<value>,...,<value>
	public String encode (){
		return request;
	}
	// decode message from a String buffer
	// return null if message format error
	public static GeneralMessage decode (String buf){
		GeneralMessage g=new GeneralMessage();
		g.request=buf;
		return g;
	}
	public MessageType getMessageType (){
		
		String[] split=request.split("\n");
		return MessageType.valueOf(split[0]);
	}



}
