

import java.util.*;

// Room domain model
class Room {
    String type;
    double price;
    List<String> amenities;

    public Room(String type, double price, List<String> amenities) {
        this.type = type;
        this.price = price;
        this.amenities = amenities;
    }

    public void display() {
        System.out.println("Room Type: " + type);
        System.out.println("Price: ₹" + price);
        System.out.println("Amenities: " + amenities);
        System.out.println("---------------------------");
    }
}

// Inventory (STATE HOLDER)
class Inventory {
    private HashMap<String, Integer> roomAvailability = new HashMap<>();

    public void addRoom(String type, int count) {
        roomAvailability.put(type, count);
    }

    // READ ONLY ACCESS
    public int getAvailability(String type) {
        return roomAvailability.getOrDefault(type, 0);
    }

    public Set<String> getAllRoomTypes() {
        return roomAvailability.keySet();
    }
}

// Search Service (READ ONLY)
class SearchService {
    private Inventory inventory;
    private HashMap<String, Room> roomDetails;

    public SearchService(Inventory inventory, HashMap<String, Room> roomDetails) {
        this.inventory = inventory;
        this.roomDetails = roomDetails;
    }

    public void searchAvailableRooms() {
        System.out.println("Available Rooms:\n");

        for (String type : inventory.getAllRoomTypes()) {

            int available = inventory.getAvailability(type);

            // VALIDATION: only show available rooms
            if (available > 0) {
                Room room = roomDetails.get(type);

                System.out.println("Available Count: " + available);
                room.display();
            }
        }
    }
}

// MAIN CLASS
public class UseCase4RoomSearch {

    public static void main(String[] args) {

        // STEP 1: Create Inventory
        Inventory inventory = new Inventory();
        inventory.addRoom("Deluxe", 3);
        inventory.addRoom("Suite", 0);
        inventory.addRoom("Standard", 5);

        // STEP 2: Create Room Details
        HashMap<String, Room> roomDetails = new HashMap<>();

        roomDetails.put("Deluxe",
                new Room("Deluxe", 2500, Arrays.asList("AC", "WiFi", "TV")));

        roomDetails.put("Suite",
                new Room("Suite", 5000, Arrays.asList("AC", "WiFi", "TV", "Mini Bar")));

        roomDetails.put("Standard",
                new Room("Standard", 1500, Arrays.asList("Fan", "WiFi")));

        // STEP 3: Search Service
        SearchService searchService = new SearchService(inventory, roomDetails);

        // STEP 4: Perform Search (READ ONLY)
        searchService.searchAvailableRooms();
    }
}