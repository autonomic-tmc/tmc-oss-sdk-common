# Common Code Used in TMC's Java SDKs

## Consistency is key
Throughout Autonomic's Java SDKs, where we can be, we are consistent. Common code helps with consistency. This repository contains common code that is used across multiple TMC SDKs.

In this repository, you will find common code for:

### [Exception Handling](src/main/java/com/autonomic/tmc/exception)
When you receive an exception from an SDK, you will immediately know:
  * The SDK's version
  * The source of the exception (service or the SDK client)
  * Explanation for the exception (if applicable)
  * The original exception
    
An example is provided below:

```shell
com.autonomic.tmc.auth.exception.SdkServiceException: tmc-auth-3.0.5-beta-SERVICE:
   Authorization failed for user [placeholder] at tokenUrl [https://accounts.autonomic.ai/v1/auth/oidc/token].
   Error: code=unauthorized and httpStatusCode=401
```

## Adding this artifact as a dependency
The `tmc-oss-sdk-common` artifact is distributed as a JAR file for easy consumption. If you are using one of our TMC Java SDKs, this artifact has already been included as dependency. You will get it by default. However, if you also want to include the `tmc-oss-sdk-common` artifact manually, you can add the following dependencies noted below. (Note: This is not common practice.)

### Distribution Management
We use [Cloudsmith](https://cloudsmith.com/) to distribute all of our open source SDKs.  The Maven repository URL to be included in your build file is:
```shell
https://dl.cloudsmith.io/public/autonomic/au-tmc-oss/maven/
```

### Maven
```shell
    <dependency>
      <groupId>com.autonomic.tmc</groupId>
      <artifactId>tmc-oss-sdk-common</artifactId>
      <version>1.0.0-beta</version>
    </dependency>
```

### Gradle
```shell
dependencies {
    implementation("com.autonomic.tmc:tmc-oss-sdk-common:1.0.0-beta")
}
```

> Are you using a different build tool other than Maven or Gradle? Are you interested in just browsing what is available in our repository? Do you want to download the jar files required for `tmc-oss-sdk-common` directly? You can find everything you need in our [Cloudsmith](https://cloudsmith.io/~autonomic/repos/au-tmc-oss/groups/) repository.


## Building
For this project, a Maven build is provided.

```shell
mvn clean install
```
## 3rd Party Components

This project has binary dependencies on other open source projects.  These components are listed in the [THIRD-PARTY.txt](THIRD-PARTY.txt) file.

## Tools we use

If you decide to fork `tmc-oss-sdk-common` or build it locally, you will be interested in learning more about the tools we use to ensure we are developing high-quality code. The tools we use are:

* [JaCoCo](https://www.eclemma.org/jacoco/) for code coverage
* [SonarCloud](https://sonarcloud.io/) for static code analysis
* [RevAPI](https://revapi.org/) to validate that we are not introducing breaking API changes
* [DependencyTrack](https://dependencytrack.org/) to analyze dependencies