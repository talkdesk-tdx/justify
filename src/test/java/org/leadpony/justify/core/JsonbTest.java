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

import static org.leadpony.justify.core.JsonSchemas.*;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.spi.JsonProvider;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests JSON validator using with {@link Jsonb}.
 * 
 * @author leadpony
 */
public class JsonbTest {
    
    private static JsonValidatorFactory validatorFactory;
    
    @BeforeClass
    public static void setUpOnce() {
        validatorFactory = JsonValidatorFactory.newFactory();
    }
    
    @AfterClass
    public static void tearDownOnce() {
        validatorFactory = null;
    }

    @Test
    public void fromJson_deserializes() {
        String schema = PERSON_SCHEMA;
        String instance = "{\"name\":\"John Smith\", \"age\": 46}";

        JsonSchema s = JsonSchemaReader.readFrom(new StringReader(schema));
        List<Problem> problems = new ArrayList<>();
        JsonProvider provider = validatorFactory.createJsonProvider(s, parser->problems::addAll); 
        Jsonb jsonb = JsonbBuilder.newBuilder().withProvider(provider).build();
        Person person = jsonb.fromJson(instance, Person.class);
        
        assertThat(person.name).isEqualTo("John Smith");
        assertThat(person.age).isEqualTo(46);
        assertThat(problems).isEmpty();
    }

    @Test
    public void fromJson_throwsExceptionIfInvalid() {
        String schema = PERSON_SCHEMA;
        String instance = "{\"name\":\"John Smith\", \"age\": \"46\"}";

        JsonSchema s = JsonSchemaReader.readFrom(new StringReader(schema));
        List<Problem> problems = new ArrayList<>();
        JsonProvider provider = validatorFactory.createJsonProvider(s, parser->problems::addAll); 
        Jsonb jsonb = JsonbBuilder.newBuilder().withProvider(provider).build();
        Person person = jsonb.fromJson(instance, Person.class);

        assertThat(person.name).isEqualTo("John Smith");
        assertThat(person.age).isEqualTo(46);
        assertThat(problems).isNotEmpty();
        problems.forEach(System.out::println);
    }

    public static class Person {
        public String name;
        public int age;
    }
}