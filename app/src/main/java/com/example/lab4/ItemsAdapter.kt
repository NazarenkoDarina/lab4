package com.example.lab4

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lab4.databinding.TaskItemBinding

class ItemsAdapter(private val listener:Listener): RecyclerView.Adapter<ItemsAdapter.TaskScreen>() {
    private val tasksList= ArrayList<Item>()
    class TaskScreen(item:View): RecyclerView.ViewHolder(item) {
        private val binding = TaskItemBinding.bind(item)
        fun bind(item:Item, listener:Listener) = with(binding){
            nameTask.text=item.name
            descTask.text=item.desc
            itemView.setOnClickListener{
                listener.onClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskScreen {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return TaskScreen(view)
    }

    override fun getItemCount(): Int {
        return tasksList.size
    }

    override fun onBindViewHolder(holder: TaskScreen, position: Int) {
        holder.bind(tasksList[position],listener)
    }
    fun addItem(item:Item)
    {
        tasksList.add(item)
        notifyDataSetChanged()
    }

    interface Listener{
        fun onClick(item:Item)
    }

    fun deleteItem(id:String){
        val intId=id.toInt()
        for (task in tasksList)
        {
            if (task.id==intId)
            {
                tasksList.remove(task)
                notifyItemRemoved(task.id!!)
            }
        }

    }
}