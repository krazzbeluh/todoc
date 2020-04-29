package com.paulleclerc.todoc.dbTests;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import com.paulleclerc.todoc.database.TodocDatabase;
import com.paulleclerc.todoc.model.Project;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class ProjectDaoTest {
    private TodocDatabase database;
    private static final long projectId = 1;
    private static final Project projectDemo = new Project(projectId, "Project Demo", 0xFFFFFF);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() {
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), TodocDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    @Test
    public void getAllWhenNoProjectInserted() throws InterruptedException {
        List<Project> projects = LiveDataTestUtil.getValue(this.database.projectDao().getAllProjects());
        assertTrue(projects.isEmpty());
    }

    @Test
    public void getProjectWithUnknownId() throws InterruptedException {
        Project project = LiveDataTestUtil.getValue(this.database.projectDao().getProject(42));
        assertNull(project);
    }

    @Test
    public void insertAndGetProject() throws InterruptedException {
        this.database.projectDao().insertProject(projectDemo);

        Project project = LiveDataTestUtil.getValue(this.database.projectDao().getProject(projectId));
        assertTrue(project.getName().equals(projectDemo.getName()) && project.getId() == projectDemo.getId() && project.getColor() == projectDemo.getColor());
    }

    @Test
    public void getAllProjects() throws InterruptedException {
        this.database.projectDao().insertProject(projectDemo);

        List<Project> projects = LiveDataTestUtil.getValue(this.database.projectDao().getAllProjects());
        assertEquals(projects.size(), 1);
        assertTrue(projects.get(0).getName().equals(projectDemo.getName()) && projects.get(0).getId() == projectDemo.getId() && projects.get(0).getColor() == projectDemo.getColor());
    }

    @After
    public void closeDb() {
        database.close();
    }
}
