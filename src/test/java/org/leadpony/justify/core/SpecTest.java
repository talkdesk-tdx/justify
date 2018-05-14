/*
 * Copyright 2018 the Justify authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.leadpony.justify.core;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * @author leadpony
 *
 */
@RunWith(Parameterized.class)
public class SpecTest extends AbstractSpecTest {

    private static final String[] NAMES = {
            "/spec/tests/draft7/allOf.json",
            "/spec/tests/draft7/anyOf.json",
            "/spec/tests/draft7/exclusiveMaximum.json",
            "/spec/tests/draft7/exclusiveMinimum.json",
            "/spec/tests/draft7/maximum.json",
            "/spec/tests/draft7/maxLength.json",
            "/spec/tests/draft7/minimum.json",
            "/spec/tests/draft7/minLength.json",
            "/spec/tests/draft7/oneOf.json",
            "/spec/tests/draft7/required.json",
            "/spec/tests/draft7/type.json"
        };
    
    public SpecTest(String name, int testIndex, String description, Fixture fixture) {
        super(name, testIndex, description, fixture);
    }
    
    @Before
    public void setUp() {
    }

    @Parameters(name = "{0}@{1}: {2}")
    public static Iterable<Object[]> parameters() {
        return parameters(NAMES);
    }
}
