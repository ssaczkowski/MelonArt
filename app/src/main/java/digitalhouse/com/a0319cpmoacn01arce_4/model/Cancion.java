package digitalhouse.com.a0319cpmoacn01arce_4.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Cancion implements Serializable {

    private String id;
    @SerializedName("title")
    private String titulo;
    @SerializedName("artist")
    private Artista artista;
    @SerializedName("album")
    private Album album;
    @SerializedName("preview")
    private String preview;

    public Cancion() {
    }

    public Cancion(String id,String titulo, Artista artista, Album album,String preview) {
        this.id = id;
        this.titulo = titulo;
        this.artista = artista;
        this.album = album;
        this.preview = preview;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Artista getArtista() {
        return artista;
    }

    public void setArtista(Artista artista) {
        this.artista = artista;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

}
