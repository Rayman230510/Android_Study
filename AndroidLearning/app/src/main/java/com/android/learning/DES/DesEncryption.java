package com.android.learning.DES;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * Created by leixun on 16/12/1.
 */

public class DesEncryption {

    private byte[] mData;
    private byte[] mKey;

//    public byte[] encrypt(byte[] data, byte[] key) throws Exception {
    public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
//        mData = data;
//        mKey = key;

        SecureRandom secureRandom = new SecureRandom();

        //DESedeKeySpec deSedeKeySpec = new DESedeKeySpec(key);
        DESKeySpec deSedeKeySpec = new DESKeySpec(key);

        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = secretKeyFactory.generateSecret(deSedeKeySpec);

        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, secureRandom);

        return cipher.doFinal(data);
    }
}
