package digitalhouse.com.a0319cpmoacn01arce_4.dao.impl;

import digitalhouse.com.a0319cpmoacn01arce_4.dao.DeezerDao;
import digitalhouse.com.a0319cpmoacn01arce_4.model.Data;
import digitalhouse.com.a0319cpmoacn01arce_4.service.DeezerService;
import digitalhouse.com.a0319cpmoacn01arce_4.util.CallBackGeneric;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DeezerDaoImplConInternet implements DeezerDao {


    private static final String BASE_URL = "https://api.deezer.com/search/track/";
    private Retrofit retrofit;
    private DeezerService deezerService;

    //private String busqueda = "?q=arjona&index=0&limit=1&output=xm";

    public DeezerDaoImplConInternet() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.deezerService = this.retrofit.create(DeezerService.class);
    }



    @Override
    public void getCanciones(final CallBackGeneric<Data> listCallBackGeneric,String busqueda) {

        Call<Data> call = deezerService.getCanciones(busqueda);

        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {Data cancions= response.body();
                listCallBackGeneric.onFinish(cancions);
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                t.printStackTrace();
            }
        });


    }
    @Override
    public void getCancioPorTitulo(CallBackGeneric<Data> callBackGeneric) {

    }


}
