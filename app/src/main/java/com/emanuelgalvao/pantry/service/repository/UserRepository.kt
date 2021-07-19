package com.emanuelgalvao.pantry.service.repository

import android.content.Context
import com.emanuelgalvao.pantry.service.listener.FirebaseListener
import com.emanuelgalvao.pantry.service.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class UserRepository(val context: Context)  {

    private val auth: FirebaseAuth = Firebase.auth
    private val mUserDatabase = LocalDatabase.getDatabase(context).userDAO()

    fun verifySignedInUser(listener: FirebaseListener<User>) {

        val currentUser = auth.currentUser

        if(currentUser != null){
            val user = User()
            user.uid = currentUser.uid
            user.email = currentUser.email.toString()
            user.name = currentUser.displayName.toString()

            mUserDatabase.clear()
            mUserDatabase.save(user)

            listener.onSucess(user)
        } else {
            listener.onFailure("Usuário não logado.")
        }
    }

    fun login(email: String, password: String, listener: FirebaseListener<User>) {

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = User()
                user.uid = auth.currentUser?.uid.toString()
                user.email = auth.currentUser?.email.toString()
                user.name = auth.currentUser?.displayName.toString()

                mUserDatabase.clear()
                mUserDatabase.save(user)

                listener.onSucess(user)
            } else {
                listener.onFailure(task.exception?.message.toString())
            }
        }
    }

    fun register(email: String, password: String, listener: FirebaseListener<User>) {

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = User()
                user.uid = auth.currentUser?.uid.toString()
                user.email = auth.currentUser?.email.toString()
                user.name = auth.currentUser?.displayName.toString()
                //Salvar no banco
                listener.onSucess(user)
            } else {
                listener.onFailure(task.exception?.message.toString())
            }
        }
    }

    fun logout() {
        auth.signOut()
    }
}