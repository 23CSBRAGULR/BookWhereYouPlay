public class Turf {
    int id;
    String name;
    boolean[] slots; // 8AM to 8PM -> 12 slots

    public Turf(int id, String name) {
        this.id = id;
        this.name = name;
        this.slots = new boolean[12]; // false = available
    }
}