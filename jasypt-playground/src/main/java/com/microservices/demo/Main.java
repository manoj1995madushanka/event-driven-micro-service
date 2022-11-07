package com.microservices.demo;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.iv.RandomIvGenerator;


public class Main {
    public static void main(String[] args) {
        StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
        standardPBEStringEncryptor.setPassword("Demo_Pwd!2020");
        standardPBEStringEncryptor.setAlgorithm("PBEWithHMACSHA512AndAES_256");
        standardPBEStringEncryptor.setIvGenerator(new RandomIvGenerator());
        String result = standardPBEStringEncryptor.encrypt("spring_cloud_pwd");
        System.out.println(result);
        System.out.println(standardPBEStringEncryptor.decrypt(result));
    }
}