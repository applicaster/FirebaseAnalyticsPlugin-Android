package applicaster.analytics.firebase;

import android.content.Context;
import android.os.Bundle;

import com.applicaster.analytics.BaseAnalyticsAgent;
import com.applicaster.util.APLogger;
import com.google.firebase.analytics.FirebaseAnalytics;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by eladbendavid on 18/01/2017.
 */

public class FirebaseAgent extends BaseAnalyticsAgent {

    public static final int MAX_PARAM_NAME_CHARACTERS_LONG = 40;
    public static final int MAX_PARAM_VALUE_CHARACTERS_LONG = 100;
    public static final String UNEXPECTED_CHARACTER_LEGEND = "_9";
    public static final String FIREBASE_PREFIX = "firebase_";
    private static final String TAG = FirebaseAgent.class.getSimpleName();
    private Map<Character,String> legend;

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    public void initializeAnalyticsAgent(Context context) {
        super.initializeAnalyticsAgent(context);
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        legend = getLegend(context);
    }

    @Override
    public void sendUserID(String userId) {
        super.sendUserID(userId);
        mFirebaseAnalytics.setUserId(userId);
    }


    public static Map<Character,String> getLegend(Context context) {
        JSONObject jObject = null;
        Map<Character, String> output = new HashMap<>();
        try {
            String content = Utilities.getFileContent(context, R.raw.legend);
            jObject = new JSONObject(content);
        } catch (Exception e) {
            APLogger.error(TAG, "failed to load JSON legend", e);
        }

        if(jObject != null) {
            Iterator<?> keys = jObject.keys();

            while (keys.hasNext()) {
                String key = (String) keys.next();
                String value = null;
                try {
                    value = jObject.getString(key);
                } catch (JSONException e) {
                    APLogger.error(TAG, "failed to load JSON legend", e);
                }
                output.put(key.charAt(0), value);
            }
        }
        return output;
    }

    @Override
    public void logEvent(String eventName) {
        logEvent(eventName, null);
    }

    @Override
    public void logEvent(String eventName, TreeMap<String, String> params) {
        super.logEvent(eventName, params);

        Bundle bundle = new Bundle();

        if(params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                StringBuilder nameBuilder = new StringBuilder(entry.getKey());
                StringBuilder valueBuilder = new StringBuilder(entry.getValue());

                String name = refactorParamName(legend, nameBuilder).toString();
                String value = refactorParamValue(valueBuilder).toString();

                bundle.putString(name, value);
            }
        }
        mFirebaseAnalytics.logEvent(eventName, bundle);
    }


    /**
     * Firebase param names limitations:
     * **********************
     * 1. Param names can be up to 40 characters long.
     * 2. Contain alphanumeric characters and underscores ("_").
     * 3. must start with an alphabetic character.
     * 4. The "firebase_" prefix is reserved and should not be used.
     */
    public static StringBuilder refactorParamName(Map<Character,String> legend, StringBuilder eventName) {
        //Contain alphanumeric characters and underscores ("_").
        for (int i =0 ; i < eventName.length() ; i++) {
            char current = eventName.charAt(i);
            if(legend.containsKey(current)){
                String replace =  legend.get(current);
                eventName.replace(i, i + 1, replace);
                i+= (replace.length() -1 );
            }else if( !StringUtils.isAlphanumeric(String.valueOf(current))){
                eventName.replace(i, i + 1, UNEXPECTED_CHARACTER_LEGEND);
                i+= (UNEXPECTED_CHARACTER_LEGEND.length() -1 );
            }
        }
        // The "firebase_" prefix is reserved and should not be used.
        if(eventName.indexOf(FIREBASE_PREFIX) == 0){
            eventName.insert(0, "9");
        }// must start with an alphabetic character.
        else if(!StringUtils.isAlphanumeric("" + eventName.charAt(0))){
            eventName.insert(0,'9');
        }

        //Param names can be up to 40 characters long.
        if (eventName.length() > MAX_PARAM_NAME_CHARACTERS_LONG) {
            eventName.delete(MAX_PARAM_NAME_CHARACTERS_LONG-1, eventName.length() -1);
        }

        return eventName;
    }

    /**
     * Firebase param Value limitations:
     * **********************
     * 1. Param values can be up to 100 characters long.
     * 2. The "firebase_" prefix is reserved and should not be used.
     */
    public static StringBuilder refactorParamValue(StringBuilder evenValue) {
        
        if(evenValue.indexOf(FIREBASE_PREFIX) == 0){
            evenValue.insert(0, "_");
        }

        //Param values can be up to 100 characters long.
        if (evenValue.length() > MAX_PARAM_VALUE_CHARACTERS_LONG) {
            evenValue.delete(MAX_PARAM_VALUE_CHARACTERS_LONG, evenValue.length() - 1);
        }
        
        return evenValue;
    }


}
