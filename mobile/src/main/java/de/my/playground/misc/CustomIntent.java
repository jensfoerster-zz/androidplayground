package de.my.playground.misc;

/**
 * Created by jensfoerster on 10.10.2016.
 */

public final class CustomIntent {

    private static final String base = "de.playground.my";
    private static final String action = ".action";
    private static final String extra = ".extra";

    public static final String NOTIFICATION_POSTED_ACTION = base + action + ".NOTIFICATION_POSTED";
    public static final String NOTIFICATION_POSTED_EXTRA = base + extra + ".NOTIFICATION_POSTED_ID";

    public static final String NOTIFICATION_REMOVED_ACTION = base + action + ".NOTIFICATION_REMOVED";
    public static final String NOTIFICATION_REMOVED_EXTRA = base + extra + ".NOTIFICATION_REMOVED_ID";
}
