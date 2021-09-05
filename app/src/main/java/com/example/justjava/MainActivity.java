
package com.example.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 2;
    boolean addWhippedCream;
    boolean addChoclate;
    String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {


        /** Taking name from the user and show edit text*/
        EditText nameField = (EditText) findViewById(R.id.name_view);
        String name = nameField.getText().toString();

        /** Creating CheckBox class object and checking Whipped Cream  **********/
        CheckBox hasWhipped = (CheckBox) findViewById(R.id.Whipped_cream_checkbox);
        boolean hasWhippedCream = hasWhipped.isChecked();

        /** Creating CheckBox class object and checking Choclate **********/
        CheckBox hasChoclate = (CheckBox) findViewById(R.id.choclate_checkbox);
        boolean hasChoclateBox = hasChoclate.isChecked();


        int price = calculatePrice(hasWhippedCream, hasChoclateBox);

        String priceMessage = createOrderSummary(price,hasWhippedCream,hasChoclateBox,name);


        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just java order for "+ name);
        intent.putExtra(Intent.EXTRA_TEXT,priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    /**
     * Calculates the price of the order.
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChoclate) {
        int baseValue = 5;
        /** ADDING 1 rs for extra Whipped Cream   ********/
        if (addWhippedCream) {
            baseValue = baseValue + 1;
        }
        /** Adding 2Rs for extra choclate  *****/
        if (addChoclate) {
            baseValue = baseValue + 2;
        }
        return baseValue * quantity;
    }

    /**
     * CreateOrderSummary method
     *
     * @param price         for different launguage we refer this R.string();
     * @return priceMessage
     */
    public String createOrderSummary(int price, boolean addWhippedCream, boolean addChoclate, String name) {

        String priceMessage = "Name: "+ name;

        priceMessage = priceMessage + "\n Add WhippedmCream?"+ addWhippedCream;

        priceMessage = priceMessage + "\n Add Choclate?"+ addChoclate;

        priceMessage = priceMessage + "\n Quantity: "+ quantity;

        priceMessage = priceMessage + "\n Total: "+ price;

        priceMessage = priceMessage + "\n Thank you!";

        return priceMessage;
    }

    /**
     * Increment by 1
     *
     * @param view
     */
    public void increment(View view) {
        if (quantity == 100) { // Show an error message as a toast
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();

            // Exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * Decrement by 1
     *
     * @param view
     */
    public void decrement(View view) {
        if (quantity == 1) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

}