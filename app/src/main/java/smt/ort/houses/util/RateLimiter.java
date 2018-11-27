package smt.ort.houses.util;

import android.os.SystemClock;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class RateLimiter {

    private final long timeout;
    private HashMap<String, Long> timestamps = new HashMap<>();

    public RateLimiter(int timeout, TimeUnit timeUnit) {
        this.timeout = timeUnit.toMillis(timeout);
    }

    public synchronized boolean shouldFetch(String key) {
        Long lastFetched = timestamps.get(key);
        long now = SystemClock.uptimeMillis();
        if (lastFetched == null) {
            timestamps.put(key, now);
            return true;
        }
        if (now - lastFetched > timeout) {
            timestamps.put(key, now);
            return true;
        }
        return false;
    }

}
