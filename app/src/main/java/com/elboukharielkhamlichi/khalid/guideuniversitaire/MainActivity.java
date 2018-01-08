package com.elboukharielkhamlichi.khalid.guideuniversitaire;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void goToAddActivity(View view) {
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }

    public void goToListActivity(View view) {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    public void goToDeleteActivity(View view) {
        Intent intent = new Intent(this, DeleteActivity.class);
        startActivity(intent);
    }

    public void quitter(View view) {
        this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.add:
                intent = new Intent(this, AddActivity.class);
                break;
            case R.id.delete:
                intent = new Intent(this, DeleteActivity.class);
                break;
            case R.id.list:
                intent = new Intent(this, ListActivity.class);
                break;
            case R.id.exit:
                this.finish();
            default:
                return super.onOptionsItemSelected(item);
        }
        startActivity(intent);
        return true;
    }


}
