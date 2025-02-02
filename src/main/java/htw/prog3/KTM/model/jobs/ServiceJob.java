package htw.prog3.KTM.model.jobs;

import htw.prog3.KTM.model.CarRepairComponent;

public class ServiceJob implements CarRepairComponent {
    private String jobName;
    private String status;
    private int jobId;

    public ServiceJob(String jobName, String status, int jobId) {
        this.jobName = jobName;
        this.status = status;
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
