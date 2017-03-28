package mislugares.example.com.mislugares;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by usuario1 on 14/03/2017.
 */

public class EdicionLugar extends AppCompatActivity {

    long id;
    private EditText nombre;
    private Spinner tipo;
    private EditText direccion;
    private EditText telefono;
    private EditText url;
    private EditText comentario;

    final Lugar l = Lugares.vectorLugares.get((int) id);


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.edicion_lugar);

        id = getIntent().getLongExtra("id", 0);



        nombre = (EditText) findViewById(R.id.ELnombre);
        nombre.setText(l.getNombre());


        direccion = (EditText) findViewById(R.id.ELdireccion);
        direccion.setText(l.getDireccion());

        telefono = (EditText) findViewById(R.id.ELtelefono);
        telefono.setText(Integer.toString(l.getTelefono()));

        url = (EditText) findViewById(R.id.ELurl);
        url.setText(l.getUrl());

        comentario = (EditText) findViewById(R.id.ELcomentario);
        comentario.setText(l.getComentario());

        tipo = (Spinner) findViewById(R.id.ELtipo);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, TipoLugar.getNombres());
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipo.setAdapter(adaptador);
        tipo.setSelection(l.getTipo().ordinal());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edicion,menu);
        return true; //True => el menu esta visible.

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        final int id = item.getItemId();


        if (id == R.id.MEAceptar){

            new AlertDialog.Builder(this)
                    .setTitle("Modificacion")
                    .setMessage("Modificado Correctamente")

                    .setPositiveButton("Ok", new DialogInterface.OnClickListener(){

                        public void onClick(DialogInterface dialog , int wichButton){

                            modificacionDeDatos();

                              finish();

                        }})

                    .show();

        }

        if (id == R.id.MECancelar){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void modificacionDeDatos(){

        l.setNombre(nombre.getText().toString());
        l.setTipo(TipoLugar.values()[tipo.getSelectedItemPosition()]);
        l.setDireccion(direccion.getText().toString());
        l.setTelefono(Integer.parseInt(telefono.getText().toString()));
        l.setUrl(url.getText().toString());


        finish();





    }
}
