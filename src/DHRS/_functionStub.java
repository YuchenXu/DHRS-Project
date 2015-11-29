package DHRS;

/**
 * Interface definition: function.
 * 
 * @author OpenORB Compiler
 */
public class _functionStub extends org.omg.CORBA.portable.ObjectImpl
        implements function
{
    static final String[] _ids_list =
    {
        "IDL:DHRS/function:1.0"
    };

    public String[] _ids()
    {
     return _ids_list;
    }

    private final static Class _opsClass = DHRS.functionOperations.class;

    /**
     * Operation reserveRoom
     */
    public String reserveRoom(int GuestID, String hotel, String RoomType, int checkindate, int checkoutdate)
    {
        while(true)
        {
            if (!this._is_local())
            {
                org.omg.CORBA.portable.InputStream _input = null;
                try
                {
                    org.omg.CORBA.portable.OutputStream _output = this._request("reserveRoom",true);
                    DHRS.IDHelper.write(_output,GuestID);
                    _output.write_string(hotel);
                    _output.write_string(RoomType);
                    DHRS.checkdateHelper.write(_output,checkindate);
                    DHRS.checkdateHelper.write(_output,checkoutdate);
                    _input = this._invoke(_output);
                    String _arg_ret = _input.read_string();
                    return _arg_ret;
                }
                catch(org.omg.CORBA.portable.RemarshalException _exception)
                {
                    continue;
                }
                catch(org.omg.CORBA.portable.ApplicationException _exception)
                {
                    String _exception_id = _exception.getId();
                    throw new org.omg.CORBA.UNKNOWN("Unexpected User Exception: "+ _exception_id);
                }
                finally
                {
                    this._releaseReply(_input);
                }
            }
            else
            {
                org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke("reserveRoom",_opsClass);
                if (_so == null)
                   continue;
                DHRS.functionOperations _self = (DHRS.functionOperations) _so.servant;
                try
                {
                    return _self.reserveRoom( GuestID,  hotel,  RoomType,  checkindate,  checkoutdate);
                }
                finally
                {
                    _servant_postinvoke(_so);
                }
            }
        }
    }

    /**
     * Operation cancelRoom
     */
    public String cancelRoom(int GuestID, String hotel, String RoomType, int checkindate, int checkoutdate)
    {
        while(true)
        {
            if (!this._is_local())
            {
                org.omg.CORBA.portable.InputStream _input = null;
                try
                {
                    org.omg.CORBA.portable.OutputStream _output = this._request("cancelRoom",true);
                    DHRS.IDHelper.write(_output,GuestID);
                    _output.write_string(hotel);
                    _output.write_string(RoomType);
                    DHRS.checkdateHelper.write(_output,checkindate);
                    DHRS.checkdateHelper.write(_output,checkoutdate);
                    _input = this._invoke(_output);
                    String _arg_ret = _input.read_string();
                    return _arg_ret;
                }
                catch(org.omg.CORBA.portable.RemarshalException _exception)
                {
                    continue;
                }
                catch(org.omg.CORBA.portable.ApplicationException _exception)
                {
                    String _exception_id = _exception.getId();
                    throw new org.omg.CORBA.UNKNOWN("Unexpected User Exception: "+ _exception_id);
                }
                finally
                {
                    this._releaseReply(_input);
                }
            }
            else
            {
                org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke("cancelRoom",_opsClass);
                if (_so == null)
                   continue;
                DHRS.functionOperations _self = (DHRS.functionOperations) _so.servant;
                try
                {
                    return _self.cancelRoom( GuestID,  hotel,  RoomType,  checkindate,  checkoutdate);
                }
                finally
                {
                    _servant_postinvoke(_so);
                }
            }
        }
    }

    /**
     * Operation checkAvailability
     */
    public String checkAvailability(int GuestID, String Preferredhotel, String RoomType, int checkindate, int checkoutdate)
    {
        while(true)
        {
            if (!this._is_local())
            {
                org.omg.CORBA.portable.InputStream _input = null;
                try
                {
                    org.omg.CORBA.portable.OutputStream _output = this._request("checkAvailability",true);
                    DHRS.IDHelper.write(_output,GuestID);
                    _output.write_string(Preferredhotel);
                    _output.write_string(RoomType);
                    DHRS.checkdateHelper.write(_output,checkindate);
                    DHRS.checkdateHelper.write(_output,checkoutdate);
                    _input = this._invoke(_output);
                    String _arg_ret = _input.read_string();
                    return _arg_ret;
                }
                catch(org.omg.CORBA.portable.RemarshalException _exception)
                {
                    continue;
                }
                catch(org.omg.CORBA.portable.ApplicationException _exception)
                {
                    String _exception_id = _exception.getId();
                    throw new org.omg.CORBA.UNKNOWN("Unexpected User Exception: "+ _exception_id);
                }
                finally
                {
                    this._releaseReply(_input);
                }
            }
            else
            {
                org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke("checkAvailability",_opsClass);
                if (_so == null)
                   continue;
                DHRS.functionOperations _self = (DHRS.functionOperations) _so.servant;
                try
                {
                    return _self.checkAvailability( GuestID,  Preferredhotel,  RoomType,  checkindate,  checkoutdate);
                }
                finally
                {
                    _servant_postinvoke(_so);
                }
            }
        }
    }

    /**
     * Operation serviceReport
     */
    public String serviceReport(String hotel, int ServiceDate)
    {
        while(true)
        {
            if (!this._is_local())
            {
                org.omg.CORBA.portable.InputStream _input = null;
                try
                {
                    org.omg.CORBA.portable.OutputStream _output = this._request("serviceReport",true);
                    _output.write_string(hotel);
                    DHRS.checkdateHelper.write(_output,ServiceDate);
                    _input = this._invoke(_output);
                    String _arg_ret = _input.read_string();
                    return _arg_ret;
                }
                catch(org.omg.CORBA.portable.RemarshalException _exception)
                {
                    continue;
                }
                catch(org.omg.CORBA.portable.ApplicationException _exception)
                {
                    String _exception_id = _exception.getId();
                    throw new org.omg.CORBA.UNKNOWN("Unexpected User Exception: "+ _exception_id);
                }
                finally
                {
                    this._releaseReply(_input);
                }
            }
            else
            {
                org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke("serviceReport",_opsClass);
                if (_so == null)
                   continue;
                DHRS.functionOperations _self = (DHRS.functionOperations) _so.servant;
                try
                {
                    return _self.serviceReport( hotel,  ServiceDate);
                }
                finally
                {
                    _servant_postinvoke(_so);
                }
            }
        }
    }

    /**
     * Operation printSatus
     */
    public String printSatus(String hotel, int Date)
    {
        while(true)
        {
            if (!this._is_local())
            {
                org.omg.CORBA.portable.InputStream _input = null;
                try
                {
                    org.omg.CORBA.portable.OutputStream _output = this._request("printSatus",true);
                    _output.write_string(hotel);
                    DHRS.checkdateHelper.write(_output,Date);
                    _input = this._invoke(_output);
                    String _arg_ret = _input.read_string();
                    return _arg_ret;
                }
                catch(org.omg.CORBA.portable.RemarshalException _exception)
                {
                    continue;
                }
                catch(org.omg.CORBA.portable.ApplicationException _exception)
                {
                    String _exception_id = _exception.getId();
                    throw new org.omg.CORBA.UNKNOWN("Unexpected User Exception: "+ _exception_id);
                }
                finally
                {
                    this._releaseReply(_input);
                }
            }
            else
            {
                org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke("printSatus",_opsClass);
                if (_so == null)
                   continue;
                DHRS.functionOperations _self = (DHRS.functionOperations) _so.servant;
                try
                {
                    return _self.printSatus( hotel,  Date);
                }
                finally
                {
                    _servant_postinvoke(_so);
                }
            }
        }
    }

    /**
     * Operation transferReservation
     */
    public String transferReservation(int GuestID, int ReservationID, String CurrentHotel, String OtherHotel)
    {
        while(true)
        {
            if (!this._is_local())
            {
                org.omg.CORBA.portable.InputStream _input = null;
                try
                {
                    org.omg.CORBA.portable.OutputStream _output = this._request("transferReservation",true);
                    DHRS.IDHelper.write(_output,GuestID);
                    DHRS.IDHelper.write(_output,ReservationID);
                    _output.write_string(CurrentHotel);
                    _output.write_string(OtherHotel);
                    _input = this._invoke(_output);
                    String _arg_ret = _input.read_string();
                    return _arg_ret;
                }
                catch(org.omg.CORBA.portable.RemarshalException _exception)
                {
                    continue;
                }
                catch(org.omg.CORBA.portable.ApplicationException _exception)
                {
                    String _exception_id = _exception.getId();
                    throw new org.omg.CORBA.UNKNOWN("Unexpected User Exception: "+ _exception_id);
                }
                finally
                {
                    this._releaseReply(_input);
                }
            }
            else
            {
                org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke("transferReservation",_opsClass);
                if (_so == null)
                   continue;
                DHRS.functionOperations _self = (DHRS.functionOperations) _so.servant;
                try
                {
                    return _self.transferReservation( GuestID,  ReservationID,  CurrentHotel,  OtherHotel);
                }
                finally
                {
                    _servant_postinvoke(_so);
                }
            }
        }
    }

}
