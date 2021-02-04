package digitalhouse.com.a0319cpmoacn01arce_4.service;

import digitalhouse.com.a0319cpmoacn01arce_4.model.Cancion;
import digitalhouse.com.a0319cpmoacn01arce_4.model.Data;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;


public interface DeezerService {



    @GET()
    Call<Data> getCanciones(@Url String busqueda);

    @GET()
    Call<Cancion> getCancionById(@Url String id);



}
