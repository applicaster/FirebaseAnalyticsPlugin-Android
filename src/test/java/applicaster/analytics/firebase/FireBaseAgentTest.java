package applicaster.analytics.firebase;


import org.apache.commons.lang3.StringUtils;
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
@Config(constants = BuildConfig.class, sdk = 23, manifest = "src/main/AndroidManifest.xml")

public class FireBaseAgentTest {

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
        assertEquals(MAX_PARAM_NAME_CHARACTERS_LONG, FirebaseAgent.refactorParamName(FirebaseAgent.getLegend(RuntimeEnvironment.application), input).length());
    }

    @Test
    public void removeNonAlphanumericCharacters() {
        StringBuilder input = new StringBuilder("%^$");
        FirebaseAgent.refactorParamName(FirebaseAgent.getLegend(RuntimeEnvironment.application), input);
        boolean hasNonAlpha = StringUtils.isAlphanumeric(input.toString().replaceAll("_", ""));
        //2. Contain alphanumeric characters and underscores ("_").
        assertTrue(hasNonAlpha);
    }

    @Test
    public void addAlphabeticPrefix(){
        StringBuilder input = new StringBuilder("$33");
        FirebaseAgent.refactorParamName(FirebaseAgent.getLegend(RuntimeEnvironment.application), input);
        boolean hasNonAlpha = StringUtils.isAlphanumeric("" + input.charAt(0));
        //3. must start with an alphabetic character.
        assertTrue(hasNonAlpha);

    }

    @Test
    public void notStartWithFirebase_(){
        StringBuilder input = new StringBuilder(FIREBASE_PREFIX);
        FirebaseAgent.refactorParamName(FirebaseAgent.getLegend(RuntimeEnvironment.application), input);
        //4. The "firebase_" prefix is reserved and should not be used.
        assertFalse(input.toString().indexOf(FIREBASE_PREFIX) == 0);

    }

}
