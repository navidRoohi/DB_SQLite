package com.example.navidroohibroojeni.dbappnavid;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.stetho.Stetho;

public class MainActivity extends AppCompatActivity {

    private EditText etName, etMajore;
    private TextView tvDisplay;

    private DBAdapter myDBAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Stetho.initializeWithDefaults(this);

        etName = (EditText)findViewById(R.id.etName);
        etMajore = (EditText)findViewById(R.id.etMajor);
        tvDisplay = (TextView) findViewById(R.id.tvDisplay);

        openDB();

    }

    public void openDB(){
        myDBAdapter =  new DBAdapter(this);
        myDBAdapter.open();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myDBAdapter.close();
    }

    public void onUpdateClicked(View view) {

        String name  = etName.getText().toString();
        String majore  = etMajore.getText().toString();

        long id =  myDBAdapter.insertRow(name, majore);

        if (id > 0){
            tvDisplay.setText("Inserted row to database, id = " + id);
        }else {
            tvDisplay.setText("Inserted row to database, faild");
        }


    }

    public void onClearClicked(View view) {

        myDBAdapter.deleteAll();
        tvDisplay.setText("");

    }

    public void onQueryClicked(View view) {
        Cursor cursor = myDBAdapter.getAllRows();
        displayRecords(cursor);

    }

    public void displayRecords(Cursor cursor){
        String output = "";
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do {
                String id  = cursor.getString(DBAdapter.COL_ROWID);
                String name  = cursor.getString(DBAdapter.COL_NAME);
                String major  = cursor.getString(DBAdapter.COL_MAJORE);

                output += "id= "+ id + " name: " + name + " majore: " + major + "\n";

            }while(cursor.moveToNext());
        }
        tvDisplay.setText(output);
        cursor.close();
    }
}
