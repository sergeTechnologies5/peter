package peter.retrofit;

import java.util.List;

import peter.models.Email;
import peter.models.LoginResponse;
import peter.models.Resume;
import peter.models.ServerResponse;
import peter.models.User;
import peter.models.Vacancy;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {

    //Vacancies end points
    @GET("vacancies")
    Call<List<Vacancy>> getVacancies();

    @GET("vacancies/{id}")
    Call<Vacancy> getVacancy(@Path("id") int id);

    @GET("vacancies/{id}")
    Call<List<Vacancy>> getVacanciesbyType(@Path("id") String vacancyType);

    @GET("vacancies/book/{id}/{token}")
    Call<Vacancy> bookVacancy(@Path("id") int id, @Path("token") String token);

    @DELETE("tokens")
    Call<LoginResponse> logout();

    //
    @POST("users")
    Call<User> createAccount(@Body User user);


    @POST("resumes")
    Call<ServerResponse> uploadFile(@Body Resume user);

    @POST("send_message")
    Call<Email>  sendmail(@Body Email email);

    @POST("sendmail")
    Call<Email>  sendmailtodev(@Body Email email);

}


