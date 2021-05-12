# TestModeller.io Java

Java client library for the Test Modeller platform.

Perform and retrieve data allocations, synchronise run results, and execute jobs.

Include in your maven project using the following code

```
<dependency>
  <groupId>ie.curiositysoftware</groupId>
  <artifactId>testmodeller</artifactId>
  <version>2.0.25</version>
</dependency>
```

## Data Allocation
Test data allocation works in three phrases. 

1. The test data required must be specified and exposed as a test data criterion within a data catalogue.

2. The automation framework must call the data catalogue API to execute and run the data allocations (this is what finds and makes the right data then assigns it to each test instance). 

3. Each test can retrieve the allocated data values which can then be used within the automation framework to perform the required actions across user-interfaces, APIs, mainframes, ETL environments, and much more. This is built to plug directly into an automation framework independent of the language or type of automation being executed.

### TestNG Frameworks
With TestNG you can tag each test with a ‘@DataAllocation’ annotation.

```java
@DataAllocation(poolName = "pool", suiteName = "suite", groups = {"testname"})
Public void testDefinition()
{
   …………
}
```

Here you can specify the data allocation to connect the test with. This corresponds to three parameters:
1.	poolName – Name of the allocation pool the tests reside in.

2.	suiteName – Name of the test suite the test resides in.

3.	groups – The tests to perform allocation on. These are the allocation tests associated with the current test being tagged. Wildcards can be used to match multiple test names. The groups tag also takes a list so multiple test types can be specified.

These three parameters must match the data values specified for each matching test case specified within the appropriate allocation pool within the portal.

Within the test case you can retrieve the results using the 'dataAllocationEngine.GetDataResult’ function. Here you can specify the pool, suite name, and test name to retrieve the results for. Again, this must match the specifications given in the associated allocation pool within the portal. The DataAllocationResult class contains the functions to retrieve results by the column names, and column indexes as specified in the initial test criteria.

```java
DataAllocationResult allocResult= dataAllocationEngine.GetDataResult("pool", "suite", "test name");
```

Before the tests are executed in TestNG we have defined a @BeforeSuite function which is executed before all the specified tests are executed. Within this function we collect all @DataAllocate functions tagged against any tests about to be executed and then call the data allocation API to perform the associated executions. 

It is more efficient to perform these operations in bulk which is why they are collected into one list and then sent for allocation as opposed to directly performing the allocation inside each individual script. 

This implementation can be transposed to other testing frameworks (e.g. Nunit, Junit, etc) by replacing the appropriate keywords (@BeforeSuite, and @Test) with their corresponding values. The purpose of this java library is to provide a set of out-the-box methods for enabling you to call the data allocation API within your framework seamlessly.

```java
@BeforeSuite(alwaysRun = true)
public void allocateData(ITestContext testContext)
{

    ConnectionProfile cp = new ConnectionProfile(“ApiHost”,”ApiKey”));

    DataAllocationEngine dataAllocationEngine = new DataAllocationEngine(cp);

    // Create a list of all the pools that need allocating
    List<AllocationType> allocationTypes = new ArrayList<AllocationType>();

    ITestNGMethod[] methods =  testContext.getAllTestMethods();
    try {

        for (int i = 0; i < methods.length; i++) {
            ITestNGMethod method = methods[i];

            Method testMethod = method.getConstructorOrMethod().getMethod();

            if (testMethod != null && testMethod.isAnnotationPresent(DataAllocation.class))
            {
                DataAllocation dataAllocation = testMethod.getAnnotation(DataAllocation.class);

                for (String testType : dataAllocation.groups()) {
                    AllocationType allocationType = new AllocationType(dataAllocation.poolName(), dataAllocation.suiteName(), testType);

                    allocationTypes.add(allocationType);
                }
            }
        }
    } catch (Throwable e) {
        System.err.println(e);
    }

    // Publish and allocate data
    if (!dataAllocationEngine.ResolvePools(serverName), allocationTypes)) {
        System.out.println("Error - " + dataAllocationEngine.getErrorMessage());
    }
}
```




