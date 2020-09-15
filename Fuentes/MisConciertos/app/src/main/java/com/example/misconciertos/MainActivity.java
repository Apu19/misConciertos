package com.example.misconciertos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Spinner generoMusical;
    private Spinner calificacion;
    private Button fechaBtn;
    private Button registrarBtn;
    private EditText fechaTxt;
    private EditText nombreArtista;
    private EditText valorEntrada;
    private int dia, mes, anio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.registrarBtn = findViewById(R.id.registrarBtn);
        this.nombreArtista = findViewById(R.id.nombreArtista);
        this.valorEntrada = findViewById(R.id.nombreArtista);
        setContentView(R.layout.activity_main);
        this.generoMusical = findViewById(R.id.spnr);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.generoMusical, android.R.layout.simple_spinner_item);
        generoMusical.setAdapter(adapter);
        this.calificacion = findViewById(R.id.spnr2);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.calificacion, android.R.layout.simple_spinner_item);
        this.calificacion.setAdapter(adapter2);
        this.fechaBtn = findViewById(R.id.fechaBtn);
        this.fechaTxt = findViewById(R.id.fechaTxt);
        this.fechaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == fechaBtn) {
                    final Calendar c = Calendar.getInstance();
                    dia = c.get(Calendar.DAY_OF_MONTH);
                    mes = c.get(Calendar.MONTH);
                    anio = c.get(Calendar.YEAR);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            fechaTxt.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                        }
                    }, anio, mes, dia);
                    datePickerDialog.show();
                }
            }
        });


    }
    private void mostrarErrores (List<String> errores) {
        String mensaje = "";
        for (String e : errores) {
            mensaje += "-" + e + "\n";
        }
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
        alertBuilder.setTitle("Error de validación")
                .setMessage(mensaje)
                .setPositiveButton("Aceptar", null)
                .create()
                .show();
    }
}

