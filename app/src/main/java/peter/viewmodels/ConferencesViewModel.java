

package peter.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

;import java.util.List;

import peter.models.Vacancy;
import peter.repositories.ConferenceRepo;

public class ConferencesViewModel extends AndroidViewModel {
    private ConferenceRepo repository;
    private LiveData<List<Vacancy>> allNotes;


    private List<Vacancy> alljobs;


    public ConferencesViewModel(@NonNull Application application) {
        super(application);

        repository = new ConferenceRepo(application);
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
