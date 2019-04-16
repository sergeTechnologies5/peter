package interfaces;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

;import java.util.List;

import peter.models.Scholarship;

@Dao
public interface ScholarshipDao {

    @Insert
    void insert(Scholarship scholarship);

    @Update
    void update(Scholarship scholarship);

    @Delete
    void delete(Scholarship scholarship);

    @Query("DELETE FROM scholarship_table")
    void deleteAllScholarships();

    @Query("SELECT * FROM scholarship_table")
    LiveData<List<Scholarship >> getAllScholarships();
}