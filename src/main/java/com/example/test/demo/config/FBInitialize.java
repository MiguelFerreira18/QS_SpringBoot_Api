package com.example.test.demo.config;

import com.google.auth.oauth2.GoogleCredentials;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.stereotype.Service;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.net.ServerSocket;

@Service
public class FBInitialize {
    private final ServerProperties serverProperties;

    public FBInitialize(ServerProperties serverProperties) {
        this.serverProperties = serverProperties;
    }

    @PostConstruct
    public void Fbinit() {
        int port = 0;
        try{
            port = serverProperties.getPort();
            System.out.println(port + " port ESTA È A MINHA PORTA PAH");
        }catch(Exception e){
            port= 8080;
        }


        if (port== 8080) {
            try {
                FileInputStream serviceAccount = new FileInputStream("serviceAccountKeyTesting.json");

                FirebaseOptions options = new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .setDatabaseUrl("firebase-adminsdk-r2dfq@methodstest-cf1a6.iam.gserviceaccount.com")
                        .build();

                FirebaseApp.initializeApp(options);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            try {
                FileInputStream serviceAccount = new FileInputStream("serviceAccountKey.json");

                FirebaseOptions options = new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .setDatabaseUrl("firebase-adminsdk-r2dfq@methodstest-cf1a6.iam.gserviceaccount.com")
                        .build();

                FirebaseApp.initializeApp(options);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
