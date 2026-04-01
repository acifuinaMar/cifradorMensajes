package com.cifrador.util;

import java.security.*;
import javax.crypto.Cipher;
import java.util.Base64;

public class Cifrado {

    private KeyPair keyPair;

    // Crear keys
    public void generarLlaves(int tamaño) throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(tamaño);
        keyPair = keyGen.generateKeyPair();
    }

    // Llave publica
    public PublicKey getPublicKey() {
        return keyPair.getPublic();
    }

    // llave priv
    public PrivateKey getPrivateKey() {
        return keyPair.getPrivate();
    }

    // cifrar
    public String cifrar(String texto) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
        byte[] cifrado = cipher.doFinal(texto.getBytes());
        return Base64.getEncoder().encodeToString(cifrado);
    }

    // descrifrar
    public String descifrar(String textoCifrado) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
        byte[] descifrado = cipher.doFinal(Base64.getDecoder().decode(textoCifrado));
        return new String(descifrado);
    }
}