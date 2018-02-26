package com.wbt.util.customview.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wbt.R;

/**
 * Created by Administrator on 2018/1/31.
 *
 */

public class UserDialog extends Dialog{

    public UserDialog(@NonNull Context context) {
        super(context);
    }

    public UserDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected UserDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public static class Builder{
        private Context context;
        private OnClickListener positiveButtonClickListener;
        private OnClickListener negativeButtonClickListener;

        private String positiveButtonText;
        private String negativeButtonText;

        private EditText editText;

        private TextView txtnames;

        private String txtname;

        private String txtedit;

        public Builder(Context context){
            this.context = context;
        }


        public EditText getEditText() {
            return editText;
        }

        public void setEditText(EditText editText) {
            this.editText = editText;
        }

        public String getTxtname() {
            return txtname;
        }

        public void setTxtname(String txtname) {
            this.txtname = txtname;
        }

        public Builder setName(String name){
            this.txtname = name;
            return this;
        }

        public Builder setEdit(String edit){
            this.txtedit = edit;
            return this;
        }

        public String getName(){
            if(!txtname.isEmpty()){
                return txtname;
            }
            return "";
        }

        public String getEdit(){
            if(!txtedit.isEmpty()){
                return txtedit;
            }
            return "";
        }


        /**
         * Set the positive button resource and it's listener
         *
         * @param positiveButtonText
         * @return
         */
        public Builder setPositiveButton(int positiveButtonText,
                                                    OnClickListener listener) {
            this.positiveButtonText = (String) context
                    .getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }


        public Builder setnegativeButton(int negativeButtonText,
                                                    OnClickListener listener) {
            this.negativeButtonText = (String)context.getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }


        public UserDialog cerate(){
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // instantiate the dialog with the custom Theme
            final UserDialog dialog = new UserDialog(context, R.style.Dialog);
            View layout = inflater.inflate(R.layout.user_dialog_layout, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));


            editText = layout.findViewById(R.id.txt_edit);
            txtnames = layout.findViewById(R.id.txt_name);

            if(!getName().isEmpty()&&!getEdit().isEmpty()){
                editText.setText(getEdit());
                txtnames.setText(getName());
            }

            setEditText(editText);

            // set the confirm button
            if (positiveButtonText != null) {

                ((Button) layout.findViewById(R.id.positiveButton))
                        .setText(positiveButtonText);
                //txtname = name.getText().toString();
                //txtaddre = name.getText().toString();
                if (positiveButtonClickListener != null) {

                    (layout.findViewById(R.id.positiveButton))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    positiveButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_POSITIVE);
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.positiveButton).setVisibility(
                        View.GONE);
            }
            // set the cancel button
            if (negativeButtonText != null) {

                ((Button) layout.findViewById(R.id.negativeButton))
                        .setText(negativeButtonText);

                if (negativeButtonClickListener != null) {
                    (layout.findViewById(R.id.negativeButton))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    negativeButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_NEGATIVE);
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.negativeButton).setVisibility(
                        View.GONE);
            }

            dialog.setContentView(layout);
            return dialog;
        }

    }
}
