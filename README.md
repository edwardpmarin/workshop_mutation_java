# workshop_mutation_java
Repository for the Mutation Testing workshop

### Unit tests

To execute unit test, run the following command (the unit test maven profile is active by default):

```bash
mvn test
```

### Mutation Tests

In order to execute the mutation tests in your local environment execute the following command

```bash
mvn org.pitest:pitest-maven:mutationCoverage -DcoverageThreshold=98 -DmutationThreshold=90
```
