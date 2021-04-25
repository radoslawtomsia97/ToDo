package com.example.todo

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


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = intent
        val receivedEmail = intent.getStringExtra("emailAddress")
        textViewWelcome.text="Witaj "+receivedEmail

        val itemlist = arrayListOf<String>()
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, itemlist)


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

}
