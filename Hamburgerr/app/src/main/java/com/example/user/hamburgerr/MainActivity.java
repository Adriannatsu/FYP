package com.example.user.hamburgerr;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText email, pass;
    private TextInputLayout inputLayoutEmail, inputLayoutPass;
    private Button btnSignIn;
    private TextView link_signup, link_forgot_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        inputLayoutEmail = findViewById(R.id.emailInputLayout);
        inputLayoutPass = findViewById(R.id.input_layout_password);
        email = findViewById(R.id.input_email);
        pass = findViewById(R.id.input_password);
        btnSignIn = findViewById(R.id.btn_login);
        link_signup = findViewById(R.id.link_signup);
        link_forgot_pass = findViewById(R.id.link_forgot_pass);

        email.addTextChangedListener(new MyTextWatcher(email));
        pass.addTextChangedListener(new MyTextWatcher(pass));

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });
        link_signup.setOnClickListener(new View.OnClickListener(){
                public void onClick(View view){
                Intent myIntent = new Intent(MainActivity.this, SignUp.class);
                    startActivity(myIntent);
                    overridePendingTransition(0, 0);
            }

        });

    }

    private void submitForm() {

        if (!validateEmail()) {
            return;
        }
        if (!validatePassword()) {
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
    //    progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Loading");
        progressDialog.show();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        //  onLoginSuccess();
                        progressDialog.dismiss();
                    }
                }, 3000);


    }


    private boolean validateEmail() {
        String email_user = email.getText().toString().trim();

        if (email_user.isEmpty() || !isValidEmail(email_user)) {
            inputLayoutEmail.setError(getString(R.string.err_msg_email));
            requestFocus(email);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword() {

        if (pass.getText().toString().trim().isEmpty()) {
            inputLayoutPass.setError(getString(R.string.err_msg_password));
            requestFocus(pass);
            return false;
        } else {
            inputLayoutPass.setErrorEnabled(false);
        }

        return true;
    }

    private static boolean isValidEmail(String email) {

        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();

    }

        private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
        }

        public void onBackPressed (){
            moveTaskToBack(true);
        }


        public class MyTextWatcher implements TextWatcher {

            private View view;

            private MyTextWatcher(View view) {
                this.view = view;
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void afterTextChanged(Editable editable) {
                switch (view.getId()) {
                    case R.id.input_email:
                        validateEmail();
                        break;
                    case R.id.input_password:
                        validatePassword();
                        break;
                }
            }
        }
    }