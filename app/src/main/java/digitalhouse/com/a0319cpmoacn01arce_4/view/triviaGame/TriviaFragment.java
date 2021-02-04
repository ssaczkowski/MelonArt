package digitalhouse.com.a0319cpmoacn01arce_4.view.triviaGame;


import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import digitalhouse.com.a0319cpmoacn01arce_4.R;
import digitalhouse.com.a0319cpmoacn01arce_4.controller.CancionController;
import digitalhouse.com.a0319cpmoacn01arce_4.model.Cancion;
import digitalhouse.com.a0319cpmoacn01arce_4.model.Data;
import digitalhouse.com.a0319cpmoacn01arce_4.util.CallBackGeneric;
import digitalhouse.com.a0319cpmoacn01arce_4.util.ContextUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class TriviaFragment extends Fragment   {
    private RecyclerView recyclerView;
    private TriviaAdapter triviaAdapter;
    private TextView messageTrivia;
    private List<Cancion> listaDeCanciones;
    public static MediaPlayer reproductorMusica;
    private Cancion cancionRandom;
    private Random random = new Random();
    private String listaGenero;


    public TriviaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trivia, container, false);
        Bundle bundle = getArguments();
        listaGenero = bundle.getString("lista") + "/tracks";

        messageTrivia = view.findViewById(R.id.message_trivia);

        recyclerView = view.findViewById(R.id.recyclerViewTrivia);
        LinearLayoutManager layoutmanager = new LinearLayoutManager(container.getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutmanager);

        listaDeCanciones = new ArrayList<>();

        ContextUtil.showLoading(getContext(),"",getString(R.string.loading),false);
        CancionController cancionController = new CancionController();
        cancionController.getCanciones(new CallBackGeneric<Data>() {
            @Override
            public void onFinish(Data data) {
                if (data.getData().size() > 4 ) {

                    for (int i = 0; i < 4; i++) {
                        int randomNumber = random.nextInt(data.getData().size());
                        listaDeCanciones.add(data.getData().get(randomNumber));

                    }

                    cargarCanciones();
                    messageTrivia.setText(getString(R.string.message_trivia));

                    ContextUtil.dismissLoading();

            }

            }
        },listaGenero);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onStop() {
        super.onStop();

        reproductorMusica.stop();
        reproductorMusica = null;

    }

    public void cargarCanciones(){
        reproductorMusica = new MediaPlayer();
        try {
            int randomNumber = random.nextInt(listaDeCanciones.size());

            cancionRandom = listaDeCanciones.get(randomNumber);

            reproductorMusica.setDataSource(cancionRandom.getPreview());
            reproductorMusica.prepare();
            reproductorMusica.start();

        } catch (IOException e) {
            e.getStackTrace();
        } catch (IllegalStateException f){
            f.getStackTrace();
        }

        triviaAdapter = new TriviaAdapter(listaDeCanciones,(TriviaAdapter.TriviaOnItemSelectedListener) getActivity(),cancionRandom);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),2, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(triviaAdapter);

    }





}
