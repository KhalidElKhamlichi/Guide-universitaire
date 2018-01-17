package com.elboukharielkhamlichi.khalid.guideuniversitaire;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;



public class SearchFragment extends DialogFragment {

    private EditText nom;
    private EditText ville;
    private String type = "universite";
    private OnFragmentInteractionListener mListener;

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        nom = (EditText) view.findViewById(R.id.etFragNom);
        ville = (EditText) view.findViewById(R.id.etFragVille);
        Button button = (Button) view.findViewById(R.id.btSearch);

        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.fragRadioGroup);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.fragRbEcole)
                    type = "ecole";
                if(checkedId == R.id.fragRbFaculte)
                    type = "faculte";
                if(checkedId == R.id.fragRbInstitut)
                    type = "institut";

            }
        });
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                search();
                dismiss();
            }
        });
        return view;
    }

    private void search() {
        if (mListener != null) {
            mListener.onFragmentInteraction(nom.getText().toString(), ville.getText().toString(), type);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String nom, String ville, String type);
    }
}
