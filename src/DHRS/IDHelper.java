package DHRS;

/** 
 * Helper class for : ID
 *  
 * @author OpenORB Compiler
 */ 
public class IDHelper
{
    /**
     * Insert ID into an any
     * @param a an any
     * @param t ID value
     */
    public static void insert(org.omg.CORBA.Any a, int t)
    {
        a.type(type());
        write(a.create_output_stream(),t);
    }

    /**
     * Extract ID from an any
     *
     * @param a an any
     * @return the extracted ID value
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
     * Return the ID TypeCode
     * @return a TypeCode
     */
    public static org.omg.CORBA.TypeCode type()
    {
        if (_tc == null) {
            org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init();
            _tc = orb.create_alias_tc( id(), "ID", orb.get_primitive_tc( org.omg.CORBA.TCKind.tk_long ) );
        }
        return _tc;
    }

    /**
     * Return the ID IDL ID
     * @return an ID
     */
    public static String id()
    {
        return _id;
    }

    private final static String _id = "IDL:DHRS/ID:1.0";

    /**
     * Read ID from a marshalled stream
     * @param istream the input stream
     * @return the readed ID value
     */
    public static int read(org.omg.CORBA.portable.InputStream istream)
    {
        int new_one;
        new_one = istream.read_long();

        return new_one;
    }

    /**
     * Write ID into a marshalled stream
     * @param ostream the output stream
     * @param value ID value
     */
    public static void write(org.omg.CORBA.portable.OutputStream ostream, int value)
    {
        ostream.write_long( value );
    }

}
