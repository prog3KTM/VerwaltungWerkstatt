package htw.prog3.KTM.model.jobs;

public abstract class Service {
    abstract public int getId();
    abstract public String getName();
    abstract public String getStatusString();
    abstract public int getTypeOrdinal();
    abstract public String getServiceType();
    abstract public String getTypeString();
}
