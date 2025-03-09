package htw.prog3.KTM.util;

import htw.prog3.KTM.config.AppConfig;
import htw.prog3.KTM.controller.KundeController;
import htw.prog3.KTM.model.kunde.Kunde;
import htw.prog3.KTM.service.MenuService;


import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.LogManager;


public class main {

    private static AppConfig appConfig;

    public static void main(String[] args) {
        System.setProperty("org.jooq.no-logo", "true");
        // Disable jOOQ logging
        LogManager.getLogManager().reset();
        String userName = "";
        String password = "";
        String url = "jdbc:sqlite:werkstatt.db";

        try {
            // DI wird durch die AppConfig-Klasse gehandhabt
            appConfig = new AppConfig();

            // Register a shutdown hook to close the database connection
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                if (appConfig != null) {
                    appConfig.getDatabaseManager().closeConnection();
                }
            }));

            MenuService.getInstance().run();
        } finally {
            // Close the database connection when the application exits
            if (appConfig != null) {
                appConfig.getDatabaseManager().closeConnection();
            }
        }
    }

    public static AppConfig getAppConfig() {
        return appConfig;
    }
}

