package com.espiandev.redux.auth;

import android.content.SharedPreferences;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;

/**
 * Created by Alex Curran on 13/02/2014.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = "AndroidManifest.xml")
public class SharedPreferencesTokenStorageTest {

    private SharedPreferencesTokenStorage storage;
    private SharedPreferences mockPreferences;

    @Before
    public void setUp() {
        mockPreferences = mock(SharedPreferences.class);
        storage = new SharedPreferencesTokenStorage(mockPreferences);
    }

    @Test
    public void testExtractToken_PullsOutTheCorrectToken() {
        String actualToken = storage.extractToken(LoginFragmentTest.VALID_TOKEN_RESPONSE);
        assertEquals(LoginFragmentTest.TOKEN, actualToken);
    }

    @Test
    public void testStoreTokenFails_IfTokenIsEmpty() {
        assertFalse(storage.storeToken(null));
    }

}
