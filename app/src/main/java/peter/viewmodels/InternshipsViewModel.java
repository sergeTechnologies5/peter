

package peter.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;


import java.util.List;

import peter.models.Vacancy;
import peter.repositories.InternshipRepo;

public class InternshipsViewModel extends AndroidViewModel {
    private InternshipRepo repository;
    private LiveData<List<Vacancy>> allNotes;


    private List<Vacancy> alljobs;


    public InternshipsViewModel(@NonNull Application application) {
        super(application);

        repository = new InternshipRepo(application);
        allNotes = repository.getHeroes();
        alljobs =repository.getAlljobs();
    }

    public LiveData<List<Vacancy>> getAllNotes() {
        return allNotes;
    }

    public List<Vacancy> getAllJobs() {
        return alljobs;
    }
}
