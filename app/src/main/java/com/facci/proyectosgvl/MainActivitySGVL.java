package com.facci.proyectosgvl;

import android.app.AlertDialog;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivitySGVL extends AppCompatActivity {

    DBHelper dbSQLite;

    EditText txtnom,txtap,txtnacimiento,txtrecinEle,Id;

    Button btnInsertar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_sgvl);
        dbSQLite = new DBHelper(this);
    }
    public void InsetarClick(View v) {
        txtnom = (EditText) findViewById(R.id.txtNombre);
        txtap = (EditText) findViewById(R.id.txtApellido);
        txtrecinEle = (EditText) findViewById(R.id.txtRecintoElectoral);
        txtnacimiento = (EditText) findViewById(R.id.txtanoNacimiento);

        boolean Reservardato = dbSQLite.Insertar(txtnom.getText().toString(),txtap.getText().toString(),txtrecinEle.getText().toString(),Integer.parseInt(txtnacimiento.getText().toString()));

        if (Reservardato) {
            Toast.makeText(MainActivitySGVL.this, "Ingresados Datos", Toast.LENGTH_SHORT).show();
        }else{Toast.makeText(MainActivitySGVL.this,"Error no ha ingresado datos",Toast.LENGTH_SHORT).show();}
    }
    public void VerTodosClick(View v) {
        Cursor res = dbSQLite.VerTodos();

        if (res.getCount() == 0) {
            Mensaje("Error","No se encontraron registros");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            buffer.append("Id : "+res.getString(0)+"\n");
            buffer.append("Nombre : "+res.getString(1)+"\n");
            buffer.append("Apellido : "+res.getString(2)+"\n");
            buffer.append("Recinto Electoral : "+res.getString(3)+"\n");
            buffer.append("Ano de Nacimiento : "+res.getInt(4)+"\n\n");
        }
        Mensaje("Registros",buffer.toString());
    }

    private void Mensaje (String titulo, String Mensaje) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(titulo);
        builder.setMessage(Mensaje);
        builder.show();
    }
    public void ModificarClick (View v) {
        txtnom = (EditText) findViewById(R.id.txtNombre);
        txtap = (EditText) findViewById(R.id.txtApellido);
        txtrecinEle = (EditText) findViewById(R.id.txtRecintoElectoral);
        txtnacimiento = (EditText) findViewById(R.id.txtanoNacimiento);
        Id = (EditText) findViewById(R.id.txtID);


        boolean ModificandoDatos = dbSQLite.ModificarRegistro(Id.getText().toString(),txtnom.getText().toString(),txtap.getText().toString(),txtrecinEle.getText().toString(),Integer.parseInt(txtnacimiento.getText().toString()));
        if(ModificandoDatos)
            Toast.makeText(MainActivitySGVL.this,"Modificado",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(MainActivitySGVL.this,"Error",Toast.LENGTH_SHORT).show();

    }
    public void EliminarClick (View v) {
        Id = (EditText) findViewById(R.id.txtID);

        Integer registrosEliminados = dbSQLite.Eliminar(Id.getText().toString());

        if(registrosEliminados > 0 ){
            Toast.makeText(MainActivitySGVL.this,"Registro borrado",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(MainActivitySGVL.this,"ERROR: Registro no borrado",Toast.LENGTH_SHORT).show();
        }
    }
}
