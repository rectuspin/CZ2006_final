package com.example.athletica.ui.login;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.fragment.app.Fragment;

import com.example.athletica.R;

public class CustomFragment extends Fragment implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        // Hides the keyboard when user clicks out of the EditTexts
        if (v.getId() == R.id.constraintLayout || v.getId() == R.id.logoImageView)
            hideKeyboard(v);
    }


    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
