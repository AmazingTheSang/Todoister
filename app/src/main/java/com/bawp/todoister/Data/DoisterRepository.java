package com.bawp.todoister.Data;

import android.app.Application;

import androidx.lifecycle.LiveData;


import com.bawp.todoister.Model.Task;
import com.bawp.todoister.util.TaskRoomDatabase;

import java.util.List;

public class DoisterRepository { //tong hop cac nguon du lieu can thiet trong truong hop app co hon 1 nguon du lieu.
    private final TaskDao taskDao;
    private final LiveData<List<Task>> allTasks;

    public DoisterRepository(Application application) {
        TaskRoomDatabase database = TaskRoomDatabase.getDatabase(application);
        this.taskDao = database.taskDao();
        this.allTasks = taskDao.getTasks();
    }
    public LiveData<List<Task>> getAllTasks(){
        return allTasks;
    }
    public void insert(Task task){
        TaskRoomDatabase.databaseWriteExecutor.execute(()->taskDao.insertTask(task));
    }
    public LiveData<Task> get(long id){return taskDao.get(id);}
    public void update(Task task){
        TaskRoomDatabase.databaseWriteExecutor.execute(()->taskDao.update(task));
    }
    public void delete(Task task){
        TaskRoomDatabase.databaseWriteExecutor.execute(()->taskDao.delete(task));
    }
}
