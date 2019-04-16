package peter.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.peter.R;

import peter.models.Resume;
import peter.models.ServerResponse;
import peter.retrofit.ApiClient;
import peter.retrofit.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UploadFiles extends AppCompatActivity {

    Button  btnMulUpload;
    EditText imgView;

    ProgressDialog progressDialog;
    String body;

    public ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_files);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");


        btnMulUpload =  findViewById(R.id.uploadMultiple);

        imgView =  findViewById(R.id.preview);




        btnMulUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                body = imgView.getText().toString();
                uploadFile(body, Login_Activity.token);
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }


    // Uploading Image/Video
    private void uploadFile(String body, String  token) {
        progressDialog.show();

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ServerResponse> call = apiInterface.uploadFile(new Resume(body,token));
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse serverResponse = response.body();
                if (serverResponse != null) {
                    if (serverResponse.getSuccess()) {
                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    assert serverResponse != null;
                    Log.v("Response", serverResponse.toString());
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

            }
        });
    }



}
