package com.bawp.todoister.Adapter;

import com.bawp.todoister.Model.Task;

public interface OnToDoClickListener {
    void onTodoClick(Task task);
    void onTodoRadioButtonClick(Task task);
}
