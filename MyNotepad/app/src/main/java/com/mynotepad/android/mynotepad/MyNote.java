package com.mynotepad.android.mynotepad;

import android.util.Log;

/**
 * Created by sachin on 2016-09-27.
 */
public class MyNote {
private String title, message;
    private long noteId, dateCreatedMilli;

    private Category category;

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    private String imagepath;
    private int colorBlue=0, text_bold=0;

    public enum Category{PERONAL, TECHNICAL, QUOTE, FINANCE};

    public MyNote(String title, String message , Category category, int colorBlue, int text_bold, String imagepath)
    {
        this.title=title;
        this.message=message;
        this.category=category;
        this.noteId=0;
        this.dateCreatedMilli=0;
        this.colorBlue=colorBlue;
        this.text_bold=text_bold;
        this.imagepath=imagepath;
   }
    public MyNote(String title, String message , Category category, long noteId, long dateCreatedMilli , int colorBlue, int text_bold, String imagepath)
    {
        Log.d("Inside MyNote Java ",colorBlue+"");
        this.title=title;
        this.message=message;
        this.category=category;
        this.noteId=noteId;
        this.dateCreatedMilli=dateCreatedMilli;
        this.colorBlue=colorBlue;
        this.text_bold=text_bold;
        this.imagepath=imagepath;
    }

    public int getColorBlue(){return colorBlue;};
    public void setColorBlue(int colorBlue){this.colorBlue=colorBlue;};

    public int getText_bold() {
        return text_bold;
    }

    public void setText_bold(int text_bold) {
        this.text_bold = text_bold;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getNoteId() {
        return noteId;
    }

    public void setNoteId(long noteId) {
        this.noteId = noteId;
    }

    public long getDateCreatedMilli() {
        return dateCreatedMilli;
    }

    public void setDateCreatedMilli(long dateCreatedMilli) {
        this.dateCreatedMilli = dateCreatedMilli;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    public String toString()
    {
        return "ID: "+noteId+ " Title: "+title+" Message: "+message +" IconID: " + category.name()
                + " Date: "+"Color: "+ colorBlue ;

    }


    public int getAssociatedDrawable(){
        return categoryToDrawable(category);
    }

    public static int categoryToDrawable(Category noteCategory)
    {
        switch(noteCategory)
        {
            case PERONAL:
                return R.drawable.p;
            case TECHNICAL:
                return R.drawable.s;
            case FINANCE:
                return R.drawable.sa;
            case QUOTE:
                return R.drawable.q;


        }
    return R.drawable.p;


    }



}
