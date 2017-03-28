package mislugares.example.com.mislugares;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by usuario1 on 21/02/2017.
 */

public class Lugares {

    protected static List<Lugar> vectorLugares = ejemploLugares();

    public Lugares(){

        vectorLugares = ejemploLugares();

    }
    static Lugar elemento (int id){

        return vectorLugares.get(id);
    }
    static void anyade ( Lugar lugar ){

        vectorLugares.add(lugar);

    }
    static int nuevo(){

        Lugar lugar= new Lugar();
        vectorLugares.add(lugar);
        return vectorLugares.size()-1;

    }
    static void borrar(int id){

        vectorLugares.remove(id);
    }
    public static int size(){

        return vectorLugares.size();

    }

public static ArrayList<Lugar> ejemploLugares(){

    ArrayList<Lugar> lugares = new ArrayList<Lugar>();

    lugares.add( new Lugar("Escuela politécnica superior de Gandía","C/ Paranimf, 1 46730 Gandia(Spain)",
            -0.166093, 38.995656,TipoLugar.EDUCACION,962849300,"http://www.epsg.upv.es","Uno de los mejores lugares para formarse.",3));
    lugares.add( new Lugar("Al de siempre","C/ Pl.Industrial - 4672, Benifla (Valencia)",
            -0.190642, 38.925857,TipoLugar.BAR,636472405,"","No te pierdas el arroz en calabaza",3));
    lugares.add( new Lugar("CursoAndroid.com"," CiberEspacio ",
            -0.0, 0.0,TipoLugar.EDUCACION,962849300,"http://androidcurso.com","Amplia tus conocimientos sobre android.",5));
    lugares.add( new Lugar("Barranco del infierno","Via verde del rio Serpis,. Villalonga(Valencia)",
            -0.295058, 38.867180,TipoLugar.NATURALEZA,0,"http://sosegaos.blogspot.com.es","Espectacular ruta para andar o ir en bici",4));
    lugares.add( new Lugar("La vital","Av/ de La Vital, 0 46701 Gandia(Valencia)",
            -0.170092, 38.9705949,TipoLugar.COMPRAS,962881070,"http://lavital.es","El típico centro comercial.",3));

    return lugares;

}
}
