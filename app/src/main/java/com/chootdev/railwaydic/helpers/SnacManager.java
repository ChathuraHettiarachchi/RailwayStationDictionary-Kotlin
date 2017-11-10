package com.chootdev.railwaydic.helpers;

import android.content.Context;

import com.chootdev.csnackbar.Duration;
import com.chootdev.csnackbar.Snackbar;
import com.chootdev.csnackbar.Type;

/**
 * Created by Choota on 11/10/17.
 */

public class SnacManager {
    public  void makeMessage(Context context, int type, String message){
        switch (type){
            case 1:
                Snackbar.with(context, null)
                        .type(Type.SUCCESS)
                        .message(message)
                        .duration(Duration.LONG)
                        .show();
                break;
            case 2:
                Snackbar.with(context, null)
                        .type(Type.ERROR)
                        .message(message)
                        .duration(Duration.LONG)
                        .show();
                break;
        }
    }
}
