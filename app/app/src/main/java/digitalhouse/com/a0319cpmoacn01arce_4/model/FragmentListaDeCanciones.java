package digitalhouse.com.a0319cpmoacn01arce_4.model;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import digitalhouse.com.a0319cpmoacn01arce_4.R;
import digitalhouse.com.a0319cpmoacn01arce_4.controller.CancionController;
import digitalhouse.com.a0319cpmoacn01arce_4.util.CallBackGeneric;


public class FragmentListaDeCanciones extends Fragment {

    private CancionAdapter cancionAdapter;
    private List<Cancion> listaDeCanciones;
    public FragmentListaDeCanciones() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fragment_lista_de_canciones, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewListaDeCanciones);
        LinearLayoutManager layoutmanager = new LinearLayoutManager(container.getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutmanager);


        listaDeCanciones = new ArrayList<>();

        cancionAdapter = new CancionAdapter(listaDeCanciones,(CancionAdapter.OnItemSelectedListener) getActivity());
        recyclerView.setAdapter(cancionAdapter);

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {

        inflater.inflate(R.menu.search_menu,menu);

        MenuItem buscarItem = menu.findItem(R.id.action_search);
        SearchView searchView   = (SearchView) buscarItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String texto) {
                CancionController cancionController = new CancionController();
                cancionController.getCanciones(new CallBackGeneric<Data>() {
                    @Override
                    public void onFinish(Data data) {
                        if (data.getData().size() != 0) {
                            for (int i = 0; i < data.getData().size(); i++) {

                                listaDeCanciones.add(data.getData().get(i));

                            }

                        }

                    }
                },"?q="+texto);
                cancionAdapter.getFilter().filter(texto);
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }


}
