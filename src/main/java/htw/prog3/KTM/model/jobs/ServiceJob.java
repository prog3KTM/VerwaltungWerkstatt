package htw.prog3.KTM.model.jobs;

import htw.prog3.KTM.model.CarRepairComponent;

public class ServiceJob implements CarRepairComponent {
    private int id;
    private String name;
    private ServiceJobType type;
    private JobStatus status;

    public ServiceJobType getType() {
        return type;
    }

    public void setType(ServiceJobType type) {
        this.type = type;
    }

    public ServiceJob(int id, ServiceJobType type, String name, String status) {
        this.name = name;
        this.status = JobStatus.fromString(status);
        this.type = type;
        this.id = id;
    }

    @Override
    public void printStatus() {
        System.out.println(toString());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return JobStatus.toString(status);
    }

    public void setStatus(String status) {
        this.status = JobStatus.fromString(status);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    @Override
    public String toString() {
        return "ServiceJob{" +
                "jobId=" + id +
                ", jobName='" + name + '\'' +
                ", status='" + status + '\'' +
                ", type=" + type +
                '}';
    }
}
