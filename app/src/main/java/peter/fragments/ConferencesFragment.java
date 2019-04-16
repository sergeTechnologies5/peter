package peter.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.peter.R;


import java.util.List;

import peter.activities.Apply;
import peter.adapters.JobsAdapter;
import peter.models.Vacancy;
import peter.retrofit.ApiInterface;
import peter.viewmodels.ConferencesViewModel;

public class ConferencesFragment extends Fragment {

    private ConferencesViewModel mViewModel;

    private JobsAdapter adapter;


    public static ConferencesFragment newInstance() {
        return new ConferencesFragment();
    }

    public ApiInterface apiInterface;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.jobs_fragment, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        TextView textView = view.findViewById(R.id.title);
        textView.setText("Conferences");

        adapter = new JobsAdapter();
        recyclerView.setAdapter(adapter);

        mViewModel = ViewModelProviders.of(this).get(ConferencesViewModel.class);
        mViewModel.getAllNotes().observe(this, new Observer<List<Vacancy>>() {
            @Override
            public void onChanged(@Nullable List<Vacancy> notes) {
                //update RecyclerView
                adapter.submitList(notes);
            }
        });

        adapter.setOnItemClickListener(new JobsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Vacancy note) {
                //Toast.makeText(getContext(), note.getVacancy_type()+" "+note.getId(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getContext(), Apply.class);
                Vacancy.vacancy_id = note.getId();
                startActivity(intent);
            }
        });


        return view;


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ConferencesViewModel.class);
        // TODO: Use the ViewModel

    }

}
