# spdx-license-expression-tools

The [SPDX specification](https://spdx.org/spdx-specification-21-web-version) was created to exchange licensing information along a software supply chain in a standard way. Part of this specification are license expressions which can be used to describe the licensing of a software in a formal way e.g. `(LGPL-3.0 OR MIT)`.

Within the [SPDX tools](https://github.com/spdx/tools), the official specification implementation of the SPDX working group, license expressions are represented as `AnyLicenseInfo` composite. Interacting with this composite can be quite complicated, therefore the spdx-license-expression-tools library was created to provide various operations that can performed on SPDX license expressions:
- simplification
- filtering
- merging
- splitting

## Operations

### Simplification

Remove redundant terms transform license expressions to the distributed normal form (DNF):

```java
AnyLicenseInfo expression = LicenseInfoFactory.parseSPDXLicenseString("(((LGPL-3.0+ OR MIT) AND GPL-2.0) OR BSD-3-Clause)");
ExpressionSimplification.simplify(expression); // -> (BSD-3-Clause OR (LGPL-3.0+ AND GPL-2.0) OR (MIT AND GPL-2.0))
```

### Filtering

Remove operators:

```java
AnyLicenseInfo expression = LicenseInfoFactory.parseSPDXLicenseString("LGPL-3.0+ WITH DigiRule-FOSS-exception AND MIT");
ExpressionFiltering.filterOperators(expression, OperatorFilter.FILTER_ALL); // -> (LGPL-3.0 AND MIT)
```

Remove licenses:

```java
AnyLicenseInfo expression = LicenseInfoFactory.parseSPDXLicenseString("((LGPL-3.0+ AND MIT) AND BSD-3-Clause)");
LicenseMatcher matcher = ExpressionFiltering.createLicenseMatcher();
matcher.setLicense(LicenseInfoFactory.parseSPDXLicenseString("LGPL-3.0+"));
ExpressionFiltering.filterLicenses(expression, matcher); // -> (MIT AND BSD-3-Clause)
```

### Merging

Join with `AND`:

```java
AnyLicenseInfo expression1 = LicenseInfoFactory.parseSPDXLicenseString("LGPL-3.0+ AND MIT");
AnyLicenseInfo expression2 = LicenseInfoFactory.parseSPDXLicenseString("BSD-3-Clause");
ExpressionMerging.andJoin(expression1, expression2); // -> ((LGPL-3.0+ AND MIT) AND BSD-3-Clause)
```

Join with `OR`:

```java
AnyLicenseInfo expression1 = LicenseInfoFactory.parseSPDXLicenseString("LGPL-3.0+ AND MIT");
AnyLicenseInfo expression2 = LicenseInfoFactory.parseSPDXLicenseString("BSD-3-Clause");
ExpressionMerging.orJoin(expression1, expression2); // -> ((LGPL-3.0+ AND MIT) OR BSD-3-Clause)
```

### Splitting

Split to conjunctive sets (after simplification):

```java
AnyLicenseInfo expression = LicenseInfoFactory.parseSPDXLicenseString("(((LGPL-3.0+ OR MIT) AND GPL-2.0) OR BSD-3-Clause)");
ExpressionSplitting.splitToConjunctiveSets(expression); // -> BSD-3-Clause, (LGPL-3.0+ AND GPL-2.0), (MIT AND GPL-2.0)
```

Split to licenses or list contained licenses:

```java
AnyLicenseInfo expression = LicenseInfoFactory.parseSPDXLicenseString("(((LGPL-3.0+ OR MIT) AND GPL-2.0) OR BSD-3-Clause)");
ExpressionSplitting.splitToLicenses(expression); // -> BSD-3-Clause, LGPL-3.0+, GPL-2.0, MIT
```
## Compiling and Integration

Maven is used as build system. To build from source use:

```
mvn package
```

spdx-license-expression-tools is available via the Maven Central Repository:

```
<dependency>
  <groupId>com.github.aschet</groupId>
  <artifactId>spdx-license-expression-tools</artifactId>
  <version>1.0.0</version>
</dependency>
```
