package com.tweeny_store.tweeny_store.service.email;

public interface EmailService {
    void sendSimpleMailMessage(String name, String to, String token) throws Exception;
}
