package digitalhouse.com.a0319cpmoacn01arce_4.service;

import digitalhouse.com.a0319cpmoacn01arce_4.model.Data;
import digitalhouse.com.a0319cpmoacn01arce_4.model.FragmentListaDeCanciones;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;


public interface DeezerService {



    @GET()
    Call<Data> getCanciones(@Url String busqueda);



}
