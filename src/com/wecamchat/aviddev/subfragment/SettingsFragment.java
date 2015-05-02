package com.wecamchat.aviddev.subfragment;

import java.lang.reflect.Field;
import java.util.Locale;
import java.util.Map;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.wecamchat.aviddev.R;
import com.wecamchat.aviddev.activity.AvidBaseActivity;
import com.wecamchat.aviddev.activity.AvidFragmentBaseActivity;
import com.wecamchat.aviddev.api.ApiName;
import com.wecamchat.aviddev.api.ApiOutput;
import com.wecamchat.aviddev.api.AvidApiClient;
import com.wecamchat.aviddev.api.AvidUrls;
import com.wecamchat.aviddev.api.ErrorObject;
import com.wecamchat.aviddev.api.io.ClaimCodeRegisterOutput;
import com.wecamchat.aviddev.api.io.ProfileEditInput;
import com.wecamchat.aviddev.api.io.ProfileEditOutput;
import com.wecamchat.aviddev.api.io.SmsVerificationOutput;
import com.wecamchat.aviddev.fragment.AvidBaseFragment;
import com.wecamchat.aviddev.util.PreferenceKeeper;

public class SettingsFragment extends AvidBaseFragment implements
        OnCheckedChangeListener,
        android.widget.CompoundButton.OnCheckedChangeListener {

    private View view;
    PreferenceKeeper prefs;
    RadioGroup handPrefs;
    RadioButton leftHand, rightHand;
    private RadioGroup rg_setting_mile_kilometers;
    private RadioButton rb_settings_miles;
    private RadioButton rb_settings_kilometers;
    private CheckBox cb_seetings_passcode;
    private String dialogTitle = "Enter Passcode";
    private Dialog accRetrivalDialog;
    private Dialog smsVerificationDialog;
    private TextView tv_settings_verify_now;

    private String enteredEmailId;
    private String enteredPhoneNumber;
    // passcode variables
    private Dialog passcodeDialog;
    private String passcode_string = "";
    private String passcode_string_again = "";
    private String change_passcode_string = "";
    private String chage_passcode_string_again = "";
    boolean isFromFirstPasscodeDialog;
    private TextView tv_settings_change_passcode;
    private Dialog currentPasscodeDialog;
    private String CurrentPasscodeString;
    private Dialog ChangePasscodeDialog;
    private boolean isFromFirstChangePasscodeDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        try {
            view = inflater.inflate(R.layout.fragment_settings, container,
                    false);

            prefs = PreferenceKeeper.getInstance(getActivity());
            initUi();
            registerListner();
            setOtherData();

            return view;
        } catch (Exception e) {
            e.printStackTrace();
            return view;
        }
    }

    private void initUi() {

        handPrefs = (RadioGroup) view.findViewById(R.id.rg_settings_hand_prefs);
        leftHand = (RadioButton) view.findViewById(R.id.left);
        rightHand = (RadioButton) view.findViewById(R.id.right);

        rg_setting_mile_kilometers = (RadioGroup) view
                .findViewById(R.id.rg_setting_mile_kilometers);
        rb_settings_miles = (RadioButton) view
                .findViewById(R.id.rb_settings_miles);
        rb_settings_kilometers = (RadioButton) view
                .findViewById(R.id.rb_settings_kilometers);

        cb_seetings_passcode = (CheckBox) view
                .findViewById(R.id.cb_seetings_passcode);

        tv_settings_verify_now = (TextView) view
                .findViewById(R.id.tv_settings_verify_now);

        tv_settings_change_passcode = (TextView) view
                .findViewById(R.id.tv_settings_change_passcode);

        if (prefs.isAccountVerified()) {
            tv_settings_verify_now.setText("Verified");

        }

    }

    private void registerListner() {

        handPrefs.setOnCheckedChangeListener(this);
        rg_setting_mile_kilometers.setOnCheckedChangeListener(this);
        cb_seetings_passcode.setOnCheckedChangeListener(this);
        tv_settings_verify_now.setOnClickListener(this);
        tv_settings_change_passcode.setOnClickListener(this);
    }

    private void setOtherData() {

        if (prefs.getDistancePrefrence()) {
            rb_settings_miles.setChecked(true);
        }

        if (prefs.getHandPrefrence()) {
            leftHand.setChecked(true);
        }
        cb_seetings_passcode.setOnCheckedChangeListener(null);

        if (prefs.isPasscodeEnabled()) {
            cb_seetings_passcode.setChecked(true);
        } else {
            cb_seetings_passcode.setChecked(false);
        }

        cb_seetings_passcode.setOnCheckedChangeListener(this);

        tv_settings_change_passcode.setClickable(prefs.isPasscodeEnabled());

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        switch (group.getId()) {
        case R.id.rg_settings_hand_prefs:
            if (checkedId == R.id.left) {
                prefs.setHandPrefrence(true);
            } else {
                prefs.setHandPrefrence(false);
            }
            ((AvidFragmentBaseActivity) getActivity()).manageFooter();
            ((AvidFragmentBaseActivity) getActivity()).setFooterImageOnSwipe(0);
            break;

        case R.id.rg_setting_mile_kilometers:
            if (checkedId == R.id.rb_settings_miles) {
                prefs.setDistancePrefrence(true);
            } else {
                prefs.setDistancePrefrence(false);
            }
            break;

        default:
            break;
        }

    }

    private void showPasscodeDialog() {

        passcodeDialog = initializePasscodeDialog();
        passcodeDialog.setCancelable(false);
        if (isFromFirstPasscodeDialog) {

            TextView passcode_label = (TextView) passcodeDialog
                    .findViewById(R.id.tv_passcode_label);
            passcode_label.setText("New Passcode Again");
        }

        final EditText firstDigitEditText = (EditText) passcodeDialog
                .findViewById(R.id.layout_passcode_first_digit_editText);
        final EditText secondDigitEditText = (EditText) passcodeDialog
                .findViewById(R.id.layout_passcode_second_digit_editText);
        final EditText thirdDigitEditText = (EditText) passcodeDialog
                .findViewById(R.id.layout_passcode_third_digit_editText);
        final EditText fourthDigitEditText = (EditText) passcodeDialog
                .findViewById(R.id.layout_passcode_fourth_digit_editText);

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/GlyphaLTStd.otf");
        firstDigitEditText.setTypeface(font);
        secondDigitEditText.setTypeface(font);
        thirdDigitEditText.setTypeface(font);
        fourthDigitEditText.setTypeface(font);

        firstDigitEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                    int count) {
                if (firstDigitEditText.getText().toString().length() == 1
                        && (!s.toString().equalsIgnoreCase(""))) {

                    secondDigitEditText.requestFocus();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                    int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equalsIgnoreCase("")) {

                    Log.i("edit", "1+afterTextChanged called");
                }
            }

        });
        secondDigitEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                    int count) {
                if (secondDigitEditText.getText().toString().length() == 1
                        && (!s.toString().equalsIgnoreCase(""))) {

                    thirdDigitEditText.requestFocus();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                    int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equalsIgnoreCase("")) {

                    Log.i("edit", "2+afterTextChanged called");
                    firstDigitEditText.requestFocus();
                }

            }
        });
        thirdDigitEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                    int count) {
                if (thirdDigitEditText.getText().toString().length() == 1
                        && (!s.toString().equalsIgnoreCase(""))) {

                    fourthDigitEditText.requestFocus();
                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                    int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equalsIgnoreCase("")) {

                    Log.i("edit", "3+afterTextChanged called");
                    secondDigitEditText.requestFocus();
                }

            }
        });
        fourthDigitEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                    int count) {

                if (fourthDigitEditText.getText().toString().length() == 1
                        && (!s.toString().equalsIgnoreCase(""))) {

                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                    int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equalsIgnoreCase("")) {

                    Log.i("edit", "4+afterTextChanged called");
                    thirdDigitEditText.requestFocus();
                }
            }
        });
        ImageView passcodeCross = (ImageView) passcodeDialog
                .findViewById(R.id.passcode_cross_button);

        passcodeCross.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                passcodeDialog.dismiss();
                isFromFirstPasscodeDialog = false;
                tv_settings_change_passcode.setClickable(false);
                cb_seetings_passcode.setChecked(false);
            }
        });

        fourthDigitEditText
                .setOnEditorActionListener(new OnEditorActionListener() {
                    public boolean onEditorAction(TextView v, int actionId,
                            KeyEvent event) {

                        Log.i("in4thbox", passcode_string + "@@@"
                                + passcode_string_again);
                        if ((event != null
                                && passcode_string.trim().length() == 4 && (event
                                .getKeyCode() == KeyEvent.KEYCODE_ENTER))
                                || (actionId == EditorInfo.IME_ACTION_DONE)) {

                            if (isFromFirstPasscodeDialog) {
                                passcode_string_again = getPasscodeFromEdittext(
                                        firstDigitEditText,
                                        secondDigitEditText,
                                        thirdDigitEditText, fourthDigitEditText);
                            } else {

                                passcode_string = getPasscodeFromEdittext(
                                        firstDigitEditText,
                                        secondDigitEditText,
                                        thirdDigitEditText, fourthDigitEditText);

                            }
                            Log.i("pass",
                                    passcode_string.toString() + "@@@@@@@"
                                            + passcode_string_again.toString());
                            if (isFromFirstPasscodeDialog) {
                                if (passcode_string
                                        .equalsIgnoreCase(passcode_string_again)) {
                                    prefs.setPasscode(passcode_string);
                                    prefs.setPasscodeInSettings(true);
                                    tv_settings_change_passcode
                                            .setClickable(true);
                                    isFromFirstPasscodeDialog = false;
                                    passcodeDialog.dismiss();
                                } else {

                                    firstDigitEditText.setText("");
                                    secondDigitEditText.setText("");
                                    thirdDigitEditText.setText("");
                                    fourthDigitEditText.setText("");
                                    firstDigitEditText.requestFocus();
                                    Toast.makeText(getActivity(),
                                            "Passcodes Do Not Match",
                                            Toast.LENGTH_LONG).show();
                                }

                            } else {

                                isFromFirstPasscodeDialog = true;
                                passcodeDialog.dismiss();
                                showPasscodeDialog();

                            }
                        }
                        return false;
                    }
                });

        passcodeDialog.show();
    }

    private void showCurrentPasscodeDialog() {

        currentPasscodeDialog = initializePasscodeDialog();
        currentPasscodeDialog.setCancelable(false);

        TextView passcode_label = (TextView) currentPasscodeDialog
                .findViewById(R.id.tv_passcode_label);
        passcode_label.setText("Enter Current Passcode");

        final TextView errorMessage = (TextView) currentPasscodeDialog
                .findViewById(R.id.tv_show_error_message);
        final EditText firstDigitEditText = (EditText) currentPasscodeDialog
                .findViewById(R.id.layout_passcode_first_digit_editText);
        final EditText secondDigitEditText = (EditText) currentPasscodeDialog
                .findViewById(R.id.layout_passcode_second_digit_editText);
        final EditText thirdDigitEditText = (EditText) currentPasscodeDialog
                .findViewById(R.id.layout_passcode_third_digit_editText);
        final EditText fourthDigitEditText = (EditText) currentPasscodeDialog
                .findViewById(R.id.layout_passcode_fourth_digit_editText);

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/GlyphaLTStd.otf");
        firstDigitEditText.setTypeface(font);
        secondDigitEditText.setTypeface(font);
        thirdDigitEditText.setTypeface(font);
        fourthDigitEditText.setTypeface(font);

        CurrentPasscodeString = new String();

        firstDigitEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                    int count) {

                secondDigitEditText.requestFocus();

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                    int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equalsIgnoreCase("")) {
                    Log.i("pass", "afterTextChanged called in 1");
                }
            }

        });
        secondDigitEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                    int count) {
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
                    Log.i("pass", "afterTextChanged called in 2");
                    firstDigitEditText.requestFocus();
                }

            }
        });
        thirdDigitEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                    int count) {
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
                    Log.i("pass", "afterTextChanged called in 3");
                    secondDigitEditText.requestFocus();
                }

            }
        });
        fourthDigitEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                    int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                    int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equalsIgnoreCase("")) {
                    Log.i("pass", "afterTextChanged called in 4");
                    thirdDigitEditText.requestFocus();
                }
            }
        });
        ImageView passcodeCross = (ImageView) currentPasscodeDialog
                .findViewById(R.id.passcode_cross_button);
        passcodeCross.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                currentPasscodeDialog.dismiss();

            }
        });

        fourthDigitEditText
                .setOnEditorActionListener(new OnEditorActionListener() {
                    public boolean onEditorAction(TextView v, int actionId,
                            KeyEvent event) {
                        if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER))
                                || (actionId == EditorInfo.IME_ACTION_DONE)) {

                            CurrentPasscodeString = getPasscodeFromEdittext(
                                    firstDigitEditText, secondDigitEditText,
                                    thirdDigitEditText, fourthDigitEditText);

                            if (prefs.getPasscode().equalsIgnoreCase(
                                    CurrentPasscodeString)) {
                                errorMessage.setVisibility(View.INVISIBLE);
                                currentPasscodeDialog.dismiss();
                                showChangePasscodeDialog();

                            } else {

                                firstDigitEditText.setText("");
                                secondDigitEditText.setText("");
                                thirdDigitEditText.setText("");
                                fourthDigitEditText.setText("");
                                firstDigitEditText.requestFocus();
                                errorMessage.setVisibility(View.VISIBLE);
                                errorMessage
                                        .setText("Wrong Passcode,try again.");
                            }
                        }
                        return false;
                    }
                });

        currentPasscodeDialog.show();

    }

    private void showChangePasscodeDialog() {

        ChangePasscodeDialog = initializePasscodeDialog();
        ChangePasscodeDialog.setCancelable(false);

        if (isFromFirstChangePasscodeDialog) {

            TextView passcode_label = (TextView) ChangePasscodeDialog
                    .findViewById(R.id.tv_passcode_label);
            passcode_label.setText("New Passcode Again");
        }

        final EditText firstDigitEditText = (EditText) ChangePasscodeDialog
                .findViewById(R.id.layout_passcode_first_digit_editText);
        final EditText secondDigitEditText = (EditText) ChangePasscodeDialog
                .findViewById(R.id.layout_passcode_second_digit_editText);
        final EditText thirdDigitEditText = (EditText) ChangePasscodeDialog
                .findViewById(R.id.layout_passcode_third_digit_editText);
        final EditText fourthDigitEditText = (EditText) ChangePasscodeDialog
                .findViewById(R.id.layout_passcode_fourth_digit_editText);

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/GlyphaLTStd.otf");
        firstDigitEditText.setTypeface(font);
        secondDigitEditText.setTypeface(font);
        thirdDigitEditText.setTypeface(font);
        fourthDigitEditText.setTypeface(font);

        firstDigitEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                    int count) {
                if (firstDigitEditText.getText().toString().length() == 1
                        && (!s.toString().equalsIgnoreCase(""))) {

                    secondDigitEditText.requestFocus();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                    int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equalsIgnoreCase("")) {

                    Log.i("edit", "1+afterTextChanged called");
                }
            }

        });
        secondDigitEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                    int count) {
                if (secondDigitEditText.getText().toString().length() == 1
                        && (!s.toString().equalsIgnoreCase(""))) {

                    thirdDigitEditText.requestFocus();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                    int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equalsIgnoreCase("")) {

                    Log.i("edit", "2+afterTextChanged called");
                    firstDigitEditText.requestFocus();
                }

            }
        });
        thirdDigitEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                    int count) {
                if (thirdDigitEditText.getText().toString().length() == 1
                        && (!s.toString().equalsIgnoreCase(""))) {

                    fourthDigitEditText.requestFocus();
                }

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

                if (fourthDigitEditText.getText().toString().length() == 1
                        && (!s.toString().equalsIgnoreCase(""))) {

                }

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
        ImageView passcodeCross = (ImageView) ChangePasscodeDialog
                .findViewById(R.id.passcode_cross_button);

        passcodeCross.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                ChangePasscodeDialog.dismiss();
                isFromFirstChangePasscodeDialog = false;
            }
        });

        fourthDigitEditText
                .setOnEditorActionListener(new OnEditorActionListener() {
                    public boolean onEditorAction(TextView v, int actionId,
                            KeyEvent event) {

                        if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER))
                                || (actionId == EditorInfo.IME_ACTION_DONE)) {

                            if (isFromFirstChangePasscodeDialog) {
                                chage_passcode_string_again = getPasscodeFromEdittext(
                                        firstDigitEditText,
                                        secondDigitEditText,
                                        thirdDigitEditText, fourthDigitEditText);
                            } else {

                                change_passcode_string = getPasscodeFromEdittext(
                                        firstDigitEditText,
                                        secondDigitEditText,
                                        thirdDigitEditText, fourthDigitEditText);

                            }
                            if (isFromFirstChangePasscodeDialog) {
                                if (change_passcode_string
                                        .equalsIgnoreCase(chage_passcode_string_again)) {
                                    prefs.setPasscode(change_passcode_string);
                                    prefs.setPasscodeInSettings(true);
                                    isFromFirstChangePasscodeDialog = false;
                                    ChangePasscodeDialog.dismiss();
                                } else {

                                    firstDigitEditText.setText("");
                                    secondDigitEditText.setText("");
                                    thirdDigitEditText.setText("");
                                    fourthDigitEditText.setText("");
                                    firstDigitEditText.requestFocus();
                                    Toast.makeText(getActivity(),
                                            "Passcodes Do Not Match",
                                            Toast.LENGTH_LONG).show();
                                }

                            } else {

                                isFromFirstChangePasscodeDialog = true;
                                ChangePasscodeDialog.dismiss();
                                showChangePasscodeDialog();

                            }
                        }
                        return false;
                    }
                });

        ChangePasscodeDialog.show();
    }

    private String getPasscodeFromEdittext(EditText first, EditText second,
            EditText third, EditText fourth) {

        String passcode = "";

        passcode = first.getText().toString().trim()
                + second.getText().toString().trim()
                + third.getText().toString().trim()
                + fourth.getText().toString().trim();
        return passcode;
    }

    private Dialog initializePasscodeDialog() {
        Dialog dialog = new Dialog(getActivity());

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_passcode_dialog);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));

        return dialog;
    }

    private void showAccountRetrievalDialog() {
        accRetrivalDialog = new Dialog(getActivity());
        accRetrivalDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        accRetrivalDialog.setContentView(R.layout.layout_account_verifier);
        accRetrivalDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));

        final EditText emailEditText = (EditText) accRetrivalDialog
                .findViewById(R.id.layout_account_retrieval_email_editText);
        final EditText mobileEditText = (EditText) accRetrivalDialog
                .findViewById(R.id.layout_account_retrieval_mobile_number_editText);
        final EditText claimCodeEditText = (EditText) accRetrivalDialog
                .findViewById(R.id.layout_account_retrieval_claim_code_editText);

        ImageView countryImageView = (ImageView) accRetrivalDialog
                .findViewById(R.id.layout_account_retrieval_country_imageview);

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/GlyphaLTStd.otf");
        emailEditText.setTypeface(font);
        mobileEditText.setTypeface(font);
        claimCodeEditText.setTypeface(font);

        ((AvidBaseActivity) getActivity()).setDefaultCountryCodeAndFlag(
                countryImageView, mobileEditText);

        ImageView buttonDialogYes = (ImageView) accRetrivalDialog
                .findViewById(R.id.layout_account_retrieval_yes_button);

        buttonDialogYes.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                enteredEmailId = emailEditText.getText().toString().trim();
                enteredPhoneNumber = mobileEditText.getText().toString().trim();

                final String claimCodeEntered = claimCodeEditText.getText()
                        .toString().trim();

                verifyAccount(enteredEmailId, enteredPhoneNumber);

            }
        });

        ImageView buttonDialogNo = (ImageView) accRetrivalDialog
                .findViewById(R.id.layout_account_retrieval_no_button);
        buttonDialogNo.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                accRetrivalDialog.dismiss();
            }
        });

        accRetrivalDialog.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

                accRetrivalDialog.dismiss();
            }
        });
        accRetrivalDialog.show();
    }

    private void showSmsVerificationDialog() {

        smsVerificationDialog = new Dialog(getActivity());
        smsVerificationDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        smsVerificationDialog.setContentView(R.layout.layout_sms_verification);
        smsVerificationDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));

        final EditText smsCode = (EditText) smsVerificationDialog
                .findViewById(R.id.layout_sms_verification_code_editText);

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/GlyphaLTStd.otf");
        smsCode.setTypeface(font);

        ImageView buttonDialogYes = (ImageView) smsVerificationDialog
                .findViewById(R.id.layout_sms_verification_yes_button);
        buttonDialogYes.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String verificationCode = smsCode.getText().toString().trim();

                // call api
                verifyVCandCC(verificationCode);
            }

        });

        ImageView buttonDialogNo = (ImageView) smsVerificationDialog
                .findViewById(R.id.layout_sms_verification_no_button);
        buttonDialogNo.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                // TODO
            }
        });

        smsVerificationDialog.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

                // TODO
            }
        });
        smsVerificationDialog.show();
    }

    private void verifyAccount(String emailEntered, String numberEntered) {

        initializeBaseApiResponseHandler();

        AvidApiClient apiClaimCode = AvidApiClient.getInstance();

        ProfileEditInput input = new ProfileEditInput();

        if (emailEntered != null && numberEntered != null) {
            input.setEmail(emailEntered);
            input.setPhone(numberEntered);
        }

        Map<String, String> params = input.getParams();

        apiClaimCode.post(1, AvidUrls.BASE_URL + AvidUrls.PROFILE_EDIT, params,
                getHeaders(), ApiName.profileEdit, new ProfileEditOutput(),
                getApiResponseHandler());

    }

    private void verifyVCandCC(String verificationCode) {

        initializeBaseApiResponseHandler();
        AvidApiClient smsVerification = AvidApiClient.getInstance();
        smsVerification.get(1, AvidUrls.BASE_URL + AvidUrls.VERIFY + "/"
                + verificationCode + "?phone=" + enteredPhoneNumber, null,
                getHeaders(), ApiName.smsVerify, new SmsVerificationOutput(),
                getApiResponseHandler());
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        switch (buttonView.getId()) {
        case R.id.cb_seetings_passcode:
            if (isChecked) {
                showPasscodeDialog();
            } else {
                prefs.setPasscodeInSettings(false);
                tv_settings_change_passcode.setClickable(false);
            }
            break;

        default:
            break;
        }

    }

    @Override
    protected void apiSuccessResponse(int reqId, ApiOutput output, ApiName type) {
        switch (type) {
        case claimCodeRegister:

            // TODO just for testing after it set 1 value.

            if (((ClaimCodeRegisterOutput) output).getStatusCode()
                    .equalsIgnoreCase("1")) {

                accRetrivalDialog.dismiss();
                showSmsVerificationDialog();
            } else {
                Toast.makeText(getActivity(), "Enter Valid Claim Code",
                        Toast.LENGTH_LONG).show();
            }
            break;
        case smsVerify:
            if (((SmsVerificationOutput) output).getStatusCode()
                    .equalsIgnoreCase("1")) {

                smsVerificationDialog.dismiss();
                tv_settings_verify_now.setText("VERIFIED");
                tv_settings_verify_now.setClickable(false);
            } else {
                smsVerificationDialog.dismiss();

                showTaost("Some error occured. Please try again.");
            }

            break;

        case profileEdit:

            // ProfileEditOutput profileEditOutput = (ProfileEditOutput) output;

            accRetrivalDialog.dismiss();
            prefs.setAccountVerified(true);
            tv_settings_verify_now.setText("Verified");

            showTaost("Account verified successfully.");
            // showSmsVerificationDialog();

            break;

        default:
            break;
        }

    }

    @Override
    protected void apiFailedResponse(int reqId, ErrorObject errorObject,
            ApiName type) {
        // TODO Auto-generated method stub
        super.apiFailedResponse(reqId, errorObject, type);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
        case R.id.tv_settings_verify_now:
            if (!prefs.isAccountVerified()) {
                showAccountRetrievalDialog();
            }
            break;
        case R.id.tv_settings_change_passcode:
            showCurrentPasscodeDialog();
        default:
            break;
        }
    }

    public boolean onBackPressed() {
        return false;
    }

}
