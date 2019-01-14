# JSONCustomLintr
Library for the creation, running, and reporting of Custom Lint Rules for files that follow [JSON Notation](https://www.json.org).  
  
# Motivation  
The primary motivation for creating the library is for creating linting rules for avro schemas in an API environment.  
  
Introducing a tool to allow developers to lint JSON helps to:  
* Introduce a style safeguard to structured data schemas
* Scale an API across multiple devs without having to worry about inconsistencies
* Allow more hands off development and less monitoring of style conventions
* Introduce rules to allow for more advanced codegen / client freedom by disallowing patterns that would clash with either  
  
# Features 
JsonLint leverages [JSON-java](https://github.com/stleary/JSON-java) to generate Java objects from JSON files.  
This allows us to retain all the information we get from the library while also wrapping to provide more context to the object when creating linting rules.  
  
Features of the library include:
* Programmatic creation of lint rules
* Configurable number of lint rules to be run
* Configurable level of lint severity
* Running of lint rules on a single file or all files in a directory
* Running of lint rules on any JSON format regardless of the file extension
* HTML report summary of all lint warnings / errors  

# Quickstart  

Simple lint rule looking for any non-key String that is `test`  

Example:  
*Bad*
``` JSON
{
    "name": "test"
}
```
*Good*
``` JSON
{
    "name": "John"
}
```

Java Implementation in one method

``` Java
class Example {
    public static void setupLinter() {

        // Create LintImplementation 
        LintImplementation<WrappedPrimitive<String>> lintImplementation = new LintImplementation<WrappedPrimitive<String>>() {
            @Override
            public Class<String> getClazz() {
                return String.class;
            }

            @Override
            public boolean shouldReport(WrappedPrimitive<String> string) {
                return string.getValue().equals("test");
            }

            @Override
            public String report(WrappedPrimitive<String> string) {
                return string.getValue() + " found in file.";
            }
        };

        // Use builder to create rule
        LintRule rule = new LintRule.Builder()
        .setLevel(LintLevel.ERROR)
        .setImplementation(lintImplementation)
        .setIssueId("STRING_VALUE_NAMED_TEST")
        .build();        

        // Create register and register rule
        LintRegister register = new LintRegister();
        register.register(rule);

        // Create LintRunner with register and path to lint
        LintRunner lintRunner = new LintRunner(register, "./models");

        // Create ReportRunner and report lint errors
        ReportRunner reportRunner = new ReportRunner(lintRunner);
        reportRunner.report("build/reports");        
    }
}

```


  
# Usage  
When creating and running lint rules there is a flow of classes to generate in order to create the rule.  

The classes are:  
  
`LintImplementation<T>`  - Target `WrappedObject` implementing class type, determine rules for failure, configure output  
&darr;  
`LintRule.Builder` &rarr; `LintRule` - Configure severity, set issue ID, explanation, description, and implementation.  
&darr;  
`LintRegister` - Register all `LintRule`s  
&darr;  
`LintRunner` - Pass in `LintRegister` and configure directories or files to be checked with registry's issues  
&darr;  
`ReportRunner`  - Pass in `LintRunner` and generate HTML report  

 ## WrappedObject  
  
`WrappedObject` is an interface that 3 of our core classes implement.  
This interface allows us to have more context about the objects we look at when analyzing them for linting.  
  
The interface provides 4 methods:  
* `getOriginatingKey()` - returns the closest `JSONObject` key associated with this Object.  If there is no immediate key it will travel up the chain until one is found.  Only the root `JSONObject` will have a `null` return
* `getParentObject()` - returns the parent `WrappedObject` that created this Object.  Only the root `JSONObject` will have a `null` return  
* `parseAndReplaceWithWrappers()` - void method that will parse the sub objects of this Object and replace them with `WrappedObject`s.
*  `isPrimitive()` - returns `true` if the Object is simply a wrapper around a primitive value  
  
In the library we have 3 `WrappedObject` implementing classes:  
* `JSONObject` - A wrapper around the [JSON-java](https://github.com/stleary/JSON-java) `JSONObject` that `@Override`s the `toMap()` to return this library's objects
* `JSONArray` - A wrapper around the [JSON-java](https://github.com/stleary/JSON-java) `JSONArray` that `@Override`s the `toList()` and `toJSONObject()` to return this library's objects
* `WrappedPrimitive<T>` - A wrapper around all other datatypes in java in order to provide extra context in terms of the JSON File.  This class has a `getValue()` method to return the original object it was generated from.   

## LintImplementation  
`LintImplementation` is the core of the library.  
`LintImplementation` is an abstract class with 3 abstract methods and a type generic.  
`LintImplementation` takes in a type generic which must be one of the 3 provided classes that implement `WrappedObject`.  

`LintImplementation` has 4 methods and an instance variable:  
 * `private String reportMessage` - the message that will be reported when this implementation catches a lint error. This `String` can be set at runtime or ignored and overwrote with `report(T t)`  
 *  `getClazz()` - returns the target class to be analyzed.  If using `WrappedPrimitive<T>` must return `T.class` else must return `JSONArray` or `JSONObject`  
 * `shouldReport(T t)` - the main function of the class. This is where your LintRule will either catch an error or not.  Every instance of the `<T>` of your `LintImlpementation` will run through this method.  This is where you should apply your Lint logic and decide whether or not to report
 * `report(T t)` - funtion to return `reportMessage` or be `overwrote` and return a more static string
 * `setReportMessage()` - manually set the `reportMessage` string in the class (usually during `shouldReport()`) to provide more detail in the lint report  
  
*Note:*  If a reportMessage is not set when `report()` is called a `NoReportSetException` will be thrown.

### WrappedPrimitive Caveats  
When working with `LintImplementation` and `WrappedPrimitive` you must create your `LintImplementation` of type `WrappedPrimitive<T>` such as
```Java
    new LintImplementatioin<WrappedPrimitive<Integer>>()
```
However when writing your `getClazz()` method you must return the inner class of the `WrappedPrimitive`.  
  
  For Example:  
  *Bad*
  ``` Java
  LintImplementation<WrappedPrimitive<String>> lintImplementation ...
  ...
    Class<T> getClazz() {
        return WrappedPrimitive.class;
    }
...
  ```  
  *Good*
  ``` Java 
  LintImplementation<WrappedPrimitive<String>> lintImplementation ...
  ...
  Class<T> getClazz() {
      return String.class;
  }

  ```

## Writing your shouldReport  

When writing your shouldReport for a `LintImplementation` you have access to a lot of helper methods to assist in navigating the `JSON` File.  
  
  A list of existing helper methods available from `BaseJSONAnalyzer` are:  
  ``` Java
    protected boolean hasKeyAndValueEqualTo(JSONObject jsonObject, String key, Object toCheck);

    protected boolean hasIndexAndValueEqualTo(JSONArray jsonArray, int index, Object toCheck);

    protected WrappedPrimitive safeGetWrappedPrimitive(JSONObject jsonObject, String key);

    protected  JSONObject safeGetJSONObject(JSONObject jsonObject, String key);

    protected JSONArray safeGetJSONArray(JSONObject jsonObject, String key);

    protected WrappedPrimitive safeGetWrappedPrimitive(JSONArray array, int index);

    protected JSONObject safeGetJSONObject(JSONArray array, int index);

    protected JSONArray safeGetJSONArray(JSONArray array, int index);

    protected <T> boolean isEqualTo(WrappedPrimitive<T> wrappedPrimitive, T toCheck);

    protected boolean isOriginatingKeyEqualTo(WrappedObject object, String toCheck);

    protected <T> boolean isType(Object object, Class<T> clazz);

    protected <T> boolean isParentOfType(WrappedObject object, Class<T> clazz);

    protected boolean reduceBooleans(Boolean... booleans);
  ```

  ### Output from report  
  There are 2 ways to set your reportMessage:
   1. `@Override` the `report()` method.  
   2. `setReportMessage()` in the `shouldReport()` and have more dynmic report messages  
     
## LintRule  
`LintRule` is our class we use to setup what triggers a failure for a lint rule as well as what will happen when we have a failure.  

`LineRule` can only be created with `LintRule.Builder` and can not be directly instantiated.

A `LintRule` can have the following properties set through the builder:  
* `LintLevel level` (REQUIRED) - can be `IGNORE`, `WARNING`, `ERROR` and signals severity of Lint Rule  
* `LintImplementation implementation` (REQUIRED) - `LintImplementation` conigured to determine when this lint rule should report issues
* `String issueId` (REQUIRED) - Name of this lint rule.  Must be unique.
* `String issueDescription` - Short description of this lint rule.
* `String issueExplanation` - More in-depth description of lint rule.  

*Note:* If the required fields are not set when `LintRule.Builder.build()` is called a `LintRuleBuilderException` will be thrown.
  
## LintRegister
`LintRegister` is a simple class to register as many or as few `LintRule`s as wanted.  

Our only method is 
``` Java
    register(LintRule ...toRegister)
 ``` 
 which will register `LintRule`s.

 Our `LintRegister` acts as a simple intermediate between non IO parts of the Lint stack and our IO parts of our Lint stack, the `LintRunner`

## LintRunner  
`LintRunner` is our class that takes in a `LintRegister` and `String basePath` to load files from.  

This class has a 
``` Java
    public Map<LintRule, Map<JSONFile, List<String>>> lint() 
```
method which will lint our files for us but usually is just used as an intermediate class between our linting stack and reporting stack.  

## ReportRunner
`ReportRunner` is the entrypoint to our Reporting stack and the end point of our linting library.

The class takes in a `LintRunner` to connect and interact with our Linting stack.

The class also has a  
``` Java  
    public void report(String outputPath);
```
method which will generate an html report of all the lint errors in the given path as supplied by the `LintRunner`

# More In-Depth Example  
In this example we are checking if a `JSONObject`: 
1. Has a `type` field which a value of `boolean`  
2. Has a `name` field with a value that is a `String` and starts with `has`
3. Has a closest key value of `fields`
4. Has a parent object that is a `JSONArray`

Example  
*Bad*
``` JSON
{
  "fields" : [
    {
      "name": "hasX",
      "type": "boolean"
    }]
}
```

``` Java
class Example {

    public static void setupLint() {
      LintImplementation<JSONObject> lintImplementation = new LintImplementation<JSONObject>() {

            @Override
            public Class<JSONObject> getClazz() {
                return JSONObject.class;
            }

            @Override
            public boolean shouldReport(JSONObject jsonObject) {
                boolean hasBooleanType = hasKeyAndValueEqualTo(jsonObject, "type", "boolean");

                WrappedPrimitive name = safeGetWrappedPrimitive(jsonObject, "name");
                boolean nameStartsWithHas = false;
                if (name != null && name.getValue() instanceof String) {
                    nameStartsWithHas = ((String) name.getValue()).startsWith("has");

                }
                boolean originatingKeyIsFields = isOriginatingKeyEqualTo(jsonObject, "fields");
                boolean isParentArray = isParentOfType(jsonObject, JSONArray.class);

                setReportMessage("This is a bad one:\t" + jsonObject);
                return reduceBooleans(hasBooleanType, nameStartsWithHas, originatingKeyIsFields, isParentArray);
            }
        };

        LintRule rule = new LintRule.Builder()
                .setLevel(LintLevel.ERROR)
                .setImplementation(lintImplementation)
                .setIssueId("BOOLEAN_NAME_STARTS_WITH_HAS")
                .build();

        LintRegister register = new LintRegister();
        register.register(rule);

        LintRunner lintRunner = new LintRunner(register, "./models");

        ReportRunner reportRunner = new ReportRunner(lintRunner);
        reportRunner.report("build/reports");
    }
}
```
# Current Test Report Sample  
As this library progresses this report will evolve over time  
  
1/14/19 - First report unstyled, minimal information  
![First basic Report](https://www.zachary-moore.com/assets/pictures/basicTemplate-1-14-19.png)  
