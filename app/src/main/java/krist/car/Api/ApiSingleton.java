package krist.car.Api;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ApiSingleton {
    private static ApiSingleton apiInstance;
    public FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;
    private FirebaseStorage storage;

    private ApiSingleton(){
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
    }

    public static ApiSingleton getInstance() {
        if(apiInstance == null) {
            apiInstance = new ApiSingleton();
        }
        return apiInstance;
    }

    public DatabaseReference getDatebaseReferenceToThisEndPoint(String endPoint) {
        return database.getReference(endPoint);
    }

    public StorageReference getFirebaseStorageToThisEndPoint(String endPoint) {
        return storage.getReference(endPoint);
    }
}
