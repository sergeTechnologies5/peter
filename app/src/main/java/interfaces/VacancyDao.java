package interfaces;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import peter.models.Vacancy;


@Dao
public interface VacancyDao {

    @Insert
    void insert(Vacancy vacancy);

    @Update
    void update(Vacancy vacancy);

    @Delete
    void delete(Vacancy vacancy);

    @Query("DELETE FROM vacancy_table")
    void deleteAllVacancies();

    @Query("SELECT * FROM vacancy_table")
    LiveData<List<Vacancy >> getAllVacancies();
}