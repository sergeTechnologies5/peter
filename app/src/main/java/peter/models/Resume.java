package peter.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "resume_table")
public class Resume {

    @PrimaryKey(autoGenerate = true)
    private int id;

    public String getCandidate_id() {
        return token;
    }

    public void setCandidate_id(String token ) {
        this.token = token;
    }

    @SerializedName("token")
    private String token;
    @SerializedName("body")
    String body;

    public Resume( String body, String candidate_id) {

        this.body = body;
        this.token = candidate_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }


}
