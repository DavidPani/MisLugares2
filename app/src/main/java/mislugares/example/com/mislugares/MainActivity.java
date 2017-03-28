package mislugares.example.com.mislugares;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;



public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AdaptadorLugares adaptador = new AdaptadorLugares(this);

        ListView listview = (ListView)findViewById(R.id.listViewPrincipal);
        listview.setAdapter(adaptador);
        listview.setOnItemClickListener(this);
    }

    public void LanzarAcercaDe(View view) {
        Intent i = new Intent(this, AcercaDe.class);
        startActivity(i);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true; //True => el menu esta visible.

    }
@Override
public boolean onOptionsItemSelected(MenuItem item) {

    int id = item.getItemId();

    if (id == R.id.AcercaDe) {
        LanzarAcercaDe(null);
        return true;

    }
    if(id==R.id.menu_buscar){
        lanzarVistaLugar(null);
        return true;

    }

    return super.onOptionsItemSelected(item);


}

public void lanzarVistaLugar(View view) {

    final EditText entrada = new EditText(this);
    entrada.setText("0");

    new AlertDialog.Builder(this)
            .setTitle("Seleccion de lugar")
            .setMessage("Indica su ID")
            .setView(entrada)
            .setPositiveButton("Ok", new DialogInterface.OnClickListener(){

                public void onClick(DialogInterface dialog , int wichButton){

                long id = Long.parseLong(entrada.getText().toString());

                   Intent i = new Intent(MainActivity.this, VistaLugar.class);
                   i.putExtra("id",id);

                   Log.e("valor de ","Intent"+i);
                   startActivity(i);


            }})
            .setNegativeButton("Cancelar", null)
            .show();

}

@Override
   public void onItemClick(AdapterView<?> parent, View vista , int posicion, long id){

        Intent i = new Intent(this, VistaLugar.class);
        i.putExtra("id", id);
        startActivity(i);
        Log.e("valor de ", "id "+id);


    }

    @Override
    public void onRestart(){

        super.onRestart();
        finish();
        startActivity(getIntent());


    }

}
