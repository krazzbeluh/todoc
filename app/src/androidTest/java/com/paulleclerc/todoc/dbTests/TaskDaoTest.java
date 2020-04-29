package com.paulleclerc.todoc.dbTests;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import com.paulleclerc.todoc.database.TodocDatabase;
import com.paulleclerc.todoc.model.Project;
import com.paulleclerc.todoc.model.Task;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TaskDaoTest {
    private TodocDatabase database;

    private static final long projectId = 0;
    private static final Project projectDemo = new Project(projectId, "Project Demo", 0xFFFFFF);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() {
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), TodocDatabase.class)
                .allowMainThreadQueries()
                .build();

        this.database.projectDao().insertProject(projectDemo);
    }

    @Test
    public void insertTaskShouldAddTaskAndRemoveTaskShouldRemove() throws InterruptedException {
        int taskListSize = LiveDataTestUtil.getValue(this.database.taskDao().getTasks()).size();
        this.database.taskDao().insertTask(new Task(0, projectId, "TaskTest", new Date().getTime()));
        List<Task> taskList = LiveDataTestUtil.getValue((this.database.taskDao().getTasks()));
        assertEquals(taskList.size(), taskListSize + 1);

        this.database.taskDao().deleteItem(taskList.get(0).getId());
        taskList = LiveDataTestUtil.getValue((this.database.taskDao().getTasks()));
        assertEquals(taskList.size(), taskListSize);
    }

    @After
    public void closeDb() {
        database.close();
    }
}
