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

package org.leadpony.justify.internal.assertion;

import java.math.BigDecimal;

import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

import org.leadpony.justify.core.InstanceType;
import org.leadpony.justify.core.Problem;
import org.leadpony.justify.internal.base.ProblemBuilder;

/**
 * @author leadpony
 */
abstract class AbstractNumericBoundAssertion extends ShallowAssertion {

    protected final BigDecimal bound;
    private final String name;
    private final String message;
    
    protected AbstractNumericBoundAssertion(BigDecimal bound, String name, String message) {
        this.bound = bound;
        this.name = name;
        this.message = message;
    }
    
    @Override
    public boolean canApplyTo(InstanceType type) {
        return type.isNumeric();
    }
   
    @Override
    protected Result evaluateShallow(Event event, JsonParser parser, int depth, ProblemReporter reporter) {
        BigDecimal actual = parser.getBigDecimal();
        if (test(actual, this.bound)) {
            return Result.TRUE;
        } else {
            Problem p = ProblemBuilder.newBuilder(parser)
                    .withMessage(this.message)
                    .withParameter("actual", actual)
                    .withParameter("bound", this.bound)
                    .build();
            reporter.reportProblem(p, parser);
            return Result.FALSE;
        }
    }

    @Override
    public void toJson(JsonGenerator generator) {
        generator.write(this.name, this.bound);
    }
    
    protected abstract boolean test(BigDecimal actual, BigDecimal bound);
}
