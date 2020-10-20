package krist.car.api;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ApiModule {
    private static ApiModule apiInstance;
    public FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;
    private FirebaseStorage storage;

    private ApiModule(){
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
    }

    public static ApiModule getInstance() {
        if(apiInstance == null) {
            apiInstance = new ApiModule();
        }
        return apiInstance;
    }

    public DatabaseReference getDatebaseReferenceToThisEndPoint(String endPoint) {
        return database.getReference(endPoint);
    }

    public StorageReference getFirebaseStorageToThisEndPoint(String endPoint) {
        return storage.getReference(endPoint);
    }

    public String getUserUId() {
        return firebaseAuth.getUid();
    }
}
