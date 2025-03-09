package com.example.moim.external.fcm;

import org.springframework.context.annotation.Configuration;

@Configuration
public class FcmConfig {

//    @Bean
//    FirebaseMessaging firebaseMessaging() throws IOException {
//        ClassPathResource resource = new ClassPathResource("firebase/meta-gachon-fcm-firebase-adminsdk-fyr7b-30929b486f.json");
//        InputStream refreshToken = resource.getInputStream();
//
//        FirebaseApp firebaseApp = null;
//        List<FirebaseApp> firebaseAppList = FirebaseApp.getApps();
//
//        if (firebaseAppList != null && !firebaseAppList.isEmpty()) {
//            for (FirebaseApp app : firebaseAppList) {
//                if (app.getName().equals(FirebaseApp.DEFAULT_APP_NAME)) {
//                    firebaseApp = app;
//                }
//            }
//        } else {
//            FirebaseOptions options = FirebaseOptions.builder()
//                    .setCredentials(GoogleCredentials.fromStream(refreshToken))
//                    .build();
//
//            firebaseApp = FirebaseApp.initializeApp(options);
//        }
//
//        return FirebaseMessaging.getInstance(firebaseApp);
//    }
}
