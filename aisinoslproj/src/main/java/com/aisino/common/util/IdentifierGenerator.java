package com.aisino.common.util;

import org.apache.commons.lang.RandomStringUtils;

import java.util.Random;

/**
 * Created by Martin.Ou on 2014/11/14.
 */
public final class IdentifierGenerator {
    /**
     * We want to have a random string with a length of 6 characters. 41 Since
     * we encode it base-36, we modulo the random number with 42 this value.
     */
    private static final long MAX_RANDOM_LEN = 2176782336L; // 36 ** 6

    private static final int MAX_VERIFYCODE_LEN = 6;

    /**
     * <p>
     * The identifier must be unique within the typical lifespan of a session;
     * the value can roll over after that.
     * </p>
     * characters: (this means a roll over after over a day, which is much
     * larger than a typical lifespan).
     */
    private static final long MAX_TIME_SECTION_LEN = 46656L; // 36 ** 3

    /**
     * Milliseconds between different tics. The 3-character time string has a
     * new value every 2 seconds.
     */
    private static final long TIC_DIFFERENCE = 2000;

    /**
     * Length of random segment
     */
    private static final int RANDOM_LENGTH = 6;

    /**
     * Length of time segment
     */
    private static final int TIME_LENGTH = 3;

    /**
     * Number of alphanumeric characters
     */

    private static final int ALPHA_NUMERIC_CHARSET_SIZE = 36;

    /**
     * Maximum length for an alphanumeric string representing an integer value
     */
    private static final int MAX_INT_ALPHANUMERIC_VALUE_LENGTH = Integer.toString(Integer.MAX_VALUE,
            ALPHA_NUMERIC_CHARSET_SIZE).length();

    /**
     * Default size of an alphanumeric identifier
     */
    private static final int DEFAULT_ALPHANUMERIC_IDENTIFIER_SIZE = 15;

    /**
     * The incrementing counter.
     */
    private int counter = 0;

    /**
     * The last time.
     */
    private long lastTimeValue = 0;

    /**
     * The randmonizer.
     */
    private static Random randomizer = new Random();

    /**
     * <p>
     * Constructor.
     * </p>
     */
    public IdentifierGenerator() {
        super();
    }

    /**
     * <p>
     * Returns the maximum length (number or characters) for an identifier from
     * this generator.
     * </p>
     *
     * @return the maximum identifier length
     */
    public long maxLength() {
        return RANDOM_LENGTH + TIME_LENGTH + MAX_INT_ALPHANUMERIC_VALUE_LENGTH;
    }

    /**
     * <p>
     * Returns the minimum length (number of characters) for an identifier 105
     * from this generator.
     * </p>
     *
     * @return the minimum identifier length 108
     */
    public long minLength() {
        return RANDOM_LENGTH + TIME_LENGTH + 1;
    }

    /**
     * <p>
     * Gets the next new identifier.
     * </p>
     * <p>
     * Only guaranteed unique within this JVM, but fairly safe 117 for cross JVM
     * usage as well.
     * </p>
     * <p/>
     * <p/>
     * <p/>
     * <p>
     * Format of identifier is
     * <code>[6 chars random][3 chars time][1+ chars count]</code>
     * </p>
     *
     * @return the next 10 char String identifier
     */
    public String nextStringIdentifier() {
        // Random value
        // --------------
        long currentRandom = randomizer.nextLong();
        if (currentRandom < 0) {
            currentRandom = -currentRandom;
        }

        currentRandom %= MAX_RANDOM_LEN;
        currentRandom += MAX_RANDOM_LEN;
        long currentTimeValue = 0;
        int currentCount = 0;

        synchronized (this) {
            // Time
            // --------------
            currentTimeValue = (System.currentTimeMillis() / TIC_DIFFERENCE);
            currentTimeValue %= MAX_TIME_SECTION_LEN;
            currentTimeValue += MAX_TIME_SECTION_LEN;
            if (lastTimeValue != currentTimeValue) {
                lastTimeValue = currentTimeValue;
                counter = 0;
            }
            currentCount = counter++;
        }
        // build string
        // --------------
        StringBuffer id = new StringBuffer(DEFAULT_ALPHANUMERIC_IDENTIFIER_SIZE);
        id.append(Long.toString(currentRandom, ALPHA_NUMERIC_CHARSET_SIZE).substring(1)); // 6
        // chars
        id.append(Long.toString(currentTimeValue, ALPHA_NUMERIC_CHARSET_SIZE).substring(1)); // 3
        // chars
        id.append(Long.toString(currentCount, ALPHA_NUMERIC_CHARSET_SIZE)); // 1+
        // chars
        return id.toString();
    }

    public String obtainMobileVerifyCode() {
        return RandomStringUtils.randomNumeric(MAX_VERIFYCODE_LEN);
    }

    public String obtainRandomAlphanumeric(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }

}
