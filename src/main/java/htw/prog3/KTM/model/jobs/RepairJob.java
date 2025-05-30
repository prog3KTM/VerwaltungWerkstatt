package htw.prog3.KTM.model.jobs;

import htw.prog3.KTM.model.CarRepairComponent;

public class RepairJob extends Service implements CarRepairComponent {
    private int id;
    private String name;
    private RepairJobType type;
    private JobStatus status;

    public RepairJobType getType() {
        return type;
    }
    public String getTypeString() {
        return type.toString();
    }

    public static final String SERVICE_TYPE = "RepairJob";
    @Override
    public String getServiceType() {
        return SERVICE_TYPE;
    }

    public void setType(RepairJobType type) {
        this.type = type;
    }

    public RepairJob(int id, RepairJobType typ, String name, String status) {
        this.name = name;
        this.status = JobStatus.fromString(status);
        this.id = id;
        this.type = typ;
    }

    @Override
    public void printStatus() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JobStatus getStatus() {
        return status;
    }

    public String getStatusString() {
        return status.toString();
    }

    @Override
    public int getTypeOrdinal() {
        return type.ordinal();
    }

    public void setStatus(JobStatus status) {
        this.status = status;
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
}
