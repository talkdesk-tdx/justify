/*
 * Copyright 2018-2019 the Justify authors.
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

package org.leadpony.justify.internal.keyword.assertion;

import org.leadpony.justify.internal.base.Message;

/**
 * Assertion specified with "minLength" validation keyword.
 *
 * @author leadpony
 */
public class MinLength extends AbstractStringLengthAssertion {

    public MinLength(int limit) {
        super(limit, "minLength", Message.INSTANCE_PROBLEM_MINLENGTH, Message.INSTANCE_PROBLEM_NOT_MINLENGTH);
    }

    @Override
    protected boolean testLength(int actualLength, int limit) {
        return actualLength >= limit;
    }
}
