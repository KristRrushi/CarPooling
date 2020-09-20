package krist.car;

import androidx.recyclerview.widget.ItemTouchHelper;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Database {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();

    public DatabaseReference getmDatabaseRefUsers() {
         return database.getReference("users");
    }

    public StorageReference getStorage() {
        return storage.getReference("uploads");
    }

    public DatabaseReference getDatabaserefTrips(){
        return database.getReference("trips");
    }

    public DatabaseReference getDatebaseImage() {return database.getReference("imageUploads");}

}

