package com.mynotepad.android.mynotepad;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MyDetailActivity extends AppCompatActivity {


    public static final String NEW_NOTE_EXRTA="NEW NOTE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node_detail);
        createAndAddFragment();
    }
    private void createAndAddFragment(){

    //Getting the intent
        Intent intent=getIntent();
        StartApp.FragementToLaunch fragementToLaunch=
                (StartApp.FragementToLaunch)intent.getSerializableExtra(StartApp.NOTE_NEXT_FRAG_TO_OPEN);


     //Getting the Fragment Manager and Fragement transaction so as to add and edit frg dynamically
        FragmentManager fragmentManager= getSupportFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();

        switch (fragementToLaunch){
            case EDIT:
                //create and add note edit fragement
                NoteEditFragment noteEditFragment=new NoteEditFragment();
                setTitle(R.string.Edit_Title);
                fragmentTransaction.add(R.id.note_container, noteEditFragment,"NOTE_EDIT_FRAGMENT");
                break;
            //crete and add note view fragment
            case VIEW:

                NoteViewFragment noteViewFragment=new NoteViewFragment();
                setTitle(R.string.Note_Title);
                fragmentTransaction.add(R.id.note_container, noteViewFragment,"NOTE_VIEW_FRAGMENT");
                break;

            case CREATE:
                NoteEditFragment noteCreateFragment=new NoteEditFragment();
                setTitle(R.string.create_fragment_title);
                Bundle bundle=new Bundle();
                bundle.putBoolean(NEW_NOTE_EXRTA, true);
                noteCreateFragment.setArguments(bundle);

                fragmentTransaction.add(R.id.note_container,noteCreateFragment,"NOTE_CREATE_FRAGMENT");
                break;

        }

        //commit changes
        fragmentTransaction.commit();




    }


}
