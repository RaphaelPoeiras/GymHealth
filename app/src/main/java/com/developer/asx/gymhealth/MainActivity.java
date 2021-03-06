package com.developer.asx.gymhealth;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final DBAdapter db = new DBAdapter(this);

        Button btDieta = (Button) findViewById(R.id.btDieta);
        Button btExercicios = (Button) findViewById(R.id.btExercicio);
        Button btCadDieta = (Button) findViewById(R.id.btCadAlimento);
        Button btCadExercicios = (Button) findViewById(R.id.btCadExercicio);

        ImageView img= (ImageView) findViewById(R.id.imageView);
        img.setImageResource(R.drawable.gym);

        btDieta.setOnClickListener(new Button.OnClickListener() {


            public void onClick(View v) {

                db.open();

                Cursor c = db.BuscarAlimentos();

                if (c.moveToFirst()){
                    do {
                        Toast.makeText(MainActivity.this, c.getString(1), Toast.LENGTH_LONG).show();
                    } while(c.moveToNext());
                }

                db.close();
            }
        });

        btExercicios.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                db.open();

                Cursor c = db.BuscarExercicios();

                if (c.moveToFirst()){
                    do {
                        Toast.makeText(MainActivity.this, c.getString(1), Toast.LENGTH_LONG).show();
                    } while(c.moveToNext());
                }

                db.close();
            }
        });

        btCadDieta.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(v.getContext(), CadAliementoActivity.class);
                startActivity(it);
            }
        });

        btCadExercicios.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(v.getContext(), CadExercicioActivity.class);
                startActivity(it);
            }
        });
    }
}
