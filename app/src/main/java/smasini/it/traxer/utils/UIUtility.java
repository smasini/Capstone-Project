package smasini.it.traxer.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;

import smasini.it.traxer.R;

/**
 * Project: Traxer
 * Package: smasini.it.traxer.utils
 * Created by Simone Masini on 07/02/2016 at 11.32.
 */
public class UIUtility {

    private static ProgressDialog progressDialog;

    public static void showProgressDialog(Context context, String message){
        progressDialog = new ProgressDialog(context, R.style.MyDialog);
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    public static void hideProgressDialog(){
        if(progressDialog!=null && progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }

    public static void showInfoDialog(Activity context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = context.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.info_dialog, null));
        builder.show();
    }

}
