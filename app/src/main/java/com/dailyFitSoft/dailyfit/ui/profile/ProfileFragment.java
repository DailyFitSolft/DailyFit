package com.dailyFitSoft.dailyfit.ui.profile;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.dailyFitSoft.dailyfit.R;
import com.dailyFitSoft.dailyfit.dataStore.DataBaseHelper;

public class ProfileFragment extends Fragment {

    private ProfileViewModel sendViewModel;
    private TextView textViewBirthday;
    private TextView textViewName;
    private TextView textViewWeight;
    private TextView textViewHight;
    private TextView textViewGender;
    private Button changeBirthday;
    private Button changeName;
    private Button changeHeight;
    private Button changeWeight;
    private Button changeGender;
    private DataBaseHelper dataBaseHelper;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        textViewBirthday = (TextView) root.findViewById(R.id.text_birthday);
        textViewName = (TextView) root.findViewById(R.id.text_name);
        textViewWeight = (TextView) root.findViewById(R.id.text_weight);
        textViewHight = (TextView) root.findViewById(R.id.text_height);
        textViewGender = (TextView) root.findViewById(R.id.text_gender);
        changeBirthday = (Button) root.findViewById(R.id.change_birthday_button);
        changeHeight = (Button) root.findViewById(R.id.change_height_button);
        changeName = (Button) root.findViewById(R.id.change_name_button);
        changeWeight = (Button) root.findViewById(R.id.change_weight_button);
        changeGender = (Button) root.findViewById(R.id.change_gender_button);
        dataBaseHelper = new DataBaseHelper(getContext());

        textViewWeight.setText("Waga: " + Double.toString(dataBaseHelper.getProfile().getWeight()) + "kg");
        textViewHight.setText("Wzrost: " + Double.toString(dataBaseHelper.getProfile().getHeight()) + "cm");
        textViewName.setText(dataBaseHelper.getProfile().getName());
        textViewBirthday.setText("Wiek: " + Integer.toString(dataBaseHelper.getProfile().getAge()));
        changeWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getActivity().getLayoutInflater();
                final View alertDialogView = inflater.inflate(R.layout.change_profile_dialog, null);
                alertDialog.setView(alertDialogView);

                final EditText weightInput = alertDialogView.findViewById(R.id.editparameters);
                alertDialog.setPositiveButton("Potwierdź", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            float weight = Float.valueOf(weightInput.getText().toString());
                            if(weight > 0 && weight < 350) {
                                textViewWeight.setText("Waga: " + weight + "kg");
                                dataBaseHelper.modifyProfileWeight(weight);
                            }
                            dialogInterface.dismiss();
                        }
                        catch(Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                alertDialog.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });
        changeHeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getActivity().getLayoutInflater();
                final View alertDialogView = inflater.inflate(R.layout.change_profile_dialog, null);
                alertDialog.setView(alertDialogView);

                final EditText hightInput = alertDialogView.findViewById(R.id.editparameters);
                hightInput.setHint("Wzrost");
                hightInput.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                alertDialog.setPositiveButton("Potwierdź", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            int height = Integer.valueOf(hightInput.getText().toString());
                            if(height >0 && height<250) {
                                textViewHight.setText("Wzrost: " + height + "cm");
                                dataBaseHelper.modifyProfileHight(height);
                            }
                            dialogInterface.dismiss();
                        }
                        catch(Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                alertDialog.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });

        changeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getActivity().getLayoutInflater();
                final View alertDialogView = inflater.inflate(R.layout.change_profile_dialog, null);
                alertDialog.setView(alertDialogView);

                final EditText nameInput = alertDialogView.findViewById(R.id.editparameters);
                nameInput.setHint("Imie");
                nameInput.setInputType(InputType.TYPE_CLASS_TEXT);
                alertDialog.setPositiveButton("Potwierdź", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            String name = nameInput.getText().toString();
                            textViewName.setText(name);
                            dataBaseHelper.modifyProfileName("'" + name + "'" );
                            dialogInterface.dismiss();
                        }
                        catch(Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                alertDialog.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });

        changeBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getActivity().getLayoutInflater();
                final View alertDialogView = inflater.inflate(R.layout.change_profile_dialog, null);
                alertDialog.setView(alertDialogView);

                final EditText birthdayInput = alertDialogView.findViewById(R.id.editparameters);
                birthdayInput.setHint("Wiek");
                birthdayInput.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                alertDialog.setPositiveButton("Potwierdź", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                             String birthday = birthdayInput.getText().toString();
                             textViewBirthday.setText("Wiek: " + birthday);
                             dataBaseHelper.modifyProfileBirthdate(birthday);
                            dialogInterface.dismiss();
                        }
                        catch(Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                alertDialog.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });

        changeGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getActivity().getLayoutInflater();
                final View alertDialogView = inflater.inflate(R.layout.change_gender_dialog,null);
                alertDialog.setView(alertDialogView);

                final RadioGroup radioGenderGroup = alertDialogView.findViewById(R.id.genderRadioGroupProfile);
                alertDialog.setPositiveButton("Potwierdź", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            RadioButton radioButton = alertDialogView.findViewById(radioGenderGroup.getCheckedRadioButtonId());
                            String gender = radioButton.getText().toString();
                            textViewGender.setText(gender);
                            dialogInterface.dismiss();
                        }
                        catch(Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                alertDialog.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });




        return root;
    }


}