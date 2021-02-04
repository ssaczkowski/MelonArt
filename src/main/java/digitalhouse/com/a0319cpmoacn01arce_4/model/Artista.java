package digitalhouse.com.a0319cpmoacn01arce_4.model;

import com.google.gson.annotations.SerializedName;

public class Artista {
    @SerializedName("name")
    private String nombre;
    private String id;
    @SerializedName("picture_medium")
    private String imagen;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Artista(String id, String nombre,String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
