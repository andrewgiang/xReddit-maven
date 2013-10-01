package com.andrew.giang.xreddit.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.TextView;

import com.andrew.giang.xreddit.R;

/**
 * Created by Andrew on 10/1/13.
 */
public class CasualDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_casual, null);
        final TextView casualSubreddit = (TextView) view.findViewById(R.id.txt_casual);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Casual Subreddit");
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String subreddit = casualSubreddit.getText().toString();
                getFragmentManager().beginTransaction().replace(R.id.content_frame,
                        SubredditListFragment.getInstance(subreddit, null)).commit();
                getActivity().setTitle(subreddit);
                getDialog().dismiss();
            }
        }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getDialog().dismiss();
            }
        }).setView(view);


        return builder.create();
    }

}
