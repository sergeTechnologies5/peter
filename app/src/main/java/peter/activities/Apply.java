package peter.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.peter.R;


import peter.models.Vacancy;
import peter.retrofit.ApiClient;
import peter.retrofit.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Apply extends AppCompatActivity {

    TextView description;

    TextView date;

    TextView title;

    Button apply;

    TextView vacancy_type;

    public ApiInterface apiInterface;
    Vacancy vacancy = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply);


        description = findViewById(R.id.description);
        date = findViewById(R.id.date);
        title = findViewById(R.id.title);
        apply = findViewById(R.id.apply);
        vacancy_type = findViewById(R.id.vacancy_type);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);



        Call<Vacancy> call = apiInterface.getVacancy(Vacancy.vacancy_id );
        call.enqueue(new Callback<Vacancy>() {
            @Override
            public void onResponse(Call<Vacancy> call, Response<Vacancy> response) {
                vacancy = response.body();

                if (vacancy != null) {
                    description.setText("Title : "+vacancy.getVacancy_description());
                    date.setText("Date : " +vacancy.getEnd_time());
                    title.setText("Type : "+vacancy.getVacancy_type());
                    vacancy_type.setText("Description : "+vacancy.getVacancy_description());

                    apply.setOnClickListener(
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Call<Vacancy> call = apiInterface.bookVacancy(vacancy.getId(),Login_Activity.token);
                                    call.enqueue(new Callback<Vacancy>() {
                                        @Override
                                        public void onResponse(Call<Vacancy> call, Response<Vacancy> response) {
                                            Toast.makeText(getApplicationContext(),response.message(),Toast.LENGTH_LONG).show();
                                        }
                                        @Override
                                        public void onFailure(Call<Vacancy> call, Throwable t) {
                                        }

                                    });
                                }
                            }
                    );
                }

            }
            @Override
            public void onFailure(Call<Vacancy> call, Throwable t) {
            }

        });

    }
}
