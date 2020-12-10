package com.example.findafriend;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private String nombre;
    private EditText nombreText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nombreText = findViewById(R.id.txt_nombre);
    }

    public void entrarMapa(View view){
        nombre= nombreText.getText().toString();
        System.out.println(nombre);
        if(nombre.equals("")){
            AlertDialog alertDialog = new
                    AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("Alerta");
            alertDialog.setMessage("Por favor ingrese un nombre para entrar");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL,"OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });alertDialog.show();
        }else{
            Intent intent= new Intent(getBaseContext(),MapsActivity.class);
            intent.putExtra("nombre",nombre);
            startActivity(intent);
        }


    }


}