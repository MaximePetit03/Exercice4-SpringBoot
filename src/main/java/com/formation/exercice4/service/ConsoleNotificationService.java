package com.formation.exercice4.service;

import org.springframework.stereotype.Service;

@Service
public class ConsoleNotificationService implements NotificationService {
    @Override
    public void notifier(String destinataire, String message) {
        System.out.println("[NOTIFICATION] " + destinataire + " : " + message);
    }
}
