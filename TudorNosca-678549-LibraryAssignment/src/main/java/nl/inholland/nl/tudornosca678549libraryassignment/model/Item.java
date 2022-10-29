package nl.inholland.nl.tudornosca678549libraryassignment.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Item implements Serializable {
    static final long serialVersionUID = 1L;

    int itemCode;
    boolean isAvailable;
    String availabilityString;
    String title;
    String author;
    LocalDate timeLent;

    public Item(boolean isAvailable, String title, String author) {
        this.isAvailable = isAvailable;
        this.title = title;
        this.author = author;
    }

    public int getItemCode() {
        return itemCode;
    }

    public void setItemCode(int itemCode) {
        this.itemCode = itemCode;
    }

    public boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean available) {
        isAvailable = available;
    }

    public String getAvailabilityString() {
        return availabilityString;
    }

    public void setAvailabilityString(String availabilityString) {
        this.availabilityString = availabilityString;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDate getTimeLent() {
        return timeLent;
    }

    public void setTimeLent(LocalDate timeLent) {
        this.timeLent = timeLent;
    }
}
