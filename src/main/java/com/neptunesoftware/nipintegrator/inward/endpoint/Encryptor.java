package com.neptunesoftware.nipintegrator.inward.endpoint;

import com.neptunesoftware.nipintegrator.NIP.security.NIPCredential;
import com.neptunesoftware.nipintegrator.NIP.security.SSMLib;
import org.springframework.stereotype.Service;


public class Encryptor {
    private static   NIPCredential nipCredential;

    public static SSMLib encryptor = new SSMLib()
            .setUsername(NIPCredential.getUsername())
            .setPassword(NIPCredential.getPassword());

}
