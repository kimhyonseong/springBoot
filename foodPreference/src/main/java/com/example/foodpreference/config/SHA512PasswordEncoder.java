package com.example.foodpreference.config;

import lombok.RequiredArgsConstructor;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@RequiredArgsConstructor
public class SHA512PasswordEncoder implements PasswordEncoder {
  private final Log logger = LogFactory.getLog(getClass());
  @Override
  public String encode(CharSequence rawPassword) {
    if (rawPassword == null) {
      throw new IllegalArgumentException("rawPassword not null");
    }
    return null;
  }

  @Override
  public boolean matches(CharSequence rawPassword, String encodedPassword) {
    return false;
  }

  private String getSHA512Password(CharSequence rawPassword) {
    MessageDigest md = null;

    try {
      md = MessageDigest.getInstance("SHA-512");
      md.update(rawPassword.toString().getBytes(StandardCharsets.UTF_8));
    } catch (Exception e) {
      this.logger.warn(e.getMessage());
    }

    byte[] mdd = md.digest();

    StringBuilder sb = new StringBuilder();

    for (byte b : mdd) {
      String tmp = Integer.toHexString(b & 0xFF);

      while (tmp.length() < 2) {
        tmp = "0" + tmp;
      }
      sb.append(tmp.substring(tmp.length() - 2));
    }

    return sb.toString();
  }
}
