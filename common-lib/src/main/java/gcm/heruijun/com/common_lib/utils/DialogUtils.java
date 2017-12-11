package gcm.heruijun.com.common_lib.utils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import gcm.heruijun.com.common_lib.R;

/**
 * Created by heruijun on 2017/12/12.
 */

public class DialogUtils {

    public static Dialog createProgressDialog(Context context) {
        return createProgressDialog(context, true);
    }

    public static Dialog createProgressDialog(Context context, boolean needCancle) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Loading ...");
        dialog.setCancelable(needCancle);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    public static Dialog showCommonDialog(Context context, String message,
                                          DialogInterface.OnClickListener listener) {
        return showCommonDialog(context, message, context.getString(R.string.dialog_positive),
                context.getString(R.string.dialog_negative), listener);
    }

    public static Dialog showCommonDialog(Context context, String message, String positiveText,
                                          String negativeText, DialogInterface.OnClickListener
                                                  listener) {
        return new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton(positiveText, listener)
                .setNegativeButton(negativeText, null)
                .show();
    }

    public static Dialog showConfirmDialog(Context context, String message,
                                           DialogInterface.OnClickListener listener) {
        return showConfirmDialog(context, message, context.getString(R.string.dialog_positive),
                listener);
    }

    public static Dialog showConfirmDialog(Context context, String message, String positiveText,
                                           DialogInterface.OnClickListener listener) {
        return new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton(positiveText, listener)
                .show();
    }

}
