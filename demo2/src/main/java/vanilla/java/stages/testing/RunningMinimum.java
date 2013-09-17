/*
 * Copyright 2013 Peter Lawrey
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package vanilla.java.stages.testing;

/**
 * User: peter
 * Date: 05/08/13
 * Time: 19:06
 */
public class RunningMinimum implements Differencer {
    private final long actualMinimum;
    private final int drift;

    private long lastStartTime = Long.MIN_VALUE;
    private long minimum = Long.MAX_VALUE;

    public RunningMinimum(long actualMinimum) {
        this(actualMinimum, 10 * 1000);
    }

    public RunningMinimum(long actualMinimum, int drift) {
        this.actualMinimum = actualMinimum;
        this.drift = drift;
    }

    @Override
    public long sample(long startTime, long endTime) {
        if (lastStartTime + drift <= startTime) {
            if (lastStartTime != Long.MIN_VALUE)
                minimum += (startTime - lastStartTime) / drift;
            lastStartTime = startTime;
        }
        long delta = endTime - startTime;
        if (minimum > delta)
            minimum = delta;
        return delta - minimum + actualMinimum;
    }

    public long minimum() {
        return minimum;
    }
}