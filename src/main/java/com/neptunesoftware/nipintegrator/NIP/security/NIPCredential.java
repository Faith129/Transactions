package com.neptunesoftware.nipintegrator.NIP.security;

import com.neptunesoftware.nipintegrator.NIP.NIPInfoData;
import com.neptunesoftware.nipintegrator.utilities.CommonMethods;
import com.neptunesoftware.nipintegrator.utilities.Marshaller;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class NIPCredential {

    private static String nibssPublicKey;
    private static String username;
    private static String password;
    private static String bankCode;
    private static String transferLimit;
    private static String wsdlUrl;
    private static String tsqWsdl;

    static ClassLoader classLoader = NIPCredential.class.getClassLoader();

    static {
        nibssPublicKey = (readNIPConfig().getPublicKeyName()).replaceAll("%20", " ");

        System.out.println("WHICH KEY IS THIS " + nibssPublicKey);

        nibssPublicKey = nibssPublicKey.startsWith("/") ? nibssPublicKey.substring(1) : nibssPublicKey;

        NIPInfoData nipInfoData = decryptContent(readNIPConfig());
        username = nipInfoData.getUsername();
        password = nipInfoData.getPassword();
        bankCode  = nipInfoData.getBankCode();
        transferLimit = nipInfoData.getTransferLimit();
        wsdlUrl = nipInfoData.getWsdlURL();
        tsqWsdl = nipInfoData.getTsqWsdl();
    }


    private static NIPInfoData readNIPConfig() {
        NIPInfoData nipInfoData = new NIPInfoData();

        try (InputStream inputStream = classLoader.getResourceAsStream("NIPInfo.xml")) {
            assert inputStream != null;
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
                StringBuilder builder = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    builder.append(line);
                }
               // System.out.println(builder);
                nipInfoData = Marshaller.convertXmlStringToObject(builder.toString(), nipInfoData);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return nipInfoData;
    }


    private static NIPInfoData decryptContent(final NIPInfoData nipCredential) {

        try {
            String username = CypherCrypt.deCypher(nipCredential.getUsername().trim()) == null
                    || CypherCrypt.deCypher(nipCredential.getUsername().trim()).equals("") ? nipCredential.getUsername()
                    : CypherCrypt.deCypher(nipCredential.getUsername());

            String password = CypherCrypt.deCypher(nipCredential.getPassword().trim()) == null
                    || CypherCrypt.deCypher(nipCredential.getPassword().trim()).equals("") ? nipCredential.getPassword()
                    : CypherCrypt.deCypher(nipCredential.getPassword());

            String bankCode = CypherCrypt.deCypher(nipCredential.getBankCode().trim()) == null
                    || CypherCrypt.deCypher(nipCredential.getBankCode().trim()).equals("") ? nipCredential.getBankCode()
                    : CypherCrypt.deCypher(nipCredential.getBankCode());

            String publicKeyName = CypherCrypt.deCypher(nipCredential.getPublicKeyName().trim()) == null
                    || CypherCrypt.deCypher(nipCredential.getPublicKeyName().trim()).equals("") ? nipCredential.getPublicKeyName()
                    : CypherCrypt.deCypher(nipCredential.getPublicKeyName());

            String transferLimit = CypherCrypt.deCypher(nipCredential.getTransferLimit().trim()) == null
                    || CypherCrypt.deCypher(nipCredential.getTransferLimit().trim()).equals("") ? nipCredential.getTransferLimit()
                    : CypherCrypt.deCypher(nipCredential.getTransferLimit());

            String wsdlUrl = CypherCrypt.deCypher(nipCredential.getWsdlURL().trim()) == null
                    || CypherCrypt.deCypher(nipCredential.getWsdlURL().trim()).equals("") ? nipCredential.getWsdlURL()
                    : CypherCrypt.deCypher(nipCredential.getWsdlURL());

            String tsqWsdl = CypherCrypt.deCypher(nipCredential.getTsqWsdl().trim()) == null
                    || CypherCrypt.deCypher(nipCredential.getTsqWsdl().trim()).equals("") ? nipCredential.getTsqWsdl()
                    : CypherCrypt.deCypher(nipCredential.getTsqWsdl());

            nipCredential.setUsername(username);
            nipCredential.setPassword(password);
            nipCredential.setBankCode(bankCode);
            nipCredential.setPublicKeyName(publicKeyName);
            nipCredential.setTransferLimit(transferLimit);
            nipCredential.setWsdlURL(wsdlUrl);
            nipCredential.setTsqWsdl(tsqWsdl);

            return nipCredential;
        } catch (Exception e) {
            System.out.println("NIPCredential: \n" + CommonMethods.objectToXml(nipCredential));
            System.out.println("Cannot decrypt content");

            return nipCredential;
        }

    }


    public static String getNibssPublicKey() {
        return nibssPublicKey;
    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }

    public static String getBankCode() {
        return bankCode;
    }

    public static String getTransferLimit() {
        return transferLimit;
    }

    public static String getWsdlUrl() {
        return wsdlUrl;
    }

    public static String getTsqWsdl() {
        return tsqWsdl;
    }

}
