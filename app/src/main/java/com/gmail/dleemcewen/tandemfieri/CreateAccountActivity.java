package com.gmail.dleemcewen.tandemfieri;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static com.gmail.dleemcewen.tandemfieri.R.id.phone;

public class CreateAccountActivity extends AppCompatActivity {

    public Button nextButton;

    public EditText firstName, lastName, address, city, state, zip, phoneNumber, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        nextButton = (Button)findViewById(R.id.nextButton);

        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        address = (EditText) findViewById(R.id.address);
        city = (EditText) findViewById(R.id.city);
        state = (EditText) findViewById(R.id.state);
        zip = (EditText) findViewById(R.id.zip);
        phoneNumber = (EditText) findViewById(phone);
        email = (EditText) findViewById(R.id.email);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateAccountActivity.this, AlmostDoneActivity.class);
                intent.putExtra("firstName", firstName.toString());
                intent.putExtra("lastName", lastName.toString());
                intent.putExtra("address", address.toString());
                intent.putExtra("city", city.toString());
                intent.putExtra("zip", zip.toString());
                intent.putExtra("phoneNumber", phoneNumber.toString());
                intent.putExtra("email", email.toString());
                startActivity(intent);
            }
        });
    }
}
