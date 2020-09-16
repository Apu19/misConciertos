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

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
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
        setContentView(R.layout.activity_main);
        registrarBtn = findViewById(R.id.registrarBtn);
        nombreArtista = findViewById(R.id.nombreArtista);
        generoMusical = findViewById(R.id.spnr);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.generoMusical, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.calificacion, android.R.layout.simple_spinner_item);
        this.generoMusical.setAdapter(adapter);
        calificacion = findViewById(R.id.spnr2);
        this.calificacion.setAdapter(adapter2);
        fechaBtn = findViewById(R.id.fechaBtn);
        fechaTxt = findViewById(R.id.fechaTxt);
        fechaBtn.setOnClickListener(this);
        registrarBtn.setOnClickListener(this);
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
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fechaBtn:
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
                break;
            case R.id.registrarBtn:
                    List<String> errores = new ArrayList<>();
                    if(nombreArtista.getText().toString().isEmpty()){
                        errores.add("Debe ingresar el nombre del artista");
                    }
                    mostrarErrores(errores);

                break;
        }
    }
}

