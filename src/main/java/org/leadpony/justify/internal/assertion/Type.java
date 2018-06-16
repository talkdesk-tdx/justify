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

import java.util.HashSet;
import java.util.Set;

import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

import org.leadpony.justify.core.InstanceType;
import org.leadpony.justify.core.Problem;
import org.leadpony.justify.internal.base.InstanceTypes;
import org.leadpony.justify.internal.base.ProblemBuilder;

/**
 * Assertion specified with "type" keyword.
 * 
 * @author leadpony
 */
public class Type extends ShallowAssertion {
    
    protected final Set<InstanceType> typeSet;
    
    public Type(Set<InstanceType> types) {
        this.typeSet = new HashSet<>(types);
    }

    @Override
    protected Result evaluateShallow(Event event, JsonParser parser, int depth, ProblemReporter reporter) {
        InstanceType type = InstanceTypes.fromEvent(event, parser);
        if (type != null) {
            return testType(type, parser, reporter);
        } else {
            return Result.TRUE;
        }
    }
  
    @Override
    public void toJson(JsonGenerator generator) {
        if (typeSet.size() <= 1) {
            InstanceType type = typeSet.iterator().next();
            generator.write("type", type.name().toLowerCase());
        } else {
            generator.writeStartArray("type");
            typeSet.stream()
                .map(InstanceType::name)
                .map(String::toLowerCase)
                .forEach(generator::write);
            generator.writeEnd();
        }
    }
    
    protected boolean contains(InstanceType type) {
        return typeSet.contains(type) ||
               (type == InstanceType.INTEGER && typeSet.contains(InstanceType.NUMBER));
    }
    
    protected Result testType(InstanceType type, JsonParser parser, ProblemReporter reporter) {
        if (contains(type)) {
            return Result.TRUE;
        } else {
            Problem p = ProblemBuilder.newBuilder(parser)
                    .withMessage("instance.problem.type")
                    .withParameter("actual", type)
                    .withParameter("expected", typeSet)
                    .build();
            reporter.reportProblem(p, parser);
            return Result.FALSE;
        }
    }

    @Override
    protected AbstractAssertion createNegatedAssertion() {
        return new NotType(this.typeSet);
    }
}
