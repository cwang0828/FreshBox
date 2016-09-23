package com.example.mac.mykitchenapp1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

/**
 * Created by mac on 6/23/16.
 */
public class Category extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_layout);

    }

    public void chooseType(View view) {
        String b = ((Button) view).getText().toString();

        if (b.equals("VEGETABLE")) {
            Intent intent = new Intent(this, Vegi.class);
            startActivity(intent);
        } else if(b.equals("MEAT")) {
            Intent intent = new Intent(this, Meat.class);
            startActivity(intent);
        } else if(b.equals("FRUIT")) {
            Intent intent = new Intent(this, Fruit.class);
            startActivity(intent);
        } else if(b.equals("CANNED")) {
            Intent intent = new Intent(this, Can.class);
            startActivity(intent);
        } else if(b.equals("DRINK")) {
            Intent intent = new Intent(this, Drink.class);
            startActivity(intent);
        } else if(b.equals("OTHER1")) {
            Intent intent = new Intent(this, Other1.class);
            startActivity(intent);
        } else if(b.equals("OTHER2")) {
            Intent intent = new Intent(this, Other2.class);
            startActivity(intent);
        }
    }
}
