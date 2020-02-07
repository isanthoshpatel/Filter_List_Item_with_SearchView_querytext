package com.example.filter_list_item_with_searchview_querytext

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.widget.GridView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var list: MutableList<CardviewData>
    lateinit var adaptor: RVAdaptor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        list = mutableListOf()


      Thread(object : Runnable {
            override fun run() {
                var i = 1
                while (i > 0) {
                    list.clear()
                    list.apply {
                        add(CardviewData("this is title1", "this is description 2"))
                        add(CardviewData("India", "NewDelhi"))
                        add(CardviewData("Indonasia", "Jakartala"))
                        add(CardviewData("Japan", "Tokyo"))
                        add(CardviewData("China", "Beging"))
                        add(CardviewData("America", "Washington"))
                        add(CardviewData("Uk", "London"))
                        add(CardviewData("France", "Paris"))
                    }
                    runOnUiThread {
                        Toast.makeText(this@MainActivity,"$i",Toast.LENGTH_SHORT).show()
                    }
                    SystemClock.sleep(6000)
                    i++
                }

            }
        }).start()

        rv.setHasFixedSize(true)
        rv.layoutManager =
            GridLayoutManager(this@MainActivity, 1, GridLayoutManager.VERTICAL, false)
        adaptor = RVAdaptor(this@MainActivity,list)
       // adaptor.submitList(list)
        rv.adapter = adaptor



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        var item = menu?.findItem(R.id.searchview)
        var sv = item!!.actionView as SearchView

        sv.imeOptions = EditorInfo.IME_ACTION_DONE
        sv.setOnCloseListener(object : SearchView.OnCloseListener {
            override fun onClose(): Boolean {

                return false
            }

        })
        sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adaptor.filter.filter(newText)
                return false
            }
        })
        return true

    }
}
