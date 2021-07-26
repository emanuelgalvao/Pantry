package com.emanuelgalvao.pantry.service.repository

import android.content.Context
import com.emanuelgalvao.pantry.service.listener.ApiListener
import com.emanuelgalvao.pantry.service.model.User
import com.emanuelgalvao.pantry.service.repository.local.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class UserRepository(val context: Context)  {

    private val mFirebaseAuth: FirebaseAuth = Firebase.auth
    private val mFirebaseDatabase = Firebase.database.reference
    private val mSharedPreferences = SharedPreferences(context)

    fun verifySignedInUser(listener: ApiListener<User>) {

        val currentUser = mFirebaseAuth.currentUser

        if(currentUser != null){
            val user = User()
            user.uid = currentUser.uid
            user.email = currentUser.email.toString()
            user.name = mSharedPreferences.get("name")

            listener.onSucess(user)
        } else {
            listener.onFailure("Usuário não logado.")
        }
    }

    fun getUserName(): String = mSharedPreferences.get("name")

    fun login(email: String, password: String, listener: ApiListener<User>) {

        mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = User()
                user.uid = mFirebaseAuth.currentUser?.uid.toString()
                user.email = mFirebaseAuth.currentUser?.email.toString()

                mFirebaseDatabase.child("users").child(user.uid).child("name").get().addOnCompleteListener {
                    user.name = it.result?.value.toString()

                    mSharedPreferences.store("uid", user.uid)
                    mSharedPreferences.store("email", user.email)
                    mSharedPreferences.store("name", user.name)

                    listener.onSucess(user)
                }
            } else {
                listener.onFailure(task.exception?.message.toString())
            }
        }
    }

    fun register(name: String, email: String, password: String, listener: ApiListener<User>) {

        mFirebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = User()
                user.uid = mFirebaseAuth.currentUser?.uid.toString()
                user.email = mFirebaseAuth.currentUser?.email.toString()
                user.name = name
                mFirebaseDatabase.child("users").child(user.uid).child("name").setValue(name).addOnCompleteListener {
                    mSharedPreferences.store("uid", user.uid)
                    mSharedPreferences.store("email", user.email)
                    mSharedPreferences.store("name", user.name)
                    listener.onSucess(user)
                }
            } else {
                listener.onFailure(task.exception?.message.toString())
            }
        }
    }

    fun updateName(name: String, listener: ApiListener<User>) {
        val currentUser = mFirebaseAuth.currentUser

        val user = User()
        user.uid = mFirebaseAuth.currentUser?.uid.toString()
        user.email = mFirebaseAuth.currentUser?.email.toString()
        user.name = name

        if (name != "") {
            mFirebaseDatabase.child("users").child(currentUser!!.uid).child("name").setValue(name).addOnCompleteListener {
                mSharedPreferences.store("name", name)
                listener.onSucess(user)
            }
        } else {
            listener.onFailure("Ocorreu um erro ao atualizar os dados. Tente novamente.")
        }
    }

    fun logout() {
        mSharedPreferences.remove("uid")
        mSharedPreferences.remove("email")
        mSharedPreferences.remove("name")
        mFirebaseAuth.signOut()
    }
}