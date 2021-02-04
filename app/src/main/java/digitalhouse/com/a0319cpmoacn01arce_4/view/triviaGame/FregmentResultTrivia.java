package digitalhouse.com.a0319cpmoacn01arce_4.view.triviaGame;


import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import digitalhouse.com.a0319cpmoacn01arce_4.R;
import digitalhouse.com.a0319cpmoacn01arce_4.model.Cancion;
import digitalhouse.com.a0319cpmoacn01arce_4.model.Data;
import digitalhouse.com.a0319cpmoacn01arce_4.util.ContextUtil;
import digitalhouse.com.a0319cpmoacn01arce_4.view.detailPage.DetalleCancionFragment;
import digitalhouse.com.a0319cpmoacn01arce_4.view.gamePage.GeneroFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class FregmentResultTrivia extends Fragment implements SeekBar.OnSeekBarChangeListener, GeneroFragment.GeneroInterface{

    private static final String ARG_TITULO = "titulo";
    private static final String ARG_ALBUM = "album";
    private static final String ARG_ARTISTA = "artista";
    private static final String ARG_IMAGEN = "imagen";
    private static final String ARG_PREVIEW = "preview";
    private static final String ARG_ID = "id";
    private static final String ARG_LISTA_GENERO = "listaGenero";
    private static final String ARG_POSICION = "posicion";
    private static final String ARG_DATA_CON_LISTA_DE_CANCIONESR = "dataConCanciones";

    public static final String TAG = "DetalleCancionFragment";

    private GeneroFragment.GeneroInterface generoInterface;
    private Button favorito;
    private Button dislike;
    private Button atraz;
    private Button adelante;
    private SeekBar seekBar;
    private String mTitulo;
    private String mAlbum;
    private String mArtista;
    private String mImagen;
    private String mPreview;
    private String mListaGenero;
    private String mid;
    private String listaGenero;
    private Data mData;
    private Integer mPosicion;
    private Handler mSeekbarUpdateHandler;
    private DetalleCancionFragment.OnFragmentDetalleCancionInteractionListener mListener;
    private MediaPlayer reproductorMusica;
    private Runnable mUpdateSeekbar;
    private FirebaseDatabase mDatabase;
    private DatabaseReference miDatabase;
    private Integer posicion;
    private Data dataConCanciones;
    private List<Cancion> cancionlist = new ArrayList<>();
    private List<Cancion> listaDeCanciones;
    private List<String> listaFavoritos = new ArrayList<>();
    public FregmentResultTrivia() {
        // Required empty public constructor
    }


    public static FregmentResultTrivia newInstance(String album, String titulo,String artista,
                                                     String imagen,String preview,String id,Data dataConListaDeCanciones,Integer posicion,String listaGenero)  {
        FregmentResultTrivia fragment = new FregmentResultTrivia();
        Bundle args = new Bundle();
        args.putString(ARG_ALBUM, album);
        args.putString(ARG_ARTISTA, artista);
        args.putString(ARG_IMAGEN, imagen);
        args.putString(ARG_PREVIEW, preview);
        args.putString(ARG_TITULO, titulo);
        args.putString(ARG_ID,id);
        args.putString(ARG_LISTA_GENERO,listaGenero);

        args.putSerializable(ARG_DATA_CON_LISTA_DE_CANCIONESR, dataConListaDeCanciones);
        args.putInt(ARG_POSICION,posicion);


        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAlbum = getArguments().getString(ARG_ALBUM);
            mArtista = getArguments().getString(ARG_ARTISTA);
            mImagen = getArguments().getString(ARG_IMAGEN);
            mPreview = getArguments().getString(ARG_PREVIEW);
            mTitulo = getArguments().getString(ARG_TITULO);
            mid = getArguments().getString(ARG_ID);
            mData = (Data) getArguments().getSerializable(ARG_DATA_CON_LISTA_DE_CANCIONESR);
            mPosicion = getArguments().getInt(ARG_POSICION);
            mListaGenero = getArguments().getString(ARG_LISTA_GENERO);
        }
        System.out.println("a");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fregment_result_trivia, container, false);

        ImageView image = view.findViewById(R.id.rimagen);
        favorito = view.findViewById(R.id.rbtnFavor);
        miDatabase = FirebaseDatabase.getInstance().getReference();
        listaFavoritos = new ArrayList<>();

        dislike = view.findViewById(R.id.rbtnDisLike);
        dislike.setVisibility(View.INVISIBLE);

        favorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFavorite();

            }
        });

        dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFavorite();
            }
        });


        checkFavorite();

        cancionlist.addAll(mData.getData());




        Button seguirJugando = view.findViewById(R.id.btnSeguirJugando);
        seguirJugando.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickGenero(mListaGenero);

            }
        });





        Glide.with(getContext())
                .load(mImagen)
                .into(image);

        TextView album = view.findViewById(R.id.ralbum);
        album.setText(mAlbum);
        TextView artista = view.findViewById(R.id.rartista);
        artista.setText(mArtista);
        TextView titulo = view.findViewById(R.id.rtitulo);
        titulo.setText(mTitulo);

        mSeekbarUpdateHandler = new Handler();
        mUpdateSeekbar = new Runnable() {
            @Override
            public void run() {
                if(seekBar != null && reproductorMusica != null) {
                    seekBar.setProgress(reproductorMusica.getCurrentPosition());
                    mSeekbarUpdateHandler.postDelayed(this::run, 50);
                }
            }
        };

        reproductorMusica = new MediaPlayer();
        try {
            reproductorMusica.setDataSource(mPreview);
            reproductorMusica.prepare();
            reproductorMusica.start();
            mSeekbarUpdateHandler.postDelayed(mUpdateSeekbar, 0);
        } catch (IOException e) {
            e.getStackTrace();
        }
        seekBar = view.findViewById(R.id.rseekbar);
        seekBar.setMax(30000);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(reproductorMusica != null && fromUser){
                    reproductorMusica.seekTo(progress);
                }
            }
        });



        return view;





    }




    @Override
    public void onProgressChanged(SeekBar seekBar, int progress,
                                  boolean fromUser) {
        if(fromUser){
            reproductorMusica.seekTo(progress);
            seekBar.setProgress(progress);
        }
        else{
            // the event was fired from code and you shouldn't call player.seekTo()
        }

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStop() {
        super.onStop();

        reproductorMusica.stop();
        mSeekbarUpdateHandler.removeCallbacks(mUpdateSeekbar);
        reproductorMusica = null;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DetalleCancionFragment.OnFragmentDetalleCancionInteractionListener) {
            mListener = (DetalleCancionFragment.OnFragmentDetalleCancionInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        this.generoInterface = (GeneroFragment.GeneroInterface) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClickGenero(String listaGenero) {

        Bundle bundle = new Bundle();
        bundle.putString("lista",listaGenero);

        TriviaFragment triviaFragment = new TriviaFragment();
        triviaFragment.setArguments(bundle);
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.contenedorTrivia,triviaFragment).addToBackStack(null);
        transaction.commit();

    }

    public interface OnFragmentDetalleCancionInteractionListener {
    }


    private void addFavorite() {




        DatabaseReference ref = miDatabase.child("Listas").child(ContextUtil.getUserId()).child("Favoritos");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() == null){
                    miDatabase.child("Listas").child(ContextUtil.getUserId()).child("Favoritos").child("0").setValue(listaFavoritos);
                    listaFavoritos.add(mid);
                    miDatabase.child("Listas").child(ContextUtil.getUserId()).child("Favoritos").setValue(listaFavoritos);
                    favorito.setEnabled(false);
                    favorito.setVisibility(View.INVISIBLE);
                    dislike.setEnabled(true);
                    dislike.setVisibility(View.VISIBLE);
                }else {
                    listaFavoritos = (List<String>) dataSnapshot.getValue();
                    Boolean b =false;
                    for (String a :listaFavoritos) {
                        if (a.equals(mid)) {
                            Toast.makeText(getContext(), "Esta canción ya está en la lista", Toast.LENGTH_SHORT).show();
                            b = false;
                            return;
                        } else {
                            b = true;

                        }
                    }
                    if (b == true){
                        listaFavoritos.add(mid);
                        miDatabase.child("Listas").child(ContextUtil.getUserId()).child("Favoritos").setValue(listaFavoritos);
                        favorito.setEnabled(false);
                        favorito.setVisibility(View.INVISIBLE);
                        dislike.setEnabled(true);
                        dislike.setVisibility(View.VISIBLE);

                    }else{
                        return;





                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }

    private void checkFavorite() {




        DatabaseReference ref = miDatabase.child("Listas").child(ContextUtil.getUserId()).child("Favoritos");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() == null){
                    miDatabase.child("Listas").child(ContextUtil.getUserId()).child("Favoritos").child("0").setValue(listaFavoritos);
                }else {
                    listaFavoritos = (List<String>) dataSnapshot.getValue();
                    Boolean b =false;
                    for (String a :listaFavoritos){
                        if (a.equals(mid)){
                            b=false;
                            return;
                        }else {
                            b=true;

                        }

                    }



                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }

    private void deleteFavorite() {




        DatabaseReference ref = miDatabase.child("Listas").child(ContextUtil.getUserId()).child("Favoritos");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() == null){
                    miDatabase.child("Listas").child(ContextUtil.getUserId()).child("Favoritos").child("0").setValue(listaFavoritos);
                }else {
                    listaFavoritos = (List<String>) dataSnapshot.getValue();
                    Integer b =null;

                    for (int i = 0; i <listaFavoritos.size() ; i++) {
                        if (listaFavoritos.get(i).equals(mid)){
                            b = i;
                        }

                    }

                    if (b != null){
                        int c = b;
                        listaFavoritos.remove(c);
                        miDatabase.child("Listas").child(ContextUtil.getUserId()).child("Favoritos").setValue(listaFavoritos);
                        favorito.setEnabled(true);
                        favorito.setVisibility(View.VISIBLE);
                        dislike.setEnabled(false);
                        dislike.setVisibility(View.INVISIBLE);

                    }else{
                        return;
                    }
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }

}
