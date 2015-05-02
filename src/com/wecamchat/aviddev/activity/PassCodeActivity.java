package com.wecamchat.aviddev.activity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.wecamchat.aviddev.R;

public class PassCodeActivity extends AvidBaseActivity {

    private Dialog passcodeDialog;
    protected String enteredPasscode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_code);

        showEnterPasscodeDialog();
    }

    public void showEnterPasscodeDialog() {

        passcodeDialog = new Dialog(this);
        passcodeDialog.setCancelable(false);
        passcodeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        passcodeDialog.setContentView(R.layout.layout_passcode_dialog);
        passcodeDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));

        TextView passcode_label = (TextView) passcodeDialog
                .findViewById(R.id.tv_passcode_label);
        passcode_label.setText("Enter Passcode");

        final TextView errorMessage = (TextView) passcodeDialog
                .findViewById(R.id.tv_show_error_message);
        final EditText firstDigitEditText = (EditText) passcodeDialog
                .findViewById(R.id.layout_passcode_first_digit_editText);
        final EditText secondDigitEditText = (EditText) passcodeDialog
                .findViewById(R.id.layout_passcode_second_digit_editText);
        final EditText thirdDigitEditText = (EditText) passcodeDialog
                .findViewById(R.id.layout_passcode_third_digit_editText);
        final EditText fourthDigitEditText = (EditText) passcodeDialog
                .findViewById(R.id.layout_passcode_fourth_digit_editText);

        Typeface font = Typeface.createFromAsset(this.getAssets(),
                "fonts/GlyphaLTStd.otf");

        firstDigitEditText.setTypeface(font);
        secondDigitEditText.setTypeface(font);
        thirdDigitEditText.setTypeface(font);
        fourthDigitEditText.setTypeface(font);

        firstDigitEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                    int count) {

                // passcode_string = "" + s;
                secondDigitEditText.requestFocus();

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                    int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });
        secondDigitEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                    int count) {
                // passcode_string = passcode_string + s;
                thirdDigitEditText.requestFocus();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                    int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equalsIgnoreCase("")) {

                    firstDigitEditText.requestFocus();
                }

            }
        });
        thirdDigitEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                    int count) {
                // passcode_string = passcode_string + s;
                fourthDigitEditText.requestFocus();

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                    int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equalsIgnoreCase("")) {

                    secondDigitEditText.requestFocus();
                }
            }
        });
        fourthDigitEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                    int count) {
                // passcode_string = passcode_string + s;

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                    int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equalsIgnoreCase("")) {

                    thirdDigitEditText.requestFocus();
                }
            }
        });

        ImageView passcodeCross = (ImageView) passcodeDialog
                .findViewById(R.id.passcode_cross_button);
        passcodeCross.setVisibility(View.GONE);

        fourthDigitEditText
                .setOnEditorActionListener(new OnEditorActionListener() {

                    public boolean onEditorAction(TextView v, int actionId,
                            KeyEvent event) {
                        if ((event != null
                                && enteredPasscode.trim().length() == 4 && (event
                                .getKeyCode() == KeyEvent.KEYCODE_ENTER))
                                || (actionId == EditorInfo.IME_ACTION_DONE)) {

                            enteredPasscode = firstDigitEditText.getText()
                                    .toString().trim()
                                    + secondDigitEditText.getText().toString()
                                            .trim()
                                    + thirdDigitEditText.getText().toString()
                                            .trim()
                                    + fourthDigitEditText.getText().toString()
                                            .trim();

                            if (getPreferenceKeeper().getPasscode()
                                    .equalsIgnoreCase(enteredPasscode.trim())) {
                                errorMessage.setVisibility(View.INVISIBLE);

                                passcodeDialog.dismiss();
                                PassCodeActivity.this.finish();

                            } else {

                                errorMessage.setVisibility(View.VISIBLE);
                                firstDigitEditText.setText("");
                                secondDigitEditText.setText("");
                                thirdDigitEditText.setText("");
                                fourthDigitEditText.setText("");
                                errorMessage
                                        .setText("Wrong Passcode,try again.");
                            }

                        }
                        return false;
                    }
                });

        passcodeDialog.show();
    }

}
