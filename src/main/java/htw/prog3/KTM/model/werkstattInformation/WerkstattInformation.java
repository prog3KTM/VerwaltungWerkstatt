package htw.prog3.KTM.model.werkstattInformation;

public record WerkstattInformation(String name, String location, int phone, String email, String website,
                                   String vat, String businessRegNumber,
                                   String iban) { }
