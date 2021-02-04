package digitalhouse.com.a0319cpmoacn01arce_4.view.userPage;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import digitalhouse.com.a0319cpmoacn01arce_4.R;
import digitalhouse.com.a0319cpmoacn01arce_4.controller.CancionController;
import digitalhouse.com.a0319cpmoacn01arce_4.model.Album;
import digitalhouse.com.a0319cpmoacn01arce_4.model.Artista;
import digitalhouse.com.a0319cpmoacn01arce_4.model.Cancion;
import digitalhouse.com.a0319cpmoacn01arce_4.model.Data;
import digitalhouse.com.a0319cpmoacn01arce_4.model.User;
import digitalhouse.com.a0319cpmoacn01arce_4.util.CallBackGeneric;
import digitalhouse.com.a0319cpmoacn01arce_4.util.ContextUtil;
import digitalhouse.com.a0319cpmoacn01arce_4.view.detailPage.DetalleCancionFragment;
import digitalhouse.com.a0319cpmoacn01arce_4.view.listasActivity.ListasActivity;
import digitalhouse.com.a0319cpmoacn01arce_4.view.searcher.CancionAdapter;


public class UserFragment extends Fragment implements CancionAdapter.OnItemSelectedListener {
    private List<Cancion> listaDeFavoritos;
    public static final String TAG = "UserFragment";
    private FirebaseAuth mAuth;
    private DatabaseReference miDatabase;
    private List<String> listaFavoritos = new ArrayList<>();
    private List<Cancion> listaDeCanciones = new ArrayList<>();
    RecyclerView recyclerView;
    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        miDatabase = FirebaseDatabase.getInstance().getReference();
        cargarFavoritos();

        ContextUtil.showLoading(getActivity(),"",getString(R.string.loading),false);



        View view = inflater.inflate(R.layout.fragment_user, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewFavoritos);
        LinearLayoutManager layoutmanager = new LinearLayoutManager(container.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutmanager);
        ImageView userImage = view.findViewById(R.id.imagenPerfil);
        TextView userName = view.findViewById(R.id.nombre);
        TextView userScore = userScore = view.findViewById(R.id.userScore);
        Integer score = ContextUtil.getScoreItem().getScore();
        userScore.setText(score.toString());


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Glide.with(getContext())
                    .load(user.getPhotoUrl() + "?type=large")
                    .into(userImage);
        userName.setText(user.getDisplayName());
        return view;



    }

    public void cargarFavoritos(){

        DatabaseReference ref = miDatabase.child("Listas").child(ContextUtil.getUserId()).child("Favoritos");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() == null){
                    miDatabase.child("Listas").child(ContextUtil.getUserId()).child("Favoritos").child("0").setValue(listaFavoritos);
                }else {
                    listaFavoritos = (List<String>) dataSnapshot.getValue();
                }

                for (String c:listaFavoritos ) {
                    CancionController controller= new CancionController();

                    controller.getCancionById(new CallBackGeneric<Cancion>() {
                        @Override
                        public void onFinish(Cancion cancion) {
                            listaDeCanciones.add(cancion);
                            cargarRecycler();
                        }
                    },"/track/" + c);


                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void cargarRecycler(){
        CancionAdapter cancionAdapter = new CancionAdapter(listaDeCanciones,(CancionAdapter.OnItemSelectedListener) this);
        recyclerView.setAdapter(cancionAdapter);
        ContextUtil.dismissLoading();

    }

    @Override
    public void onItemClick(Cancion cancion, Data data, Integer posicion) {
        DetalleCancionFragment detalleCancionFragment = DetalleCancionFragment.newInstance(cancion.getAlbum().getNombre(), cancion.getTitulo(), cancion.getArtista().getNombre(), cancion.getAlbum().getImagen(), cancion.getPreview(),cancion.getId(),data,posicion);
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.contenedor, detalleCancionFragment);
        transaction.commit();
    }
}
