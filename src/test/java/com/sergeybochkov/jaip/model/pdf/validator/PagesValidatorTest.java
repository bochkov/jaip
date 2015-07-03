package com.sergeybochkov.jaip.model.pdf.validator;

import junit.framework.TestCase;

public class PagesValidatorTest extends TestCase {

    PagesValidator validator = new PagesValidator();

    public void testIsValid() throws Exception {
        assertTrue(validator.isValid("-", null));
        assertFalse(validator.isValid("--", null));
        assertTrue(validator.isValid("-2", null));
        assertTrue(validator.isValid("1-2", null));
        assertTrue(validator.isValid("1-2,5", null));
        assertTrue(validator.isValid("4,7", null));
        assertFalse(validator.isValid("a-b", null));
        assertFalse(validator.isValid("a-", null));
        assertFalse(validator.isValid("-b", null));
        assertFalse(validator.isValid("1,4-b", null));
        assertFalse(validator.isValid(",,", null));
        assertFalse(validator.isValid(",", null));
    }
}