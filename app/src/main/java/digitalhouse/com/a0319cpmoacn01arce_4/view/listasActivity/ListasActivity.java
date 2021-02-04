package digitalhouse.com.a0319cpmoacn01arce_4.view.listasActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import digitalhouse.com.a0319cpmoacn01arce_4.R;
import digitalhouse.com.a0319cpmoacn01arce_4.controller.CancionController;
import digitalhouse.com.a0319cpmoacn01arce_4.model.Cancion;
import digitalhouse.com.a0319cpmoacn01arce_4.util.CallBackGeneric;
import digitalhouse.com.a0319cpmoacn01arce_4.util.ContextUtil;

public class ListasActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    private List<String> listi = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listas);

        mDatabase = FirebaseDatabase.getInstance().getReference();


        writeNewUser();

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                List<String> listi = (List<String>) dataSnapshot.getValue();
                Toast.makeText(ListasActivity.this, listi.get(0).toString(), Toast.LENGTH_SHORT).show();
                CancionController controller= new CancionController();

                controller.getCancionById(new CallBackGeneric<Cancion>() {
                    @Override
                    public void onFinish(Cancion cancion) {
                        Toast.makeText(ListasActivity.this, cancion.getTitulo(), Toast.LENGTH_SHORT).show();
                    }
                },"/track/" + listi.get(0));
                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Toast.makeText(ListasActivity.this, "Fallo", Toast.LENGTH_SHORT).show();
                // ...
            }
        };
        DatabaseReference ref = mDatabase.child("Listas").child(ContextUtil.getUserId()).child("Favoritos");
        ref.addValueEventListener(postListener);



    }

    private void writeNewUser() {
        List<String> lista = new ArrayList<>();
        lista.add("3135556");

        mDatabase.child("Listas").child(ContextUtil.getUserId()).child("Favoritos").setValue(lista);
    }

}
