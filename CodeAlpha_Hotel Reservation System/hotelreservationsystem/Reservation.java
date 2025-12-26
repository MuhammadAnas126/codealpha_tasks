package hotelreservationsystem;

import java.io.*;

class Reservation implements Serializable {
    private String guestName;
    private Room room;
    private boolean isPaid;

    public Reservation(String guestName, Room room, boolean isPaid) {
        this.guestName = guestName;
        this.room = room;
        this.isPaid = isPaid;
    }

    @Override
    public String toString() {
        return "Guest: " + guestName + " | Room: " + room.getRoomNumber() +
                " (" + room.getCategory() + ") | Paid: " + (isPaid ? "Yes" : "No");
    }
}