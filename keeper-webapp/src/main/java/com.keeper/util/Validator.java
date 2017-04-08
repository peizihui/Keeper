package com.keeper.util;

/*
 * Created by @GoodforGod on 7.04.2017.
 */

import org.apache.taglibs.standard.tag.common.core.NullAttributeException;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Used to Validate and Generate Hashes (for password as example)
 */
public class Validator {

    public enum HashType {
        // Values are SALT used for hash generation
        COMMON(11),
        PASS(17),
        EMAIL(13);

        private final int value;

        HashType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public static final int EMAIL_INDEX = -1;

    private static final String EMPTY_HASH = "";

    public static boolean isEmailValid(String email) {
        return email != null && email.length() >= 3 && email.indexOf('@') != 0;
    }

    public static int isEmailValidWithIndex(String email) {
        return (email != null && email.length() > 3) ? email.indexOf('@') : EMAIL_INDEX;
    }

    public static boolean isPhoneValid(String phone) {
        return false;
    }

    public static String generateHash(String value, HashType type) {
        return (value == null || value.isEmpty())
                ? EMPTY_HASH
                : BCrypt.hashpw(value, BCrypt.gensalt(type.getValue()));
    }

    public static boolean validateHash(String candidate, String hash) throws NullAttributeException {
        if(candidate == null || candidate.isEmpty())
            throw new NullAttributeException("Nullable", "CANDIDATE");

        if(hash == null || hash.isEmpty())
            throw new NullAttributeException("Nullable", "HASH");

        return BCrypt.checkpw(candidate, hash);
    }
}
