package com.example.todo

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import kotlinx.android.synthetic.main.activity_main2.*
import com.google.firebase.ktx.Firebase

class Rejestracja : AppCompatActivity(){
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        textViewSignUpLink.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
        auth = Firebase.auth
        buttonSignIn.setOnClickListener {
            val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)

            if (editTextEmail.text.toString().isNullOrEmpty() || editTextPassword.text.toString()
                            .isNullOrEmpty())
                textViewResponse.text = "nie podano adresu e-mail ani hasła"
            else {
                auth.createUserWithEmailAndPassword(
                        editTextEmail.text.toString(),
                        editTextPassword.text.toString())
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                textViewResponse.text =
                                        "Rejestracja przebiegła pomyślnie!"
                                val user = auth.currentUser
                                updateUI(user)
                            } else {
                                textViewResponse.text = "Rejestracja nie udana"
                                updateUI(null)
                            }
                        }
            }
        }
    }


    fun updateUI(currentUser: FirebaseUser?) {

    }
}

