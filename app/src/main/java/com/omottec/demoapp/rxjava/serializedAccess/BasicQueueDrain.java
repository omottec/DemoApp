package com.omottec.demoapp.rxjava.serializedAccess;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by qinbingbing on 27/04/2018.
 */

public class BasicQueueDrain {
    // work-in-progress
    final AtomicInteger wip = new AtomicInteger(); // (1)

    public void drain() {
        // work preparation
        if (wip.getAndIncrement() == 0) { // (2)
            do {
                // work draining
            } while (wip.decrementAndGet() != 0); // (3)
        }
    }
}
