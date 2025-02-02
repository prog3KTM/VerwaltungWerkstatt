package htw.prog3.KTM.model.jobs;

public enum JobStatus {
    CREATED,          // Job has been created but not started
    IN_PROGRESS,      // Job is actively being worked on
    ON_HOLD,          // Job is temporarily paused
    CANCELLED,        // Job has been cancelled
    COMPLETED,        // Job has been successfully finished
    FAILED            // Job could not be completed due to an issue
}
