package com.example.fastfoodorderapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ResumenPedidoActivity extends AppCompatActivity {

    private int hamburguesa, papas, refresco, helado;

    private double total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen_pedido);

        TextView tvResumen = findViewById(R.id.tvResumen);
        TextView tvTotal = findViewById(R.id.tvTotal);

        MaterialButton btnConfirmar = findViewById(R.id.btnConfirmar);
        MaterialButton btnEditar = findViewById(R.id.btnEditar);
        MaterialButton btnCancelar = findViewById(R.id.btnCancelar);

        Intent intentRecibido = getIntent();

        hamburguesa = intentRecibido.getIntExtra("hamburguesa", 0);
        papas = intentRecibido.getIntExtra("papas", 0);
        refresco = intentRecibido.getIntExtra("refresco", 0);
        helado = intentRecibido.getIntExtra("helado", 0);

        String resumen = intentRecibido.getStringExtra("pedido_resumen");

        tvResumen.setText(resumen);

        total = calcularTotal();

        tvTotal.setText(String.format("$%.2f", total));

        // CONFIRMAR

        btnConfirmar.setOnClickListener(v -> {

            Log.d("PEDIDO", "Pedido confirmado");

            String fecha = new SimpleDateFormat(
                    "dd/MM/yyyy HH:mm",
                    Locale.getDefault()
            ).format(new Date());

            Pedido pedido = new Pedido(
                    fecha,
                    hamburguesa,
                    papas,
                    refresco,
                    helado,
                    total
            );

            DatabaseHelper db = new DatabaseHelper(this);

            boolean guardado = db.insertarPedido(pedido);

            if (guardado) {

                Toast.makeText(
                        this,
                        "Pedido guardado en SQLite ✅",
                        Toast.LENGTH_LONG
                ).show();

            } else {

                Toast.makeText(
                        this,
                        "Error al guardar pedido ❌",
                        Toast.LENGTH_LONG
                ).show();
            }

            Intent intentRespuesta = new Intent();

            intentRespuesta.putExtra("pedido_confirmado", true);

            setResult(RESULT_OK, intentRespuesta);

            finish();
        });

        // EDITAR

        btnEditar.setOnClickListener(v -> {

            Intent intentRespuesta = new Intent();

            intentRespuesta.putExtra("hamburguesa", hamburguesa);
            intentRespuesta.putExtra("papas", papas);
            intentRespuesta.putExtra("refresco", refresco);
            intentRespuesta.putExtra("helado", helado);

            setResult(RESULT_CANCELED, intentRespuesta);

            finish();
        });

        // CANCELAR

        btnCancelar.setOnClickListener(v -> {

            Intent intentRespuesta = new Intent();

            intentRespuesta.putExtra("hamburguesa", 0);
            intentRespuesta.putExtra("papas", 0);
            intentRespuesta.putExtra("refresco", 0);
            intentRespuesta.putExtra("helado", 0);

            setResult(RESULT_CANCELED, intentRespuesta);

            finish();
        });
    }

    private double calcularTotal() {

        double total = 0;

        total += hamburguesa * 5.99;
        total += papas * 3.99;
        total += refresco * 2.99;
        total += helado * 2.49;

        return total;
    }
}