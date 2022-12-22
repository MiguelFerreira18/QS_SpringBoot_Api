package com.example.test.demo.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;

@Service
public class FBInitialize {


    @PostConstruct
    public void Fbinit() {
        boolean isTesting = true;//*?ALTERAR ESTA PARTE AQUI PARA TROCAR A BASE DE DADOS OU PARA TESTE OU PARA PRODUÇÃO?*/

        if (isTesting) {
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
