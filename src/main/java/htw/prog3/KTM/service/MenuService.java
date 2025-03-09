package htw.prog3.KTM.service;

import htw.prog3.KTM.controller.AutoController;
import htw.prog3.KTM.controller.KundeController;
import htw.prog3.KTM.controller.WerkstattInformationController;
import htw.prog3.KTM.controller.ServiceJobController;
import htw.prog3.KTM.model.CarRepairComponent;
import htw.prog3.KTM.model.auto.Auto;
import htw.prog3.KTM.model.auto.Auto.AutoStatus;
import htw.prog3.KTM.model.jobs.ServiceJob;
import htw.prog3.KTM.model.jobs.ServiceJobType;
import htw.prog3.KTM.model.kunde.Kunde;
import htw.prog3.KTM.model.werkstattInformation.WerkstattInformation;
import htw.prog3.KTM.util.main;
import htw.prog3.KTM.view.MenuInteractions;
import htw.prog3.KTM.view.TextLineInterface;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class MenuService {

    private static MenuService instance;

    private MenuInteractions menu;
    private boolean running;
    private KundeController kundeController;
    private AutoController autoController;
    private ServiceJobController serviceJobController;
    
    private MenuService() {
        menu = new TextLineInterface(main.getAppConfig().getDatabaseManager());
        running = true;
        kundeController = main.getAppConfig().getKundeController();
        autoController = main.getAppConfig().getAutoController();
        serviceJobController = main.getAppConfig().getServiceJobController();
    }

    public void run() {
        while (running) {
            checkForWerkstattInformation();
            menu.showMainMenu();
            int option = menu.getOption();
            switch (option) {
                case 1:
                    menu.showWerk();
                    break;
                case 2:
                    runKundenMenuLogic();
                    break;
                case 3:
                    runAutoMenuLogic();
                    break;
                case 4:
                    runServiceMenuLogic();
                    break;
                case 99:
                    running = false;
                    break;
                default:
                    menu.throwError("Invalid option.");
                    break;
            }
        }
    }

    private void runServiceMenuLogic() {
        menu.showServiceMenu();
        int option = menu.getOption();
        switch (option) {
            case 1:
                addNewService();
                break;
            case 2:
                showAllServices();
                break;
            case 3:
                findServiceById();
                break;
            case 4:
                updateServiceStatus();
                break;
            case 9:
                // Return to main menu
                break;
            default:
                menu.throwError("Invalid option.");
                break;
        }
    }

    private void updateServiceStatus() {
        try {
            int serviceId = menu.getInt("Service-ID eingeben:");
            
            Optional<ServiceJob> serviceJobOpt = serviceJobController.getServiceJobById(serviceId);
            
            if (serviceJobOpt.isPresent()) {
                ServiceJob serviceJob = serviceJobOpt.get();
                String autoId = serviceJobController.getAutoIdForServiceJob(serviceId);
                Auto auto = autoController.getAutoById(autoId);
                
                if (auto != null) {
                    menu.sendMessage("Auto: " + auto.getBrand() + " " + auto.getModel() + " (Fahrzeug-Status: " + auto.getAutoStatus() + ")");
                    menu.sendMessage("Aktueller Bearbeitungs-Status des Services: " + serviceJob.getStatus());
                    menu.sendMessage("Neuen Bearbeitungs-Status wählen:");
                    menu.sendMessage("1. PENDING (Ausstehend)");
                    menu.sendMessage("2. IN_PROGRESS (In Bearbeitung)");
                    menu.sendMessage("3. COMPLETED (Abgeschlossen)");
                    menu.sendMessage("4. CANCELLED (Storniert)");
                    
                    int statusChoice = menu.getInt("Auswahl:");
                    String newStatus;
                    
                    switch (statusChoice) {
                        case 1: newStatus = "PENDING"; break;
                        case 2: newStatus = "IN_PROGRESS"; break;
                        case 3: newStatus = "COMPLETED"; break;
                        case 4: newStatus = "CANCELLED"; break;
                        default:
                            menu.throwError("Ungültige Auswahl.");
                            return;
                    }
                    
                    // Update the service job status
                    serviceJob.setStatus(newStatus);
                    serviceJobController.updateServiceJob(serviceJob, autoId);
                    
                    // Update the auto status if needed
                    boolean allServicesCompleted = true;
                    List<ServiceJob> autoServices = serviceJobController.getServiceJobsByAutoId(autoId);
                    
                    for (ServiceJob job : autoServices) {
                        if (!job.getStatus().equals("COMPLETED") && !job.getStatus().equals("CANCELLED")) {
                            allServicesCompleted = false;
                            break;
                        }
                    }
                    
                    if (allServicesCompleted && !autoServices.isEmpty()) {
                        auto.setAutoStatus(AutoStatus.AVAILABLE);
                        autoController.updateAuto(auto);
                        menu.sendMessage("Alle Services für dieses Auto sind abgeschlossen oder storniert.");
                        menu.sendMessage("Fahrzeug-Status wurde auf AVAILABLE gesetzt.");
                    } else {
                        // Make sure the auto is marked as in service
                        if (auto.getAutoStatus() != AutoStatus.IN_SERVICE) {
                            auto.setAutoStatus(AutoStatus.IN_SERVICE);
                            autoController.updateAuto(auto);
                            menu.sendMessage("Fahrzeug-Status wurde auf IN_SERVICE gesetzt.");
                        }
                    }
                    
                    menu.sendMessage("Bearbeitungs-Status des Services wurde auf " + newStatus + " aktualisiert.");
                } else {
                    menu.throwError("Auto für diesen Service nicht gefunden.");
                }
            } else {
                menu.throwError("Service mit ID " + serviceId + " nicht gefunden.");
            }
        } catch (NumberFormatException e) {
            menu.throwError("Ungültige Service-ID: " + e.getMessage());
            e.printStackTrace(); // Print the stack trace for debugging
        } catch (Exception e) {
            menu.throwError("Fehler beim Aktualisieren des Service-Status: " + e.getMessage());
            e.printStackTrace(); // Print the stack trace for debugging
        }
    }

    private void findServiceById() {
        try {
            int serviceId = menu.getInt("Service-ID eingeben:");
            
            Optional<ServiceJob> serviceJobOpt = serviceJobController.getServiceJobById(serviceId);
            
            if (serviceJobOpt.isPresent()) {
                ServiceJob serviceJob = serviceJobOpt.get();
                String autoId = serviceJobController.getAutoIdForServiceJob(serviceId);
                Auto auto = autoController.getAutoById(autoId);
                
                if (auto != null) {
                    menu.sendMessage("Service gefunden für Auto: " + auto.getBrand() + " " + auto.getModel());
                    menu.sendMessage("Fahrzeug-Status: " + auto.getAutoStatus());
                    menu.sendMessage("Service ID: " + serviceJob.getJobId());
                    menu.sendMessage("Typ: " + serviceJob.getType());
                    menu.sendMessage("Name: " + serviceJob.getJobName());
                    menu.sendMessage("Bearbeitungs-Status: " + serviceJob.getStatus());
                } else {
                    menu.throwError("Auto für diesen Service nicht gefunden.");
                }
            } else {
                menu.throwError("Service mit ID " + serviceId + " nicht gefunden.");
            }
        } catch (NumberFormatException e) {
            menu.throwError("Ungültige Service-ID: " + e.getMessage());
            e.printStackTrace(); // Print the stack trace for debugging
        } catch (Exception e) {
            menu.throwError("Fehler beim Suchen des Services: " + e.getMessage());
            e.printStackTrace(); // Print the stack trace for debugging
        }
    }

    private void showAllServices() {
        try {
            List<Auto> autos = autoController.getAllAutos();
            
            if (autos.isEmpty()) {
                menu.sendMessage("Es sind keine Autos vorhanden.");
                return;
            }
            
            menu.sendMessage("Alle Services:");
            boolean foundServices = false;
            
            for (Auto auto : autos) {
                List<ServiceJob> serviceJobs = serviceJobController.getServiceJobsByAutoId(auto.getId());
                
                if (!serviceJobs.isEmpty()) {
                    menu.sendMessage("Auto: " + auto.getBrand() + " " + auto.getModel() + " (ID: " + auto.getId() + ", Fahrzeug-Status: " + auto.getAutoStatus() + ")");
                    
                    for (ServiceJob serviceJob : serviceJobs) {
                        menu.sendMessage("  - Service ID: " + serviceJob.getJobId() + 
                                       ", Typ: " + serviceJob.getType() + 
                                       ", Name: " + serviceJob.getJobName() + 
                                       ", Bearbeitungs-Status: " + serviceJob.getStatus());
                        foundServices = true;
                    }
                    menu.sendMessage("------------------------");
                }
            }
            
            if (!foundServices) {
                menu.sendMessage("Es wurden keine Services gefunden.");
            }
        } catch (Exception e) {
            menu.throwError("Fehler beim Anzeigen der Services: " + e.getMessage());
            e.printStackTrace(); // Print the stack trace for debugging
        }
    }

    private void addNewService() {
        try {
            // First, get the car to add the service to
            menu.sendMessage("Für welches Auto soll der Service hinzugefügt werden?");
            List<Auto> autos = autoController.getAllAutos();
            
            if (autos.isEmpty()) {
                menu.sendMessage("Es sind keine Autos vorhanden. Bitte fügen Sie zuerst ein Auto hinzu.");
                return;
            }
            
            for (int i = 0; i < autos.size(); i++) {
                Auto auto = autos.get(i);
                menu.sendMessage((i + 1) + ". " + auto.getBrand() + " " + auto.getModel() + " (ID: " + auto.getId() + ", Fahrzeug-Status: " + auto.getAutoStatus() + ")");
            }
            
            int autoIndex = menu.getInt("Auto auswählen (Nummer eingeben):") - 1;
            if (autoIndex < 0 || autoIndex >= autos.size()) {
                menu.throwError("Ungültige Auswahl.");
                return;
            }
            
            Auto selectedAuto = autos.get(autoIndex);
            
            // Now get the service details
            String serviceInfo = menu.getServiceInfo();
            String[] parts = serviceInfo.split(",");
            
            if (parts.length < 3) {
                menu.throwError("Ungültige Service-Informationen. Format: ID,Typ,Name");
                return;
            }
            
            int jobId = Integer.parseInt(parts[0]);
            int typeChoice = Integer.parseInt(parts[1]);
            String jobName = parts[2];
            
            // Check if a service with this ID already exists
            if (serviceJobController.getServiceJobById(jobId).isPresent()) {
                menu.throwError("Ein Service mit der ID " + jobId + " existiert bereits. Bitte wählen Sie eine andere ID.");
                return;
            }
            
            ServiceJobType type;
            switch (typeChoice) {
                case 1: type = ServiceJobType.OIL_CHANGE; break;
                case 2: type = ServiceJobType.TIRE_REPLACEMENT; break;
                case 3: type = ServiceJobType.SOFTWARE_UPDATE; break;
                case 4: type = ServiceJobType.BRAKE_REPAIR; break;
                case 5: type = ServiceJobType.ENGINE_DIAGNOSTIC; break;
                case 6: type = ServiceJobType.GENERAL_MAINTENANCE; break;
                default: 
                    menu.throwError("Ungültiger Service-Typ.");
                    return;
            }
            
            ServiceJob serviceJob = new ServiceJob(jobId, type, jobName, "PENDING");
            
            // Add the service job to the auto object in memory
            selectedAuto.addJob(serviceJob);
            selectedAuto.setAutoStatus(AutoStatus.IN_SERVICE);
            
            // Save the service job to the database
            serviceJobController.addServiceJob(serviceJob, selectedAuto.getId());
            
            // Update the auto in the database
            autoController.updateAuto(selectedAuto);
            
            menu.sendMessage("Service wurde erfolgreich hinzugefügt.");
            menu.sendMessage("Bearbeitungs-Status des Services wurde auf PENDING gesetzt.");
            menu.sendMessage("Fahrzeug-Status wurde auf IN_SERVICE gesetzt.");
        } catch (NumberFormatException e) {
            menu.throwError("Ungültige Nummer: " + e.getMessage());
            e.printStackTrace(); // Print the stack trace for debugging
        } catch (Exception e) {
            menu.throwError("Fehler beim Hinzufügen des Services: " + e.getMessage());
            e.printStackTrace(); // Print the stack trace for debugging
        }
    }

    private void runAutoMenuLogic() {
        menu.showAutoMenu();
        int option = menu.getOption();
        switch (option) {
            case 1:
                showAllAutos();
                break;
            case 2:
                findAutoById();
                break;
            case 3:
                addNewAuto();
                break;
            case 4:
                deleteAuto();
                break;
            case 5:
                updateAutoStatus();
                break;
            case 9:
                // Return to main menu
                break;
            default:
                menu.throwError("Invalid option.");
                break;
        }
    }

    private void deleteAuto() {
        try {
            String autoId = menu.getString("Auto-ID eingeben:");
            autoController.deleteAutoById(autoId);
            menu.sendMessage("Auto wurde erfolgreich gelöscht.");
        } catch (Exception e) {
            menu.throwError("Fehler beim Löschen des Autos: " + e.getMessage());
        }
    }

    private void findAutoById() {
        try {
            String autoId = menu.getString("Auto-ID eingeben:");
            Auto auto = autoController.getAutoById(autoId);
            if (auto != null) {
                menu.sendMessage("Auto gefunden:");
                menu.sendMessage("ID: " + auto.getId());
                menu.sendMessage("Marke: " + auto.getBrand());
                menu.sendMessage("Modell: " + auto.getModel());
                menu.sendMessage("Kennzeichen: " + auto.getLicensePlate());
                menu.sendMessage("Fahrzeug-Status: " + auto.getAutoStatus());
                
                // Display associated services if any
                List<ServiceJob> services = serviceJobController.getServiceJobsByAutoId(auto.getId());
                if (!services.isEmpty()) {
                    menu.sendMessage("\nServices für dieses Auto:");
                    for (ServiceJob service : services) {
                        menu.sendMessage("  - ID: " + service.getJobId() + 
                                       ", Typ: " + service.getType() + 
                                       ", Name: " + service.getJobName() + 
                                       ", Bearbeitungs-Status: " + service.getStatus());
                    }
                } else {
                    menu.sendMessage("\nKeine Services für dieses Auto vorhanden.");
                }
            } else {
                menu.sendMessage("Auto mit ID " + autoId + " nicht gefunden.");
            }
        } catch (Exception e) {
            menu.throwError("Fehler beim Suchen des Autos: " + e.getMessage());
            e.printStackTrace(); // Print the stack trace for debugging
        }
    }

    private void showAllAutos() {
        try {
            List<Auto> autos = autoController.getAllAutos();
            if (autos.isEmpty()) {
                menu.sendMessage("Es sind keine Autos vorhanden.");
            } else {
                menu.sendMessage("Alle Autos:");
                for (Auto auto : autos) {
                    menu.sendMessage("ID: " + auto.getId() + 
                                   ", Marke: " + auto.getBrand() + 
                                   ", Modell: " + auto.getModel() + 
                                   ", Kennzeichen: " + auto.getLicensePlate() + 
                                   ", Fahrzeug-Status: " + auto.getAutoStatus());
                    
                    // Display associated services if any
                    List<ServiceJob> services = serviceJobController.getServiceJobsByAutoId(auto.getId());
                    if (!services.isEmpty()) {
                        menu.sendMessage("  Services:");
                        for (ServiceJob service : services) {
                            menu.sendMessage("    - ID: " + service.getJobId() + 
                                           ", Typ: " + service.getType() + 
                                           ", Name: " + service.getJobName() + 
                                           ", Bearbeitungs-Status: " + service.getStatus());
                        }
                    }
                    menu.sendMessage("------------------------");
                }
            }
        } catch (Exception e) {
            menu.throwError("Fehler beim Anzeigen der Autos: " + e.getMessage());
            e.printStackTrace(); // Print the stack trace for debugging
        }
    }

    private void addNewAuto() {
        try {
            // First, get the customer to add the car to
            menu.sendMessage("Für welchen Kunden soll das Auto hinzugefügt werden?");
            List<Kunde> kunden = kundeController.getAllKunden();
            
            if (kunden.isEmpty()) {
                menu.sendMessage("Es sind keine Kunden vorhanden. Bitte fügen Sie zuerst einen Kunden hinzu.");
                return;
            }
            
            for (int i = 0; i < kunden.size(); i++) {
                menu.sendMessage((i + 1) + ". " + kunden.get(i).getName());
            }
            
            int kundeIndex = menu.getOption() - 1;
            if (kundeIndex < 0 || kundeIndex >= kunden.size()) {
                menu.throwError("Ungültige Auswahl.");
                return;
            }
            
            Kunde selectedKunde = kunden.get(kundeIndex);
            
            // Now get the car details
            String autoInfo = menu.getAutoInfo();
            String[] parts = autoInfo.split(",");
            
            String id = parts[0];
            String brand = parts[1];
            String model = parts[2];
            String licensePlate = parts[3];
            
            Auto auto = new Auto(id, model, brand, licensePlate, AutoStatus.AVAILABLE);
            
            // Add the car to the customer and update in the database
            selectedKunde.addAuto(auto);
            kundeController.updateKunde(selectedKunde);
            
            // Also add the car to the auto repository
            autoController.addAuto(auto);
            
            menu.sendMessage("Auto wurde erfolgreich hinzugefügt.");
        } catch (SQLException e) {
            menu.throwError("Datenbankfehler: " + e.getMessage());
        } catch (Exception e) {
            menu.throwError("Fehler beim Hinzufügen des Autos: " + e.getMessage());
        }
    }

    private void runKundenMenuLogic() {
        menu.showKundenMenu();
        int option = menu.getOption();
        switch (option) {
            case 1:
                showAllKunden();
                break;
            case 2:
                findKundeById();
                break;
            case 3:
                addNewKunde();
                break;
            case 4:
                updateKunde();
                break;
            case 5:
                deleteKunde();
                break;
            case 9:
                // Return to main menu
                break;
            default:
                menu.throwError("Invalid option.");
                break;
        }
    }

    private void deleteKunde() {
        try {
            int kundeId = menu.getInt("Kunden-ID eingeben:");
            kundeController.deleteKunde(kundeId);
            menu.sendMessage("Kunde wurde erfolgreich gelöscht.");
        } catch (SQLException e) {
            menu.throwError("Datenbankfehler: " + e.getMessage());
        }
    }

    private void updateKunde() {
        try {
            int kundeId = menu.getInt("Kunden-ID eingeben:");
            Optional<Kunde> kundeOpt = kundeController.getKundeById(kundeId);
            
            if (kundeOpt.isPresent()) {
                Kunde kunde = kundeOpt.get();
                menu.sendMessage("Aktuelle Daten:");
                menu.showKunde(kunde);
                
                // Get updated information
                Kunde updatedKunde = menu.getKundeInfo();
                updatedKunde.setId(kundeId); // Keep the same ID
                
                // Keep the same cars
                for (Auto auto : kunde.getAutos()) {
                    updatedKunde.addAuto(auto);
                }
                
                kundeController.updateKunde(updatedKunde);
                menu.sendMessage("Kunde wurde erfolgreich aktualisiert.");
            } else {
                menu.sendMessage("Kunde mit ID " + kundeId + " nicht gefunden.");
            }
        } catch (SQLException e) {
            menu.throwError("Datenbankfehler: " + e.getMessage());
        }
    }

    private void addNewKunde() {
        try {
            Kunde kunde = menu.getKundeInfo();
            kundeController.createKunde(kunde);
            menu.sendMessage("Kunde wurde erfolgreich hinzugefügt.");
        } catch (SQLException e) {
            menu.throwError("Datenbankfehler: " + e.getMessage());
        }
    }

    private void findKundeById() {
        try {
            int kundeId = menu.getInt("Kunden-ID eingeben:");
            Optional<Kunde> kundeOpt = kundeController.getKundeById(kundeId);
            
            if (kundeOpt.isPresent()) {
                menu.showKunde(kundeOpt.get());
            } else {
                menu.sendMessage("Kunde mit ID " + kundeId + " nicht gefunden.");
            }
        } catch (SQLException e) {
            menu.throwError("Datenbankfehler: " + e.getMessage());
        }
    }

    private void showAllKunden() {
        try {
            List<Kunde> kunden = kundeController.getAllKunden();
            if (kunden.isEmpty()) {
                menu.sendMessage("Es sind keine Kunden vorhanden.");
            } else {
                menu.sendMessage("Alle Kunden:");
                for (Kunde kunde : kunden) {
                    menu.showKunde(kunde);
                    menu.sendMessage("------------------------");
                }
            }
        } catch (SQLException e) {
            menu.throwError("Datenbankfehler: " + e.getMessage());
        }
    }

    private void checkForWerkstattInformation() {
        WerkstattInformationController werkstattInformationController = new WerkstattInformationController(main.getAppConfig().getDatabaseManager());
        if(!werkstattInformationController.ifInformationExists()) {
            WerkstattInformation werkstattInformation = menu.getWerkstattInformation();
            werkstattInformationController.save(werkstattInformation);
            menu.sendMessage("Werkstatt-Information wurde gespeichert!");
        }
    }

    private void updateAutoStatus() {
        try {
            String autoId = menu.getString("Auto-ID eingeben:");
            Auto auto = autoController.getAutoById(autoId);
            
            if (auto == null) {
                menu.sendMessage("Auto mit ID " + autoId + " nicht gefunden.");
                return;
            }
            
            menu.sendMessage("Auto gefunden: " + auto.getBrand() + " " + auto.getModel());
            menu.sendMessage("Aktueller Fahrzeug-Status: " + auto.getAutoStatus());
            
            // Check if the auto has any active services
            List<ServiceJob> serviceJobs = serviceJobController.getServiceJobsByAutoId(autoId);
            boolean hasActiveServices = false;
            
            for (ServiceJob job : serviceJobs) {
                if (!job.getStatus().equals("COMPLETED") && !job.getStatus().equals("CANCELLED")) {
                    hasActiveServices = true;
                    break;
                }
            }
            
            if (hasActiveServices) {
                menu.sendMessage("Dieses Auto hat aktive Services. Der Fahrzeug-Status kann nicht geändert werden, bis alle Services abgeschlossen oder storniert sind.");
                menu.sendMessage("Bitte aktualisieren Sie zuerst die Bearbeitungs-Status der Services im Service-Menü.");
                return;
            }
            
            menu.sendMessage("Neuen Fahrzeug-Status wählen:");
            menu.sendMessage("1. AVAILABLE (Verfügbar)");
            menu.sendMessage("2. IN_SERVICE (In Wartung)");
            menu.sendMessage("3. SOLD (Verkauft)");
            
            int statusChoice = menu.getInt("Auswahl:");
            AutoStatus newStatus;
            
            switch (statusChoice) {
                case 1: newStatus = AutoStatus.AVAILABLE; break;
                case 2: newStatus = AutoStatus.IN_SERVICE; break;
                case 3: newStatus = AutoStatus.SOLD; break;
                default:
                    menu.throwError("Ungültige Auswahl.");
                    return;
            }
            
            auto.setAutoStatus(newStatus);
            autoController.updateAuto(auto);
            
            menu.sendMessage("Fahrzeug-Status wurde auf " + newStatus + " aktualisiert.");
        } catch (NumberFormatException e) {
            menu.throwError("Ungültige Nummer: " + e.getMessage());
            e.printStackTrace(); // Print the stack trace for debugging
        } catch (Exception e) {
            menu.throwError("Fehler beim Aktualisieren des Fahrzeug-Status: " + e.getMessage());
            e.printStackTrace(); // Print the stack trace for debugging
        }
    }

    public static MenuService getInstance() {
        if(instance == null) instance = new MenuService();
        return instance;
    }
}
