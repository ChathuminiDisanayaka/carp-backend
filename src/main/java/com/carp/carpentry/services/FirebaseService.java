package com.carp.carpentry.services;

import com.google.auth.oauth2.GoogleCredentials;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.cloud.StorageClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class FirebaseService {
    private final Firestore firestore;
    private final StorageClient storageClient;

    public FirebaseService() throws IOException, InstantiationException, IllegalAccessException {

        FileInputStream serviceAccount =
                new FileInputStream("src/main/resources/serviceAccountKey.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setStorageBucket("carp-web.appspot.com")
                .build();

        FirebaseApp firebaseApp = FirebaseApp.initializeApp(options);

//        FileInputStream serviceAccountDetails =
//                new FileInputStream("E:\\Damish-Dev\\Web-Projects\\Carp\\carp-backend\\src\\main\\resources\\serviceAccountKey.json");
//        InputStream serviceAccount = serviceAccountDetails.getClass().newInstance();
//        FirebaseOptions options = new FirebaseOptions.Builder()
//                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                .setStorageBucket("gs://carp-web.appspot.com/products")
//                .build();
//        FirebaseApp.initializeApp(options);
        firestore = FirestoreClient.getFirestore();
//
//        // Initialize Storage Client
        storageClient = StorageClient.getInstance();
    }
    public String uploadImageAndSaveToDB(MultipartFile file) throws IOException {
//         Generate a unique filename
        String filename ="products/"+ UUID.randomUUID().toString() + "_" + file.getOriginalFilename();


        String contentType = determineContentType(Objects.requireNonNull(file.getOriginalFilename()));

        storageClient.bucket("carp-web.appspot.com").create(filename, file.getBytes(), contentType);

        // Adjust the document structure as needed

        // Upload image to Firebase Storage
//        BlobId blobId = BlobId.of("carp-web.appspot.com", filename);
//        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
//        Blob blob = storageClient.bucket("carp-web.appspot.com").create(String.valueOf(blobInfo), file.getBytes());
//
//        // Get image URL
//        String imageUrl = storageClient.bucket().get(filename).signUrl(365, TimeUnit.DAYS).toString();

        // Save image URL to Firestore or your preferred database
//        firestore.collection("images").add(imageUrl);

        String string = storageClient.bucket().get(filename).signUrl(1095, TimeUnit.DAYS).toString();
        return string;
    }
    private String determineContentType(String fileName) {
        if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (fileName.endsWith(".png")) {
            return "image/png";
        } else if (fileName.endsWith(".gif")) {
            return "image/gif";
        } else {
            return "application/octet-stream";
        }
    }
}
