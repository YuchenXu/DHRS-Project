package DHRS;

/**
 * Interface definition: function.
 * 
 * @author OpenORB Compiler
 */
public interface functionOperations
{
    /**
     * Operation reserveRoom
     */
    public String reserveRoom(int GuestID, String hotel, String RoomType, int checkindate, int checkoutdate);

    /**
     * Operation cancelRoom
     */
    public String cancelRoom(int GuestID, String hotel, String RoomType, int checkindate, int checkoutdate);

    /**
     * Operation checkAvailability
     */
    public String checkAvailability(int GuestID, String Preferredhotel, String RoomType, int checkindate, int checkoutdate);

    /**
     * Operation serviceReport
     */
    public String serviceReport(String hotel, int ServiceDate);

    /**
     * Operation printSatus
     */
    public String printSatus(String hotel, int Date);

    /**
     * Operation transferReservation
     */
    public String transferReservation(int GuestID, int ReservationID, String CurrentHotel, String OtherHotel);

}
