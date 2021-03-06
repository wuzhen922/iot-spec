/*
 * Licensed to the Rhiot under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.rhiot.spec.transport;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class LatchListener implements Listener  {

    Integer expected;
    Long timeout;
    CountDownLatch latch;

    public LatchListener(int expected, long timeout) {
        this.expected = expected;
        this.timeout = timeout;
        if (expected != -1) {
            latch = new CountDownLatch(expected);
        } else {
            latch = new CountDownLatch(1);
        }
    }

    @Override
    public void onMessage(String destination, Object data) {
        if (expected != -1) {
            latch.countDown();
        }
    }

    public void await() throws InterruptedException {
        if (timeout == -1) {
            latch.await();
        } else {
            latch.await(timeout, TimeUnit.MILLISECONDS);
        }
    }
}
