package peter.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "vacancy_table")
public class Vacancy {


    public static  int vacancy_id=0;

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String vacancy_type;

    public String getVacancy_type() {
        return vacancy_type;
    }

    public void setVacancy_type(String vacancy_type) {
        this.vacancy_type = vacancy_type;
    }

    public String getVacancy_title() {
        return vacancy_title;
    }

    public void setVacancy_title(String vacancy_title) {
        this.vacancy_title = vacancy_title;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getVacancy_description() {
        return vacancy_description;
    }

    public void setVacancy_description(String vacancy_description) {
        this.vacancy_description = vacancy_description;
    }

    private String vacancy_description;
    private String vacancy_title;
    private String company_id;
    private String end_time;
    private String start_time;

    public Vacancy(String vacancy_type, String vacancy_title, String company_id,
                   String end_time, String start_time,String vacancy_description) {
        this.vacancy_description = vacancy_description;
        this.vacancy_type = vacancy_type;
        this.vacancy_title = vacancy_title;
        this.company_id= company_id;
        this.end_time = end_time;
        this.start_time= start_time;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}
