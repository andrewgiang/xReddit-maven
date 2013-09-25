package com.andrew.giang.xreddit.common;

import android.text.Editable;
import android.text.Html;
import org.xml.sax.XMLReader;

/**
 * Created by Andrew on 9/22/13.
 * <p/>
 * Taken from stackoverflow - http://stackoverflow.com/posts/9546532/revisions
 */
public class CustomTagHandler implements Html.TagHandler {
    boolean first = true;
    String parent = null;
    int index = 1;

    @Override
    public void handleTag(boolean opening, String tag, Editable output,
                          XMLReader xmlReader) {

        if (tag.equals("ul")) parent = "ul";
        else if (tag.equals("ol")) parent = "ol";
        if (tag.equals("li")) {
            if (parent.equals("ul")) {
                if (first) {
                    output.append("\n \u2022");
                    first = false;
                } else {
                    first = true;
                }
            } else {
                if (first) {
                    output.append("\n " + index + ". ");
                    first = false;
                    index++;
                } else {
                    first = true;
                }
            }
        }
    }
}

