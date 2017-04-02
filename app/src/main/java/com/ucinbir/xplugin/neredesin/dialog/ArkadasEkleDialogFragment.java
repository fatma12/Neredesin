package com.ucinbir.xplugin.neredesin.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ucinbir.xplugin.neredesin.R;
import com.ucinbir.xplugin.neredesin.adapter.KimlerRecyclerViewAdapter;
import com.ucinbir.xplugin.neredesin.background.ArkadasEkleAsyncTask;


public class ArkadasEkleDialogFragment extends DialogFragment {

    private KimlerRecyclerViewAdapter.KimlerItemClickListener kimlerItemClickListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        kimlerItemClickListener = (KimlerRecyclerViewAdapter.KimlerItemClickListener) activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View layout = inflater.inflate(R.layout.arkadas_ekle_dialog, null);

        final EditText arkadasEkleEditText = (EditText) layout.findViewById(R.id.kullaniciAdiDialogEditText);
        Button arkadasEkleButton = (Button) layout.findViewById(R.id.ekleArkadasEkleDialogButton);
        arkadasEkleButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Editable kullaniciAdiEditable = arkadasEkleEditText.getText();
                String kullaniciAdi = (kullaniciAdiEditable != null) ? kullaniciAdiEditable.toString() : "";
                arkadasEkle(kullaniciAdi);
                dismiss();
            }
        });

        Button iptalButton = (Button) layout.findViewById(R.id.iptalArkadasEkleDialogButton);
        iptalButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(layout);

        return builder.create();
    }

    private void arkadasEkle(String arkadasKullaniciAdi) {

        if(TextUtils.isEmpty(arkadasKullaniciAdi)) {
            String message = getResources().getString(R.string.toast_bos_parametre_hatasi, "Kullanıcı Adı");
            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            return;
        }

        ArkadasEkleAsyncTask task = new ArkadasEkleAsyncTask(getActivity(), kimlerItemClickListener);
        task.execute(arkadasKullaniciAdi);

    }

    public void show(FragmentManager fragmentManager, String arkadasEkleDialogFragment) {
    }
}
