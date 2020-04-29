package com.paulleclerc.todoc.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;
import com.paulleclerc.todoc.database.dao.ProjectDao;
import com.paulleclerc.todoc.database.dao.TaskDao;
import com.paulleclerc.todoc.model.Project;
import com.paulleclerc.todoc.model.Task;

@Database(entities = {Task.class, Project.class}, version = 1, exportSchema = false)
public abstract class TodocDatabase extends RoomDatabase {
    private static volatile TodocDatabase INSTANCE;
    public static final String DB_NAME = "Todoc.database";

    public abstract TaskDao taskDao();
    public abstract ProjectDao projectDao();

    public static TodocDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (TodocDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TodocDatabase.class, DB_NAME)
                            .addCallback(prepopulateDatabase())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static Callback prepopulateDatabase() {
        return new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                ContentValues[] contentValues = new ContentValues[]{new ContentValues(), new ContentValues(), new ContentValues()};
                contentValues[0].put("id", 1);
                contentValues[0].put("name", "Projet Tartampion");
                contentValues[0].put("color", 0xFFEADAD1);
                contentValues[1].put("id", 2);
                contentValues[1].put("name", "Projet Lucidia");
                contentValues[1].put("color", 0xFFB4CDBA);
                contentValues[2].put("id", 3);
                contentValues[2].put("name", "Projet Circus");
                contentValues[2].put("color", 0xFFA3CED2);

                for (ContentValues values : contentValues) {
                    db.insert("Project", OnConflictStrategy.IGNORE, values);
                }
            }
        };
    }
}