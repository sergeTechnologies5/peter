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
import peter.repositories.LoginResponseRepo;
import peter.retrofit.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Login_Activity extends AppCompatActivity {


    private LoginResponseRepo repository;
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @BindView(R.id.link_signup)
    TextView _signupLink;
    EditText _emailText;
    EditText _passwordText;
    @BindView(R.id.btn_login)
    Button _loginButton;


    public static String token = "";
    public static String emailaddress = "";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        ButterKnife.bind(this);

        _passwordText = findViewById(R.id.input_password);
        _emailText = findViewById(R.id.input_email);

        _loginButton.setOnClickListener(
                new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                String email = _emailText.getText().toString();
                Login_Activity.emailaddress = email;
                String password = _passwordText.getText().toString();
                login(email, password);
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public  void login(String user, String password) {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }else {
            Call<LoginResponse> call = ApiClient.getInstance(user, password).getService().userLogin();

            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                    assert response.body() != null;
                    if (response.isSuccessful() && response.body().getError()!="Unauthorized") {
                        _loginButton.setEnabled(false);
                        token = response.body().getToken();
                        repository = new LoginResponseRepo(getApplication());
                        repository.insertToken(response.body());
                        final ProgressDialog progressDialog = new ProgressDialog(Login_Activity.this,
                                R.style.AppTheme_Dark);
                        progressDialog.setIndeterminate(true);
                        progressDialog.setMessage("Authenticating...");
                        progressDialog.show();
                        String email = _emailText.getText().toString();
                        Toast.makeText(getApplicationContext(),"Login for "+email+"  Success",Toast.LENGTH_LONG).show();
                        // TODO: Implement your own authentication logic here.

                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        // On complete call either onLoginSuccess or onLoginFailed
                                        onLoginSuccess();
                                        // onLoginFailed();
                                        progressDialog.dismiss();
                                    }
                                }, 3000);

                    } else {
                        Toast.makeText(getApplicationContext(), "Login Failed Check Username or Password!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Log.e("login_error", t.getMessage());
                }
            });
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
       startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 2 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}
