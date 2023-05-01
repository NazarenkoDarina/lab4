package com.example.lab4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab4.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(), ItemsAdapter.Listener {
    lateinit var binding: ActivityMainBinding
    private val adapter = ItemsAdapter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            init()
        }
        binding.plusButton.setOnClickListener {
            val intent = Intent(this, AddTask::class.java)
            startActivity(intent)

        }


    }

    private suspend fun init() {
        binding.apply {
            rcView.layoutManager = LinearLayoutManager(this@MainActivity)
            rcView.adapter = adapter
            val db = MainDB.getDB(this@MainActivity)
            db.getDao().getAllItems().asLiveData().observe(this@MainActivity) {
                it.forEach {
                    adapter.addItem(it)
                }
            }
        }

    }


    override fun onClick(item: Item) {
        intent=Intent(this, DeleteTask::class.java)
        val arrStr= arrayListOf<String>(item.name,item.desc,item.id.toString())
        intent.putExtra("item",arrStr)
        startActivityForResult(intent,0)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val dialog= AlertDialog.Builder(this)
            .setTitle("Поздравляем!")
            .setMessage("Задача выполнена")
            .setPositiveButton("Ок"){ _, _ ->

            }
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                val id = intent.extras?.getStringArrayList("item")?.get(2)
                val db = MainDB.getDB(this)
                Thread {
                    if (id != null) {
                        db.getDao().deleteItem(db.getDao().getElem(id.toInt()))
                    }

                }.start()
                if (id != null) {
                    adapter.deleteItem(id)
                }

            }
        }
        dialog.show()
    }
}