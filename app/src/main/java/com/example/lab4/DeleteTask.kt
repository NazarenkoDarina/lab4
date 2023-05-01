package com.example.lab4

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.example.lab4.databinding.ActivityDeleteTaskBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DeleteTask : AppCompatActivity() {
    private lateinit var binding: ActivityDeleteTaskBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDeleteTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val arr = intent.getStringArrayListExtra("item")
        if (arr != null) {
            binding.name.text= arr.get(0)
            binding.desc.text=arr.get(1)
        }


        binding.button.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            val db = MainDB.getDB(this)
            Thread{
                if (arr != null) {
                    if(arr.get(1).toString()==null){
                        arr[1]
                    }
                    val item=db.getDao().getElem(arr.get(2).toInt())
                    intent.putExtra("item", item.id.toString())
                }
            }.start()

            setResult(Activity.RESULT_OK, intent)
            finish()

//            dialog.show()
        }

        binding.backButton.setOnClickListener{
            finish()
        }
    }


}