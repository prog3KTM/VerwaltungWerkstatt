package htw.prog3.KTM.model;

import java.util.ArrayList;
import java.util.List;


public class Kunde {
    private int id;
    private String name;
    private String address;
    private String phone;
    private List<Auto> autos;


    public Kunde(int id, String name, String address, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.autos = new ArrayList<>();
    }

    public List<Auto> getAutos() {
        return autos;
    }

    public void addAuto(Auto auto) {
        this.autos.add(auto);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
