package peter.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import interfaces.AttachmentDao;
import interfaces.HomeDao;
import interfaces.LoginResponseDao;
import interfaces.ScholarshipDao;
import interfaces.UserDao;
import interfaces.VacancyDao;
import peter.models.Attachment;
import peter.models.Home;
import peter.models.LoginResponse;
import peter.models.Scholarship;
import peter.models.User;
import peter.models.Vacancy;


@Database(entities = {User.class, LoginResponse.class, Vacancy.class, Attachment.class, Scholarship.class, Home.class}, version = 1)
public abstract class Db extends RoomDatabase {

    private static Db instance;

    public abstract UserDao userDao();
    public abstract LoginResponseDao loginResponseDao();
    public abstract ScholarshipDao scholarshipDao();
    public abstract AttachmentDao attachmentDao();
    public abstract VacancyDao vacancyDao();
    public abstract HomeDao homeDao();


    public static synchronized Db getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    Db.class, "jobs_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }

        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            new PopulateDbUserAsyncTask(instance).execute();
            new PopulateDbVacancyAsyncTask(instance).execute();
            new PopulateDbAttachmentAsyncTask(instance).execute();
            new PopulateDbScholarshipAsyncTask(instance).execute();
            new PopulateDbHomeAsyncTask(instance).execute();
        }
    };


    private static class PopulateDbHomeAsyncTask extends AsyncTask<Void, Void, Void> {
        private HomeDao noteDao;

        private PopulateDbHomeAsyncTask(Db db) {
            noteDao = db.homeDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            return null;
        }
    }
    private static class PopulateDbUserAsyncTask extends AsyncTask<Void, Void, Void> {
        private UserDao noteDao;

        private PopulateDbUserAsyncTask(Db db) {
            noteDao = db.userDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
           // noteDao.insert(new User("Title 1", "","Description 1", "1234"));

            return null;
        }
    }

    private static class PopulateDbVacancyAsyncTask extends AsyncTask<Void, Void, Void> {
        private VacancyDao vacancyDao;

        private PopulateDbVacancyAsyncTask(Db db) {
            vacancyDao = db.vacancyDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            return null;
        }
    }

    private static class PopulateDbAttachmentAsyncTask extends AsyncTask<Void, Void, Void> {
        private AttachmentDao attachmentDao;

        private PopulateDbAttachmentAsyncTask(Db db) {
            attachmentDao = db.attachmentDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            return null;
        }
    }

    private static class PopulateDbScholarshipAsyncTask extends AsyncTask<Void, Void, Void> {
        private ScholarshipDao scholarshipDao;

        private PopulateDbScholarshipAsyncTask(Db db) {
            scholarshipDao = db.scholarshipDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            return null;
        }
    }


}

/*
*       "email": "peter@gmail.com",
        "first_name": "Peter",
        "id": 1,
        "last_name": "Njenga",
        "username": "Peter Njenga"*/
