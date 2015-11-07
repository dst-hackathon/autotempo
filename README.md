# AutoTempo

AutoTempo is a Java application to download appointments from Exchange and log them onto Tempo. Rules can be
written to map between Exchange appointments and Tempo work logs.

## Configuration

Example of configurations are available in the `example_configs` folder.

If no comment is set from the rule, then the subject of the appointment will be used. If the subject is empty, then
a default comment will be generated.

The types of rules available are:

### SimpleCategoryMappingRule

This matches Exchange appointments based on the category set within Exchange.

### SubjectSensitiveMappingRule

This matches Exchange appointments based on the subject of the appointment. Wildcards are supported.

## Usage

```
Usage: java -jar autotempo.jar [options]
  Options:
    -date
       Date to log in YYYY-mm-dd format
       Default: Sat Nov 07 16:55:08 ICT 2015
    -dry
       Run without logging to Tempo
       Default: false
    -profile
       Path to user profile
       Default: user.profile
    -rule
       Path to rule configuration
       Default: rules.xml
```

## Download

Travis CI continuously builds the application. The latest snapshot is available at the following URL:

<https://storage.googleapis.com/radiant-wall-112112.appspot.com/autotempo/autotempo-1.1-SNAPSHOT.jar>

[![Build Status](https://travis-ci.org/dst-hackathon/autotempo.svg)](https://travis-ci.org/dst-hackathon/autotempo)