package com.example.dandane.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.dandane.R;

public class CustomDialog extends Dialog {
    EditText titleEdit, descEdit;
    Button confirmButton, cancelButton;

    public CustomDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.add_voca_dialog);
        titleEdit = findViewById(R.id.addVocaTitle);
        descEdit = findViewById(R.id.addVocaDesc);
        confirmButton = findViewById(R.id.addVocaConfirm);
        cancelButton = findViewById(R.id.addVocaCancel);
    }
    public void setPositiveOnclickListner(View.OnClickListener onclickListner) {
        confirmButton.setOnClickListener(onclickListner);
    }
    public void setNegativeOnclickListner(View.OnClickListener onclickListner) {
        cancelButton.setOnClickListener(onclickListner);
    }
    public String getTitle() {
        return titleEdit.getText().toString();
    }
    public String getDesc() {
        return descEdit.getText().toString();
    }
}
