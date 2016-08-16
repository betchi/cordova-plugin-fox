package com.github.betchi.fox;

import org.apache.cordova.*;
import jp.appAdForce.android.AdManager;
import jp.appAdForce.android.LtvManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Intent;
import android.content.Context;

public class Fox extends CordovaPlugin {
    @Override
    public boolean execute(String action, JSONArray inputs, final CallbackContext callbackContext) throws JSONException {
        PluginResult result = null;

        final Activity activity = cordova.getActivity();
        final Context context = activity.getApplicationContext();
        Intent intent = new Intent(context, cordova.getActivity().getClass());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

        if (action.equals("init")) {

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AdManager ad = new AdManager(context);
                    ad.sendConversion("default");
                }
            });

            callbackContext.success();
        } else if (action.equals("sendLtvConversion")) {
            JSONObject options = inputs.optJSONObject(0);
            if (!options.has("conversionId")) {
                return false;
            }

            AdManager ad = new AdManager(context);
            LtvManager ltv = new LtvManager(ad);
            int conversionId = options.getInt("conversionId");
            if (options.has("price") && options.has("currency")) {
                String price = options.getString("price");
                String currency = options.getString("currency");
                ltv.addParam(LtvManager.URL_PARAM_PRICE, price);
                ltv.addParam(LtvManager.URL_PARAM_CURRENCY, currency);
            }
            ltv.sendLtvConversion(conversionId);
        }

        if (result != null) callbackContext.sendPluginResult(result);
        return true;
    }
}

