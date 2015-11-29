package DHRS;

/**
 * Interface definition: function.
 * 
 * @author OpenORB Compiler
 */
public abstract class functionPOA extends org.omg.PortableServer.Servant
        implements functionOperations, org.omg.CORBA.portable.InvokeHandler
{
    public function _this()
    {
        return functionHelper.narrow(_this_object());
    }

    public function _this(org.omg.CORBA.ORB orb)
    {
        return functionHelper.narrow(_this_object(orb));
    }

    private static String [] _ids_list =
    {
        "IDL:DHRS/function:1.0"
    };

    public String[] _all_interfaces(org.omg.PortableServer.POA poa, byte [] objectId)
    {
        return _ids_list;
    }

    public final org.omg.CORBA.portable.OutputStream _invoke(final String opName,
            final org.omg.CORBA.portable.InputStream _is,
            final org.omg.CORBA.portable.ResponseHandler handler)
    {

        if (opName.equals("cancelRoom")) {
                return _invoke_cancelRoom(_is, handler);
        } else if (opName.equals("checkAvailability")) {
                return _invoke_checkAvailability(_is, handler);
        } else if (opName.equals("printSatus")) {
                return _invoke_printSatus(_is, handler);
        } else if (opName.equals("reserveRoom")) {
                return _invoke_reserveRoom(_is, handler);
        } else if (opName.equals("serviceReport")) {
                return _invoke_serviceReport(_is, handler);
        } else if (opName.equals("transferReservation")) {
                return _invoke_transferReservation(_is, handler);
        } else {
            throw new org.omg.CORBA.BAD_OPERATION(opName);
        }
    }

    // helper methods
    private org.omg.CORBA.portable.OutputStream _invoke_reserveRoom(
            final org.omg.CORBA.portable.InputStream _is,
            final org.omg.CORBA.portable.ResponseHandler handler) {
        org.omg.CORBA.portable.OutputStream _output;
        int arg0_in = DHRS.IDHelper.read(_is);
        String arg1_in = _is.read_string();
        String arg2_in = _is.read_string();
        int arg3_in = DHRS.checkdateHelper.read(_is);
        int arg4_in = DHRS.checkdateHelper.read(_is);

        String _arg_result = reserveRoom(arg0_in, arg1_in, arg2_in, arg3_in, arg4_in);

        _output = handler.createReply();
        _output.write_string(_arg_result);

        return _output;
    }

    private org.omg.CORBA.portable.OutputStream _invoke_cancelRoom(
            final org.omg.CORBA.portable.InputStream _is,
            final org.omg.CORBA.portable.ResponseHandler handler) {
        org.omg.CORBA.portable.OutputStream _output;
        int arg0_in = DHRS.IDHelper.read(_is);
        String arg1_in = _is.read_string();
        String arg2_in = _is.read_string();
        int arg3_in = DHRS.checkdateHelper.read(_is);
        int arg4_in = DHRS.checkdateHelper.read(_is);

        String _arg_result = cancelRoom(arg0_in, arg1_in, arg2_in, arg3_in, arg4_in);

        _output = handler.createReply();
        _output.write_string(_arg_result);

        return _output;
    }

    private org.omg.CORBA.portable.OutputStream _invoke_checkAvailability(
            final org.omg.CORBA.portable.InputStream _is,
            final org.omg.CORBA.portable.ResponseHandler handler) {
        org.omg.CORBA.portable.OutputStream _output;
        int arg0_in = DHRS.IDHelper.read(_is);
        String arg1_in = _is.read_string();
        String arg2_in = _is.read_string();
        int arg3_in = DHRS.checkdateHelper.read(_is);
        int arg4_in = DHRS.checkdateHelper.read(_is);

        String _arg_result = checkAvailability(arg0_in, arg1_in, arg2_in, arg3_in, arg4_in);

        _output = handler.createReply();
        _output.write_string(_arg_result);

        return _output;
    }

    private org.omg.CORBA.portable.OutputStream _invoke_serviceReport(
            final org.omg.CORBA.portable.InputStream _is,
            final org.omg.CORBA.portable.ResponseHandler handler) {
        org.omg.CORBA.portable.OutputStream _output;
        String arg0_in = _is.read_string();
        int arg1_in = DHRS.checkdateHelper.read(_is);

        String _arg_result = serviceReport(arg0_in, arg1_in);

        _output = handler.createReply();
        _output.write_string(_arg_result);

        return _output;
    }

    private org.omg.CORBA.portable.OutputStream _invoke_printSatus(
            final org.omg.CORBA.portable.InputStream _is,
            final org.omg.CORBA.portable.ResponseHandler handler) {
        org.omg.CORBA.portable.OutputStream _output;
        String arg0_in = _is.read_string();
        int arg1_in = DHRS.checkdateHelper.read(_is);

        String _arg_result = printSatus(arg0_in, arg1_in);

        _output = handler.createReply();
        _output.write_string(_arg_result);

        return _output;
    }

    private org.omg.CORBA.portable.OutputStream _invoke_transferReservation(
            final org.omg.CORBA.portable.InputStream _is,
            final org.omg.CORBA.portable.ResponseHandler handler) {
        org.omg.CORBA.portable.OutputStream _output;
        int arg0_in = DHRS.IDHelper.read(_is);
        int arg1_in = DHRS.IDHelper.read(_is);
        String arg2_in = _is.read_string();
        String arg3_in = _is.read_string();

        String _arg_result = transferReservation(arg0_in, arg1_in, arg2_in, arg3_in);

        _output = handler.createReply();
        _output.write_string(_arg_result);

        return _output;
    }

}
