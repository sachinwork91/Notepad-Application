package com.mynotepad.android.mynotepad;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


/**
 * THis is the main activity which is called on startup
 */

public class StartApp extends AppCompatActivity implements Communicator{

   /* public static final String MYNOTE_IDENTIFIER_EXTRA="com.mynotepad.android.mynotepad.Identifier";
    public static final String NOTE_TITLE_EXTRA="com.mynotepad.android.mynotepad.Title";
    public static final String NOTE_MESSAGE_EXTRA="com.mynotepad.android.mynotepad.Message";
    public static final String NOTE_CATEGORY_EXTRA="com.mynotepad.android.mynotepad.Category";
    public static final String NOTE_NEXT_FRAG_TO_OPEN="com.mynotepad.android.mynotepad.Fragment_next_frag_to_open";
   */

      public static final String MYNOTE_IDENTIFIER_EXTRA="mynotepad.Identifier";
      public static final String NOTE_TITLE_EXTRA="mynotepad.Title";
      public static final String NOTE_MESSAGE_EXTRA="mynotepad.Message";
      public static final String NOTE_CATEGORY_EXTRA="mynotepad.Category";
      public static final String NOTE_NEXT_FRAG_TO_OPEN="mynotepad.Fragment_next_frag_to_open";

     int flag=0;

    //This enumeration is used to  identify which case is running
    public enum FragementToLaunch{VIEW, EDIT, CREATE};


    //This is a method of Activity this is called when the
    //Activity is started
    //SavedInstanceState will hold the values when the activity is destroyed or created
    //I have used this value to save content on rotation of the phone
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("STARTAPP","STARTAPP");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(savedInstanceState!=null)
        {
            this.flag=1;
        }
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int item_on_menu_selected = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (item_on_menu_selected == R.id.action_settings) {
            return true;
        }else if (item_on_menu_selected==R.id.action_add_note){

            Intent intent=new Intent(this,MyDetailActivity.class);
            intent.putExtra(StartApp.NOTE_NEXT_FRAG_TO_OPEN,FragementToLaunch.CREATE);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //This function is used to search on the basis of the text entered in the searchtext
    @Override
    public void searchString(String str)
    {
        Log.d("STARTAPP1","STARTAPP1");
       if(flag==0) {
           FragmentManager manager = getSupportFragmentManager();
           StartListFragment fragment2 = (StartListFragment) manager.findFragmentById(R.id.MainActivityFrag);
           //   fragment2.searchtext=str;
           fragment2.my_filter(str);
       }

        flag=0;

    }

    @Override
    public void searchLabel(String str){
        FragmentManager manager=getSupportFragmentManager();
        StartListFragment fragment2=(StartListFragment) manager.findFragmentById(R.id.MainActivityFrag) ;
        fragment2.my_filter_for_label(str);


    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        //   savedInstanceState.putSerializable(MODIFIED_CATEGORY,savedButtonCategory);
        savedInstanceState.putString("Search","changed");
        Log.d("INVERTED", "INVERTED");

    }

}
