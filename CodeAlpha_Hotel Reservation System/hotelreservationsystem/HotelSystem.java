package hotelreservationsystem;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class HotelSystem {

    private static ArrayList<Room> rooms = new ArrayList<>();
    private static ArrayList<Reservation> reservations = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static final String FILE_NAME = "hotel_data.ser"; // File to save data

    public static void main(String[] args) {
        loadData(); // REQ: File I/O - Load previous data if it exists
        if (rooms.isEmpty()) {
            initializeRooms(); // Only init if no file was loaded
        }

        System.out.println("Welcome to the Hotel Reservation System (with Persistence)");

        boolean running = true;
        while (running) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Search Rooms by Category"); // REQ: Search
            System.out.println("2. View All Available Rooms");
            System.out.println("3. Make a Reservation"); // REQ: Booking
            System.out.println("4. View All Bookings"); // REQ: Details View
            System.out.println("5. Exit & Save");
            System.out.print("Enter your choice: ");

            int choice = 0;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
                continue;
            }

            switch (choice) {
                case 1:
                    searchRooms();
                    break;
                case 2:
                    displayAvailableRooms();
                    break;
                case 3:
                    makeReservation();
                    break;
                case 4:
                    viewBookings();
                    break;
                case 5:
                    saveData(); // REQ: File I/O - Save data before exit
                    System.out.println("Data saved. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // -- Logic Methods --

    private static void initializeRooms() {
        // REQ: Room Categorization
        rooms.add(new Room(101, "Standard", 100.00));
        rooms.add(new Room(102, "Standard", 100.00));
        rooms.add(new Room(201, "Deluxe", 200.00));
        rooms.add(new Room(301, "Suite", 350.00));
    }

    private static void searchRooms() {
        System.out.print("Enter category to search (Standard/Deluxe/Suite): ");
        String cat = scanner.nextLine();
        System.out.println("\n--- Search Results ---");
        boolean found = false;
        for (Room room : rooms) {
            if (room.getCategory().equalsIgnoreCase(cat) && room.isAvailable()) {
                System.out.println(room);
                found = true;
            }
        }
        if (!found)
            System.out.println("No available rooms found for category: " + cat);
    }

    private static void displayAvailableRooms() {
        System.out.println("\n--- Available Rooms ---");
        for (Room room : rooms) {
            if (room.isAvailable())
                System.out.println(room);
        }
    }

    private static void makeReservation() {
        System.out.println("\n--- Make a Reservation ---");
        displayAvailableRooms();

        System.out.print("Enter Room Number to book: ");
        int roomNum = Integer.parseInt(scanner.nextLine());

        Room selectedRoom = null;
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNum && room.isAvailable()) {
                selectedRoom = room;
                break;
            }
        }

        if (selectedRoom != null) {
            System.out.print("Enter Guest Name: ");
            String name = scanner.nextLine();

            // REQ: Payment Simulation
            System.out.println("Total Amount: $" + selectedRoom.getPrice());
            System.out.print("Proceed to payment? (yes/no): ");
            String payment = scanner.nextLine();

            if (payment.equalsIgnoreCase("yes")) {
                System.out.println("Processing payment...");
                System.out.println("Payment Successful!"); // Payment Simulated

                selectedRoom.setAvailable(false);
                reservations.add(new Reservation(name, selectedRoom, true));
                System.out.println("Booking Confirmed for " + name);
            } else {
                System.out.println("Payment cancelled. Booking failed.");
            }
        } else {
            System.out.println("Room not found or already booked.");
        }
    }

    private static void viewBookings() {
        System.out.println("\n--- Current Bookings ---");
        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
        } else {
            for (Reservation res : reservations) {
                System.out.println(res);
            }
        }
    }

    // -- File I/O Methods (Saving & Loading) --

    private static void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(rooms);
            oos.writeObject(reservations);
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private static void loadData() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                rooms = (ArrayList<Room>) ois.readObject();
                reservations = (ArrayList<Reservation>) ois.readObject();
                System.out.println("Data loaded successfully.");
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error loading data: " + e.getMessage());
            }
        }
    }
}