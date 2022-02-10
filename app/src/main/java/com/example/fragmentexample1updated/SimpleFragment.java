
package com.example.fragmentexample1updated;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SimpleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SimpleFragment extends Fragment {

    // Kondisi pilihan yang dipilih user di fragment
    private static final int YES = 0;
    private static final int NO = 1;
    private static final int NONE = 2;

    // Variabel untuk menyimpan pilihan radio button
    private int mainActCurrentChoice = NONE;

    // Di attach ke MainActivity ( host ) untuk ngasih tau state fragment
    private OnFragmentInteractionListener mainActListener;
    interface OnFragmentInteractionListener {
        void OnRadioButtonChoiceChecked(int choice);
    }

    // method ini dipanggil saat fragment nya ditempelkan ke activity
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        // Cek apakah host activity udah implementasi interface onRadioButtonChoiceChecked
        if ( context instanceof OnFragmentInteractionListener ) {
            mainActListener = (OnFragmentInteractionListener) context;
        }
        else {
            throw new ClassCastException(context.toString()
                + getResources().getString(R.string.exception_message));
        }
    }

    private static final String CHOICE = "choice";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SimpleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
     * @return A new instance of fragment SimpleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SimpleFragment newInstance(int choice) {
        SimpleFragment fragment = new SimpleFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(CHOICE,choice);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    // Tempat buat ngeload fragment nya dimana
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_simple, container, false);

        RadioGroup radioGroup = rootView.findViewById(R.id.radio_group);

        if (getArguments().containsKey(CHOICE)) {
            mainActCurrentChoice = getArguments().getInt(CHOICE);
            if (mainActCurrentChoice != NONE ) {
                radioGroup.check(radioGroup.getChildAt(mainActCurrentChoice).getId());
            }
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedButton = radioGroup.findViewById(checkedId);
                int idx = radioGroup.indexOfChild(checkedButton);
                TextView textView = rootView.findViewById(R.id.fragment_header);

                if ( idx == YES  ) {
                    textView.setText(R.string.yes_message);
                    mainActCurrentChoice = YES;
                    mainActListener.OnRadioButtonChoiceChecked(YES);
                }
                else if(idx == NO) {
                    textView.setText(R.string.no_message);
                    mainActCurrentChoice = NO;
                    mainActListener.OnRadioButtonChoiceChecked(NO);
                }
                else {
                    mainActCurrentChoice = NONE;
                    mainActListener.OnRadioButtonChoiceChecked(NONE);
                }
            }
        });

        return rootView;
    }
}