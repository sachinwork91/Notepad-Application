package com.mynotepad.android.mynotepad;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sachin on 2016-09-27.
This class helps in laying out the basic list view and populating the view
 from the database */


public class AdapterForNote extends ArrayAdapter<MyNote> {

   public static class ViewHolder{

       TextView title;
       TextView note;
       ImageView noteIcon;
   }


    public AdapterForNote(Context context, ArrayList<MyNote> myNotes)
   {
       super(context,0, myNotes);

   }



    //*
    // This is an Overriden method this is called to populate the views
    //View is populated on the basis of what we have passed in the MyNote
    // */
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
      //GEt THe DAta Item for this position
        MyNote myNote =getItem(position);


        //create a new view holder
        ViewHolder viewHolder;


      //Checking if an existing view is there, if not then creating a new view
        if(convertView==null)
        {
           //if dont have a view then create one to save view references
            viewHolder =new ViewHolder();
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.list_row, parent, false);

            //Setting our Views

            viewHolder.noteIcon=(ImageView)convertView.findViewById(R.id.listItemNoteImg);
            viewHolder.title=(TextView)convertView.findViewById(R.id.listItemNoteTitle);
            viewHolder.note=(TextView)convertView.findViewById(R.id.listItemNoteBody);
            //now set these to the convert view
            convertView.setTag(viewHolder);
        }else{

            viewHolder=(ViewHolder)convertView.getTag();
         }

        viewHolder.title.setText(myNote.getTitle());
        viewHolder.note.setText(myNote.getMessage());
        viewHolder.noteIcon.setImageResource(myNote.getAssociatedDrawable());


        //Filling the references with the associated myNote'id'sa referencing


        //Returning the view
        return convertView;
    }


}
