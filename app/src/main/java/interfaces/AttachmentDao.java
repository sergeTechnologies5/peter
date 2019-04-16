package interfaces;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import peter.models.Attachment;


@Dao
public interface AttachmentDao {

    @Insert
    void insert(Attachment attachment);

    @Update
    void update(Attachment attachment);

    @Delete
    void delete(Attachment attachment);

    @Query("DELETE FROM attachment_table")
    void deleteAllAttachments();

    @Query("SELECT * FROM attachment_table")
    LiveData<List<Attachment >> getAllAttachments();
}