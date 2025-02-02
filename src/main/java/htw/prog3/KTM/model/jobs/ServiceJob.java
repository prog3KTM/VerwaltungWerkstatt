package htw.prog3.KTM.model.jobs;

import htw.prog3.KTM.model.CarRepairComponent;

public class ServiceJob implements CarRepairComponent {
    private int jobId;
    private String jobName;
    private String status;
    private ServiceJobType type;

    public ServiceJobType getType() {
        return type;
    }

    public void setType(ServiceJobType type) {
        this.type = type;
    }

    public ServiceJob(int jobId, ServiceJobType type, String jobName, String status) {
        this.jobName = jobName;
        this.status = status;
        this.type = type;
        this.jobId = jobId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }
}
