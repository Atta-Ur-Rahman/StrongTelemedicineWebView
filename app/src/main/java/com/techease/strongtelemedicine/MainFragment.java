package com.techease.strongtelemedicine;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private View parentView;
    private Button btnCurrentMember, btnNeedToSign;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        parentView = inflater.inflate(R.layout.fragment_main, container, false);

        btnCurrentMember = parentView.findViewById(R.id.btn_current_member);
        btnNeedToSign = parentView.findViewById(R.id.btn_neet_to_sign_up);

        btnCurrentMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utilities.putValueInEditor(getActivity()).putString("url", "https://www.strongtelemed.com/sign-in").commit();
                getActivity().startActivity(new Intent(getActivity(), WebViewActivity.class));

            }
        });

        btnNeedToSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utilities.putValueInEditor(getActivity()).putString("url", "https://www.strongtelemed.com/signup").commit();
                getActivity().startActivity(new Intent(getActivity(), WebViewActivity.class));
            }
        });

        if (Utilities.CheckNetwork.isInternetAvailable(getActivity())) {


        } else {
            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE);
            sweetAlertDialog.setConfirmText("Refresh")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            Utilities.withOutBackStackConnectFragment(getActivity(), new MainFragment());
                        }
                    })
                    .setTitleText("Oops...")
                    .setContentText("No internet connection!")
                    .setOnKeyListener(new DialogInterface.OnKeyListener() {
                        @Override
                        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                            getActivity().finish();
                            return false;
                        }
                    });

            sweetAlertDialog.setCancelable(false);
            sweetAlertDialog.show();

        }


        return parentView;
    }

}
