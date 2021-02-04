package digitalhouse.com.a0319cpmoacn01arce_4.model;

import com.google.gson.annotations.SerializedName;

public class Album {
    private int id;
    @SerializedName("title")
    private String nombre;

    @SerializedName("cover_medium")
    private String imagen;

    public Album(){

    }

    public Album(int id,String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
