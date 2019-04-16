package peter.retrofit;

import peter.models.LoginResponse;
import retrofit2.Call;
import retrofit2.http.POST;


public interface ApiService {

    @POST("tokens")
    Call<LoginResponse> userLogin();

}
