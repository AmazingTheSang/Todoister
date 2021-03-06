package com.bawp.todoister.util;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;


import com.bawp.todoister.Data.TaskDao;
import com.bawp.todoister.Model.Task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Task.class},version = 1,exportSchema = false)
@TypeConverters({Converter.class})// type convert
public abstract class TaskRoomDatabase extends RoomDatabase {
    public static final int NUMBER_OF_THREADS = 4;
    public static final String DATABASE_NAME = "todoister_database";
    private static volatile TaskRoomDatabase INSTANCE;
    public static final ExecutorService databaseWriteExecutor // dung de chay bat dong bo cac hanh dong lien quan den database vs so luong toi da la 4.
            = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    public static final Callback sRoomDatabaseCallback=new Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(()->{
                //invoke dao, and write
                TaskDao taskDao = INSTANCE.taskDao();
                taskDao.deleteAll();//cleant slate
            });
        }
    };
    public static TaskRoomDatabase getDatabase(final Context context)
    {
        if(INSTANCE==null)
        {
            synchronized (TaskRoomDatabase.class){
                if(INSTANCE==null)
                {
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),
                            TaskRoomDatabase.class,DATABASE_NAME)
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    public abstract TaskDao taskDao();
}
