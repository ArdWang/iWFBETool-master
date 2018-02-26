package com.wbt.util.customview.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;

import com.wbt.R;

/**
 * Created by rnd on 2018/1/30.
 *
 */

public class EditDialog extends Dialog{

    public EditDialog(@NonNull Context context) {
        super(context);
    }

    public EditDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected EditDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


    public static class Builder{
        private Context context;
        private String title;
        private OnClickListener positiveButtonClickListener;
        private OnClickListener negativeButtonClickListener;

        private String positiveButtonText;
        private String negativeButtonText;

        public EditText getEdname() {
            return edname;
        }

        public void setEdname(EditText edname) {
            this.edname = edname;
        }

        public EditText getEdaddre() {
            return edaddre;
        }

        public void setEdaddre(EditText edaddre) {
            this.edaddre = edaddre;
        }

        private EditText edname;

        private EditText edaddre;

        private String txtname;

        private String txtaddre;

        public Builder(Context context){
            this.context = context;
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

        public Builder setDeviceName(String deivcename){
            this.txtname = deivcename;
            return this;
        }

        public Builder setDeviceAddre(String deviceaddre){
            this.txtaddre = deviceaddre;
            return this;
        }

        public String getDevicename(){
            if(!txtname.isEmpty()){
                return txtname;
            }
            return "";
        }

        public String getDeviceaddre(){
            if(!txtaddre.isEmpty()){
                return txtaddre;
            }
            return "";
        }



        public EditDialog create(){
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // instantiate the dialog with the custom Theme
            final EditDialog dialog = new EditDialog(context, R.style.Dialog);
            View layout = inflater.inflate(R.layout.edit_dialog_layout, null);
            dialog.addContentView(layout, new LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

            /*Window window = dialog == null ? null : dialog.getWindow();
            if (window != null) {
                //window.setWindowAnimations(R.style.Theme_Slide);
                LayoutParams attr = window.getAttributes();
                if (attr != null) {
                    attr.width = android.view.ViewGroup.LayoutParams.MATCH_PARENT;
                    attr.height = android.view.ViewGroup.LayoutParams.MATCH_PARENT;
                }
            }*/


            // set the dialog title
            //((TextView) layout.findViewById(R.id.title)).setText(title);
            // set the dialog title
            EditText name = layout.findViewById(R.id.txt_devicename);
            EditText addre = layout.findViewById(R.id.txt_deviceaddre);

            if(!getDevicename().isEmpty()&&!getDeviceaddre().isEmpty()) {
                name.setText(getDevicename());
                addre.setText(getDeviceaddre());
            }

            setEdname(name);
            setEdaddre(addre);

            // set the confirm button
            if (positiveButtonText != null) {

                ((Button) layout.findViewById(R.id.positiveButton))
                        .setText(positiveButtonText);
                txtname = name.getText().toString();
                txtaddre = name.getText().toString();
                if (positiveButtonClickListener != null&&!txtname.isEmpty()&&!txtaddre.isEmpty()) {

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
