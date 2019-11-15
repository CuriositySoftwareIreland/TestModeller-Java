package ie.curiositysoftware.allocation.dto;

public class AllocationType {
    private String poolName;

    private String allocationTestName;

    private String suiteName;

    public AllocationType(String poolName, String suiteName, String allocationTestName)
    {
        this.poolName = poolName;

        this.suiteName = suiteName;

        this.allocationTestName = allocationTestName;
    }

    public String getAllocationTestName() {
        return allocationTestName;
    }

    public String getPoolName() {
        return poolName;
    }

    public String getSuiteName() {
        return suiteName;
    }

    public void setAllocationTestName(String allocationTestName) {
        this.allocationTestName = allocationTestName;
    }

    public void setPoolName(String poolName) {
        this.poolName = poolName;
    }

    public void setSuiteName(String suiteName) {
        this.suiteName = suiteName;
    }
}

