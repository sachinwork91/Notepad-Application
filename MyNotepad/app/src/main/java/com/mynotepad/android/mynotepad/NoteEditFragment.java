package com.mynotepad.android.mynotepad;


import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ToggleButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoteEditFragment extends Fragment {

    private ImageButton noteCatButton;
    private EditText title,message;
    private MyNote.Category  savedButtonCategory;
    private AlertDialog categoryDialogObject, confirmDialogObject;
    private static final String MODIFIED_CATEGORY="Modified Category";
    private boolean newNote=false;
    private int color_yn=0, text_bold=0;
    private long noteId=0;
    //For creating Alert Dialog Object
    private AlertDialog categoryDialogObjectForImage;
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private Uri mCapturedImageURI;    //This is used to store the path of image from camera
    private String imagepath="";

    public NoteEditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        Bundle bundle=this.getArguments();
        if(bundle!=null)
        {
          newNote=bundle.getBoolean(MyDetailActivity.NEW_NOTE_EXRTA, false);
        }

      if(savedInstanceState!=null){

           savedButtonCategory= (MyNote.Category)savedInstanceState.get(MODIFIED_CATEGORY);
        }

        //populate the layout
        View fragmentLayout=inflater.inflate(R.layout.fragment_note_edit, container, false);

        //Getting the references
        title= (EditText) fragmentLayout.findViewById(R.id.editNoteTitle);
        message= (EditText) fragmentLayout.findViewById(R.id.editNoteMessage);
        noteCatButton=(ImageButton)fragmentLayout.findViewById(R.id.editNoteButton);
        Button savedButton=(Button)fragmentLayout.findViewById(R.id.saveNote);
        ToggleButton colorBlue=(ToggleButton) fragmentLayout.findViewById(R.id.colorBlue);
        ToggleButton boldItaclics=(ToggleButton) fragmentLayout.findViewById(R.id.boldItalics);
        Button addImgbutton=(Button)fragmentLayout.findViewById(R.id.addimg);
        boldItaclics.setTextOff("BOLD");

        if(text_bold==1)
        {
            boldItaclics.setTextOn("Unbold");
        }
        //Populating the views with the data
        Intent intent=getActivity().getIntent();
        title.setText(intent.getExtras().getString(StartApp.NOTE_TITLE_EXTRA,""));
        message.setText(intent.getExtras().getString(StartApp.NOTE_MESSAGE_EXTRA, ""));
        noteId=intent.getExtras().getLong(StartApp.MYNOTE_IDENTIFIER_EXTRA, 0);

        //setting the background color of text
        if(color_yn==1)
        {
           message.setBackgroundColor(Color.BLUE);
        }
        //IF the text which was stored had font bold and italics
        if(text_bold==1)
        {
           // message.setTypeface(null, Typeface.BOLD_ITALIC);
            String tempString=message.getText().toString();
            SpannableString spanString = new SpannableString(tempString);
            spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);
            spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
            spanString.setSpan(new StyleSpan(Typeface.ITALIC), 0, spanString.length(), 0);
            //  message.setTypeface(null, Typeface.BOLD_ITALIC);
            message.setText(tempString);
        }

        if(savedButtonCategory!=null){
            noteCatButton.setImageResource(MyNote.categoryToDrawable(savedButtonCategory));
        }else if (!newNote) {
//This is the part which gets executed when the note is not new
            color_yn=intent.getExtras().getInt("colorBlue");
            text_bold=intent.getExtras().getInt("textBold");
            imagepath=intent.getExtras().getString("path");

            if(color_yn==1)
            {
                message.setBackgroundColor(Color.BLUE);
            }
            if(text_bold==1)
            {
                String tempString=message.getText().toString();
                SpannableString spanString = new SpannableString(tempString);
                spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);
                spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
                spanString.setSpan(new StyleSpan(Typeface.ITALIC), 0, spanString.length(), 0);
              //  message.setTypeface(null, Typeface.BOLD_ITALIC);
                   message.setText(spanString);

            }

            MyNote.Category noteCat = (MyNote.Category) intent.getSerializableExtra(StartApp.NOTE_CATEGORY_EXTRA);
            savedButtonCategory = noteCat;
            noteCatButton.setImageResource(MyNote.categoryToDrawable(noteCat));
        }
        buildCategoryDailog();
        //used for creating Confirm DialogBox
        buildConfirmDialog();
        //used for the image
        buildCategoryDailogforimage();
        noteCatButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                categoryDialogObject.show();

            }

        });
        savedButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                confirmDialogObject.show();

            }

        });

        //Setting the listener for AddImage Button
        addImgbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                categoryDialogObjectForImage.show();
            }
        });



        colorBlue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    message.setBackgroundColor(Color.BLUE);
                    color_yn=1;
                } else {
                    // The toggle is disabled
                         message.setBackgroundColor(Color.WHITE);
                    color_yn=0;
                }
            }
        });


        boldItaclics.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Log.d("Bolditaclis pressed","yoyo");
                    String tempString=message.getText().toString();
                    SpannableString spanString = new SpannableString(tempString);
                    spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);
                    spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
                    spanString.setSpan(new StyleSpan(Typeface.ITALIC), 0, spanString.length(), 0);
                    message.setText(spanString);
                 //   message.setTypeface(null, Typeface.BOLD_ITALIC);
                    text_bold=1;
                } else {
                    // The toggle is disabled
                 //   message.setTypeface(null, 0);
                    message.setText(message.getText().toString());
                    text_bold=0;
                }
            }
        });

     return fragmentLayout;


    }

//This function is called when the screen is rotated
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable(MODIFIED_CATEGORY,savedButtonCategory);



    }

    private void buildCategoryDailog(){

        final String[] categories=new String[]{"Personal", "Technical","Quote","Finance"};
        AlertDialog.Builder categoryBuilder=new AlertDialog.Builder(getActivity());
        categoryBuilder.setTitle("Choose MyNote Type");

        categoryBuilder.setSingleChoiceItems(categories, 0, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {
                categoryDialogObject.cancel();
           switch(item){

               case 0:
                   savedButtonCategory= MyNote.Category.PERONAL;
                   noteCatButton.setImageResource(R.drawable.p);
                   break;

               case 1:
                   savedButtonCategory= MyNote.Category.TECHNICAL;
                   noteCatButton.setImageResource(R.drawable.s);
                   break;

               case 2:
                   savedButtonCategory= MyNote.Category.QUOTE;
                   noteCatButton.setImageResource(R.drawable.q);
                   break;

               case 3:
                   savedButtonCategory= MyNote.Category.FINANCE;
                   noteCatButton.setImageResource(R.drawable.sa);
                   break;

           }
            }
        });
        categoryDialogObject=categoryBuilder.create();
    }


    //Used for Dialog box for Save Button
    private void buildConfirmDialog(){

        AlertDialog.Builder confirmBuilder=new AlertDialog.Builder(getActivity());

        confirmBuilder.setTitle("Are You Sure");
        confirmBuilder.setMessage("Are you sure you want to save the note ? ");

        confirmBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            Log.d("Save MyNote", "MyNote Title: "+title.getText()+" MyNote Message: "+message.getText()+"" +
                    "MyNote Category: "+savedButtonCategory);


            NotebookDbAdapter dbAdapter=new NotebookDbAdapter(getActivity().getBaseContext());
            dbAdapter.open();
             if(newNote)
             {
                 if(savedButtonCategory==null)
                 {
                     Log.d("SAVED IS NULL","SAVED IS NULL");
                 }else
                 { Log.d("SAVED IS NOT NULL","SAVED IS NOT NULL");}
                    //if it is a new note then create it in the database
                dbAdapter.createNote(title.getText()+"",message.getText()+"",
                        (savedButtonCategory==null)? MyNote.Category.PERONAL:savedButtonCategory, color_yn, text_bold,imagepath);
             }else
             { //Old note and update the existing note
                 dbAdapter.updateNote(noteId, title.getText()+"", message.getText()+"", savedButtonCategory, color_yn, text_bold,imagepath);

             }
                dbAdapter.close();
                Intent intent=new Intent(getActivity(),StartApp.class);
                startActivity(intent);


            }
        });

        confirmBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //do nothing
            }
        });

        confirmDialogObject=confirmBuilder.create();
    }


    //THIS IS USED FOR IMAGES
    private void buildCategoryDailogforimage(){

        final String[] to_do_with_image=new String[]{"Open Gallery", "Take Photo","REMOVE IMAGE","Exit"};
        AlertDialog.Builder categoryBuilder=new AlertDialog.Builder(getContext());
        categoryBuilder.setTitle("Choose MyNote Type");

        categoryBuilder.setSingleChoiceItems(to_do_with_image, 0, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {
                categoryDialogObjectForImage.cancel();
                switch(item){

                    case 0:
                        activeGallery();
                        break;

                    case 1:
                        activeTakePhoto();
                        break;

                    case 2:
                        imagepath="";
                        break;

                    case 3:
                        break;

                }
            }
        });
        categoryDialogObjectForImage=categoryBuilder.create();
    }
    //This function is used to open the gallery to select the image on the button click
    private void activeGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, RESULT_LOAD_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_LOAD_IMAGE:
                if (requestCode == RESULT_LOAD_IMAGE &&
                        resultCode == Activity.RESULT_OK && null != data) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContext().getContentResolver()
                            .query(selectedImage, filePathColumn, null, null,
                                    null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    imagepath = picturePath;

                } break;
            case REQUEST_IMAGE_CAPTURE:
                if (requestCode == REQUEST_IMAGE_CAPTURE &&
                        resultCode == Activity.RESULT_OK) {
                    String[] projection = {MediaStore.Images.Media.DATA};
                    Cursor cursor =
                            /*getActivity().managedQuery(mCapturedImageURI, projection, null,
                                    null, null);*/
                            getActivity().getContentResolver().query(mCapturedImageURI, projection, null, null, null);
                    int column_index_data = cursor.getColumnIndexOrThrow(
                            MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    String picturePath = cursor.getString(column_index_data);
                    imagepath = picturePath;
                }
        }
    }

    //This function is called when the user selects to add the image from camera
    private void activeTakePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            String fileName = "temp.jpg";
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, fileName);
            mCapturedImageURI = getActivity().getContentResolver()
                    .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            values);
            takePictureIntent
                    .putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

}
