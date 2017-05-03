package com.example.eliaschang8.tigernewspaper;

import android.icu.text.IDNA;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class SignupActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button signupButton;

    public static String APPLICATION_ID = "455645A8-8F0A-B86E-FFC0-0E642E4F0B00";
    public static String SECRET_KEY = "19E40444-F63B-9C7B-FF20-2550DC0CBD00";
    public static final String TAG = SignupActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Backendless.initApp(this, APPLICATION_ID, SECRET_KEY, "v1");

        //Wire widgets
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        signupButton = (Button) findViewById(R.id.button_signup);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
                //TODO: Add intent to travel to [whatever] page
            }
        });
    }

    private void registerUser() {

        BackendlessUser user = new BackendlessUser();
        Log.d(TAG, "registerUser: User: " + (user == null));
        //Reporting false so user is created, not null
        user.setEmail(email.getText().toString());
        Log.d(TAG, "registerUser: Password: " + (password == null));
        user.setPassword(password.getText().toString());
        //Log.d(TAG, "registerUser: after " + password.getText());

        Backendless.UserService.register(user, new AsyncCallback<BackendlessUser>()
        {
            public void handleResponse( BackendlessUser registeredUser )
            {
                // user has been registered and now can login
                Log.d(TAG, "onCreate: User registration successful");
                Toast.makeText(SignupActivity.this, "User registration successful!",
                        Toast.LENGTH_SHORT).show();
            }

            public void handleFault( BackendlessFault fault )
            {
                // an error has occurred, the error code can be retrieved with fault.getCode()
                Log.e(TAG, "onCreate: User registration FAILED: " + fault.getCode());
            }
        } );
    }
}
