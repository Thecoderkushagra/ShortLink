package com.TheCoderKushagra.service;

import org.springframework.stereotype.Service;

@Service
public class Base62 {
    private static final String CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String encode(Long id) {
        StringBuilder sb = new StringBuilder();
        while (id > 0) {
            int remainder = (int) (id % 62);
            sb.append(CHARS.charAt(remainder));
            id /= 62;
        }
        return sb.reverse().toString();
    }

    public static long decode(String shortCode) {
        long result = 0;
        for (int i = 0; i < shortCode.length(); i++) {
            char c = shortCode.charAt(i);
            int value = CHARS.indexOf(c);
            if (value == -1) {
                throw new IllegalArgumentException("Invalid Base62 character: " + c);
            }
            result = result * 62 + value;
        }
        return result;
    }

}