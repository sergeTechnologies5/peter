package interfaces;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import peter.models.Home;


@Dao
public interface HomeDao {

    @Insert
    void insert(Home home);

    @Update
    void update(Home home);

    @Delete
    void delete(Home home);

    @Query("DELETE FROM home_table")
    void deleteAllAttachments();

    @Query("SELECT * FROM home_table")
    LiveData<List<Home>> getAllHome();
}