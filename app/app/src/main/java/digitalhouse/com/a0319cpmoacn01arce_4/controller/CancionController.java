package digitalhouse.com.a0319cpmoacn01arce_4.controller;



import digitalhouse.com.a0319cpmoacn01arce_4.dao.DeezerDao;
import digitalhouse.com.a0319cpmoacn01arce_4.dao.impl.DeezerDaoImplConInternet;
import digitalhouse.com.a0319cpmoacn01arce_4.model.Data;
import digitalhouse.com.a0319cpmoacn01arce_4.util.CallBackGeneric;

public class CancionController {

    private DeezerDao deezerDao;


    public CancionController() {
        this.deezerDao= new DeezerDaoImplConInternet();
    }

    public void getCanciones(final CallBackGeneric<Data> escuchadorDeLaView,String busqueda){

        deezerDao.getCanciones(new CallBackGeneric<Data>() {


            @Override
            public void onFinish(Data data) {

                    escuchadorDeLaView.onFinish(data);
            }


        },busqueda );
    }


}
