package com.example.trucksharingapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;

import java.math.BigDecimal;

public class Paypal extends AppCompatActivity {

    Button button;
    TextView editTextNumber;
    String money;
    private int PAYPAL_REQ_CODE=12;
    private static PayPalConfiguration payPalConfiguration=new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(PaypalClientIDConfigClass.PAYPAL_CLIENT_ID);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paypal);

        button=findViewById(R.id.button);
        editTextNumber=findViewById(R.id.editTextNumber);
        Intent intent1=getIntent();
        money=intent1.getStringExtra("money");
        Log.d("money",money );
        editTextNumber.setText("Do you want to pay: "+money+" AUD");

        Intent intent=new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,payPalConfiguration);
        startService(intent);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PayPalPaymentMethod();
            }
        });
    }

    private void PayPalPaymentMethod() {

        PayPalPayment payment=new PayPalPayment(new BigDecimal(money),"AUD",
                "PayPal",PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent=new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,payPalConfiguration);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payment);
        startActivityForResult(intent,PAYPAL_REQ_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PAYPAL_REQ_CODE){
            if (requestCode== Activity.RESULT_OK){

                Toast.makeText(this,"Payment made successfully",Toast.LENGTH_LONG).show();

            }
            else {
                Toast.makeText(this,"Payment is unsuccessful",Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this,PayPalService.class));
        super.onDestroy();
    }
}