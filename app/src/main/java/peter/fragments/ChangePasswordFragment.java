package peter.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.peter.R;

import peter.viewmodels.ChangePasswordViewModel;


public class ChangePasswordFragment extends Fragment {

    private ChangePasswordViewModel mViewModel;


    EditText _change_emailText;


    EditText _change_passwordText;

    Button _submitButton;
    TextView link_signup;
    EditText _change_confirm_passwordText;

    public static ChangePasswordFragment newInstance() {
        return new ChangePasswordFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.change_password_fragment, container, false);

        link_signup  = v.findViewById(R.id.link_signup);
        _submitButton = v.findViewById(R.id.btn_submit);
        _change_passwordText = v.findViewById(R.id.input_change_password);
        _change_emailText =  v.findViewById(R.id.input_change_email);
        _change_confirm_passwordText = v.findViewById(R.id.input_password_confirm);

        _submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               changeLoginDetails();
                _change_emailText.setText("");
                _change_confirm_passwordText.setText("");
                _change_passwordText.setText("");
            }
        });


        return v;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ChangePasswordViewModel.class);

        // TODO: Use the ViewModel

    }

    private void changeLoginDetails(){

        final String new_password = _change_passwordText.getText().toString();
        final String confirm_new_password = _change_confirm_passwordText.getText().toString();
        final String edit_email = _change_emailText.getText().toString().trim();
        Toast.makeText(getContext(),edit_email+"\n"+new_password+"\n"+confirm_new_password,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onViewCreated(View view,@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View v = view;


    }
}
