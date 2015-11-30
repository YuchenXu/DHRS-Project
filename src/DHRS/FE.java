package DHRS;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.ObjectNotActive;
import org.omg.PortableServer.POAPackage.ServantAlreadyActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

public class FE {

	/**
	 * @param args
	 * @throws WrongPolicy 
	 * @throws ServantAlreadyActive 
	 * @throws ObjectNotActive 
	 * @throws FileNotFoundException 
	 * @throws AdapterInactive 
	 */
	public static void main(String[] args) throws ServantAlreadyActive, WrongPolicy, ObjectNotActive, FileNotFoundException, AdapterInactive {
		ORB orb=ORB.init(args,null);
		try {
			POA rootpoa=POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			InetSocketAddress testaddress= new InetSocketAddress("localhost",2020);
			functionImpl FE=new functionImpl();
			FE.start_FE(2015, testaddress);
			byte[] id=rootpoa.activate_object(FE);
			org.omg.CORBA.Object ref=rootpoa.id_to_reference(id);
			String ior=orb.object_to_string(ref);
			System.out.println(ref);
			PrintWriter file=new PrintWriter("ior.txt");
			file.println(ior);
			file.close();
			rootpoa.the_POAManager().activate();
			orb.run();
		} catch (InvalidName e) {
			e.printStackTrace();
		}

	}

}
