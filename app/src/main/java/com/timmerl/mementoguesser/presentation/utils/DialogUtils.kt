package com.timmerl.mementoguesser.presentation.utils

import android.app.Activity
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.timmerl.mementoguesser.R

/**
 * Created by Timmerman_Lyderic on 28/02/2021.
 */

fun Activity.showAlertWithEditText(
    title: String,
    positiveButtonText: String,
    action: (String) -> Unit,
    showKeyBoardOnAppear: Boolean = true,
    hideKeyBoardOnDismiss: Boolean = true
) =
    AlertDialog.Builder(this).apply {
        setTitle(title)
        val view = layoutInflater.inflate(R.layout.edit_text_dialog, null)
        setView(view)
        setPositiveButton(positiveButtonText) { _, _ ->
            action.invoke(view.findViewById<EditText>(R.id.dialog_edit_text).text.toString())
        }
        if (hideKeyBoardOnDismiss)
            setOnDismissListener { view.hideKeyboard() }
        if (showKeyBoardOnAppear)
            view.showKeyboard()
    }.create().show()

/*

public void showAlertDialogButtonClicked(View view)
    {

        // Create an alert builder
        AlertDialog.Builder builder
            = new AlertDialog.Builder(this);
        builder.setTitle("Name");

        // set the custom layout
        final View customLayout
            = getLayoutInflater()
                  .inflate(
                      R.layout.custom_layout,
                      null);
        builder.setView(customLayout);

        // add a button
        builder
            .setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(
                        DialogInterface dialog,
                        int which)
                    {

                        // send data from the
                        // AlertDialog to the Activity
                        EditText editText
                            = customLayout
                                  .findViewById(
                                      R.id.editText);
                        sendDialogDataToActivity(
                            editText
                                .getText()
                                .toString());
                    }
                });

        // create and show
        // the alert dialog
        AlertDialog dialog
            = builder.create();
        dialog.show();
    }
 */