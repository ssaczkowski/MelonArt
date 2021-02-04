package digitalhouse.com.a0319cpmoacn01arce_4.view.triviaGame;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import digitalhouse.com.a0319cpmoacn01arce_4.R;
import digitalhouse.com.a0319cpmoacn01arce_4.model.Cancion;

public class TriviaAdapter extends RecyclerView.Adapter {
        private List<Cancion> listaDeCanciones;
        private List <Cancion> listaDeCancionesCompleta;
        private TriviaOnItemSelectedListener onItemSelectedListener;
        private Cancion cancionRandom;



        public TriviaAdapter(List<Cancion> listaDeCanciones, TriviaOnItemSelectedListener onItemSelectedListener,Cancion cancionRandom) {
            this.listaDeCanciones = listaDeCanciones;
            this.listaDeCancionesCompleta = new ArrayList<>(listaDeCanciones);
            this.onItemSelectedListener = onItemSelectedListener;
            this.cancionRandom = cancionRandom;

        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflador = LayoutInflater.from(viewGroup.getContext());
            View view = inflador.inflate(R.layout.celda_trivia, viewGroup,false);
            TriviaAdapter.TriviaViewHolder triviaViewHolder= new TriviaAdapter.TriviaViewHolder(view,onItemSelectedListener);
            return triviaViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            Cancion cancionDeLalista = this.listaDeCanciones.get(i);
            TriviaAdapter.TriviaViewHolder cancionViewHolder = (TriviaAdapter.TriviaViewHolder) viewHolder;
            cancionViewHolder.bindCancion(cancionDeLalista);

        }

        @Override
        public int getItemCount() {

            return this.listaDeCanciones.size();
        }

        private class TriviaViewHolder extends RecyclerView.ViewHolder {
            private ImageView imagen;
            private TextView titulo;
            private TextView album;
            private TextView artista;
            private Cancion cancionDeLaCelda;

            public TriviaViewHolder(@NonNull View itemView,final TriviaAdapter.TriviaOnItemSelectedListener onItemSelectedListener) {
                super(itemView);
                imagen = itemView.findViewById(R.id.imgFrgListaCanciones);
                titulo = itemView.findViewById(R.id.txtFrgListaCancionesTitulo);
                album = itemView.findViewById(R.id.txtFrgListaCancionesAlbum);
                artista = itemView.findViewById(R.id.txtFrgListaCancionesArtista);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        onItemSelectedListener.TriviaonItemClick(listaDeCanciones.get(getAdapterPosition()),cancionRandom);

                    }
                });



            }



            public void bindCancion(Cancion cancionDeLaCelda){
                this.cancionDeLaCelda = cancionDeLaCelda;
                Glide.with(this.itemView.getContext())
                        .load(cancionDeLaCelda.getAlbum().getImagen())
                        .into(imagen);
                /*this.titulo.setText(cancionDeLaCelda.getTitulo());
                this.album.setText(cancionDeLaCelda.getAlbum().getNombre());
                this.artista.setText(cancionDeLaCelda.getArtista().getNombre());*/

            }
        }
        public interface TriviaOnItemSelectedListener {
            void TriviaonItemClick(Cancion cancion, Cancion cancionRandom);
        }





}
