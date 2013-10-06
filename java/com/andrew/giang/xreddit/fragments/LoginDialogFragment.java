package com.andrew.giang.xreddit.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.andrew.giang.xreddit.R;
import com.andrew.giang.xreddit.network.LoginRequest;
import com.andrew.giang.xreddit.network.RequestManager;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * Created by Andrew on 9/3/13.
 */
public class LoginDialogFragment extends DialogFragment implements EditText.OnEditorActionListener {


    public void onFinishLoginDialog(String username, String password) {

        LoginRequest request = new LoginRequest(username, password, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject array) {
                Toast.makeText(getActivity(), array.toString(), Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.w("COOKIE", volleyError.getMessage());
            }
        }
        );
        RequestManager.getInstance(getActivity()).makeRequest(request);
    }


    private EditText mUsername;
    private EditText mPassword;

    public LoginDialogFragment() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final View root = getActivity().getLayoutInflater().inflate(R.layout.dialog_login, null);
        mUsername = (EditText) root.findViewById(R.id.txt_username);
        mPassword = (EditText) root.findViewById(R.id.txt_password);
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        mPassword.setOnEditorActionListener(this);


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Login");
        builder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onFinishLoginDialog(mUsername.getText().toString(), mPassword.getText().toString());
                dialog.dismiss();
            }
        }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setView(root);
        return builder.create();
    }


    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            onFinishLoginDialog(mUsername.getText().toString(), mPassword.getText().toString());
            this.dismiss();
            return true;
        }
        return false;
    }
}
