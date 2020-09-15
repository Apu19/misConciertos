package com.example.misconciertos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {
    Spinner generoMusical;
    Spinner calificacion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        generoMusical = (Spinner)findViewById(R.id.spnr);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.generoMusical, android.R.layout.simple_spinner_item);
        generoMusical.setAdapter(adapter);
        calificacion = (Spinner)findViewById(R.id.spnr2);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,R.array.calificacion, android.R.layout.simple_spinner_item);
        calificacion.setAdapter(adapter2);
    }
}