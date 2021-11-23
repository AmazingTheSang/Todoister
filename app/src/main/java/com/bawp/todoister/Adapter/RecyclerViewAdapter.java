package com.bawp.todoister.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bawp.todoister.Model.Task;
import com.bawp.todoister.R;
import com.bawp.todoister.util.Utils;
import com.google.android.material.chip.Chip;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private final List<Task> taskList;
    private final OnToDoClickListener toDoClickListener;
    public RecyclerViewAdapter(List<Task> taskList, OnToDoClickListener onToDoClickListener) {
        this.taskList = taskList;
        this.toDoClickListener = onToDoClickListener;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        Task task = taskList.get(position);
        String formatted = Utils.formatDate(task.getDueDate());
        holder.task.setText(task.getTask());
        holder.todayChip.setText(formatted);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public AppCompatRadioButton radioButton;
        public AppCompatTextView task;
        public Chip todayChip;
        OnToDoClickListener onToDoClickListener;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            radioButton = itemView.findViewById(R.id.todo_radio_button);
            task = itemView.findViewById(R.id.todo_row_todo);
            todayChip = itemView.findViewById(R.id.todo_row_chip);
            this.onToDoClickListener = toDoClickListener;
            itemView.setOnClickListener(this);
            radioButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            Task currTask = taskList.get(getAdapterPosition());
            if(id == R.id.todo_row_layout){
                onToDoClickListener.onTodoClick(getAdapterPosition(),currTask);
            }
            else if(id == R.id.todo_radio_button){
                onToDoClickListener.onTodoRadioButtonClick(currTask);
            }
        }
    }
}
