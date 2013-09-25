package com.andrew.giang.xreddit.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.*;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import com.andrew.giang.xreddit.R;

/**
 * Created by Andrew on 9/3/13.
 */
public class LoginDialogFragment extends DialogFragment implements EditText.OnEditorActionListener {
    public interface LoginDialogListener {
        void onFinishLoginDialog(String username, String password);
    }

    private EditText mUsername;
    private EditText mPassword;

    public LoginDialogFragment() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_login, container);
        mUsername = (EditText) view.findViewById(R.id.txt_username);
        mPassword = (EditText) view.findViewById(R.id.txt_password);
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        mPassword.setOnEditorActionListener(this);
        getDialog().setTitle("Login");

        return view;
    }


    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            // Return input text to activity
            LoginDialogListener activity = (LoginDialogListener) getActivity();
            activity.onFinishLoginDialog(mUsername.getText().toString(), mPassword.getText().toString());
            this.dismiss();
            return true;
        }
        return false;
    }
}
