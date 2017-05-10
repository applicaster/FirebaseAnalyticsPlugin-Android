package applicaster.analytics.firebase;


import android.app.Application;

import com.applicaster.app.CustomApplication;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static applicaster.analytics.firebase.FirebaseAgent.FIREBASE_PREFIX;
import static applicaster.analytics.firebase.FirebaseAgent.MAX_PARAM_NAME_CHARACTERS_LONG;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by eladbendavid on 25/01/2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, application = FireBaseAgentTest.TestCustomApplication.class, sdk = 18)



public class FireBaseAgentTest {

    // since CustomApplication failed on Robolectric init. i override the problematic function
    public static class TestCustomApplication extends CustomApplication {

        @Override
        protected void primaryApplicationSetup(Application application) {
//            onCreateBehaviour(application);
            initializeInjectors();
        }

    }
    /**
     * Firebase param names limitations:
     * **********************
     * 1. Param names can be up to 40 characters long.
     * 2. Contain alphanumeric characters and underscores ("_").
     * 3. must start with an alphabetic character.
     * 4. The "firebase_" prefix is reserved and should not be used.
     */

    @Test
    public void removeLastCharacters() {
        StringBuilder input = new StringBuilder();

        for (int i = 0; i < MAX_PARAM_NAME_CHARACTERS_LONG + 100; i++) {
            input.append('1');
        }
        // 1. Param names can be up to 40 characters long.
        assertEquals(MAX_PARAM_NAME_CHARACTERS_LONG, FirebaseAgent.refactorEventNameAndParamsName(FirebaseAgent.getLegend(RuntimeEnvironment.application), input).length());
    }

    @Test
    public void removeNonAlphanumericCharacters() {
        StringBuilder input = new StringBuilder("%^$");
        FirebaseAgent.refactorEventNameAndParamsName(FirebaseAgent.getLegend(RuntimeEnvironment.application), input);
        boolean hasNonAlpha = FirebaseAgent.isAlphanumeric(input.toString().replaceAll("_", ""));
        //2. Contain alphanumeric characters and underscores ("_").
        assertTrue(hasNonAlpha);
    }

    @Test
    public void addAlphabeticPrefix(){
        StringBuilder input = new StringBuilder("$33");
        FirebaseAgent.refactorEventNameAndParamsName(FirebaseAgent.getLegend(RuntimeEnvironment.application), input);
        boolean hasNonAlpha = FirebaseAgent.isAlphanumeric("" + input.charAt(0));
        //3. must start with an alphabetic character.
        assertTrue(hasNonAlpha);

    }
    @Test
    public void isAlphanumeric(){
        StringBuilder input = new StringBuilder("3_3");
        boolean hasNonAlpha = FirebaseAgent.isAlphanumeric("" + input);
        //3. must start with an alphabetic character.
        assertTrue(hasNonAlpha);

    }
    @Test
    public void notStartWithFirebase_(){
        StringBuilder input = new StringBuilder(FIREBASE_PREFIX);
        FirebaseAgent.refactorEventNameAndParamsName(FirebaseAgent.getLegend(RuntimeEnvironment.application), input);
        //4. The "firebase_" prefix is reserved and should not be used.
        assertFalse(input.toString().indexOf(FIREBASE_PREFIX) == 0);

    }

}
