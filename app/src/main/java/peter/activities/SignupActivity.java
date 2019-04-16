package peter.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.peter.R;


import butterknife.BindView;
import butterknife.ButterKnife;
import peter.MainActivity;
import peter.models.LoginResponse;
import peter.models.User;
import peter.repositories.LoginResponseRepo;
import peter.repositories.UserRepo;
import peter.retrofit.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    UserRepo userRepo;

    @BindView(R.id.input_firstname) EditText _firtstnameText;

    @BindView(R.id.input_lastname) EditText _lastnameText;


    @BindView(R.id.input_name) EditText _nameText;

    @BindView(R.id.input_email) EditText _emailText;

    @BindView(R.id.input_password) EditText _passwordText;

    @BindView(R.id.btn_signup) Button _signupButton;
    @BindView(R.id.link_login) TextView _loginLink;

    private LoginResponseRepo repository;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_page);
        ButterKnife.bind(this);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = _emailText.getText().toString();
                String firstname = _firtstnameText.getText().toString();
                String lastname = _lastnameText.getText().toString();
                String password = _passwordText.getText().toString();
                String username = _nameText.getText().toString();

                signup(username,email,  firstname,lastname,password );
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(),Login_Activity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void signup(String username, String email, String firstname, String lastname, String password) {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }else {

            userRepo = new UserRepo(getApplication());
            userRepo.creatAccount(new User(email,firstname,lastname,username,password));
            _signupButton.setEnabled(false);

            final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                    R.style.AppTheme_Dark);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Creating Account...");
            progressDialog.show();



            // TODO: Implement your own signup logic here.

            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            // On complete call either onSignupSuccess or onSignupFailed
                            // depending on success
                            onSignupSuccess();
                            // onSignupFailed();
                            progressDialog.dismiss();
                        }
                    }, 3000);

        }


    }


    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);

        String password = _passwordText.getText().toString();
        String username = _emailText.getText().toString();
        Call<LoginResponse> call = ApiClient.getInstance(username, password).getService().userLogin();

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                assert response.body() != null;
                if (response.isSuccessful() && response.body().getError()!="Unauthorized") {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    Login_Activity.token = response.body().getToken();
                    repository = new LoginResponseRepo(getApplication());
                    repository.insertToken(response.body());

                } else {
                    startActivity(new Intent(getApplicationContext(), Login_Activity.class));
                    Toast.makeText(getApplicationContext(), "Login Failed Check Username or Password!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("login_error", t.getMessage());
            }
        });

    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();

        String email = _emailText.getText().toString();

        String password = _passwordText.getText().toString();


        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }




        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }


        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }



        return valid;
    }
}