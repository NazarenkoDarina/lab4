package com.example.lab4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import com.example.lab4.databinding.ActivityAddTaskBinding
import com.example.lab4.databinding.ActivityMainBinding

class AddTask : AppCompatActivity() {
    lateinit var binding: ActivityAddTaskBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val db= MainDB.getDB(this)
        val intent= Intent(this@AddTask, MainActivity::class.java)
        binding.button.setOnClickListener {
            if (binding.name.text.toString().trim().isNotEmpty())
            {
                val item =Item(null, binding.name.text.toString(), binding.desc.text.toString())

                Thread{
                    db.getDao().insertItem(item)
                }.start()
                startActivity(intent)
            }
            else{
                var text="Введите обязательные поля!"
                val toast =Toast.makeText(applicationContext,text,Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.TOP or Gravity.CENTER, 0, 0)
                toast.show()
            }
        }

        binding.backButton.setOnClickListener{
            startActivity(intent)
        }
    }
}