package com.example.contactus;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ConversionActivity extends AppCompatActivity {

    Button b; TextView t; EditText e;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversion);

        b=(Button) findViewById(R.id.button);
        t=(TextView) findViewById(R.id.textView);
        e=(EditText) findViewById(R.id.editTextNumberSigned);





                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        float v1;
                        try {
                             v1 = Float.parseFloat(e.getText().toString());
                        }
                        catch (Exception e)
                        {
                            t.setText("Enter Numeric Value");
                            return;
                        }
                        PopupMenu popupMenu = new PopupMenu(ConversionActivity.this, v);
                        popupMenu.getMenuInflater().inflate(R.menu.conv, popupMenu.getMenu());

                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.ctof:
                                        float c = v1;
                                        t.setText(String.valueOf(c*1.8+32));
                                        return true;
                                    case R.id.ftoc:
                                        float f = v1;
                                        t.setText(String.valueOf((f-32)/1.8));
                                        return true;
                                    default:
                                        return false;
                                }
                            }
                        });

                        popupMenu.show();
                    }
                });



    }



}
