package com.mynotepad.android.mynotepad;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.Nullable;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    Communicator commander;
    TopSectionListener activitycommander;
    public static String labelSearch="";
  public static int pos=0;

    public void setlabel(String str)
    {
     Log.d("TOP PRINT=>",str);
        commander=(Communicator) getActivity();
        commander.searchLabel(str);

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.d("TOP", ""+ position);
        String labelSelected= new String("");
        labelSelected= (String) parent.getItemAtPosition(position);
        Log.d("TOP VALUE=>", labelSelected);
          labelSearch=labelSelected;
        //  commander.searchLabel(labelSelected);
       setlabel(labelSelected);
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public interface TopSectionListener{
        public void passSearchString(String str);
    }

    public TopFragment() {
        // Required empty public constructor
    }

    public void textchanged(String s)
    {
        commander=(Communicator) getActivity();
        commander.searchString(s);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_top,container,false);


        EditText searchtext=(EditText)view.findViewById(R.id.searchtext_edit_view);
        searchtext.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //noteAdapter.getFilter().filter(sa.toString());
                String str;
                str=s.toString();
                textchanged(str);
            }


        });

        //For Populating the drop for search as per the Label selected in the drop down
        Spinner dropdown = (Spinner)view.findViewById(R.id.spinner);
        String[] items = new String[]{"ALL","PERONAL", "TECHNICAL", "QUOTE", "FINANCE"};
        Log.d("ACtivityName","HERE");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener(this);



                return view;
    }

}
