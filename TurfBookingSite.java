package BookWhereYouPlay;

import java.util.*;

public class TurfBookingSite {
    Scanner sc = new Scanner(System.in);
    List<User> users = new ArrayList<>();
    List<Turf> turfs = new ArrayList<>();
    List<Booking> bookings = new ArrayList<>();
    User loggedInUser = null;

    public void start() {
        // Sample turfs
        turfs.add(new Turf(1, "GreenField Turf"));
        turfs.add(new Turf(2, "Champion Arena"));

        while (true) {
            System.out.println("\n--- BOOK WHERE YOU PLAY ---");
            System.out.println("1. Register\n2. Login\n3. Exit");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> register();
                case 2 -> login();
                case 3 -> System.exit(0);
                default -> System.out.println("Invalid choice");
            }
        }
    }

    void register() {
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();
        users.add(new User(username, password));
        System.out.println("Registration successful!");
    }

    void login() {
        System.out.print("Username: ");
        String username = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();

        for (User user : users) {
            if (user.username.equals(username) && user.password.equals(password)) {
                loggedInUser = user;
                System.out.println("Login successful!");
                userMenu();
                return;
            }
        }
        System.out.println("Invalid credentials!");
    }

    void userMenu() {
        while (true) {
            System.out.println("\n1. View Turfs\n2. Book Turf\n3. My Bookings\n4. Cancel Booking\n5. Logout");
            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> showTurfs();
                case 2 -> bookTurf();
                case 3 -> showMyBookings();
                case 4 -> cancelBooking();
                case 5 -> {
                    loggedInUser = null;
                    return;
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }

    void showTurfs() {
        System.out.println("Available Turfs:");
        for (Turf turf : turfs) {
            System.out.println("ID: " + turf.id + " | Name: " + turf.name);
        }
    }

    void bookTurf() {
        showTurfs();
        System.out.print("Enter Turf ID: ");
        int id = sc.nextInt();

        Turf selectedTurf = null;
        for (Turf turf : turfs) {
            if (turf.id == id) {
                selectedTurf = turf;
                break;
            }
        }

        if (selectedTurf == null) {
            System.out.println("Turf not found!");
            return;
        }

        System.out.println("Available Slots (8AM to 8PM):");
        for (int i = 0; i < selectedTurf.slots.length; i++) {
            String status = selectedTurf.slots[i] ? "Booked" : "Available";
            System.out.printf("Slot %d (%d:00 - %d:00) - %s\n", i, 8 + i, 9 + i, status);
        }

        System.out.print("Enter Slot Number (0 to 11): ");
        int slot = sc.nextInt();

        if (slot < 0 || slot >= 12 || selectedTurf.slots[slot]) {
            System.out.println("Invalid or already booked slot.");
            return;
        }

        selectedTurf.slots[slot] = true;
        bookings.add(new Booking(loggedInUser.username, selectedTurf.id, slot));
        System.out.println("Booking successful!");
    }

    void showMyBookings() {
        System.out.println("Your Bookings:");
        for (Booking b : bookings) {
            if (b.username.equals(loggedInUser.username)) {
                Turf t = turfs.get(b.turfId - 1);
                System.out.printf("Turf: %s | Slot: %d (%d:00 - %d:00)\n", t.name, b.slot, 8 + b.slot, 9 + b.slot);
            }
        }
    }

    void cancelBooking() {
        showMyBookings();
        System.out.print("Enter Turf ID to cancel: ");
        int turfId = sc.nextInt();
        System.out.print("Enter Slot to cancel: ");
        int slot = sc.nextInt();

        Booking toRemove = null;
        for (Booking b : bookings) {
            if (b.username.equals(loggedInUser.username) && b.turfId == turfId && b.slot == slot) {
                toRemove = b;
                break;
            }
        }

        if (toRemove != null) {
            bookings.remove(toRemove);
            turfs.get(turfId - 1).slots[slot] = false;
            System.out.println("Booking cancelled.");
        } else {
            System.out.println("Booking not found.");
        }
    }
}