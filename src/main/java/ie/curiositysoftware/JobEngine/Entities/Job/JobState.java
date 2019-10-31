package ie.curiositysoftware.JobEngine.Entities.Job;

public enum JobState {
    Created,
    InQueue,
    InProgress,
    Complete,
    Error,
    Cancelled
}
