package krist.car.api

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import javax.inject.Inject

interface FirebaseApiInterface{
    fun getDatabaseReferenceToThisEndPoint(endPoint: String): DatabaseReference
    fun getStorageReferenceToThisEndPoint(endPoint: String) : StorageReference
    fun signInWithUsernameAndPass(email: String, password: String) : Task<AuthResult>
    fun createUserWithEmailAndPassword(email: String, password: String) : Task<AuthResult>
}

class FirebaseApi @Inject constructor(
        private val firebaseStorage: FirebaseStorage,
        private val firebaseAuth: FirebaseAuth,
        private val firebaseDatabase: FirebaseDatabase) : FirebaseApiInterface
{
    override fun signInWithUsernameAndPass(email: String, password: String): Task<AuthResult> =
            firebaseAuth.signInWithEmailAndPassword(email, password)

    override fun createUserWithEmailAndPassword(email: String, password: String): Task<AuthResult> =
            firebaseAuth.createUserWithEmailAndPassword(email, password)

    override fun getDatabaseReferenceToThisEndPoint(endPoint: String): DatabaseReference =
            firebaseDatabase.getReference(endPoint)

    override fun getStorageReferenceToThisEndPoint(endPoint: String): StorageReference =
            firebaseStorage.getReference(endPoint)
}