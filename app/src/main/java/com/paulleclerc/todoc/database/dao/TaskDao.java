package com.paulleclerc.todoc.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import com.paulleclerc.todoc.model.Task;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM Task WHERE 1")
    LiveData<List<Task>> getTasks();

    @Insert
    void insertTask(Task task);

    @Query("DELETE FROM Task WHERE id = :id")
    void deleteItem(long id);
}
