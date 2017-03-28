package mislugares.example.com.mislugares;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;


/**
 * Created by usuario1 on 03/03/2017.
 */

public class VistaLugar extends AppCompatActivity {

    long id;
    Lugar l = new Lugar();

    final static int RESULTADO_EDITAR = 1 ;
    final static int RESULTADO_GALERIA = 2 ;
    final static int RESULTADO_FOTO = 3 ;
    private String KEY_foto = "foto";
    private Uri uriFoto;
    SharedPreferences.Editor editor;
    SharedPreferences settings;
    ImageView imageView;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_lugar);



        id = (int) getIntent().getLongExtra("id", 0);
        l = Lugares.vectorLugares.get((int) id);
        KEY_foto +=id;

        imageView = (ImageView)findViewById(R.id.foto);
        ActualizarVistas();


        settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = settings.edit();
        String foto = settings.getString(KEY_foto,null);


    if(foto != null){
        imageView.setImageBitmap(StringToBitmap(foto));

    }

    }
    public void ActualizarVistas(){
        TextView nombre = (TextView) findViewById(R.id.setNombreLugar);
        nombre.setText(l.getNombre());

        //ImageView logo_tipo = (ImageView) findViewById(R.id.logo_tipo);
        //logo_tipo.setImageResource(l.getTipo().getRecurso());

        TextView tipo = (TextView) findViewById(R.id.setTipo);
        tipo.setText(l.getTipo().getTexto());

        TextView direccion = (TextView) findViewById(R.id.setCalle);
        direccion.setText(l.getDireccion());

        TextView telefono = (TextView) findViewById(R.id.setTelefono);
        telefono.setText(Integer.toString(l.getTelefono()));

        TextView url = (TextView) findViewById(R.id.setUrl);
        url.setText(l.getUrl());

        TextView comentario = (TextView) findViewById(R.id.setInfo);
        comentario.setText(l.getComentario());

        TextView fecha = (TextView) findViewById(R.id.setFecha);
        fecha.setText(l.getFecha());


        TextView hora = (TextView) findViewById(R.id.setHora);
        hora.setText(l.getHour());

        RatingBar valoracion = (RatingBar) findViewById(R.id.SetValoracion);
        valoracion.setRating(l.getValoracion());

        valoracion.setOnRatingBarChangeListener(

                new RatingBar.OnRatingBarChangeListener() {

                    public void onRatingChanged(RatingBar ratingBar, float valor, boolean fromUser) {

                        l.setValoracion(valor);
                    }


                });

        ponerFoto(imageView, l.getFoto());

    }

    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.vista_lugar, menu);

        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item) {

        final int Id = item.getItemId();

        switch (Id) {

            case R.id.accionCompartir:

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, l.getNombre()+ " - " + l.getUrl());
                startActivityForResult(intent,RESULTADO_EDITAR);

                return true;

            case R.id.accionLlegar:

                verMapa(null);
                return true;

            case R.id.accionEditar:

                Intent i = new Intent(this, EdicionLugar.class);
                startActivity(i);
                return true;

            case R.id.accionBorrar:

                new AlertDialog.Builder(this)
                        .setTitle("Borado de lugar")
                        .setMessage("Desea borrar el lugar?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener(){

                            public void onClick(DialogInterface dialog, int wichButton){


                                Lugares.borrar((int)id);
                                Log.e("valor de ","id"+id);
                                finish();

                            }
                        })
                        .setNegativeButton("Cancelar", null)
                        .show();


                return true;

            default:
                return super.onOptionsItemSelected(item);
        }


    }


    @Override
    public void onRestart(){

        super.onRestart();
        finish();
        startActivity(getIntent());


    }

    public void EliminarFoto(View view){
        l.setFoto(null);
        ponerFoto(imageView,null);

    }

    public void TomarFoto(View view){

//        //Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//        uriFoto = Uri.fromFile(new File(Environment.getExternalStorageDirectory()+ File.separator +"img_"+(System.currentTimeMillis()/1000) + ".jpg"));
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriFoto);
//      //  startActivity(intent);
//        startActivityForResult(intent, RESULTADO_FOTO);

            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }




    }
    public void galeria(View view){

             Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
             intent.addCategory(Intent.CATEGORY_OPENABLE);
             intent.setType("image/*");
             startActivityForResult(intent, RESULTADO_GALERIA);

   }
   protected void onActivityResult(int requestCode, int resultCode, Intent data ){



       if (requestCode == RESULTADO_EDITAR){
           ActualizarVistas();
           findViewById(R.id.scrollView1).invalidate();

       }else if(requestCode == RESULTADO_GALERIA && resultCode == Activity.RESULT_OK){

           Log.e("entramos en "," GALERIA");

           try{
               final Uri imageUri = data.getData();
               final InputStream imageStream = getContentResolver().openInputStream(imageUri);
               final Bitmap SelectedImage = BitmapFactory.decodeStream(imageStream);
               final Bitmap img = Bitmap.createScaledBitmap(SelectedImage,500,500,false);
               imageView.setImageBitmap(img);

               String base64img = bitmaptoBase64(img);
               editor.putString(KEY_foto, base64img);
               editor.commit();



           }catch(FileNotFoundException e){

           }


       }else if(requestCode == RESULTADO_FOTO && resultCode == RESULT_OK && l !=null && uriFoto != null){



           Log.e("entramos en "," opcionCamara");

            l.setFoto(uriFoto.toString());
            ponerFoto(imageView,l.getFoto());

       }


   }
   public Bitmap StringToBitmap(String s){

       try{
           byte[] encodeByte = Base64.decode(s, Base64.DEFAULT);
           return BitmapFactory.decodeByteArray(encodeByte,0,encodeByte.length);

       }catch(NullPointerException e){
           return null;
       }catch(OutOfMemoryError ee){
           return null;
       }


   }
private String bitmaptoBase64(Bitmap img){
    try{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.PNG,100,baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b,Base64.DEFAULT);
        return temp;

    }catch(NullPointerException e){
        return null;
    }catch(OutOfMemoryError ee){
        return null;

    }


}
public static Bitmap reduceBitmap(Context contexto,String uri,int maxAncho, int maxAlto){

    try{
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds =  true;
        BitmapFactory.decodeStream(contexto.getContentResolver().openInputStream(Uri.parse(uri)),null,options);
        options.inSampleSize = (int) Math.max(Math.ceil(options.outWidth / maxAncho), Math.ceil(options.outHeight/maxAlto));
        return BitmapFactory.decodeStream(contexto.getContentResolver().openInputStream(Uri.parse(uri)), null , options);

    }catch(FileNotFoundException e){
        Toast.makeText(contexto, "Fichero/recurso no encontrado",Toast.LENGTH_LONG).show();
        e.printStackTrace();
        return null;

    }


}
   protected void ponerFoto(ImageView imageView, String uri){
       if (uri != null){

           imageView.setImageBitmap(reduceBitmap(this,uri,1024,1024));

       }else{

           imageView.setImageBitmap(null);
       }

   }



public void verMapa(View view){
    Uri uri;

    double lat = l.getPosicion().getLatitud();
    double lon = l.getPosicion().getLongitud();

    if(lon != 0 || lat != 0 ){

        uri = Uri.parse("geo:" + lat + "," + lon);

    }else{

        uri = Uri.parse("geo:0,0?q" + l.getDireccion());

    }
    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
    startActivity(intent);

}
public void llamTel(View view){

    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+l.getTelefono())));

}
public void pgWeb(View view){
    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(l.getUrl())));

}


}







