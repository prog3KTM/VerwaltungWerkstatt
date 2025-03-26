package htw.prog3.KTM.util;

import htw.prog3.KTM.config.AppConfig;
import htw.prog3.KTM.service.MenuService;


import java.util.logging.LogManager;


public class main {

    private static AppConfig appConfig;

    public static void main(String[] args) {
        System.setProperty("org.jooq.no-logo", "true");
        LogManager.getLogManager().reset();
        String userName = "";
        String password = "";
        String url = "jdbc:sqlite:werkstatt.db";

        try {
            appConfig = new AppConfig();

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                if (appConfig != null) {
                    appConfig.getDatabaseManager().closeConnection();
                }
            }));

            MenuService.getInstance().run();
        } finally {
            if (appConfig != null) {
                appConfig.getDatabaseManager().closeConnection();
            }
        }
    }

    public static AppConfig getAppConfig() {
        return appConfig;
    }
}

