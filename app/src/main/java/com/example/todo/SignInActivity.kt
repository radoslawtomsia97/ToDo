package com.example.todo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.logowanie.*

class SignInActivity : AppCompatActivity() {
    private lateinit var  auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        setContentView(R.layout.logowanie)
        textViewSignUpLink.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        buttonSignIn.setOnClickListener {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
            if ( editTextEmail.text.toString().isNullOrEmpty() || editTextPassword.text.toString().isNullOrEmpty())
                textViewResponse.text = "Adres email lub hasło nie zgadzają się"
            else {
                auth.signInWithEmailAndPassword(editTextEmail.text.toString(),editTextPassword.text.toString())
                        .addOnCompleteListener(this) { task ->
                           if (task.isSuccessful) {
                               textViewResponse.text = "Udało się zalogować"
                               val user = auth.currentUser
                               updateUI(user, editTextEmail.text.toString() )
                           } else
                               textViewResponse.text = "Błędny login lub hasło"
                        }
            }
        }
    }
    private fun updateUI(currentUser: FirebaseUser?, emailAdd: String) {
        if (currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("emailAddress", emailAdd);
            startActivity(intent)
        }
    }
}
