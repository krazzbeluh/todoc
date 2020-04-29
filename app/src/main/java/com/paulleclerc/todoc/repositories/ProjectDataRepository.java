package com.paulleclerc.todoc.repositories;

import android.arch.lifecycle.LiveData;
import com.paulleclerc.todoc.database.dao.ProjectDao;
import com.paulleclerc.todoc.model.Project;

import java.util.List;

public class ProjectDataRepository {
    private final ProjectDao projectDao;

    public ProjectDataRepository(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    public LiveData<List<Project>> getAllProjects() {
        return this.projectDao.getAllProjects();
    }
}
