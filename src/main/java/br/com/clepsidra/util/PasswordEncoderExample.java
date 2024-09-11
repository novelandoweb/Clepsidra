package br.com.clepsidra.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderExample {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "teste1234";
        String encodedPassword = encoder.encode(rawPassword);
        System.out.println(encodedPassword);
        System.out.println(encoder.matches("teste1234", encodedPassword));
    }
}
