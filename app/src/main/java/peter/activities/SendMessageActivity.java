package peter.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.peter.R;

import peter.models.Email;
import peter.retrofit.ApiClient;
import peter.retrofit.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SendMessageActivity extends
        AppCompatActivity {


    TextView email;
    TextView message;
    public ApiInterface apiInterface;
    ProgressDialog progressDialog;
    Button sendbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sendmessage);

        email = findViewById(R.id.email);
        message = findViewById(R.id.message);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");

        sendbtn = findViewById(R.id.sendbtn);

        sendbtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String emailaddress = email.getText().toString();
                        String mess = message.getText().toString();
                        String sender = Login_Activity.emailaddress;


                        apiInterface = ApiClient.getClient().create(ApiInterface.class);
                        Call<Email> call = apiInterface.sendmail(new Email(sender,emailaddress,mess));
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
                                    Log.v("Response", serverResponse.toString());
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
