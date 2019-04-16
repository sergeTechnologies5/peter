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

import peter.models.Attachment;


public class AttachmentAdapter extends ListAdapter<Attachment, AttachmentAdapter.AttachmentHolder> {

    private OnItemClickListener listener;

    public AttachmentAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Attachment> DIFF_CALLBACK = new DiffUtil.ItemCallback<Attachment>() {
        @Override
        public boolean areItemsTheSame(@NonNull Attachment note, @NonNull Attachment t1) {
            return note.getId() == t1.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Attachment note, @NonNull Attachment t1) {
            return note.getPassword().equals(t1.getPassword())
                    && note.getUsername().equals(t1.getUsername())
                    && note.getToken() == t1.getToken();
        }
    };

    @NonNull
    @Override
    public AttachmentHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.note_item, viewGroup, false);
        return new AttachmentHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AttachmentHolder noteHolder, int i) {
        Attachment currentNote = getItem(i);
        noteHolder.textViewTitle.setText(currentNote.getUsername());
        noteHolder.textViewDescription.setText(currentNote.getPassword());
        noteHolder.textViewPriority.setText(String.valueOf(currentNote.getToken()));
    }


    public Attachment getNoteAt(int position) {
        return getItem(position);
    }

    class AttachmentHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewPriority;


        public AttachmentHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            textViewPriority = itemView.findViewById(R.id.text_view_priority);

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
        void onItemClick(Attachment note);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
