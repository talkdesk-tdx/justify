# Justify
[![Apache 2.0 License](https://img.shields.io/:license-Apache%202.0-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![Maven Central](https://img.shields.io/maven-central/v/org.leadpony.justify/justify.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22org.leadpony.justify%22%20AND%20a:%22justify%22)
[![Javadocs](https://www.javadoc.io/badge/org.leadpony.justify/justify.svg?color=green)](https://www.javadoc.io/doc/org.leadpony.justify/justify)

Justify is a JSON validator based on [JSON Schema Specification] and [Java API for JSON Processing (JSR 374)].

## Main Features

* Compliant with [JSON Schema Specification] Draft 7.
* Reinforces [Java API for JSON Processing (JSR 374)] transparently with the validation functionality.
* Can be used with [Java API for JSON Binding (JSR 367)] via a custom JsonProvider.
* Reports problems with exact locations including line and column numbers.
* Passes 1000+ test cases including official ones provided by [JSON Schema Test Suite].
* Supports Java 8, 9, 10 and 11, can be used as a modular jar in Java 9 and higher.
* Internationalized problem messages, including Japanese language support.

## Getting Started

### Minimum Setup

This software is available in the [Maven Central Repository].
In runtime the library requires one of [Java API for JSON Processing (JSR 374)] implementations.
If your choice is [Reference Implementation] of the API,
the following two dependencies are all you need to add to your pom.xml.

```xml
<dependency>
  <groupId>org.leadpony.justify</groupId>
  <artifactId>justify</artifactId>
  <version>0.9.0</version>
</dependency>

<dependency>
  <groupId>org.glassfish</groupId>
  <artifactId>javax.json</artifactId>
  <version>1.1.3</version>
</dependency>
```

Alternatively, the latter dependency can be replaced with other implementation
such as [Apache Johnzon] as below.

```xml
<dependency>
  <groupId>org.leadpony.justify</groupId>
  <artifactId>justify</artifactId>
  <version>0.9.0</version>
</dependency>

<dependency>
  <groupId>org.apache.johnzon</groupId>
  <artifactId>johnzon-core</artifactId>
  <version>1.1.10</version>
</dependency>
```

### Using with the Streaming API for JSON Processing

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

### Using with the Object Model API for JSON Processing

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

* [API Reference in Javadoc]
* [Justify Examples]

## Current Development Status

### Schema keywords implemented

* type
* enum
* const
* multipleOf
* maximum/exclusiveMaximum
* minimum/exclusiveMinimum
* maxLength
* minLength
* pattern
* items
* additionalItems
* maxItems
* minItems
* uniqueItems
* contains
* maxProperties
* minProperties
* required
* properties
* patternProperties
* additionalProperties
* dependencies
* propertyNames
* if/then/else
* allOf
* anyOf
* oneOf
* not
* definitions
* title
* description
* format
  * date-time/date/time
  * email (compliant with [RFC 5322])
  * idn-email (compliant with [RFC 6531])
  * hostname (compliant with [RFC 1034])
  * idn-hostname (compliant with [RFC 5890])
  * ipv4 (compliant with [RFC 2673])
  * ipv6 (compliant with [RFC 4291])
  * json-pointer (compliant with [RFC 6901])
  * relative-json-pointer
  * uri/uri-reference (compliant with [RFC 3986])
  * iri/iri-reference (compliant with [RFC 3987])
  * uri-template (compliant with [RFC 6570])
  * regex (compliant with [ECMA 262])

### Schema keywords not implemented yet

* default
* contentMediaType
* contentEncoding

## Building from Source

The following tools are required to build this library.
* [JDK 9] or higher
* [Apache Maven] 3.6.0 or higher

The commands below build the library and install it into your local Maven repository.

```bash
$ git clone https://github.com/leadpony/justify.git
$ cd justify
$ mvn clean install
```

## Similar Solutions

There exist several JSON validator implementations conformant to the JSON Schema Specification, including those for other programming languages. [The list of implementations] is available on the JSON Schema web site.

## Copyright Notice
Copyright &copy; 2018 the Justify authors. This software is licensed under [Apache License, Versions 2.0][Apache 2.0 License].

[JSON Schema Specification]: https://json-schema.org/
[Java API for JSON Processing (JSR 374)]: https://javaee.github.io/jsonp/
[Java API for JSON Binding (JSR 367)]: http://json-b.net/
[JDK 9]: https://jdk.java.net/archive/
[Apache Maven]: https://maven.apache.org/
[JSON Schema Test Suite]: https://github.com/json-schema-org/JSON-Schema-Test-Suite
[Apache 2.0 License]: https://www.apache.org/licenses/LICENSE-2.0
[RFC 1034]: https://tools.ietf.org/html/rfc1034.html
[RFC 2673]: https://tools.ietf.org/html/rfc2673.html
[RFC 3986]: https://tools.ietf.org/html/rfc3986.html
[RFC 3987]: https://tools.ietf.org/html/rfc3987.html
[RFC 4291]: https://tools.ietf.org/html/rfc4291.html
[RFC 5322]: https://tools.ietf.org/html/rfc5322.html
[RFC 5890]: https://tools.ietf.org/html/rfc5890.html
[RFC 6531]: https://tools.ietf.org/html/rfc6531.html
[RFC 6570]: https://tools.ietf.org/html/rfc6570.html
[RFC 6901]: https://tools.ietf.org/html/rfc6901.html
[ECMA 262]: https://www.ecma-international.org/publications/standards/Ecma-262.htm
[Justify Examples]: https://github.com/leadpony/justify-examples
[API Reference in Javadoc]: https://www.javadoc.io/doc/org.leadpony.justify/justify
[Maven Central Repository]: https://mvnrepository.com/repos/central
[Reference Implementation]: https://github.com/eclipse-ee4j/jsonp
[Apache Johnzon]: https://johnzon.apache.org/
[The list of implementations]: https://json-schema.org/implementations.html  
