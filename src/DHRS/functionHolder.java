package DHRS;

/**
 * Holder class for : function
 * 
 * @author OpenORB Compiler
 */
final public class functionHolder
        implements org.omg.CORBA.portable.Streamable
{
    /**
     * Internal function value
     */
    public DHRS.function value;

    /**
     * Default constructor
     */
    public functionHolder()
    { }

    /**
     * Constructor with value initialisation
     * @param initial the initial value
     */
    public functionHolder(DHRS.function initial)
    {
        value = initial;
    }

    /**
     * Read function from a marshalled stream
     * @param istream the input stream
     */
    public void _read(org.omg.CORBA.portable.InputStream istream)
    {
        value = functionHelper.read(istream);
    }

    /**
     * Write function into a marshalled stream
     * @param ostream the output stream
     */
    public void _write(org.omg.CORBA.portable.OutputStream ostream)
    {
        functionHelper.write(ostream,value);
    }

    /**
     * Return the function TypeCode
     * @return a TypeCode
     */
    public org.omg.CORBA.TypeCode _type()
    {
        return functionHelper.type();
    }

}
