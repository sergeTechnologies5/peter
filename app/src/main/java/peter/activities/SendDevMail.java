package peter.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.peter.R;

import peter.models.Email;
import peter.retrofit.ApiClient;
import peter.retrofit.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SendDevMail extends AppCompatActivity {

    EditText body;

    Button send;

    public ApiInterface apiInterface;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_dev_mail);

        body = findViewById(R.id.message);

        send = findViewById(R.id.sendbtn);

        progressDialog = new ProgressDialog(this);

        send.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String bodymess = body.getText().toString();
                        String recipient = "njesh74@gmail.com";

                        apiInterface = ApiClient.getClient().create(ApiInterface.class);
                        Call<Email> call = apiInterface.sendmailtodev(new Email("",recipient,bodymess));
                        call.enqueue(new Callback<Email>() {
                            @Override
                            public void onResponse(Call<Email> call, Response<Email> response) {
                                Email serverResponse = response.body();
                                if (serverResponse != null) {
                                    if (response.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    assert serverResponse != null;
                                    Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
                                }
                                progressDialog.dismiss();
                            }

                            @Override
                            public void onFailure(Call<Email> call, Throwable t) {

                            }
                        });

                    }
                }
        );

    }
}
