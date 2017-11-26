package com.mynotepad.android.mynotepad;


import android.support.v4.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class StartListFragment extends ListFragment  {

    private static final String MODIFIED_CATEGORY="Modified Category";
 private ArrayList<MyNote> myNotes;
 private AdapterForNote adapterForNote;
    public String searchtext;
    private EditText search;
 //This is called When the Activity related to this Fragment is created.
 @Override
 public void onActivityCreated(Bundle savedInstanceState) {
  super.onActivityCreated(savedInstanceState);
    Log.d("STARTLISTFRAG","STARTLISTFRAG");

     /*
     This adapter helps in populating the list of the myNotes available at the starting of the app
     this just helps in getting the value of the myNotes, it does not create the structure
     */
     NotebookDbAdapter dbAdapter= new NotebookDbAdapter(getActivity().getBaseContext());
     dbAdapter.open();
     myNotes = dbAdapter.getAllNotes();
     dbAdapter.close();

//This helps in layout of the list, this takes the data from the note and use layout.xml to display
  adapterForNote = new AdapterForNote(getActivity(), myNotes);
  setListAdapter(adapterForNote);

  //This function is used to provide the divider between the list of the myNotes
  getListView().setDivider(ContextCompat.getDrawable(getActivity(), android.R.color.black));
  getListView().setDividerHeight(1);

     registerForContextMenu(getListView());

 }






    @Override
    public boolean onContextItemSelected(MenuItem item){


        //Get the position of the node which was long pressed.
        AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int rowPosition=info.position;

        MyNote myNote =(MyNote)getListAdapter().getItem(rowPosition);
        //This will return the value of what is selected in the menu
        switch(item.getItemId())
        {
            case R.id.edit:
              launchMyDetailActivity(StartApp.FragementToLaunch.EDIT, rowPosition);
                Log.d("Clicks","Edit was pressed");
                Toast.makeText(getActivity(), "You Can Edit MyNote",
                        Toast.LENGTH_SHORT).show();
              return true;

            case R.id.delete:
                NotebookDbAdapter dbAdapter= new NotebookDbAdapter(getActivity().getBaseContext());
                dbAdapter.open();
                dbAdapter.deleteNote(myNote.getNoteId());
                myNotes.clear();
                myNotes.addAll(dbAdapter.getAllNotes());
                adapterForNote.notifyDataSetChanged();

                dbAdapter.close();
                Toast.makeText(getActivity(), "MyNote Deleted",
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.styling:
                String textShare="TITLE OF NOTE : "+ myNote.getTitle()
                                  + "\n CATEGORY : "+ myNote.getCategory()+
                                 "\n MESSAGE OF THE NOTE: "+ myNote.getMessage();
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, textShare);

                startActivity(Intent.createChooser(share, "Title of the dialog the system will open"));

                break;




        }

        return super.onContextItemSelected(item);
    }



    //This method creates the menu which is displayed when we long press on the item in the list
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
     super.onCreateContextMenu(menu, v , menuInfo);

        MenuInflater menuInflater=getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.long_press_menu,menu);


    }


 //This is called when we click on the List Items
 @Override
 public void onListItemClick(ListView l, View v, int position, long id) {
  super.onListItemClick(l, v, position, id);
  launchMyDetailActivity( StartApp.FragementToLaunch.VIEW,position);

 }


 private void launchMyDetailActivity(StartApp.FragementToLaunch ftl, int position)
 {
     MyNote myNote =(MyNote)getListAdapter().getItem(position);

   //create a intent that launches launch Activity
     Intent intent=new Intent(getActivity(),MyDetailActivity.class);
  //Passing the information of the node to the new activity

     intent.putExtra(StartApp.NOTE_TITLE_EXTRA, myNote.getTitle());
     intent.putExtra(StartApp.NOTE_CATEGORY_EXTRA, myNote.getCategory());
     intent.putExtra(StartApp.NOTE_MESSAGE_EXTRA, myNote.getMessage());
     intent.putExtra(StartApp.MYNOTE_IDENTIFIER_EXTRA , myNote.getNoteId());
     intent.putExtra("textBold", myNote.getText_bold());
     intent.putExtra("path", myNote.getImagepath());

     //Adding for color
     intent.putExtra("colorBlue", myNote.getColorBlue());

     switch (ftl)
     {

         case VIEW :
         intent.putExtra(StartApp.NOTE_NEXT_FRAG_TO_OPEN, StartApp.FragementToLaunch.VIEW);
            break;

         case EDIT:intent.putExtra(StartApp.NOTE_NEXT_FRAG_TO_OPEN, StartApp.FragementToLaunch.EDIT);break;
     }

     startActivity(intent);

 }


    //This function is called whenever we add string in the search box
    public void my_filter(String str)
    {

        NotebookDbAdapter dbAdapter= new NotebookDbAdapter(getActivity().getBaseContext());
        dbAdapter.open();

            myNotes.clear();

            myNotes.addAll(dbAdapter.getoneNote(str));
            adapterForNote.notifyDataSetChanged();

        dbAdapter.close();
    }

   //This function is called from Startapp Activity whenever we select the label from the spinner
    public void my_filter_for_label(String str)
    {
        //here we are populating the database on the basis of the label selected
        NotebookDbAdapter dbAdapter= new NotebookDbAdapter(getActivity().getBaseContext());
        dbAdapter.open();

        myNotes.clear();
//IF All is selected then all categories should be displayed
        if(str=="ALL")
        {

            str="%%";
        }
        //This function get all the myNotes from the database on the basis of the label
        myNotes.addAll(dbAdapter.getNoteForLabel(str));
        adapterForNote.notifyDataSetChanged();
        dbAdapter.close();
    }







}
