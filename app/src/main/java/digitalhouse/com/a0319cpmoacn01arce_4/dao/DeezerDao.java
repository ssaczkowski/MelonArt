package digitalhouse.com.a0319cpmoacn01arce_4.dao;

import digitalhouse.com.a0319cpmoacn01arce_4.model.Cancion;
import digitalhouse.com.a0319cpmoacn01arce_4.model.Data;
import digitalhouse.com.a0319cpmoacn01arce_4.util.CallBackGeneric;

public interface DeezerDao {

    void getCanciones(CallBackGeneric<Data> listCallBackGeneric, String busqueda);

    void getCancioPorTitulo(CallBackGeneric<Data> callBackGeneric);

    void getCancionById(CallBackGeneric<Cancion> callBackGeneric, String id);


}
