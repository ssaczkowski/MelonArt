package digitalhouse.com.a0319cpmoacn01arce_4.model;

public class Genero {
    private int id;
    private String nombre;

    public Genero(int id,String nombre) {
       this.id=id;
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
