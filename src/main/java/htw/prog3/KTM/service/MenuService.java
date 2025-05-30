package htw.prog3.KTM.service;

import htw.prog3.KTM.controller.*;
import htw.prog3.KTM.generated.Tables;
import htw.prog3.KTM.model.car.Car;
import htw.prog3.KTM.model.car.Car.CarStatus;
import htw.prog3.KTM.model.jobs.RepairJob;
import htw.prog3.KTM.model.jobs.RepairJobType;
import htw.prog3.KTM.model.jobs.ServiceJob;
import htw.prog3.KTM.model.jobs.ServiceJobType;
import htw.prog3.KTM.model.customer.Customer;
import htw.prog3.KTM.model.order.Order;
import htw.prog3.KTM.model.order.OrderStatus;
import htw.prog3.KTM.model.workshopinformation.WorkshopInformation;
import htw.prog3.KTM.util.main;
import htw.prog3.KTM.view.MenuInteractions;
import htw.prog3.KTM.view.TextLineInterface;
import org.jooq.DSLContext;

import java.sql.SQLException;
import java.util.*;

public class MenuService {

    private static MenuService instance;

    private MenuInteractions menu;
    private boolean running;
    private CustomerController customerController;
    private CarController carController;
    private OrderController orderController;
    private ServiceJobController serviceJobController;
    private RepairJobController repairJobController;
    
    private MenuService() {
        menu = new TextLineInterface(main.getAppConfig().getDatabaseManager());
        running = true;
        customerController = main.getAppConfig().getCustomerController();
        carController = main.getAppConfig().getCarController();
        serviceJobController = main.getAppConfig().getServiceJobController();
        repairJobController = main.getAppConfig().getRepairJobController();
        orderController = main.getAppConfig().getOrderController();
    }

    public void run() {
        while (running) {
            checkForWerkstattInformation();
            menu.showMainMenu();
            int option = menu.getOption();
            switch (option) {
                case 1:
                    menu.showWerk(main.getAppConfig().getWorkshopInformationController().getWerkstattInformation());
                    break;
                case 2:
                    updateWorkshopInformation();
                    break;
                case 3:
                    runCarMenuLogic();
                    break;
                case 4:
                    runServiceMenuLogic();
                    break;
                case 5:
                    runOrderMenuLogic();
                    break;
                case 6:
                    runKundenMenuLogic();
                    break;
                case 7:
                    runRepairMenuLogic();
                    break;
                case 8:
                    deleteAllData();
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

    private void updateWorkshopInformation() {
        main.getAppConfig().getDatabaseManager().getDSLContext().deleteFrom(Tables.KONFIGURATIONSTABELLE);
        WorkshopInformation workshopInformation = menu.getWerkstattInformation();
        main.getAppConfig().getWorkshopInformationController().save(workshopInformation);
        menu.sendMessage("Werkstatt-Information wurde gespeichert!");
    }

    private void deleteAllData() {
        DSLContext dsl = main.getAppConfig().getDatabaseManager().getDSLContext();
        dsl.delete(Tables.CUSTOMER).execute();
        dsl.delete(Tables.ORDERS).execute();
        dsl.delete(Tables.SERVICE).execute();
        dsl.delete(Tables.CAR).execute();
    }

    private void runOrderMenuLogic() {
        menu.showOrderMenu();
        int option = menu.getOption();
        switch (option) {
            case 1:
                addNewOrder();
                break;
            case 2:
                showAllOrders();
                break;
            case 3:
                findOrderById();
                break;
            case 4:
                updateOrderStatus();
                break;
            case 5:
                updateOrderPrice();
                break;
            case 6:
                updateAddServiceToOrder();
                break;
            case 9:
                // Return to main menu
                break;
            default:
                menu.throwError("Invalid option.");
                break;
        }
    }

    private void updateAddServiceToOrder() {
        int orderId = menu.getInt("Order-ID eingeben:");
        Optional<Order> order = orderController.getOrderById(orderId);
        if(!order.isPresent()) {
            menu.throwError("Order konnte nicht gefunden werden.");
            return;
        }
        Order orderToUpdate = order.get();

        int serviceId = 0;
        while (serviceId != 9999) {
            serviceId = menu.getInt("ServiceJob-ID eingeben oder aufhören mit 9999:");
            if(serviceJobController.getServiceJobById(serviceId).isPresent()
                || repairJobController.getRepairJobById(serviceId).isPresent()) {
                orderToUpdate.addService(serviceId);
                menu.sendMessage("Service-ID wurde hinzugefügt");
            }else {
                if(serviceId != 9999) {
                    menu.throwError("Diese ServiceJob existiert nicht.");
                }
            }
        }

        orderController.updateOrder(orderToUpdate);
        menu.sendMessage("Order wurde geupdatet.");
    }

    private void updateOrderPrice() {
        int id = menu.getInt("Order-ID eingeben:");
        float price = menu.getFloat("Bitte neuen preis eingeben:");
        Optional<Order> order = orderController.getOrderById(id);
        order.ifPresentOrElse(order1 -> {
            order1.setTotal(price);
            orderController.updateOrder(order1);
        }, () -> {
            menu.sendMessage("Diese Order existiert nicht.");
        });
    }

    private void updateOrderStatus() {
        int id = menu.getInt("Order-ID eingeben:");
        OrderStatus status = menu.getOrderStatus("Bitte neuen Status wählen:");
        Optional<Order> order = orderController.getOrderById(id);
        order.ifPresentOrElse(order1 -> {
            order1.setStatus(status);
            orderController.updateOrder(order1);
        }, () -> {
            menu.sendMessage("Diese Order existiert nicht.");
        });
    }

    private void findOrderById() {
        int id = menu.getInt("Order-ID eingeben:");
        Optional<Order> order = orderController.getOrderById(id);
        menu.showOrder(order);
        menu.hold();
    }

    private void showAllOrders() {
        List<Order> orders = orderController.getAllOrders();
        if(orders.isEmpty()) {
            menu.throwError("Keine Orders vorhanden.");
            return;
        }
        menu.showAllOrder(orders);
        menu.hold();
    }

    private void addNewOrder() {
        try {
            customerController.getAllCustomers().forEach((customer -> {
                menu.sendMessage(customer.getId()+". " + customer.getName());
            }));
        } catch (SQLException e) {
            menu.sendMessage("Fehler beim laden der Kunden aufgetreten.");
        }
        int customerId = -1;
        while (customerId == -1) {
            customerId = menu.getInt("Kunde-ID eingeben:");
            try {
                if(customerController.getCustomerById(customerId).isEmpty()) {
                    customerId = -1;
                    menu.sendMessage("Kunde mit dieser ID existiert nicht.");
                }
            } catch (SQLException e) {
                customerId = -1;
            }
        }
        Set<Integer> services = new HashSet<>();
        int serviceId = 0;
        while (serviceId != 9999) {
            serviceId = menu.getInt("Service-ID eingeben oder aufhören mit 9999:");
            if(repairJobController.getRepairJobById(serviceId).isPresent()
                    || serviceJobController.getServiceJobById(serviceId).isPresent()) {
                services.add(serviceId);
                menu.sendMessage("Service-ID wurde hinzugefügt");
            }else {
                if(serviceId != 9999) {
                    menu.throwError("Dieser Service existiert nicht.");
                }
            }
        }
        float price = menu.getFloat("Bitte Preis eingeben:");
        Order order = new Order(services, price, OrderStatus.PENDING, customerId);
        int id = orderController.createOrder(order);
        menu.sendMessage("Die neue Order wurde erfolgreich erstellt und hat die ID:"+id+".");
    }

    private void runRepairMenuLogic() {
        menu.showRepairServiceMenu();
        int option = menu.getOption();
        switch (option) {
            case 1:
                addNewRepairService();
                break;
            case 2:
                showAllRepairServices();
                break;
            case 3:
                findRepairServiceById();
                break;
            case 4:
                updateRepairServiceStatus();
                break;
            case 9:
                // Return to main menu
                break;
            default:
                menu.throwError("Invalid option.");
                break;
        }
    }

    private void addNewRepairService() {
        try {
            // First, get the car to add the service to
            menu.sendMessage("Für welches Auto soll der Repair-Service hinzugefügt werden?");
            List<Car> cars = carController.getAllCars();

            if (cars.isEmpty()) {
                menu.sendMessage("Es sind keine Autos vorhanden. Bitte fügen Sie zuerst ein Auto hinzu.");
                return;
            }

            for (int i = 0; i < cars.size(); i++) {
                Car car = cars.get(i);
                menu.sendMessage((i + 1) + ". " + car.getBrand() + " " + car.getModel() + " (ID: " + car.getId() + ", Fahrzeug-Status: " + car.getCarStatus() + ")");
            }

            int carIndex = menu.getInt("Auto auswählen (Nummer eingeben):") - 1;
            if (carIndex < 0 || carIndex >= cars.size()) {
                menu.throwError("Ungültige Auswahl.");
                return;
            }

            Car selectedCar = cars.get(carIndex);

            // Now get the service details
            String serviceInfo = menu.getRepairServiceInfo();
            String[] parts = serviceInfo.split(",");

            if (parts.length < 3) {
                menu.throwError("Ungültige Service-Informationen. Format: ID,Typ,Name");
                return;
            }

            int jobId = Integer.parseInt(parts[0]);
            int typeChoice = Integer.parseInt(parts[1]);
            String jobName = parts[2];

            // Check if a service with this ID already exists
            if (repairJobController.getRepairJobById(jobId).isPresent()) {
                menu.throwError("Ein Repair-Service mit der ID " + jobId + " existiert bereits. Bitte wählen Sie eine andere ID.");
                return;
            }

            RepairJobType type;
            switch (typeChoice) {
                case 1: type = RepairJobType.ENGINE_REPAIR; break;
                case 2: type = RepairJobType.ELECTRICAL_REPAIR; break;
                case 3: type = RepairJobType.SUSPENSION_REPAIR; break;
                case 4: type = RepairJobType.COOLING_SYSTEM_REPAIR; break;
                case 5: type = RepairJobType.BRAKE_REPLACEMENT; break;
                case 6: type = RepairJobType.TRANSMISSION_FIX; break;
                case 7: type = RepairJobType.EXHAUST_SYSTEM_FIX; break;
                default:
                    menu.throwError("Ungültiger Repair-Typ.");
                    return;
            }

            RepairJob serviceJob = new RepairJob(jobId, type, jobName, "PENDING");

            // Add the service job to the auto object in memory
            selectedCar.addJob(serviceJob);
            selectedCar.setCarStatus(CarStatus.IN_SERVICE);

            // Save the service job to the database
            repairJobController.addRepairJob(serviceJob, selectedCar.getId());

            // Update the auto in the database
            carController.updateCar(selectedCar);

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
                String carId = serviceJobController.getCarIdForServiceJob(serviceId);
                Car car = carController.getCarById(carId);
                
                if (car != null) {
                    menu.sendMessage("Auto: " + car.getBrand() + " " + car.getModel() + " (Fahrzeug-Status: " + car.getCarStatus() + ")");
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
                    serviceJobController.updateServiceJob(serviceJob, carId);
                    
                    // Update the car status if needed
                    boolean allServicesCompleted = true;
                    List<ServiceJob> carServices = serviceJobController.getServiceJobsByCarId(carId);
                    
                    for (ServiceJob job : carServices) {
                        if (!job.getStatus().equals("COMPLETED") && !job.getStatus().equals("CANCELLED")) {
                            allServicesCompleted = false;
                            break;
                        }
                    }
                    
                    if (allServicesCompleted && !carServices.isEmpty()) {
                        car.setCarStatus(CarStatus.AVAILABLE);
                        carController.updateCar(car);
                        menu.sendMessage("Alle Services für dieses Auto sind abgeschlossen oder storniert.");
                        menu.sendMessage("Fahrzeug-Status wurde auf AVAILABLE gesetzt.");
                    } else {
                        // Make sure the auto is marked as in service
                        if (car.getCarStatus() != CarStatus.IN_SERVICE) {
                            car.setCarStatus(CarStatus.IN_SERVICE);
                            carController.updateCar(car);
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

    private void updateRepairServiceStatus() {
        try {
            int serviceId = menu.getInt("Service-ID eingeben:");

            Optional<RepairJob> serviceJobOpt = repairJobController.getRepairJobById(serviceId);

            if (serviceJobOpt.isPresent()) {
                RepairJob serviceJob = serviceJobOpt.get();
                String carId = repairJobController.getCarIdForRepairJob(serviceId);
                Car car = carController.getCarById(carId);

                if (car != null) {
                    menu.sendMessage("Auto: " + car.getBrand() + " " + car.getModel() + " (Fahrzeug-Status: " + car.getCarStatus() + ")");
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
                    repairJobController.updateRepairJob(serviceJob, carId);

                    // Update the car status if needed
                    boolean allServicesCompleted = true;
                    List<RepairJob> carServices = repairJobController.getRepairJobsByCarId(carId);

                    for (RepairJob job : carServices) {
                        if (!job.getStatus().equals("COMPLETED") && !job.getStatus().equals("CANCELLED")) {
                            allServicesCompleted = false;
                            break;
                        }
                    }

                    if (allServicesCompleted && !carServices.isEmpty()) {
                        car.setCarStatus(CarStatus.AVAILABLE);
                        carController.updateCar(car);
                        menu.sendMessage("Alle Services für dieses Auto sind abgeschlossen oder storniert.");
                        menu.sendMessage("Fahrzeug-Status wurde auf AVAILABLE gesetzt.");
                    } else {
                        // Make sure the auto is marked as in service
                        if (car.getCarStatus() != CarStatus.IN_SERVICE) {
                            car.setCarStatus(CarStatus.IN_SERVICE);
                            carController.updateCar(car);
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
                String carId = serviceJobController.getCarIdForServiceJob(serviceId);
                Car car = carController.getCarById(carId);
                
                if (car != null) {
                    menu.sendMessage("Service gefunden für Auto: " + car.getBrand() + " " + car.getModel());
                    menu.sendMessage("Fahrzeug-Status: " + car.getCarStatus());
                    menu.sendMessage("Service ID: " + serviceJob.getId());
                    menu.sendMessage("Typ: " + serviceJob.getType());
                    menu.sendMessage("Name: " + serviceJob.getName());
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

    private void findRepairServiceById() {
        try {
            int serviceId = menu.getInt("Service-ID eingeben:");

            Optional<RepairJob> serviceJobOpt = repairJobController.getRepairJobById(serviceId);

            if (serviceJobOpt.isPresent()) {
                RepairJob serviceJob = serviceJobOpt.get();
                String carId = repairJobController.getCarIdForRepairJob(serviceId);
                Car car = carController.getCarById(carId);

                if (car != null) {
                    menu.sendMessage("Service gefunden für Auto: " + car.getBrand() + " " + car.getModel());
                    menu.sendMessage("Fahrzeug-Status: " + car.getCarStatus());
                    menu.sendMessage("Service ID: " + serviceJob.getId());
                    menu.sendMessage("Typ: " + serviceJob.getType());
                    menu.sendMessage("Name: " + serviceJob.getName());
                    menu.sendMessage("Bearbeitungs-Status: " + serviceJob.getStatus());
                } else {
                    menu.throwError("Auto für diesen Service nicht gefunden.");
                }
            } else {
                menu.throwError("Reparatur mit ID " + serviceId + " nicht gefunden.");
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
            List<Car> cars = carController.getAllCars();
            
            if (cars.isEmpty()) {
                menu.sendMessage("Es sind keine Autos vorhanden.");
                return;
            }
            
            menu.sendMessage("Alle Services:");
            boolean foundServices = false;
            
            for (Car car : cars) {
                List<ServiceJob> serviceJobs = serviceJobController.getServiceJobsByCarId(car.getId());
                
                if (!serviceJobs.isEmpty()) {
                    menu.sendMessage("Auto: " + car.getBrand() + " " + car.getModel() + " (ID: " + car.getId() + ", Fahrzeug-Status: " + car.getCarStatus() + ")");
                    
                    for (ServiceJob serviceJob : serviceJobs) {
                        menu.sendMessage("  - Service ID: " + serviceJob.getId() +
                                       ", Typ: " + serviceJob.getType() + 
                                       ", Name: " + serviceJob.getName() +
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

    private void showAllRepairServices() {
        try {
            List<Car> cars = carController.getAllCars();

            if (cars.isEmpty()) {
                menu.sendMessage("Es sind keine Autos vorhanden.");
                return;
            }

            menu.sendMessage("Alle Services:");
            boolean foundServices = false;

            for (Car car : cars) {
                List<RepairJob> serviceJobs = repairJobController.getRepairJobsByCarId(car.getId());

                if (!serviceJobs.isEmpty()) {
                    menu.sendMessage("Auto: " + car.getBrand() + " " + car.getModel() + " (ID: " + car.getId() + ", Fahrzeug-Status: " + car.getCarStatus() + ")");

                    for (RepairJob serviceJob : serviceJobs) {
                        menu.sendMessage("  - Service ID: " + serviceJob.getId() +
                                ", Typ: " + serviceJob.getType() +
                                ", Name: " + serviceJob.getName() +
                                ", Bearbeitungs-Status: " + serviceJob.getStatus());
                        foundServices = true;
                    }
                    menu.sendMessage("------------------------");
                }
            }

            if (!foundServices) {
                menu.sendMessage("Es wurden keine Reparaturen gefunden.");
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
            List<Car> cars = carController.getAllCars();
            
            if (cars.isEmpty()) {
                menu.sendMessage("Es sind keine Autos vorhanden. Bitte fügen Sie zuerst ein Auto hinzu.");
                return;
            }
            
            for (int i = 0; i < cars.size(); i++) {
                Car car = cars.get(i);
                menu.sendMessage((i + 1) + ". " + car.getBrand() + " " + car.getModel() + " (ID: " + car.getId() + ", Fahrzeug-Status: " + car.getCarStatus() + ")");
            }
            
            int carIndex = menu.getInt("Auto auswählen (Nummer eingeben):") - 1;
            if (carIndex < 0 || carIndex >= cars.size()) {
                menu.throwError("Ungültige Auswahl.");
                return;
            }
            
            Car selectedCar = cars.get(carIndex);
            
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
            selectedCar.addJob(serviceJob);
            selectedCar.setCarStatus(CarStatus.IN_SERVICE);
            
            // Save the service job to the database
            serviceJobController.addServiceJob(serviceJob, selectedCar.getId());
            
            // Update the auto in the database
            carController.updateCar(selectedCar);
            
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

    private void runCarMenuLogic() {
        menu.showCarMenu();
        int option = menu.getOption();
        switch (option) {
            case 1:
                showAllCars();
                break;
            case 2:
                findCarById();
                break;
            case 3:
                addNewCar();
                break;
            case 4:
                deleteCar();
                break;
            case 5:
                updateCarStatus();
                break;
            case 9:
                // Return to main menu
                break;
            default:
                menu.throwError("Invalid option.");
                break;
        }
    }

    private void deleteCar() {
        try {
            String carId = menu.getString("Auto-ID eingeben:");
            carController.deleteCarById(carId);
            menu.sendMessage("Auto wurde erfolgreich gelöscht.");
        } catch (Exception e) {
            menu.throwError("Fehler beim Löschen des Autos: " + e.getMessage());
        }
    }

    private void findCarById() {
        try {
            String carId = menu.getString("Auto-ID eingeben:");
            Car car = carController.getCarById(carId);
            if (car != null) {
                menu.sendMessage("Auto gefunden:");
                menu.sendMessage("ID: " + car.getId());
                menu.sendMessage("Marke: " + car.getBrand());
                menu.sendMessage("Modell: " + car.getModel());
                menu.sendMessage("Kennzeichen: " + car.getLicensePlate());
                menu.sendMessage("Fahrzeug-Status: " + car.getCarStatus());
                
                // Display associated services if any
                List<ServiceJob> services = serviceJobController.getServiceJobsByCarId(car.getId());
                if (!services.isEmpty()) {
                    menu.sendMessage("\nServices für dieses Auto:");
                    for (ServiceJob service : services) {
                        menu.sendMessage("  - ID: " + service.getId() +
                                       ", Typ: " + service.getType() + 
                                       ", Name: " + service.getName() +
                                       ", Bearbeitungs-Status: " + service.getStatus());
                    }
                } else {
                    menu.sendMessage("\nKeine Services für dieses Auto vorhanden.");
                }
            } else {
                menu.sendMessage("Auto mit ID " + carId + " nicht gefunden.");
            }
        } catch (Exception e) {
            menu.throwError("Fehler beim Suchen des Autos: " + e.getMessage());
            e.printStackTrace(); // Print the stack trace for debugging
        }
    }

    private void showAllCars() {
        try {
            List<Car> cars = carController.getAllCars();
            if (cars.isEmpty()) {
                menu.sendMessage("Es sind keine Autos vorhanden.");
            } else {
                menu.sendMessage("Alle Autos:");
                for (Car car : cars) {
                    menu.sendMessage("ID: " + car.getId() +
                                   ", Marke: " + car.getBrand() +
                                   ", Modell: " + car.getModel() +
                                   ", Kennzeichen: " + car.getLicensePlate() +
                                   ", Fahrzeug-Status: " + car.getCarStatus());
                    
                    // Display associated services if any
                    List<ServiceJob> services = serviceJobController.getServiceJobsByCarId(car.getId());
                    if (!services.isEmpty()) {
                        menu.sendMessage("  Services:");
                        for (ServiceJob service : services) {
                            menu.sendMessage("    - ID: " + service.getId() +
                                           ", Typ: " + service.getType() + 
                                           ", Name: " + service.getName() +
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

    private void addNewCar() {
        try {
            // First, get the customer to add the car to
            menu.sendMessage("Für welchen Kunden soll das Auto hinzugefügt werden?");
            List<Customer> customer = customerController.getAllCustomers();
            
            if (customer.isEmpty()) {
                menu.sendMessage("Es sind keine Kunden vorhanden. Bitte fügen Sie zuerst einen Kunden hinzu.");
                return;
            }
            
            for (int i = 0; i < customer.size(); i++) {
                menu.sendMessage((i + 1) + ". " + customer.get(i).getName());
            }
            
            int customerIndex = menu.getOption() - 1;
            if (customerIndex < 0 || customerIndex >= customer.size()) {
                menu.throwError("Ungültige Auswahl.");
                return;
            }
            
            Customer selectedCustomer = customer.get(customerIndex);
            
            // Now get the car details
            String carInfo = menu.getCarInfo();
            String[] parts = carInfo.split(",");
            
            String id = parts[0];
            String brand = parts[1];
            String model = parts[2];
            String licensePlate = parts[3];
            // carStatus 1 = AVAILABLE
            Car car = new Car(id, model, brand, licensePlate,"1");
            
            // Add the car to the customer and update in the database
            selectedCustomer.addCar(car);
            customerController.updateCustomer(selectedCustomer);
            
            // Also add the car to the auto repository
            carController.addCar(car);
            
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
            customerController.deleteCustomer(kundeId);
            menu.sendMessage("Kunde wurde erfolgreich gelöscht.");
        } catch (SQLException e) {
            menu.throwError("Datenbankfehler: " + e.getMessage());
        }
    }

    private void updateKunde() {
        try {
            int kundeId = menu.getInt("Kunden-ID eingeben:");
            Optional<Customer> kundeOpt = customerController.getCustomerById(kundeId);
            
            if (kundeOpt.isPresent()) {
                Customer customer = kundeOpt.get();
                menu.sendMessage("Aktuelle Daten:");
                menu.showKunde(customer);
                
                // Get updated information
                Customer updatedCustomer = menu.getKundeInfo();
                updatedCustomer.setId(kundeId); // Keep the same ID
                
                // Keep the same cars
                for (Car car : customer.getCars()) {
                    updatedCustomer.addCar(car);
                }
                
                customerController.updateCustomer(updatedCustomer);
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
            Customer customer = menu.getKundeInfo();
            customerController.createCustomer(customer);
            menu.sendMessage("Kunde wurde erfolgreich hinzugefügt.");
        } catch (SQLException e) {
            menu.throwError("Datenbankfehler: " + e.getMessage());
        }
    }

    private void findKundeById() {
        try {
            int kundeId = menu.getInt("Kunden-ID eingeben:");
            Optional<Customer> kundeOpt = customerController.getCustomerById(kundeId);
            
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
            List<Customer> kunden = customerController.getAllCustomers();
            if (kunden.isEmpty()) {
                menu.sendMessage("Es sind keine Kunden vorhanden.");
            } else {
                menu.sendMessage("Alle Kunden:");
                for (Customer customer : kunden) {
                    menu.showKunde(customer);
                    menu.sendMessage("------------------------");
                }
            }
        } catch (SQLException e) {
            menu.throwError("Datenbankfehler: " + e.getMessage());
        }
    }

    private void checkForWerkstattInformation() {
        if(!main.getAppConfig().getWorkshopInformationController().ifInformationExists()) {
            WorkshopInformation workshopInformation = menu.getWerkstattInformation();
            main.getAppConfig().getWorkshopInformationController().save(workshopInformation);
            menu.sendMessage("Werkstatt-Information wurde gespeichert!");
        }
    }

    private void updateCarStatus() {
        try {
            String carId = menu.getString("Auto-ID eingeben:");
            Car car = carController.getCarById(carId);
            
            if (car == null) {
                menu.sendMessage("Auto mit ID " + carId + " nicht gefunden.");
                return;
            }
            
            menu.sendMessage("Auto gefunden: " + car.getBrand() + " " + car.getModel());
            menu.sendMessage("Aktueller Fahrzeug-Status: " + car.getCarStatus());
            
            // Check if the auto has any active services
            List<ServiceJob> serviceJobs = serviceJobController.getServiceJobsByCarId(carId);
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
            CarStatus newStatus;
            
            switch (statusChoice) {
                case 1: newStatus = CarStatus.AVAILABLE; break;
                case 2: newStatus = CarStatus.IN_SERVICE; break;
                case 3: newStatus = CarStatus.SOLD; break;
                default:
                    menu.throwError("Ungültige Auswahl.");
                    return;
            }
            
            car.setCarStatus(newStatus);
            carController.updateCar(car);
            
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
