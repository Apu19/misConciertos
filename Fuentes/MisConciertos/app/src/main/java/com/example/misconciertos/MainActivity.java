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
    Adaptador eventosAdapter;
    private Spinner generoMusical;
    private Spinner calificacion;
    private Button registrarBtn;
    private EditText fechaTxt;
    private EditText nombreArtista;
    private EditText valorEntrada;
    private int dia, mes, anio;
    private String[] itemsGenero;
    private String[] itemsCalificacion;
    private ListView conciertosLv;
    private EventosDAO eventosDAO = new EventosDAO();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.conciertosLv = findViewById(R.id.conciertosLv);
        this.eventosAdapter = new Adaptador(this, R.layout.items, eventosDAO.getAll());
        this.conciertosLv.setAdapter(eventosAdapter);
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
        this.fechaTxt = findViewById(R.id.fechaTxt);
        this.itemsGenero = getResources().getStringArray(R.array.generoMusical);
        this.itemsCalificacion = getResources().getStringArray(R.array.calificacion);
        this.fechaTxt.setOnClickListener(new View.OnClickListener() {
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
                List<String> errores = new ArrayList<>();
                String nombre = nombreArtista.getText().toString();
                if (nombre.isEmpty()) {
                    errores.add("Ingrese nombre del artista");
                }

                String fechaEvento = fechaTxt.getText().toString();
                if (fechaEvento.isEmpty()) {
                    errores.add("Seleccione fecha del evento");
                }

                String generoTxt = generoMusical.getSelectedItem().toString();
                if (generoTxt.equalsIgnoreCase("Seleccionar")) {
                    errores.add("Seleccione género musical");
                }

                int entrada = 0;
                String valor = valorEntrada.getText().toString().trim();
                if (valor.isEmpty()) {
                    errores.add("Ingrese valor de la entrada");
                }else {
                    try {
                        entrada = Integer.parseInt(valor);
                        if (entrada <= 0) {
                            throw new NumberFormatException();
                        }
                    } catch (Exception ex) {
                        errores.add("El valor de la entrada debe ser mayor a 0");
                    }
                }

                String calificacionTxt = calificacion.getSelectedItem().toString();
                int calificacionItem = 0;
                try {
                    calificacionItem = Integer.parseInt(calificacionTxt);
                } catch (Exception ex) {
                    errores.add("Seleccione calificación");
                }

                if (errores.isEmpty()) {
                    Evento e = new Evento();
                    e.setNombreArtista(nombre);
                    e.setFecha(fechaEvento);
                    e.setValor(entrada);
                    if (calificacionItem > 0 && calificacionItem < 4) {
                        calificacionItem = R.drawable.sad;
                    }
                    if(calificacionItem == 4 || calificacionItem == 5) {
                        calificacionItem = R.drawable.thinking;
                    }
                    if(calificacionItem == 6 || calificacionItem == 7) {
                        calificacionItem = R.drawable.happy;
                    }
                    e.setCalificacion(calificacionItem);
                    eventosDAO.add(e);
                    eventosAdapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "Se ha registrado correctamente", Toast.LENGTH_SHORT).show();
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


