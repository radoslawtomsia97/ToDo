package com.example.todo

import android.content.Context
import android.os.Bundle
import android.util.SparseBooleanArray
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.Dodaj
import kotlinx.android.synthetic.main.activity_main2.*
import com.google.firebase.ktx.Firebase
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = Firebase.auth
        var itemlist = arrayListOf<String>()
        var adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, itemlist)
        buttonSignUp.setOnClickListener {
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

        Dodaj.setOnClickListener {
            itemlist.add(wpisz.text.toString())
            wyswietlanie.adapter = adapter
            adapter.notifyDataSetChanged()
            wpisz.text.clear()
        }

        Usun.setOnClickListener {
            val position: SparseBooleanArray = wyswietlanie.checkedItemPositions
            val count = wyswietlanie.count
            var item = count - 1
            while (item >= 0) {
                if (position.get(item)) {
                    adapter.remove(itemlist.get(item))
                }
                item--
            }
            position.clear()
            adapter.notifyDataSetChanged()
        }

        wyswietlanie.setOnItemClickListener { adapterView, view, i, l ->
            Toast.makeText(
                this,
                "Wybrałeś zadanie: " + itemlist.get(i),
                Toast.LENGTH_SHORT
            ).show()
        }

    }
    fun updateUI(currentUser: FirebaseUser?) {

    }
}
