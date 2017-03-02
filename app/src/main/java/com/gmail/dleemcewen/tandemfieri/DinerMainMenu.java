package com.gmail.dleemcewen.tandemfieri;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gmail.dleemcewen.tandemfieri.Entities.User;
import com.gmail.dleemcewen.tandemfieri.Logging.LogWriter;
import com.google.firebase.auth.FirebaseAuth;

import java.util.logging.Level;

public class DinerMainMenu extends AppCompatActivity {

    private User user;

    private TextView dinerMainMenuName;
    private Button dinerMainMenuPlaceOrder;
    private Button dinerMainMenuTrackOrders;
    private Button dinerMainMenuViewOrderHistory;
    private Button dinerMainMenuRateResturaunts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diner_main_menu);

        Bundle bundle = new Bundle();
        bundle = this.getIntent().getExtras();
        user = (User) bundle.getSerializable("User");

        dinerMainMenuName = (TextView) findViewById(R.id.dinerMainMenuName);
        dinerMainMenuPlaceOrder = (Button) findViewById(R.id.dinerMainMenuPlaceOrderButton);
        dinerMainMenuTrackOrders = (Button) findViewById(R.id.dinerMainMenuTrackOrdersButton);
        dinerMainMenuViewOrderHistory = (Button) findViewById(R.id.dinerMainMenuViewOrderHistoryButton);
        dinerMainMenuRateResturaunts = (Button) findViewById(R.id.dinerMainMenuRateRestaurantButton);

        dinerMainMenuName.setText("Hello, " + user.getFirstName() + "!");

        dinerMainMenuPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Place Order", Toast.LENGTH_SHORT).show();
            }
        });

        dinerMainMenuTrackOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Track Orders", Toast.LENGTH_SHORT).show();
            }
        });

        dinerMainMenuViewOrderHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "View Order History", Toast.LENGTH_SHORT).show();
            }
        });

        dinerMainMenuRateResturaunts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Rate Restaurants", Toast.LENGTH_SHORT).show();
            }
        });

        LogWriter.log(getApplicationContext(), Level.INFO, "The user is " + user.getEmail());
    }//end onCreate

    //create menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.diner_menu, menu);
        return true;
    }

    //determine which menu option was selected and call that option's action method
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.sign_out:
                signOut();
                return true;
            case R.id.edit_personal_info:
                editPersonalInformation();
                return true;
            case R.id.edit_password:
                editPassword();
                return true;
            case R.id.map:
                launchMap();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //called when user selects sign out from the drop down menu
    private void signOut(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("Exit me", true);
        startActivity(intent);
        finish();
    }

    //called when user selects edit information from the drop down menu
    private  void editPersonalInformation(){
        //need to send user type so that the user can be located in the database
        Bundle dinerBundle = new Bundle();
        Intent intent = new Intent(DinerMainMenu.this, EditAccountActivity.class);
        dinerBundle.putSerializable("User", user);
        intent.putExtras(dinerBundle);
        intent.putExtra("UserType", "Diner");
        startActivity(intent);
    }

    //called when user selects edit password from the drop down menu
    private void editPassword(){
        //need to send user type so that the user can be located in the database
        Bundle dinerBundle = new Bundle();
        Intent intent = new Intent(DinerMainMenu.this, EditPasswordActivity.class);
        dinerBundle.putSerializable("User", user);
        intent.putExtras(dinerBundle);
        intent.putExtra("UserType", "Diner");
        startActivity(intent);
    }

    private void launchMap(){
        //need to send user type so that the user can be located in the database
        //Bundle dinerBundle = new Bundle();
        Intent intent = new Intent(DinerMainMenu.this, DinerMapActivity.class);
        //dinerBundle.putSerializable("User", user);
        //intent.putExtras(dinerBundle);
        //intent.putExtra("UserType", "Diner");
        startActivity(intent);
    }
}//end Activity
