package com.example.todo

import android.os.Bundle
import android.util.SparseBooleanArray
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var itemlist = arrayListOf<String>()
        var adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, itemlist)


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
            android.widget.Toast.makeText(
                this,
                "Wybrałeś zadanie: " + itemlist.get(i),
                android.widget.Toast.LENGTH_SHORT
            ).show()
        }

    }

}
