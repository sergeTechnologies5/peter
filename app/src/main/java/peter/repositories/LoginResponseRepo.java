package peter.repositories;

import android.app.Application;

import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

import interfaces.LoginResponseDao;
import peter.activities.Login_Activity;
import peter.database.Db;
import peter.models.LoginResponse;
import peter.retrofit.ApiClient;
import peter.retrofit.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginResponseRepo {
    private LoginResponseDao tokenDao;
    private LiveData<List<LoginResponse>> alltokens;

    Application application;
    public ApiInterface apiInterface;

    public LoginResponseRepo(Application application) {
        Db database = Db.getInstance(application);
        tokenDao = database.loginResponseDao();
        alltokens = tokenDao.getallTokens();

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        this.application = application;
    }
    public LiveData<List<LoginResponse>> getAlltokens() {
        return alltokens;
    }

    public  void insertToken(LoginResponse response){
        new PopulateDbTokensAsyncTask(tokenDao).execute(response);
    }

    private static class PopulateDbTokensAsyncTask extends AsyncTask<LoginResponse, Void, Void> {
        private LoginResponseDao tokenDao;

        private PopulateDbTokensAsyncTask(LoginResponseDao db) {
            tokenDao = db;
        }

        @Override
        protected Void doInBackground(LoginResponse... voids) {
            tokenDao.insert(voids[0]);
            return null;
        }
    }



    public Response<LoginResponse> logout(){

        final Response<LoginResponse>  response = null;
        Call<LoginResponse> call = apiInterface.logout();
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Login_Activity.token = "";
            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(application, t.getMessage(),Toast.LENGTH_LONG).show();

            }

        });
        return response;
    }

}
