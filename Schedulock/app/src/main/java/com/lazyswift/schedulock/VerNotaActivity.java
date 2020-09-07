package com.lazyswift.schedulock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lazyswift.schedulock.Modelo.EditarNotaActivity;

public class VerNotaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_nota);
    }

    public void goEditNote(View v)
    {
        startActivity(new Intent(this, EditarNotaActivity.class));
    }
}