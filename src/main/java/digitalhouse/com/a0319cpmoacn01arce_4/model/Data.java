package digitalhouse.com.a0319cpmoacn01arce_4.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Data implements Serializable {
    private List<Cancion> data = new ArrayList<>();

    public Data(List<Cancion> data) {
        this.data = data;
    }

    public Data() {

    }

    public List<Cancion> getData() {
        return data;
    }

    public void setData(List<Cancion> data) {
        this.data = data;
    }
}

