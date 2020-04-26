package ie.curiositysoftware.allocation.dto;

public class AllocationLookupDto {
    private String pool;

    private String testName;

    private String suite;

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getPool() {
        return pool;
    }

    public String getSuite() {
        return suite;
    }

    public void setPool(String pool) {
        this.pool = pool;
    }

    public void setSuite(String suite) {
        this.suite = suite;
    }
}
