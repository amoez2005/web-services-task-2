# Verification record

Verified on November 14, 2026 (Europe/Kyiv), Windows, Eclipse Adoptium JDK 25.0.3,
Maven 3.9.9, compiling handwritten sources with Java 17 release compatibility.

## Clean build

`mvnw.cmd -B -ntp clean verify` completed with BUILD SUCCESS.

| Module | Tests | Failures | Errors | Skipped |
| --- | ---: | ---: | ---: | ---: |
| transformations | 5 | 0 | 0 | 0 |
| soap-service | 6 | 0 | 0 | 0 |
| soap-client | 1 | 0 | 0 | 0 |

JavaDoc was generated successfully in each module's `target/reports/apidocs/`.
The build invokes real Metro wsgen and wsimport tools. Tests include actual HTTP
SOAP exchanges using an ephemeral listener, nested field deserialization, and a
typed missing-student fault.

## Published endpoint and generated client

The service was started at `http://localhost:8080/students`.
`mvnw.cmd -pl soap-client -Plive-wsdl compile exec:exec` fetched the live WSDL,
generated and compiled the client, and successfully printed both student records
and `Lookup 1: Alice Morgan`. See `generated-client-run.log`.

## Postman collection

Newman 6.2.1 ran the provided Postman collection against the published service:
4 requests, 14 assertions, zero failures. See `newman-results.json` and
`postman-run.log`. Actual WSDL and response XML files are saved alongside them.
The missing-student operation returned HTTP 500 with a SOAP fault containing
`NOT_FOUND` and studentId 999. Successful requests returned HTTP 200.

The Postman-compatible CLI runner was used; a desktop SoapUI/Postman GUI session
and GUI screenshots were not performed. The README includes instructions for
that manual demonstration.

## Reports

`mvnw.cmd -pl transformations exec:exec` generated the HTML and PDF examples.
PDFBox tests checked the PDF's page count and extracted student/subject content.
The sample PDF was additionally rasterized and visually inspected; its single
A4 page has readable tables, student metadata, and page numbering without clipping.
See `students-preview.png` and `../reports/` for the rendered outputs.
