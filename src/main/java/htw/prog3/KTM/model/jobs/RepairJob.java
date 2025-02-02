package htw.prog3.KTM.model.jobs;

import htw.prog3.KTM.model.CarRepairComponent;

public class RepairJob implements CarRepairComponent {
    private String jobName;
    private JobStatus status;
    private int jobId;
    private RepairJobType type;

    public RepairJobType getType() {
        return type;
    }

    public void setType(RepairJobType type) {
        this.type = type;
    }

    public RepairJob(int jobId, RepairJobType typ, String jobName, JobStatus status) {
        this.jobName = jobName;
        this.status = status;
        this.jobId = jobId;
        this.type = typ;
    }

    @Override
    public void printStatus() {

    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }
}
