package digitalhouse.com.a0319cpmoacn01arce_4.view.gamePage;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import digitalhouse.com.a0319cpmoacn01arce_4.R;
import digitalhouse.com.a0319cpmoacn01arce_4.util.ContextUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class GeneroFragment extends Fragment {


    private GeneroInterface generoInterface;
    public static final String TAG = "GeneroFragment";
    private static Dialog dialog ;


    public GeneroFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_genero, container, false);
        String listaGenero;
        Button rockNacional = view.findViewById(R.id.cardRockNacional);
        Button rockInternacional = view.findViewById(R.id.cardRockInternacional);
        Button salsa = view.findViewById(R.id.cardSalsa);
        Button clasico = view.findViewById(R.id.cardClasico);
        Button trap = view.findViewById(R.id.cardTrap);
        Button cumbia = view.findViewById(R.id.cardCumbia);
        dialog =  new Dialog(getActivity());

        rockNacional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextUtil.isNetConnected(getContext())) {
                    generoInterface.onClickGenero("playlist/4559779468");
                } else {
                    dialog.setContentView(R.layout.dialog_no_connection);
                    dialog.show();

                    dialog.findViewById(R.id.alert_dialog_acept).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });


                }
            }

        });

        cumbia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextUtil.isNetConnected(getContext())) {
                    generoInterface.onClickGenero("playlist/5239361546");
                } else {

                    dialog.show();

                    dialog.findViewById(R.id.alert_dialog_acept).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                }
            }
        });

        trap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextUtil.isNetConnected(getContext())) {
                    generoInterface.onClickGenero("playlist/4271999106");
                } else {
                    dialog.show();

                    dialog.findViewById(R.id.alert_dialog_acept).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                }
            }
        });

        rockInternacional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextUtil.isNetConnected(getContext())) {
                    generoInterface.onClickGenero("playlist/1306931615");
                } else {
                    dialog.show();

                    dialog.findViewById(R.id.alert_dialog_acept).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                }
            }
        });

        clasico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextUtil.isNetConnected(getContext())) {
                    generoInterface.onClickGenero("playlist/2255658886");
                } else {
                    dialog.show();

                    dialog.findViewById(R.id.alert_dialog_acept).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                }
            }
        });

        salsa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextUtil.isNetConnected(getContext())) {
                    generoInterface.onClickGenero("playlist/1467063127");
                } else {
                    dialog.show();

                    dialog.findViewById(R.id.alert_dialog_acept).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                }
            }
        });


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.generoInterface = (GeneroInterface) context;
    }

    public interface GeneroInterface {
        void onClickGenero(String listaGenero);
    }

}

