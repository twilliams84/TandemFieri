package com.gmail.dleemcewen.tandemfieri;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.gmail.dleemcewen.tandemfieri.Entities.User;
import com.gmail.dleemcewen.tandemfieri.Repositories.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AlmostDoneActivity extends AppCompatActivity{

    public String firstName, lastName, address, city, state, zip, phoneNumber, email;
    public Button createButton;
    public Button cancelButton;
    public EditText username, password, confirmPassword;
    public User newUser;
    private DatabaseReference mDatabase;
    public RadioButton radioDining, radioRestaurant, radioDriver;
    FirebaseAuth user = FirebaseAuth.getInstance();

    private Users<User> usersRepo = new Users<User>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_almost_done);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        radioDining = (RadioButton) findViewById(R.id.diningRadio);
        radioRestaurant = (RadioButton) findViewById(R.id.restaurantRadio);
        radioDriver = (RadioButton) findViewById(R.id.driverRadio);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        confirmPassword = (EditText) findViewById(R.id.confrimPassword);

        createButton = (Button) findViewById(R.id.createButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);

        firstName = getIntent().getStringExtra("firstName");
        lastName = getIntent().getStringExtra("lastName");
        address = getIntent().getStringExtra("address");
        city = getIntent().getStringExtra("city");
        state = getIntent().getStringExtra("state");
        zip = getIntent().getStringExtra("zip");
        phoneNumber = getIntent().getStringExtra("phoneNumber");
        email = getIntent().getStringExtra("email");

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (password.getText().toString()
                        .equals(confirmPassword.getText().toString())
                        && password.getText().toString().matches(".*\\w.*")
                        && password.getText().toString().length() >= 6){
                    createUser();
            }else {
                    if (!password.getText().toString()
                            .equals(confirmPassword.getText().toString()))
                        Toast.makeText(AlmostDoneActivity.this,
                                "Please input two matching passwords.", Toast.LENGTH_SHORT).show();
                    else if (!password.getText().toString().matches(".*\\w.*"))
                        Toast.makeText(AlmostDoneActivity.this,
                                "Please use characters and not only whitespace.", Toast.LENGTH_SHORT)
                        .show();
                    else if (password.getText().toString().length() < 6)
                        Toast.makeText(AlmostDoneActivity.this,
                                "Please enter a password with 6 or more characters.",
                                Toast.LENGTH_SHORT).show();
                    password.setText("");
                    confirmPassword.setText("");
                    password.requestFocus();
                }

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                CreateAccountActivity.getInstance().finish();
            }
        });

    }//end on create

    public void createUser (){
        user.createUserWithEmailAndPassword(email, password.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //Toast.makeText(getApplicationContext(), "In create user.", Toast.LENGTH_LONG).show();
                if (task.isSuccessful()) {
                    //Toast.makeText(getApplicationContext(), "Task was successful.", Toast.LENGTH_LONG).show();
                    Bundle bundle = new Bundle();
                    Intent intent = null;
                    FirebaseUser user = task.getResult().getUser();

                    newUser = new User();
                    newUser.setFirstName(firstName);
                    newUser.setLastName(lastName);
                    newUser.setUsername(username.getText().toString());
                    newUser.setCity(city);
                    newUser.setAuthUserID(user.getUid());
                    newUser.setEmail(email);
                    newUser.setAddress(address);
                    newUser.setZip(zip);
                    newUser.setPhoneNumber(phoneNumber);
                    newUser.setState(state);
                    //TODO: Remove the following intents when they are no longer needed for testing
                    if (radioDining.isChecked() == true) {
                        mDatabase.child("User").child("Diner").child(user.getUid()).setValue(newUser);
                        //intent = new Intent(AlmostDoneActivity.this, DinerMainMenu.class);
                    } else if (radioRestaurant.isChecked() == true){
                        mDatabase.child("User").child("Restaurant").child(user.getUid()).setValue(newUser);
                        //intent = new Intent(AlmostDoneActivity.this, RestaurantMainMenu.class);
                    } else if (radioDriver.isChecked() == true){
                        mDatabase.child("User").child("Driver").child(user.getUid()).setValue(newUser);
                        //intent = new Intent(AlmostDoneActivity.this, DriverMainMenu.class);
                    }
                    //bundle.putSerializable("User", newUser);
                    //intent.putExtras(bundle);
                    //startActivity(intent);
                    user.sendEmailVerification();
                    finish();
                    CreateAccountActivity.getInstance().finish();
                    Toast.makeText(getApplicationContext(), "Successfully created user. Email Verification Sent", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Unable to create user. Exception was " + task.getException().toString(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
