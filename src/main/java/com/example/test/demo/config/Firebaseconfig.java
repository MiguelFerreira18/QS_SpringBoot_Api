package com.example.test.demo.config;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Firebaseconfig {
    
    @Bean
    public Firestore getDb() {
        return FirestoreClient.getFirestore();
    }


}
