package htw.prog3.KTM.model;

import java.util.List;

public record WerkstattInformation(String name, String location, int phone, String email, String website,
                                   List<String> offeredServices, String vat, String businessRegNumber,
                                   String iban) { }
