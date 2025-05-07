package com.example.tarea1corte2ja;

import android.os.Bundle;
import android.view.ContextMenu;

import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    EditText x1,y1, X2, y2;
    Button pendiente, punto_medio, ecuacion, cuadrantes;
    TextView resultados;
    View selectedView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        x1= (EditText) findViewById(R.id.x1);
        y1= (EditText) findViewById(R.id.y1);
        X2= (EditText) findViewById(R.id.x2);
        y2= (EditText) findViewById(R.id.y2);
        pendiente= (Button) findViewById(R.id.btn_pendiente);
        punto_medio= (Button) findViewById(R.id.btn_puntoMedio);
        ecuacion= (Button) findViewById(R.id.btn_ecuacion);
        cuadrantes= (Button) findViewById(R.id.btn_cuadrantes);
        resultados= (TextView) findViewById(R.id.resultado);


        pendiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pendiente();
            }
        });
        punto_medio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                punto_medio();
            }
        });
        ecuacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ecuacion();
            }
        });
        cuadrantes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cuadrantes();
            }
        });
        registerForContextMenu(x1);
        registerForContextMenu(y1);
        registerForContextMenu(X2);
        registerForContextMenu(y2);
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        selectedView = v;
        int viewId = v.getId();
        if (viewId == R.id.x1 || viewId == R.id.y1 || viewId == R.id.x2 || viewId == R.id.y2) {
            getMenuInflater().inflate(R.menu.contextmenu, menu);
        }
    }
    public void showPopupMenu(View view){
        PopupMenu popupMenu= new PopupMenu(this,view);
        MenuInflater inflater= popupMenu.getMenuInflater();
        inflater.inflate(R.menu.layoutmenu,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.show();
    }
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_layout_c1) {
            generarPuntos(1);
            return true;
        } else if (id == R.id.item_layout_c2) {
            generarPuntos(2);
            return true;
        } else if (id == R.id.item_layout_c3) {
            generarPuntos(3);
            return true;
        } else if (id == R.id.item_layout_c4) {
            generarPuntos(4);
            return true;
        } else {
            return false;
        }
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_aleatorio) {
            aleatorio();
            return true;
        } else if (id == R.id.item_cambiar_signo) {
            cambioSigno();
            return true;
        } else {
            return super.onContextItemSelected(item);
        }
    }
    private void aleatorio() {
        if (selectedView instanceof EditText) {
            int random = (int)(Math.random() * 201) - 100;
            ((EditText) selectedView).setText(String.valueOf(random));
        } else {
            resultados.setText("Selecciona un campo primero (x1, y1, x2 o y2)");
        }
    }
    private void cambioSigno(){
        if (selectedView instanceof EditText){
            EditText num = (EditText) selectedView;
            double valor = Double.parseDouble(num.getText().toString());
            double numInvertido = valor * -1;
            num.setText(String.valueOf(numInvertido));
        }
    }
    private void pendiente(){
        double px1 = Double.parseDouble(x1.getText().toString());
        double py1 = Double.parseDouble(y1.getText().toString());
        double px2 = Double.parseDouble(X2.getText().toString());
        double py2 = Double.parseDouble(y2.getText().toString());

        if (px2 - px1 == 0) {
            resultados.setText("Pendiente indefinida");
        } else {
            double p = (double)(py2 - py1) / (px2 - px1);
            resultados.setText("Pendiente: " + p);
        }
    }
    private void punto_medio(){
        double pmx1 = Double.parseDouble(x1.getText().toString());
        double pmy1 = Double.parseDouble(y1.getText().toString());
        double pmx2 = Double.parseDouble(X2.getText().toString());
        double pmy2 = Double.parseDouble(y2.getText().toString());
        double xm = (pmx1 + pmx2) / 2.0;
        double ym = (pmy1 + pmy2) / 2.0;
        resultados.setText("Punto medio:(" + xm + ", " + ym + ")");
    }
    private void ecuacion(){
        double ecx1 = Double.parseDouble(x1.getText().toString());
        double ecy1 = Double.parseDouble(y1.getText().toString());
        double ecx2 = Double.parseDouble(X2.getText().toString());
        double ecy2 = Double.parseDouble(y2.getText().toString());

        if (ecx2 - ecx1 == 0) {
            resultados.setText("Ecuación: x = " + ecx1 + " (recta vertical)");
        }else {
            double m = (double)(ecy2 - ecy1) / (ecx2 - ecx1);
            double b = ecy1 - m * ecx1;
            resultados.setText(String.format("Ecuación: y = %.2fx + %.2f", m, b));
        }
    }
    private void cuadrantes() {
        double cx1 = Double.parseDouble(x1.getText().toString());
        double cy1 = Double.parseDouble(y1.getText().toString());
        double cx2 = Double.parseDouble(X2.getText().toString());
        double cy2 = Double.parseDouble(y2.getText().toString());
            String cuadrante1;
            if (cx1 > 0 && cy1 > 0) cuadrante1 = "I";
            else if (cx1 < 0 && cy1 > 0) cuadrante1 = "II";
            else if (cx1 < 0 && cy1 < 0) cuadrante1 = "III";
            else if (cx1 > 0 && cy1 < 0) cuadrante1 = "IV";
            else if (cx1 == 0 && cy1 == 0) cuadrante1 = "Origen";
            else if (cx1 == 0) cuadrante1 = "Eje Y";
            else cuadrante1 = "Eje X";
            String cuadrante2;
            if (cx2 > 0 && cy2 > 0) cuadrante2 = "I";
            else if (cx2 < 0 && cy2 > 0) cuadrante2 = "II";
            else if (cx2 < 0 && cy2 < 0) cuadrante2 = "III";
            else if (cx2 > 0 && cy2 < 0) cuadrante2 = "IV";
            else if (cx2 == 0 && cy2 == 0) cuadrante2 = "Origen";
            else if (cx2 == 0) cuadrante2 = "Eje Y";
            else cuadrante2 = "Eje X";

            String punto1 = "(" + cx1 + ", " + cy1 + ")";
            String punto2 = "(" + cx2 + ", " + cy2 + ")";
            resultados.setText("Punto 1: " + punto1 + "Cuadrante: " + cuadrante1 +
                    "\nPunto 2: " + punto2 + "Cuadrante: " + cuadrante2);
    }
    private void generarPuntos(int cuadrante) {
        double x = 0, y = 0;
        switch (cuadrante) {
                case 1:
                    x = Math.random() * 100 + 1;
                    y = Math.random() * 100 + 1;
                    break;
                case 2:
                    x = -1 * (Math.random() * 100 + 1);
                    y = Math.random() * 100 + 1;
                    break;
                case 3:
                    x = -1 * (Math.random() * 100 + 1);
                    y = -1 * (Math.random() * 100 + 1);
                    break;
                case 4:
                    x = Math.random() * 100 + 1;
                    y = -1 * (Math.random() * 100 + 1);
                    break;

        }
            x1.setText(String.format("%.2f", x));
            y1.setText(String.format("%.2f", y));
            X2.setText(String.format("%.2f", x + 10));
            y2.setText(String.format("%.2f", y + 10));
            resultados.setText("Puntos generados en cuadrante " + cuadrante);
    }
}
