package digitalhouse.com.a0319cpmoacn01arce_4;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import digitalhouse.com.a0319cpmoacn01arce_4.controller.CancionController;
import digitalhouse.com.a0319cpmoacn01arce_4.model.Cancion;
import digitalhouse.com.a0319cpmoacn01arce_4.model.CancionAdapter;
import digitalhouse.com.a0319cpmoacn01arce_4.model.Data;
import digitalhouse.com.a0319cpmoacn01arce_4.model.DetalleCancionFragment;
import digitalhouse.com.a0319cpmoacn01arce_4.model.FragmentListaDeCanciones;
import digitalhouse.com.a0319cpmoacn01arce_4.util.CallBackGeneric;

public class MainActivity extends AppCompatActivity implements CancionAdapter.OnItemSelectedListener, DetalleCancionFragment.OnFragmentDetalleCancionInteractionListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //INSTANCIADO DE FRAGMENT PARA TESTEAR
        FragmentListaDeCanciones fragmentListaDeCanciones = new FragmentListaDeCanciones();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.contenedor, fragmentListaDeCanciones);
        transaction.commit();
        //FIN DEL INSTANCIADO
        Toast.makeText(this, "EQUIPO 4", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onItemClick(Cancion cancion) {

        DetalleCancionFragment detalleCancionFragment = DetalleCancionFragment.newInstance(cancion.getAlbum().getNombre(),cancion.getTitulo(),cancion.getArtista().getNombre(),cancion.getArtista().getImagen(),cancion.getPreview());
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.contenedor, detalleCancionFragment);
        transaction.commit();

    }

}
