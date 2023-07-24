package com.example.contactus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView l; ArrayList<String> name; ArrayList<String> number;  ArrayAdapter<String> adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        l =(ListView) findViewById(R.id.list1);
        name= new ArrayList<>();
        number= new ArrayList<>();
        adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,name);
        registerForContextMenu(l);
        l.setAdapter(adapter);
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String vi="Name: "+name.get(position)+"\nNumber: "+number.get(position);
                Toast.makeText(MainActivity.this,vi, Toast.LENGTH_LONG).show();
                Bundle b=new Bundle();
                b.putString("name",name.get(position));
                b.putString("num", number.get(position));
            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if(v.getId()==R.id.list1)
            getMenuInflater().inflate(R.menu.context_menu, menu);
        MenuItem callMenuItem = menu.findItem(R.id.contexts);
        // Set the icon programmatically
        callMenuItem.setIcon(R.drawable.call_symbol);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if(item.getItemId()==R.id.contexts)
        {
            String str=number.get(info.position);
            Intent i=new Intent(Intent.ACTION_DIAL);
            i.setData(Uri.parse("tel:"+str));
            startActivity(i);
        }
        return super.onContextItemSelected(item);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contact_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.add:
                LayoutInflater li=LayoutInflater.from(this);
                View promptsView=li.inflate(R.layout.add_data,null);
                AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(this);
                alertDialogBuilder.setView(promptsView);
                @SuppressLint({"MissingInflatedId", "LocalSuppress"}) EditText peoname=promptsView.findViewById(R.id.name);
                @SuppressLint({"MissingInflatedId", "LocalSuppress"}) EditText peonum=promptsView.findViewById(R.id.number);
                alertDialogBuilder.setCancelable(false).setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String n=peoname.getText().toString();
                        String nu=peonum.getText().toString();
                        name.add(n);
                        number.add(nu);
                        adapter.notifyDataSetChanged();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog=alertDialogBuilder.create();
                alertDialog.show();
                return true;
            case R.id.delete:
                LayoutInflater lis=LayoutInflater.from(this);
                View promptsViews=lis.inflate(R.layout.delete_data,null);
                AlertDialog.Builder alertDialogBuilders=new AlertDialog.Builder(this);
                alertDialogBuilders.setView(promptsViews);
                ArrayAdapter<String> ada=new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,name);
                @SuppressLint({"MissingInflatedId", "LocalSuppress"}) EditText names = promptsViews.findViewById(R.id.name1);
                alertDialogBuilders.setCancelable(false).setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try{
                            String n=names.getText().toString();
                            String nu=number.get(name.indexOf(n));
                            name.remove(n);
                            number.remove(nu);
                            adapter.notifyDataSetChanged();
                        }
                        catch (Exception e)
                        {
                            Toast.makeText(MainActivity.this,"Enter correct name",Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialo=alertDialogBuilders.create();
                alertDialo.show();
                return true;
            case R.id.conversion:
                Intent i = new Intent(this,ConversionActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}