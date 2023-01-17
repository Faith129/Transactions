package com.neptunesoftware.nipintegrator.NIP.security;

import com.didisoft.pgp.KeyStore;
import com.didisoft.pgp.PGPException;
import com.didisoft.pgp.PGPLib;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.StringTokenizer;

public class SSMLib {

    private NIPCredential nipCredential;
    private String privateKeyFileName = "private.key";
    private String publicKeyFileName = "public.key";
    private String username = NIPCredential.getUsername();
    private String password = NIPCredential.getPassword();
    static ClassLoader classLoader = SSMLib.class.getClassLoader();

    public SSMLib() {
        System.out.println("SSMLib :: SSMLib :: publicKeyFileName: " + publicKeyFileName);
        System.out.println("SSMLib :: SSMLib :: privateKeyFileName: " + privateKeyFileName);
        System.out.println("SSMLib :: SSMLib :: username: " + username);
        System.out.println("SSMLib :: SSMLib :: password: " + password);
    }

    public SSMLib privateKeyFileName(String privateKeyFileName) {
        this.privateKeyFileName = privateKeyFileName;
        System.out.println("SSMLib :: SSMLib :: privateKeyFileName: " + privateKeyFileName);
        return this;
    }

    public SSMLib publicKeyFileName(String publicKeyFileName) {
        this.publicKeyFileName = publicKeyFileName;
        System.out.println("SSMLib :: SSMLib :: publicKeyFileName: " + publicKeyFileName);
        return this;
    }

    public SSMLib username(String username) {
        this.username = username;
        return this;
    }

    public SSMLib password(String password) {
        this.password = password;
        return this;
    }

    public boolean generateKeyPair() {
        boolean armor = false;
        boolean isGenerated = false;
        if (this.publicKeyFileName.equals("")) {
            return false;
        } else if (this.privateKeyFileName.equals("")) {
            return false;
        } else {
            try {
                KeyStore keyStore = new KeyStore("pgp1.keystore", "changeit");
                keyStore.generateKeyPair(2048, this.username, this.password);
                keyStore.exportPrivateKey(this.privateKeyFileName, this.username, armor);
                keyStore.exportPublicKey(this.publicKeyFileName, this.username, armor);
                isGenerated = true;
            } catch (Exception var4) {
                System.out.println("SSMLib :: MessageProcessor :: generateKeyPair () :: Error Occurred ...");
            }

            return isGenerated;
        }
    }

    public String encryptMessage(String text) {
        String encryptedMessage = "";
        int start = 0;
        int len = 1024;
        int end = len;
        int intDiv = text.length() / len;
        int divRemainder = text.length() % len;

        String tempEncryptedData;
        for(int i = 0; i < intDiv; ++i) {
            tempEncryptedData = text.substring(start, end);
            tempEncryptedData = this.encryptMessage_1(tempEncryptedData);
            System.out.println(tempEncryptedData);
            if (tempEncryptedData.equals("***Error***")) {
                encryptedMessage = "";
                break;
            }

            encryptedMessage = encryptedMessage + tempEncryptedData;
            encryptedMessage = encryptedMessage + ";";
            start = end;
            end += len;
        }

        if (divRemainder != 0) {
            end = start + divRemainder;
            String newText = text.substring(start, end);
            tempEncryptedData = this.encryptMessage_1(newText);
            if (tempEncryptedData.equals("***Error***")) {
                encryptedMessage = "";
            } else {
                encryptedMessage = encryptedMessage + tempEncryptedData;
                encryptedMessage = encryptedMessage + ";";
            }
        }

        return encryptedMessage;
    }

    private String encryptMessage_1(String text) {
        String encryptedMessage;
        encryptedMessage = newEncryptionAlgorithm(text);
        return encryptedMessage;
    }

    private String newEncryptionAlgorithm(String text) {
        // create an instance of the library
        PGPLib pgp = new PGPLib();
        String encryptedMessage = " ";

        System.out.println(publicKeyFileName);
        try (InputStream publicEncryptionKeyStream = classLoader.getResourceAsStream(publicKeyFileName)) {
            encryptedMessage =  pgp.encryptString(text, publicEncryptionKeyStream);
        } catch (IOException e){
            e.getMessage();
        } catch (PGPException e) {
            throw new RuntimeException(e);
        }
        return encryptedMessage;
    }

    private String oldEncryptionAlgorithm(String text, String encryptedMessage) {
        try {
            PGPLib pgp = new PGPLib();
            boolean armor = false;
            boolean withIntegrityCheck = false;
            PipedInputStream pin = new PipedInputStream();
            OutputStream o = new PipedOutputStream(pin);
            InputStream iStream = new ByteArrayInputStream(text.getBytes("UTF-8"));
            InputStream publicKeyStream = new FileInputStream(this.publicKeyFileName);
            pgp.encryptStream(iStream, this.publicKeyFileName, publicKeyStream, o, armor, withIntegrityCheck);

            while(pin.available() <= 0) {
            }

            byte[] body = new byte[pin.available()];
            pin.read(body);
            encryptedMessage = this.byte2hex(body);
        } catch (Exception var11) {
            System.out.println("SSMLib :: encryptMessage () :: Error Occurred ...\n\r" + var11.getMessage());
        }
        return encryptedMessage;
    }

    public String decryptFile(String text) {
        String decryptedMessage = "";

        for(StringTokenizer sToken = new StringTokenizer(text, ";"); sToken.hasMoreTokens(); decryptedMessage = decryptedMessage + this.decryptFile_1(sToken.nextToken(), this.password)) {
        }

        return decryptedMessage;
    }

    private String decryptFile_1(String text, String password) {
        String decryptedMessage = "";
        decryptedMessage = newDecryptionAlgorithm(text, password);

        return decryptedMessage;
    }

    private String newDecryptionAlgorithm(String text, String password) {
        // create instance of the library
        PGPLib pgp = new PGPLib();
        String decryptedText = "";
        ByteArrayOutputStream decryptedStream = new ByteArrayOutputStream();

        // obtain an encrypted data stream
        try {
                InputStream encryptedStream = new ByteArrayInputStream(text.getBytes());
                InputStream privateKeyStream = classLoader.getResourceAsStream(privateKeyFileName);

            pgp.decryptStream(encryptedStream,
                    privateKeyStream,
                    password,
                    decryptedStream);

            decryptedStream.write(decryptedText.getBytes());

        } catch (IOException e){
            e.getMessage();
        } catch (PGPException e) {
            throw new RuntimeException(e);
        }
        return decryptedStream.toString();
    }

    private String oldDecryptionAlgorithm(String text, String password, String decryptedMessage) {
        byte[] bytText = this.hex2byte(text);

        try {
            PGPLib pgp = new PGPLib();
            InputStream iStream = new ByteArrayInputStream(bytText);
            InputStream privateKeyStream = new FileInputStream(this.privateKeyFileName);
            PipedInputStream pin = new PipedInputStream();
            OutputStream oStream = new PipedOutputStream(pin);
            pgp.decryptStream(iStream, privateKeyStream, password, oStream);

            while(pin.available() <= 0) {
            }

            byte[] body = new byte[pin.available()];
            pin.read(body);
            decryptedMessage = (new String(body)).toString();
        } catch (Exception var11) {
            System.out.println("SSMLib :: decryptFile () :: Error Occurred ...\n\r" + var11.getMessage());
            var11.printStackTrace();
        }
        return decryptedMessage;
    }

    private String byte2hex(byte[] b) {
        StringBuilder d = new StringBuilder(b.length * 2);

        for(int i = 0; i < b.length; ++i) {
            char hi = Character.forDigit(b[i] >> 4 & 15, 16);
            char lo = Character.forDigit(b[i] & 15, 16);
            d.append(Character.toUpperCase(hi));
            d.append(Character.toUpperCase(lo));
        }

        return d.toString();
    }

    private byte[] hex2byte(byte[] b, int offset, int len) {
        byte[] d = new byte[len];

        for(int i = 0; i < len * 2; ++i) {
            int shift = i % 2 == 1 ? 0 : 4;
            int tmp37_36 = i >> 1;
            d[tmp37_36] = (byte)(d[tmp37_36] | Character.digit((char)b[offset + i], 16) << shift);
        }

        return d;
    }

    private byte[] hex2byte(String s) {
        return this.hex2byte(s.getBytes(), 0, s.length() >> 1);
    }

    public void setPrivateKeyFileName(String privateKeyFileName) {
        this.privateKeyFileName = privateKeyFileName;
        System.out.println("SSMLib :: SSMLib :: privateKeyFileName: " + privateKeyFileName);
    }

    public void setPublicKeyFileName(String publicKeyFileName) {
        this.publicKeyFileName = publicKeyFileName;
        System.out.println("SSMLib :: SSMLib :: publicKeyFileName: " + publicKeyFileName);
    }

    public SSMLib setUsername(String username) {
        this.username = username;
        System.out.println("SSMLib :: SSMLib :: username: " + username);
        return this;
    }

    public SSMLib setPassword(String password) {
        this.password = password;
        System.out.println("SSMLib :: SSMLib :: password: " + password);
        return this;
    }
}
