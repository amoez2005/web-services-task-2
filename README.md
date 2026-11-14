# Web services task 2

A Java 17+ Maven project demonstrating document/literal JAX-WS SOAP, a generated
`wsimport` client, XSLT-to-HTML, and XSLT-to-XSL-FO-to-PDF using Apache FOP.
JAX-WS is now named Jakarta XML Web Services; this project uses Metro 4 and
`jakarta.*` annotations. JAXP/XSLT APIs remain under `javax.xml.*`.

## Build and test on Windows

Install a **JDK 17 or newer**, with `javac` on PATH or a valid `JAVA_HOME`.
The launcher downloads Maven 3.9.9 to `.tools/` and verifies its SHA-512 checksum.
No global Maven installation is required. Run commands from this directory:

```powershell
.\mvnw.cmd clean verify
```

This compiles all modules, generates the service WSDL using `wsgen`, generates
the client using `wsimport`, runs JUnit tests including actual HTTP SOAP calls,
and generates JavaDoc. The first build needs internet access for dependencies.
If Maven is already installed, `mvn clean verify` also works on other platforms.

Generated files:

- `soap-service/target/wsdl/StudentService.wsdl` and its XSD schema.
- `soap-client/target/generated-sources/wsimport/`: actual generated client code.
- Each module's `target/reports/apidocs/index.html`: JavaDoc.
- Each module's `target/surefire-reports/`: test results.

Use the root reactor build; the client generation uses the service's generated
WSDL, so building the client alone before building the service is insufficient.
To run all commands for individual modules independently, first run
`.\mvnw.cmd install` once to install their dependencies locally.

## Part 1.1: XML reports

```powershell
.\mvnw.cmd -pl transformations exec:exec
```

Open `output/reports/students.html` in a browser and
`output/reports/students.pdf` in a PDF viewer. The Maven configuration passes
paths as separate process arguments and supports spaces in the project path.

- XML input: `transformations/src/main/resources/data/students.xml`.
- HTML stylesheet: `transformations/src/main/resources/xsl/students-html.xsl`.
- FO stylesheet: `transformations/src/main/resources/xsl/students-fo.xsl`.
- Java transformation code: `transformations/src/main/java/edu/example/transform/`.

The FO stylesheet emits formatting objects; Apache FOP converts their SAX events
to a binary PDF. XSLT alone does not serialize a PDF. The stylesheets use XSLT
1.0 instructions and XSL-FO constructs supported by Apache FOP. Reports include
all student fields, nested subjects, enrollment states, and PDF page numbering.
The example report XML is a standalone presentation model, not a raw SOAP
envelope. Its group codes are readable letters.

## Part 1.2: Publish the SOAP service

In terminal 1:

```powershell
.\mvnw.cmd -pl soap-service exec:exec
```

- SOAP endpoint: `http://localhost:8080/students`
- Live WSDL: `http://localhost:8080/students?wsdl`
- Namespace: `https://example.edu/students`
- SOAP 1.1, document/literal wrapped binding.
- Stop the service with Ctrl+C. Data is read-only and recreated on each read.

Optional address override:

```powershell
.\mvnw.cmd -pl soap-service exec:exec "-Dstudent.endpoint=http://localhost:8090/students"
```

`Student` has String, int, float, boolean, char, and `List<Subject>` fields.
JAXB maps Java `char` to XML `xs:unsignedShort`, so group A is transported as
`65` and B as `66`. The generated client consequently exposes an integer code.

| Operation | Input | Expected result |
| --- | --- | --- |
| `getStudents` | Empty operation wrapper | Alice and Daniel, each with two subjects |
| `getStudent` | `id=1` | Alice, age 20, GPA 3.8, active, group code 65 |
| `getStudent` | `id=999` | HTTP 500 SOAP fault, `NOT_FOUND`, studentId 999 |

## Test in SoapUI

1. Start the service as above.
2. In SoapUI, choose **File > New SOAP Project**.
3. Enter `StudentService` and Initial WSDL
   `http://localhost:8080/students?wsdl`; enable **Create Requests**.
4. Open the generated request for `getStudents` and submit it.
5. Open `getStudent`, set the unqualified `<id>` element to `1`, and submit.
6. Change the value to `999` and inspect the SOAP fault detail and HTTP status.

Complete request bodies are in `testing/*.xml`. Each operation wrapper uses the
service namespace; its `id` child has no namespace. SOAPAction is the empty
quoted string `""`, and Content-Type is `text/xml; charset=utf-8`.
Record screenshots of your SoapUI session if required for submission.

## Test in Postman or its Newman runner

Import `testing/student-service.postman_collection.json` in Postman. Its
`endpoint` collection variable defaults to the published address. Run the
collection to fetch the WSDL and exercise successful and fault requests with
response assertions. The same collection can run without the desktop UI:

```powershell
npx.cmd --yes newman@6.2.1 run testing/student-service.postman_collection.json --reporters cli,json --reporter-json-export output/evidence/newman-results.json
```

## Run the wsimport-generated client

After the root build, while the service is running, in terminal 2:

```powershell
.\mvnw.cmd -pl soap-client exec:exec
```

Expected output includes both students, their GPAs and two subjects, followed
by `Lookup 1: Alice Morgan`. To use another address, add
`"-Dstudent.endpoint=http://localhost:8090/students"`.
The generated classes are built from the locally generated WSDL for a repeatable
build. At runtime the client fetches the actual published WSDL and calls the
published endpoint. Its integration test also verifies the typed SOAP fault.

To demonstrate generation directly from the **running** endpoint:

```powershell
.\mvnw.cmd -pl soap-client -Plive-wsdl generate-sources
```

Override `"-Dstudent.wsdl=http://localhost:8090/students?wsdl"` for another
published endpoint. The default build uses the generated local contract.

## Design and tests

`ReportTransformer` separates report orchestration from HTML/PDF rendering.
`XsltSupport` centralizes secure XML parsing and stylesheet loading, rejecting
DOCTYPE and external resources. `StudentServiceImpl` depends on
`StudentRepository`; alternative storage implementations can be injected without
changing the SOAP contract. DTOs, transport, storage, application entry points,
and rendering each have separate responsibilities. Interfaces stay small and
implementations honor their contracts, following SOLID principles.

JUnit tests cover every handwritten concrete production class and exercise the
interfaces through implementations. Generated transport code is verified by a
client/server integration test rather than hand-edited or individually tested.
Tests check nested object graphs, defensive repository reads, field types,
repository injection, typed faults, endpoint lifecycle, HTML escaping, blocked
external entities, readable PDF contents, and application entry points.

## References

- [Metro wsgen Maven goal](https://eclipse-ee4j.github.io/metro-jax-ws/jaxws-maven-plugin/wsgen-mojo.html)
- [Metro wsimport Maven goal](https://eclipse-ee4j.github.io/metro-jax-ws/jaxws-maven-plugin/wsimport-mojo.html)
- [SoapUI: working with WSDLs](https://www.soapui.org/docs/soap-and-wsdl/working-with-wsdls/)
- [Apache FOP embedding](https://xmlgraphics.apache.org/fop/trunk/embedding.html)
- [XSL 1.1 specification](https://www.w3.org/TR/xsl11/)

The supplied brief states April 15 as the deadline without specifying a year.
