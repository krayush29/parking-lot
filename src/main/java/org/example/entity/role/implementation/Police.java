package org.example.entity.role.implementation;

import org.example.entity.role.Subscriber;

public class Police implements Subscriber {

    @Override
    public void update(String message) {
        System.out.println("Police received message: " + message);
    }
}
