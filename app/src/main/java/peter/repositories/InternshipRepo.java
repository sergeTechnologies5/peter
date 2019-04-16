
package peter.repositories;

import android.app.Application;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.widget.Toast;

import java.util.List;

import peter.models.Vacancy;
import peter.retrofit.ApiClient;
import peter.retrofit.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InternshipRepo {

    private LiveData<List<Vacancy>> allNotes;

    private MutableLiveData<List<Vacancy>> heroList;

    List<Vacancy> alljobs;
    public ApiInterface apiInterface;

    public InternshipRepo(final Application application) {

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<List<Vacancy>> call = apiInterface.getVacanciesbyType("internship");
        call.enqueue(new Callback<List<Vacancy>>() {
            @Override
            public void onResponse(Call<List<Vacancy>> call, Response<List<Vacancy>> response) {
                heroList.setValue(response.body());
            }
            @Override
            public void onFailure(Call<List<Vacancy>> call, Throwable t) {
                Toast.makeText(application, t.getMessage(),Toast.LENGTH_LONG).show();
            }

        });

    }



    public List<Vacancy> getAlljobs() {

        return alljobs;
    }


    //we will call this method to get the data
    public LiveData<List<Vacancy>> getHeroes() {
        //if the list is null
        if (heroList == null) {
            heroList = new MutableLiveData<List<Vacancy>>();
            //we will load it asynchronously from server in this method
            heroList.setValue(getAlljobs());
        }

        //finally we will return the list
        return heroList;
    }




}
