package peter.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "attachment_table")
public class Attachment {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String username;
    private String password;
    private String token;

    public Attachment(String username, String password, String token) {
        this.username = username;
        this.password = password;
        this.token= token;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }
}
