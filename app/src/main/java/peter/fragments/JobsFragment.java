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
import peter.viewmodels.JobsViewModel;

public class JobsFragment extends Fragment {

    private JobsViewModel mViewModel;

    private JobsAdapter adapter;


    public static JobsFragment newInstance() {
        return new JobsFragment();
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
        textView.setText("Jobs");
        adapter = new JobsAdapter();
        recyclerView.setAdapter(adapter);

        mViewModel = ViewModelProviders.of(this).get(JobsViewModel.class);

        return view;


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(JobsViewModel.class);

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
                Intent intent = new Intent(getContext(), Apply.class);
                Vacancy.vacancy_id = note.getId();
                startActivity(intent);
            }
        });


        // TODO: Use the ViewModel

    }

}
