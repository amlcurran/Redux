package com.espiandev.redux.auth;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertFalse;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = "AndroidManifest.xml")
public class ValidatorTest {

    private Validator validator;

    @Before
    public void setUp() {
        validator = new Validator();
    }


    @Test
    public void testWhenPasswordIsEmpty_ItIsNotValid() {
        assertFalse("Empty password should not be valid", validator.isPasswordValid(""));
    }

    @Test
    public void testWhenUsernameIsEmpty_ItIsNotValid() {
        assertFalse("Empty password should not be valid", validator.isUsernameValid(""));
    }

}
