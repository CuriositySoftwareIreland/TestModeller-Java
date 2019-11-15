package ie.curiositysoftware.jobengine.dto.job;

public enum JobState {
    Created,
    InQueue,
    InProgress,
    Complete,
    Error,
    Cancelled
}
