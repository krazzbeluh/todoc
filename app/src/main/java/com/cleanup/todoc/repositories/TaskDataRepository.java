package com.cleanup.todoc.repositories;

import android.arch.lifecycle.LiveData;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskDataRepository {
    private final TaskDao taskDao;

    public TaskDataRepository(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public LiveData<List<Task>> getTasks() {
        return this.taskDao.getTasks();
    }

    public void insertTask(Task task) {
        this.taskDao.insertTask(task);
    }

    public void deleteTask(long taskId) {
        this.taskDao.deleteItem(taskId);
    }
}
