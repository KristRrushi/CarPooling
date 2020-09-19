package krist.car.Utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Toast;

import java.util.regex.Pattern;

public class Helpers {
    public static void goToActivity(Context context, Class<?> nextActivity) {
        Intent intent = new Intent(context, nextActivity);
        context.startActivity(intent);
    }

    public static void goToActivityAttachBundle(Context context, Class<?> nextActivity, String extrasName, Bundle bundle) {
        Intent intent = new Intent(context, nextActivity);
        intent.putExtra(extrasName, bundle);
        context.startActivity(intent);
    }

    public static void showToastMessage(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static Boolean validateStringBaseOnRegex(String text, String regexPattern) {
        return (!TextUtils.isEmpty(text) && Pattern.compile(regexPattern).matcher(text).matches());
    }
}
