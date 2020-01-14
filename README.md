# Justify
[![Apache 2.0 License](https://img.shields.io/:license-Apache%202.0-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)

Justify is a JSON validator based on [JSON Schema Specification] and [Java API for JSON Processing (JSR 374)].

## Key Features

* Compliant with [JSON Schema Specification] Draft-07, -06, and -04.
* Reinforces [Java API for JSON Processing (JSR 374)] transparently with the validation functionality.
* Can be used with [Java API for JSON Binding (JSR 367)] via a custom JsonProvider.
* Reports problems with the source locations including line and column numbers.
* Passes all test cases provided by [JSON Schema Test Suite] including both mandatory and optional tests.
* Validates the input in streaming way, which claims small memory footprint even when the input is a large JSON.
* Accepts custom formats for string and other simple types.
* Supports Java 8, 9, 10, 11, and 12.
* Can be used as a modular jar in Java 9 and higher.
* Internationalized problem messages, including Japanese language support.

## Additions to the original project

### Partial implementation of the AJV Extension proposal as defined here [AJV](https://ajv.js.org/keywords.html#keywords-for-strings) with slight modifications. Currently from the AJV extensions only the string section is implemented. It allows comparison of dates, times and datetimes as string objects. 

Date should be a complete date ISO 8601 (e.g. YYYY-MM-DD).
Time should be in ISO 8601 (e.g. 11:13:06+00:00 or UTC 11:13:06Z).
Datetime should be ISO 8601 in the form \<date>T\<time>.

* formatMaximum: No change. It states that formatMaximum should be the latest date accepted, excluding the same temporal unit.
* formatMinimum: No change: It states that formatMinimum should be the earliest date accepted, excluding the same temporal unit.
* exclusiveFormatMaximum: *Modified*. This field should also be a string with a temporal unit of the above format.
* exclusiveFormatMinimum: *Modified*. This field should also be a string with a temporal unit of the above format.
    
You may use a combination of any of the constrainsts above as they are evaluated indenpendent of each other, for instance to define a range of accepted values.

### AJV Extension proposal changed to be the default option.

## Getting Started

### Minimum Setup

This software is available in the private TDX nexus repository and the following dependency should be added to your build. 
The x represents the leadpony version and y is TDX internal versioning.

*Maven*
```xml
<dependency>
    <groupId>org.leadpony.justify</groupId>
    <artifactId>justify</artifactId>
    <version>2.x.x.TDX.y.y</version>
</dependency>
```

Note that the addition of this dependency brings the following artifacts as transitive dependencies.

* `jakarta.json:jakarta.json-api`
* `com.ibm.icu:icu4j`

Besides the library itself, one of [Java API for JSON Processing (JSR 374)] implementations is needed during runtime.
This library supports the following implementations and you should select [Jakarta JSON Processing]

Please add this dependency to your build as shown below.

#### Jakarta JSON Processing
*Maven*
```xml
<dependency>
    <groupId>org.glassfish</groupId>
    <artifactId>jakarta.json</artifactId>
    <classifier>module</classifier>
    <version>1.1.6</version>
    <scope>runtime</scope>
</dependency>
```

### Using with the Streaming API of JSON Processing

```java
JsonValidationService service = JsonValidationService.newInstance();

// Reads the JSON schema
JsonSchema schema = service.readSchema(Paths.get("news.schema.json"));

// Problem handler which will print problems found.
ProblemHandler handler = service.createProblemPrinter(System.out::println);

Path path = Paths.get("fake-news.json");
// Parses the JSON instance by javax.json.stream.JsonParser
try (JsonParser parser = service.createParser(path, schema, handler)) {
    while (parser.hasNext()) {
        JsonParser.Event event = parser.next();
        // Do something useful here
    }
}
```

### Using with the Object Model API of JSON Processing

```java
JsonValidationService service = JsonValidationService.newInstance();

// Reads the JSON schema
JsonSchema schema = service.readSchema(Paths.get("news.schema.json"));

// Problem handler which will print problems found.
ProblemHandler handler = service.createProblemPrinter(System.out::println);

Path path = Paths.get("fake-news.json");
// Reads the JSON instance by javax.json.JsonReader
try (JsonReader reader = service.createReader(path, schema, handler)) {
    JsonValue value = reader.readValue();
    // Do something useful here
}
```


## Additional Resources

* [Justify Examples] which show how to use this library.
* [API Reference in Javadoc]
* [Changelog]

## Conformance to Specification

This software is one of the most correct implementation of the JSON Schema Specification. Please refer to the result of [JSON Schema Conformance Test].


## Building from Source

The following tools are required to build this software.
* [JDK] 11
* [Apache Maven] 3.6.2 or higher

The commands below build this software and install it into your local Maven repository.

```bash
$ git clone --recursive https://github.com/talkdesk-tdx/justify.git
$ cd justify
$ mvn clean install -P release
```
## Copyright Notice
Copyright &copy; 2018-2019 the Justify authors. This software is licensed under [Apache License, Versions 2.0][Apache 2.0 License].

[Apache 2.0 License]: https://www.apache.org/licenses/LICENSE-2.0
[Apache Maven]: https://maven.apache.org/
[API Reference in Javadoc]: https://www.javadoc.io/doc/org.leadpony.justify/justify
[Changelog]: CHANGELOG.md
[everit-org/json-schema]: https://github.com/everit-org/json-schema
[Jakarta JSON Processing]: https://github.com/eclipse-ee4j/jsonp
[Java API for JSON Processing (JSR 374)]: https://eclipse-ee4j.github.io/jsonp/
[Java API for JSON Binding (JSR 367)]: http://json-b.net/
[java-json-tools/json-schema-validator]: https://github.com/java-json-tools/json-schema-validator
[JDK]: https://jdk.java.net/
[JSON Schema Conformance Test]: https://github.com/leadpony/json-schema-conformance-test
[JSON Schema Specification]: https://json-schema.org/
[JSON Schema Test Suite]: https://github.com/json-schema-org/JSON-Schema-Test-Suite
[Justify CLI]: https://github.com/leadpony/justify-cli
[Justify Examples]: https://github.com/leadpony/justify-examples
[Maven Central Repository]: https://mvnrepository.com/repos/central
[networknt/json-schema-validator]: https://github.com/networknt/json-schema-validator
[Releases]: https://github.com/talkdesk-tdx/justify/releases/latest
[The list of implementations]: https://json-schema.org/implementations.html  
