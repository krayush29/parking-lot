package org.example.entity.role.implementation;

import org.example.entity.role.Subscriber;

import java.util.LinkedList;
import java.util.Queue;

public class Police implements Subscriber {

    private final Queue<String> messages;

    public Police() {
        this.messages = new LinkedList<>();
    }

    @Override
    public void update(String message) {
        this.messages.add(message);
        System.out.println("Police: " + message);
    }

    public LinkedList<String> getNotificationMessages() {
        return (LinkedList<String>) this.messages;
    }
}
