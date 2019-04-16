package interfaces;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;



import java.util.List;

import peter.models.LoginResponse;

@Dao
public interface LoginResponseDao {

    @Insert
    void insert(LoginResponse user);

    @Update
    void update(LoginResponse user);

    @Delete
    void delete(LoginResponse user);

    @Query("DELETE FROM tokens_table")
    void deleteAllTokens();

    @Query("SELECT * FROM tokens_table")
    LiveData<List<LoginResponse >> getallTokens();
}
