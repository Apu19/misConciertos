package com.example.misconciertos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.misconciertos.dao.EventosDAO;
import com.example.misconciertos.dto.Evento;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Spinner generoMusical;
    private Spinner calificacion;
    private Button fechaBtn;
    private Button registrarBtn;
    private EditText fechaTxt;
    private EditText nombreArtista;
    private EditText valorEntrada;
    private int dia, mes, anio;
    private String[] itemsGenero;
    private String[] itemsCalificacion;
    private String genero;
    private String valorCal;
    private ListView conciertosLv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        conciertosLv = findViewById(R.id.conciertosLv);
        valorEntrada = findViewById(R.id.valorEntrada);
        registrarBtn = findViewById(R.id.registrarBtn);
        nombreArtista = findViewById(R.id.nombreArtista);
        generoMusical = findViewById(R.id.spnr);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.generoMusical, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.calificacion, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.generoMusical.setAdapter(adapter);
        calificacion = findViewById(R.id.spnr2);
        this.calificacion.setAdapter(adapter2);
        fechaBtn = findViewById(R.id.fechaBtn);
        fechaTxt = findViewById(R.id.fechaTxt);
        fechaBtn.setOnClickListener(this);
        registrarBtn.setOnClickListener(this);
        itemsGenero = getResources().getStringArray(R.array.generoMusical);
        itemsCalificacion = getResources().getStringArray(R.array.calificacion);
        generoMusical.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                genero = itemsGenero[i].toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        calificacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                valorCal = itemsCalificacion[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void mostrarErrores(List<String> errores) {
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
        switch (view.getId()) {
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
                int valor = 0;
                List<String> errores = new ArrayList<>();
                if (nombreArtista.getText().toString().isEmpty()) {
                    errores.add("Ingrese nombre del artista");
                    if (fechaTxt.getText().toString().isEmpty()) {
                        errores.add("Seleccione fecha");
                    }
                    if (genero.equals("Seleccionar")) {
                        errores.add("Seleccione género");
                    }
                    try {
                        valor = Integer.parseInt(valorEntrada.getText().toString());
                        if (valor <= 0) {
                            throw new NumberFormatException();
                        }
                    } catch (NumberFormatException ex) {
                        errores.add("El valor debe ser mayor a 0");
                    }
                    if (valorCal.equals("Seleccionar")) {
                        errores.add("Seleccione Calificación");
                    }
                    if (errores.isEmpty()) {
                        Evento e = new Evento();
                        ArrayAdapter<Evento> conciertosAdapter;
                        e.setNombreArtista(nombreArtista.getText().toString());
                        e.setValor(valor);
                        e.setCalificacion(valorCal);
                        e.setFecha(fechaTxt.getText().toString());
                        EventosDAO eventosDAO = new EventosDAO();
                        eventosDAO.add(e);
                        conciertosAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, eventosDAO.getAll(e));
                        this.conciertosLv.setAdapter(conciertosAdapter);
                        conciertosAdapter.notifyDataSetChanged();
                        Toast.makeText(this, "Se ha registrado con exito", Toast.LENGTH_SHORT).show();
                    } else {
                        mostrarErrores(errores);
                    }
                    break;
                }
        }
    }
}

