package com.dailyFitSoft.dailyfit.ui.profile.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.dailyFitSoft.dailyfit.R;


public class WeightDialog extends AppCompatDialogFragment {
    private EditText editWeight;
    private WeightDialogListiner listiner;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listiner = (WeightDialogListiner) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+ "must implement WeightDialogListiner");
        }

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.change_profile_dialog, null);
        editWeight = view.findViewById(R.id.editparameters);

        builder.setView(view)
                .setTitle("Nowa waga")
                .setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("Potwierdz", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String weight = editWeight.getText().toString();
                        listiner.applyTexts(weight);
                    }
                });
        return builder.create();
    }
    public interface WeightDialogListiner{
        void applyTexts(String weight);
    }
}
