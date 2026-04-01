package com.cifrador.util;

import java.util.UUID;

public class GenerarToken {

    public static String generarToken() {
        return UUID.randomUUID().toString();
    }
}