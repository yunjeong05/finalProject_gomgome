package com.ezen.gomgome.service.mail;

import java.util.Map;

public interface MailService {
   public Map<String, Object> send(String email, String title, String body);
}