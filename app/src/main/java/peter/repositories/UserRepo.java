
package peter.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.widget.Toast;


import java.util.List;

import interfaces.UserDao;
import peter.database.Db;
import peter.models.User;
import peter.retrofit.ApiClient;
import peter.retrofit.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepo {
    private UserDao noteDao;
    private LiveData<List<User>> allUsers;

    private MutableLiveData<User> heroList;
    Application application;

    List<User> alljobs;
    public ApiInterface apiInterface;

    public UserRepo(final Application application) {
        Db database = Db.getInstance(application);
        noteDao = database.userDao();
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        this.application = application;

    }

    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

    public List<User> getAlljobs() {

        return alljobs;
    }


    public void creatAccount(User user){
        Call<User> call = apiInterface.createAccount(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
               // heroList.setValue(response.body());
//                Toast.makeText(application, response.body().getEmail(),Toast.LENGTH_LONG).show();
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
               Toast.makeText(application, t.getMessage(),Toast.LENGTH_LONG).show();
            }

        });

    }


}
