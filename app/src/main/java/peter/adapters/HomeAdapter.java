package peter.adapters;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.peter.R;

import peter.models.Vacancy;


public class HomeAdapter extends ListAdapter<Vacancy, HomeAdapter.VacanyHolder> {

    private OnItemClickListener listener;

    public HomeAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Vacancy> DIFF_CALLBACK = new DiffUtil.ItemCallback<Vacancy>() {
        @Override
        public boolean areItemsTheSame(@NonNull Vacancy note, @NonNull Vacancy t1) {
            return note.getId() == t1.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Vacancy note, @NonNull Vacancy t1) {
            return note.getCompany_id().equals(t1.getCompany_id())
                    && note.getVacancy_type().equals(t1.getVacancy_type())
                    && note.getEnd_time() == t1.getEnd_time();
        }
    };

    @NonNull
    @Override
    public VacanyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.note_item, viewGroup, false);
        return new VacanyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VacanyHolder noteHolder, int i) {
        Vacancy currentNote = getItem(i);
        noteHolder.textViewTitle.setText(currentNote.getVacancy_title());
        noteHolder.textViewDescription.setText(currentNote.getVacancy_description());
        noteHolder.enddate.setText(currentNote.getEnd_time());
        noteHolder.textViewPriority.setText(String.valueOf(currentNote.getVacancy_type()));
    }


    public Vacancy getNoteAt(int position) {
        return getItem(position);
    }


    class VacanyHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewPriority;
        private TextView enddate;


        public VacanyHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            textViewPriority = itemView.findViewById(R.id.text_view_priority);
            enddate = itemView.findViewById(R.id.date_field);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Vacancy note);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {

        this.listener = listener;
    }
}
