package toluog.quickeats.model;

public class Table {
    private String id;
    private String restaurantId;
    private boolean occupied;
    private double total;

    public Table() {
    }

    public Table(String id, String restaurantId, boolean occupied, double total) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.occupied = occupied;
        this.total = total;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
