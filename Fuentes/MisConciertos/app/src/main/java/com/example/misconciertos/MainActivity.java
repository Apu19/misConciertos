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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.misconciertos.dao.EventosDAO;
import com.example.misconciertos.dto.Evento;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListAdapter eventosAdapter;
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
    private EventosDAO eventosDAO = new EventosDAO();
    ArrayAdapter<Evento> conciertosAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.conciertosLv = findViewById(R.id.conciertosLv);
        this.eventosAdapter = new Adaptador(this,R.layout.items,eventosDAO.getAll());
        this.conciertosLv.setAdapter(conciertosAdapter);
        this.valorEntrada = findViewById(R.id.valorEntrada);
        this.registrarBtn = findViewById(R.id.registrarBtn);
        this.nombreArtista = findViewById(R.id.nombreArtista);
        this.generoMusical = findViewById(R.id.spnr);
        this.calificacion = findViewById(R.id.spnr2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.generoMusical, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.calificacion, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.generoMusical.setAdapter(adapter);
        this.calificacion.setAdapter(adapter2);
        this.fechaBtn = findViewById(R.id.fechaBtn);
        this.fechaTxt = findViewById(R.id.fechaTxt);
        this.itemsGenero = getResources().getStringArray(R.array.generoMusical);
        this.itemsCalificacion = getResources().getStringArray(R.array.calificacion);
        this.generoMusical.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        this.fechaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        });
        this.registrarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int valor = 0;
                List<String> errores = new ArrayList<>();
                if (nombreArtista.getText().toString().isEmpty()) {
                    errores.add("Ingrese nombre del artista");
                }
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
                int cal = Integer.parseInt(valorCal.toString());
                if (cal > 0 && cal < 4 ) {
                    cal = R.drawable.sad;
                }
                if (cal == 4 || cal == 5) {
                    cal = R.drawable.thinking;
                }
                if(cal == 6 || cal == 7) {
                    cal = R.drawable.happy;
                }
                if (errores.isEmpty()) {
                    Evento e = new Evento();
                    e.setNombreArtista(nombreArtista.getText().toString());
                    e.setValor(valor);
                    e.setCalificacion(cal);
                    e.setFecha(fechaTxt.getText().toString());
                    eventosDAO.add(e);
                    conciertosAdapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "Se registó correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    mostrarErrores(errores);
                }
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
}


