package com.developer.asx.gymhealth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CadAliementoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_aliemento);
        final DBAdapter db = new DBAdapter(this);

        final EditText editText = (EditText) findViewById(R.id.txtNomeAlimento);
        Button btSalvar = (Button) findViewById(R.id.btCad);

        btSalvar.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                db.open();
                db.insertAlimento(editText.getText().toString());
                db.close();
            }
        });
    }
}
