package digitalhouse.com.a0319cpmoacn01arce_4.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import digitalhouse.com.a0319cpmoacn01arce_4.MainActivity;
import digitalhouse.com.a0319cpmoacn01arce_4.R;

public class CancionAdapter extends RecyclerView.Adapter implements Filterable {
    private List <Cancion> listaDeCanciones;
    private List <Cancion> listaDeCancionesCompleta;
    private OnItemSelectedListener onItemSelectedListener;

    public CancionAdapter(List<Cancion> listaDeCanciones,OnItemSelectedListener onItemSelectedListener) {
        this.listaDeCanciones = listaDeCanciones;
        this.listaDeCancionesCompleta = new ArrayList<>(listaDeCanciones);
        this.onItemSelectedListener = onItemSelectedListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflador = LayoutInflater.from(viewGroup.getContext());
        View view = inflador.inflate(R.layout.celda_cancion, viewGroup,false);
        CancionViewHolder cancionViewHolder = new CancionViewHolder(view,onItemSelectedListener);
        return cancionViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Cancion cancionDeLalista = this.listaDeCanciones.get(i);
        CancionViewHolder cancionViewHolder = (CancionViewHolder) viewHolder;
        cancionViewHolder.bindCancion(cancionDeLalista);

    }

    @Override
    public int getItemCount() {

        return this.listaDeCanciones.size();
    }

    private class CancionViewHolder extends RecyclerView.ViewHolder {
        private ImageView imagen;
        private TextView titulo;
        private TextView album;
        private TextView artista;
        private Cancion cancionDeLaCelda;

        public CancionViewHolder(@NonNull View itemView,final OnItemSelectedListener onItemSelectedListener) {
            super(itemView);
            imagen = itemView.findViewById(R.id.imgFrgListaCanciones);
            titulo = itemView.findViewById(R.id.txtFrgListaCancionesTitulo);
            album = itemView.findViewById(R.id.txtFrgListaCancionesAlbum);
            artista = itemView.findViewById(R.id.txtFrgListaCancionesArtista);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    onItemSelectedListener.onItemClick(listaDeCanciones.get(getAdapterPosition()));

                }
            });

        }

        public void bindCancion(Cancion cancionDeLaCelda){
            this.cancionDeLaCelda = cancionDeLaCelda;
            Glide.with(this.itemView.getContext())
                    .load(cancionDeLaCelda.getArtista().getImagen())
                    .into(imagen);
            this.titulo.setText(cancionDeLaCelda.getTitulo());
            this.album.setText(cancionDeLaCelda.getAlbum().getNombre());
            this.artista.setText(cancionDeLaCelda.getArtista().getNombre());

        }
    }

    @Override
    public Filter getFilter() {
        return cancionFilter;
    }

    private Filter cancionFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<Cancion> listaFiltrada = new ArrayList<>();
            if (constraint == null || constraint.length() == 0 ){
                listaFiltrada.addAll(listaDeCancionesCompleta);
            }else{
                String patronFiltro = constraint.toString().toLowerCase().trim();
                for (Cancion item : listaDeCancionesCompleta){
                    if(item.getTitulo().toLowerCase().contains(patronFiltro)){
                        listaFiltrada.add(item);
                    }
                }
            }

            FilterResults resultados = new FilterResults();
            resultados.values = listaFiltrada;

            return resultados;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listaDeCanciones.clear();
            listaDeCanciones.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public interface OnItemSelectedListener {
        void onItemClick(Cancion cancion);
    }


}
