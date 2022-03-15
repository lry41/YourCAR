package com.example.yourcar;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;


import com.example.yourcar.controller.SearchCarController;

public class DialogFilter extends AppCompatDialogFragment {
private RadioGroup FilterMarque;
private RadioButton marqueChoose;

private RadioGroup filterkm;
private RadioButton kmChoose;
private DialogFilterInterface dialogFilter;
private int kmChooseInt;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
       
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //builder.setListener(this);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog, null);

        FilterMarque=view.findViewById(R.id.filterMarque);
        filterkm = view.findViewById(R.id.filterKm);

        builder.setView(view)
                .setTitle(R.string.filtres)
                .setPositiveButton(R.string.appliquer, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                //Verification si nul sinon association à la valeur"vide" pour gérer si on check pas les 2
                        int selectedIdmarque=FilterMarque.getCheckedRadioButtonId();
                        marqueChoose=view.findViewById(selectedIdmarque);
                        String marqueChooseText;
                        if(marqueChoose!=null){
                            marqueChooseText = marqueChoose.getText().toString();
                        }
                       else{
                            marqueChooseText="vide";
                        }


                        int selectedIdkm=filterkm.getCheckedRadioButtonId();
                        kmChoose=view.findViewById(selectedIdkm);
                        Log.d("testBUTTON",""+selectedIdkm);
                        Log.d("testBUTTON",""+kmChoose);


                        int kmChooseText;
                        switch(selectedIdkm){
                            case 2131296582: kmChooseText=30000;
                                break;
                            case 2131296580: kmChooseText=100000;
                                break;
                            case 2131296581: kmChooseText=200000;
                                break;
                            case 2131296662: kmChooseText=10000000;
                                break;
                            default:kmChooseText=0;
                                break;
                        }

                        dialogFilter.applyFilter(marqueChooseText,kmChooseText);
                    }
                 });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Fragment fragment = getTargetFragment();
        if (fragment instanceof DialogFilterInterface) {
            dialogFilter = (DialogFilterInterface) fragment;
        } else {
            throw new RuntimeException("Target Fragment is not implementing SendData interface");
        }


    }

    public interface DialogFilterInterface{
        void applyFilter(String marqueChoose, int kmChoose);
    }
    public void setListener(DialogFilterInterface listener){
        this.dialogFilter= listener;
    }
}