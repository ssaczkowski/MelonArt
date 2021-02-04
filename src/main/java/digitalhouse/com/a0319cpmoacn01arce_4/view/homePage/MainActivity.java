package digitalhouse.com.a0319cpmoacn01arce_4.view.homePage;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.List;

import digitalhouse.com.a0319cpmoacn01arce_4.R;
import digitalhouse.com.a0319cpmoacn01arce_4.model.Cancion;
import digitalhouse.com.a0319cpmoacn01arce_4.model.Data;
import digitalhouse.com.a0319cpmoacn01arce_4.model.ScoreItem;
import digitalhouse.com.a0319cpmoacn01arce_4.model.User;
import digitalhouse.com.a0319cpmoacn01arce_4.util.ContextUtil;
import digitalhouse.com.a0319cpmoacn01arce_4.view.listasActivity.ListasActivity;
import digitalhouse.com.a0319cpmoacn01arce_4.view.searcher.CancionAdapter;
import digitalhouse.com.a0319cpmoacn01arce_4.view.detailPage.DetalleCancionFragment;
import digitalhouse.com.a0319cpmoacn01arce_4.view.searcher.FragmentListaDeCanciones;
import digitalhouse.com.a0319cpmoacn01arce_4.view.loginPage.LoginActivity;
import digitalhouse.com.a0319cpmoacn01arce_4.view.triviaGame.TriviaAdapter;
import digitalhouse.com.a0319cpmoacn01arce_4.view.userPage.UserFragment;


public class MainActivity extends AppCompatActivity  implements HomeFragment.OnFragmentHomeInteractionListener, CancionAdapter.OnItemSelectedListener,
        DetalleCancionFragment.OnFragmentDetalleCancionInteractionListener, TriviaAdapter.TriviaOnItemSelectedListener {


    private  DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.as_above);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");



        ContextUtil.getInstance(this);

         drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView lateralMenu = findViewById(R.id.main_activity_navigation_view);

        lateralMenu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.logout:
                        closeSession();
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        break;
                    case R.id.User:
                        gotoUser();
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        break;
                    case R.id.search:
                        goToSearchSong();
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        break;
                    default:
                        return false;
                }
                return false;

            }

        });

         toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        User user = new User(null,ContextUtil.getUserId(),ContextUtil.getNameByAuthId(),ContextUtil.getImageCurrentUser());
        ScoreItem scoreItem;
        if(!ContextUtil.existsScore()){
            scoreItem = new ScoreItem("0",0,user.getName(),"ic_argentina");
            //rellenoHARDCODE();

            ContextUtil.insertScoreItem(scoreItem);

        }

        ContextUtil.showLoading(this,"",getString(R.string.loading),false);
        HomeFragment homeFragment = new HomeFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.contenedor,homeFragment);
        transaction.commit();


        View header = lateralMenu.getHeaderView(0);
        ImageView userImage = header.findViewById(R.id.header_circle_image);
        TextView userName = header.findViewById(R.id.header_name);

        Glide.with(this)
                .load(user.getImageProfile() + "?type=large")
                .into(userImage);
        userName.setText(user.getName());



}

    private void rellenoHARDCODE() {
        User user0 = new User(null,"","Pepe23",ContextUtil.getImageCurrentUser());
        User user1 = new User(null,"","JuanM",ContextUtil.getImageCurrentUser());
        User user2 = new User(null,"","Herny",ContextUtil.getImageCurrentUser());
        User user3 = new User(null,"","Lara",ContextUtil.getImageCurrentUser());
        User user4 = new User(null,"","Oscar",ContextUtil.getImageCurrentUser());

        ScoreItem scoreItem0 = new ScoreItem("0",1012,user0.getName(),"ic_argentina");
        ScoreItem scoreItem1 = new ScoreItem("0",1233,user1.getName(),"ic_venezuela");
        ScoreItem scoreItem2 = new ScoreItem("0",54563,user2.getName(),"ic_spain");
        ScoreItem scoreItem3 = new ScoreItem("0",4545,user3.getName(),"ic_uruguay");
        ScoreItem scoreItem4 = new ScoreItem("0",8965,user4.getName(),"ic_argentina");


        ContextUtil.insertUser(user0);
        ContextUtil.insertScoreItem(scoreItem0);

        ContextUtil.insertUser(user1);
        ContextUtil.insertScoreItem(scoreItem1);

        ContextUtil.insertUser(user2);
        ContextUtil.insertScoreItem(scoreItem2);

        ContextUtil.insertUser(user3);
        ContextUtil.insertScoreItem(scoreItem3);

        ContextUtil.insertUser(user4);
        ContextUtil.insertScoreItem(scoreItem4);




    }

    private void closeSession() {
        LoginManager.getInstance().logOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


    private void goToSearchSong() {
        getSupportActionBar().show();
        FragmentListaDeCanciones fragmentListaDeCanciones = new FragmentListaDeCanciones();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.contenedor, fragmentListaDeCanciones);
        transaction.commit();
    }
    private void gotoUser() {

        UserFragment userFragment = new UserFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.contenedor, userFragment);
        transaction.commit();
    }





    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onItemClick(Cancion cancion,Data dataConLista, Integer posicion) {
        DetalleCancionFragment detalleCancionFragment = DetalleCancionFragment.newInstance(cancion.getAlbum().getNombre(), cancion.getTitulo(), cancion.getArtista().getNombre(), cancion.getAlbum().getImagen(), cancion.getPreview(),cancion.getId(),dataConLista,posicion);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.contenedor, detalleCancionFragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed(){
        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            if(this != MainActivity.this) {
                HomeFragment homeFragment = new HomeFragment();
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.contenedor, homeFragment);
                transaction.commit();
            }
        } else {
            getSupportFragmentManager().popBackStack();
        }
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(Gravity.LEFT);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);


    }

    @Override
    public void TriviaonItemClick(Cancion cancion,Cancion cancionRandom) {


        if (cancion.getId() == cancionRandom.getId()) {
            Toast.makeText(this, "Correcto", Toast.LENGTH_SHORT).show();
            Data data = new Data();
            Integer posicion = 0;
            DetalleCancionFragment detalleCancionFragment = DetalleCancionFragment.newInstance(cancion.getAlbum().getNombre(), cancion.getTitulo(), cancion.getArtista().getNombre(), cancion.getAlbum().getImagen(), cancion.getPreview(),cancion.getId(),data,posicion);
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.contenedor, detalleCancionFragment);
            transaction.commit();
        } else {
            Toast.makeText(this, "Incorrecto", Toast.LENGTH_SHORT).show();
        }
    }
}