package com.apsitcoders.qrscanner;

/**
 * Created by adityathanekar on 07/10/17.
 */

public class Ticket {
    int fare;
    String source, destination;
    boolean verified = false;

    public Ticket() {

    }

    public Ticket(int fare, String source, String destination, boolean verified) {
        this.fare = fare;
        this.source = source;
        this.destination = destination;
        this.verified = verified;
    }

    public int getFare() {
        return fare;
    }

    public void setFare(int fare) {
        this.fare = fare;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }
}
