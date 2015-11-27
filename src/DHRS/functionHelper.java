package DHRS;

/** 
 * Helper class for : function
 *  
 * @author OpenORB Compiler
 */ 
public class functionHelper
{
    /**
     * Insert function into an any
     * @param a an any
     * @param t function value
     */
    public static void insert(org.omg.CORBA.Any a, DHRS.function t)
    {
        a.insert_Object(t , type());
    }

    /**
     * Extract function from an any
     *
     * @param a an any
     * @return the extracted function value
     */
    public static DHRS.function extract( org.omg.CORBA.Any a )
    {
        if ( !a.type().equivalent( type() ) )
        {
            throw new org.omg.CORBA.MARSHAL();
        }
        try
        {
            return DHRS.functionHelper.narrow( a.extract_Object() );
        }
        catch ( final org.omg.CORBA.BAD_PARAM e )
        {
            throw new org.omg.CORBA.MARSHAL(e.getMessage());
        }
    }

    //
    // Internal TypeCode value
    //
    private static org.omg.CORBA.TypeCode _tc = null;

    /**
     * Return the function TypeCode
     * @return a TypeCode
     */
    public static org.omg.CORBA.TypeCode type()
    {
        if (_tc == null) {
            org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init();
            _tc = orb.create_interface_tc( id(), "function" );
        }
        return _tc;
    }

    /**
     * Return the function IDL ID
     * @return an ID
     */
    public static String id()
    {
        return _id;
    }

    private final static String _id = "IDL:DHRS/function:1.0";

    /**
     * Read function from a marshalled stream
     * @param istream the input stream
     * @return the readed function value
     */
    public static DHRS.function read(org.omg.CORBA.portable.InputStream istream)
    {
        return(DHRS.function)istream.read_Object(DHRS._functionStub.class);
    }

    /**
     * Write function into a marshalled stream
     * @param ostream the output stream
     * @param value function value
     */
    public static void write(org.omg.CORBA.portable.OutputStream ostream, DHRS.function value)
    {
        ostream.write_Object((org.omg.CORBA.portable.ObjectImpl)value);
    }

    /**
     * Narrow CORBA::Object to function
     * @param obj the CORBA Object
     * @return function Object
     */
    public static function narrow(org.omg.CORBA.Object obj)
    {
        if (obj == null)
            return null;
        if (obj instanceof function)
            return (function)obj;

        if (obj._is_a(id()))
        {
            _functionStub stub = new _functionStub();
            stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
            return stub;
        }

        throw new org.omg.CORBA.BAD_PARAM();
    }

    /**
     * Unchecked Narrow CORBA::Object to function
     * @param obj the CORBA Object
     * @return function Object
     */
    public static function unchecked_narrow(org.omg.CORBA.Object obj)
    {
        if (obj == null)
            return null;
        if (obj instanceof function)
            return (function)obj;

        _functionStub stub = new _functionStub();
        stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
        return stub;

    }

}
