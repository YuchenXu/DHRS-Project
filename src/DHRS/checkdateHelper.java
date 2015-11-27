package DHRS;

/** 
 * Helper class for : checkdate
 *  
 * @author OpenORB Compiler
 */ 
public class checkdateHelper
{
    /**
     * Insert checkdate into an any
     * @param a an any
     * @param t checkdate value
     */
    public static void insert(org.omg.CORBA.Any a, int t)
    {
        a.type(type());
        write(a.create_output_stream(),t);
    }

    /**
     * Extract checkdate from an any
     *
     * @param a an any
     * @return the extracted checkdate value
     */
    public static int extract( org.omg.CORBA.Any a )
    {
        if ( !a.type().equivalent( type() ) )
        {
            throw new org.omg.CORBA.MARSHAL();
        }
        return read( a.create_input_stream() );
    }

    //
    // Internal TypeCode value
    //
    private static org.omg.CORBA.TypeCode _tc = null;

    /**
     * Return the checkdate TypeCode
     * @return a TypeCode
     */
    public static org.omg.CORBA.TypeCode type()
    {
        if (_tc == null) {
            org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init();
            _tc = orb.create_alias_tc( id(), "checkdate", orb.get_primitive_tc( org.omg.CORBA.TCKind.tk_long ) );
        }
        return _tc;
    }

    /**
     * Return the checkdate IDL ID
     * @return an ID
     */
    public static String id()
    {
        return _id;
    }

    private final static String _id = "IDL:DHRS/checkdate:1.0";

    /**
     * Read checkdate from a marshalled stream
     * @param istream the input stream
     * @return the readed checkdate value
     */
    public static int read(org.omg.CORBA.portable.InputStream istream)
    {
        int new_one;
        new_one = istream.read_long();

        return new_one;
    }

    /**
     * Write checkdate into a marshalled stream
     * @param ostream the output stream
     * @param value checkdate value
     */
    public static void write(org.omg.CORBA.portable.OutputStream ostream, int value)
    {
        ostream.write_long( value );
    }

}
