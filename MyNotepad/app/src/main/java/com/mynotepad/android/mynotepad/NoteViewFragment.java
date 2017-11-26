package com.mynotepad.android.mynotepad;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoteViewFragment extends Fragment {

    private String imagepath;
    public NoteViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        //THis return the layout
        View fragmentLayout = inflater.inflate(R.layout.fragment_note_view, container, false);

        TextView title = (TextView) fragmentLayout.findViewById(R.id.viewNoteTitle);
        TextView message = (TextView) fragmentLayout.findViewById(R.id.viewNoteMessage);
        ImageView icon = (ImageView) fragmentLayout.findViewById(R.id.viewNoteIcon);
        Button viewimage=(Button) fragmentLayout.findViewById(R.id.viewimage);

        Log.d("inside NoteViewFragment", "inside NoteViewFragment");


        Intent intent = getActivity().getIntent();

        title.setText(intent.getExtras().getString(StartApp.NOTE_TITLE_EXTRA));
        message.setText(intent.getExtras().getString(StartApp.NOTE_MESSAGE_EXTRA));
        MyNote.Category noteCat = (MyNote.Category) intent.getSerializableExtra(StartApp.NOTE_CATEGORY_EXTRA);
        imagepath=intent.getExtras().getString("path");
        icon.setImageResource(MyNote.categoryToDrawable(noteCat));




        int colorBlue = 0;
        int text_bold=0;
        text_bold=intent.getExtras().getInt("textBold");
        Log.d("TextBox value: ", text_bold+ "");
        colorBlue = intent.getExtras().getInt("colorBlue");
        if (colorBlue == 1) {
                message.setBackgroundColor(Color.BLUE);
        } else
        {
                }
       if(text_bold==1)
       {    Log.d("Textbox","Textbox :"+text_bold+"");
        //   message.setTypeface(null, Typeface.BOLD_ITALIC);
           String tempString=message.getText().toString();
           SpannableString spanString = new SpannableString(tempString);
           spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);
           spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
           spanString.setSpan(new StyleSpan(Typeface.ITALIC), 0, spanString.length(), 0);
           //  message.setTypeface(null, Typeface.BOLD_ITALIC);
           message.setText(spanString);
       }

        //Adding a Listener to the view Image button
        viewimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("VIEW CLICKING","VIEW CLICKING");
                Intent intentforimage=new Intent( getActivity(),ViewImage.class );

                intentforimage.putExtra("path",imagepath);
                Log.d("ImgPath snt2 note1=> ", imagepath);
                startActivity(intentforimage);

            }
        });

        // Inflate the layout for this fragment
        return fragmentLayout;
    }

}
