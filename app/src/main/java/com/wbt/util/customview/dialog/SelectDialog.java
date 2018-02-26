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
import com.wbt.R;


/**
 * Created by rnd on 2018/2/1.
 *
 */

public class SelectDialog extends Dialog{

    public SelectDialog(@NonNull Context context) {
        super(context);
    }

    public SelectDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected SelectDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public static class Builder{

        private Context context;

        private OnClickListener positiveButtonClickListener;

        private OnClickListener negativeButtonClickListener;

        private String positiveButtonText;

        private String negativeButtonText;


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


        public SelectDialog create(){
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // instantiate the dialog with the custom Theme
            final SelectDialog dialog = new SelectDialog(context, R.style.Dialog);
            View layout = inflater.inflate(R.layout.select_dialog_layout, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            // set the confirm button
            if (positiveButtonText != null) {
                ((Button) layout.findViewById(R.id.positiveButton))
                        .setText(positiveButtonText);

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
