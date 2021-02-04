package digitalhouse.com.a0319cpmoacn01arce_4.model;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.IOException;

import digitalhouse.com.a0319cpmoacn01arce_4.R;

public class DetalleCancionFragment extends Fragment {

    private static final String ARG_TITULO = "titulo";
    private static final String ARG_ALBUM = "album";
    private static final String ARG_ARTISTA = "artista";
    private static final String ARG_IMAGEN = "imagen";
    private static final String ARG_PREVIEW = "preview";


    private String mTitulo;
    private String mAlbum;
    private String mArtista;
    private String mImagen;
    private String mPreview;

    private OnFragmentDetalleCancionInteractionListener mListener;
    private MediaPlayer reproductorMusica;

    public DetalleCancionFragment() {
        // Required empty public constructor
    }


    public static DetalleCancionFragment newInstance(String album, String titulo,String artista,String imagen,String preview) {
        DetalleCancionFragment fragment = new DetalleCancionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ALBUM, album);
        args.putString(ARG_ARTISTA, artista);
        args.putString(ARG_IMAGEN, imagen);
        args.putString(ARG_PREVIEW, preview);
        args.putString(ARG_TITULO, titulo);

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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detalle_cancion, container, false);

        ImageView image = view.findViewById(R.id.imagen);

        Glide.with(getContext())
                .load(mImagen)
                .into(image);

        TextView album = view.findViewById(R.id.album);
        album.setText(mAlbum);
        TextView artista = view.findViewById(R.id.artista);
        artista.setText(mArtista);
        TextView titulo = view.findViewById(R.id.titulo);
        titulo.setText(mTitulo);

        reproductorMusica = new MediaPlayer();
        try {
            reproductorMusica.setDataSource(mPreview);
            reproductorMusica.prepare();
            reproductorMusica.start();
        } catch (IOException e) {
           e.getStackTrace();
        }

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();

        reproductorMusica.stop();
        reproductorMusica = null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentDetalleCancionInteractionListener) {
            mListener = (OnFragmentDetalleCancionInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentDetalleCancionInteractionListener {
    }
}
