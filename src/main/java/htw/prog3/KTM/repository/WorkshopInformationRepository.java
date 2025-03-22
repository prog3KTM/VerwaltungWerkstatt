package htw.prog3.KTM.repository;

import htw.prog3.KTM.database.DatabaseManager;
import htw.prog3.KTM.model.workshopinformation.WorkshopInformation;
import htw.prog3.KTM.model.workshopinformation.WorkshopInformationStaticConfiguration;
import org.jooq.DSLContext;

import java.util.Optional;

import static htw.prog3.KTM.generated.Tables.KONFIGURATIONSTABELLE;

public class WorkshopInformationRepository {

    private final DatabaseManager databaseManager;
    private WorkshopInformation workshopInformation;
    public WorkshopInformationRepository(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public void saveName(String name) {
        DSLContext dslContext = databaseManager.getDSLContext();
        dslContext.insertInto(KONFIGURATIONSTABELLE)
                .columns(KONFIGURATIONSTABELLE.KEY, KONFIGURATIONSTABELLE.VALUE)
                .values(WorkshopInformationStaticConfiguration.NAME, name)
                .onDuplicateKeyUpdate()
                .set(KONFIGURATIONSTABELLE.VALUE, name)
                .execute();
    }

    public void saveLocation(String location) {
        DSLContext dslContext = databaseManager.getDSLContext();
        dslContext.insertInto(KONFIGURATIONSTABELLE)
                .columns(KONFIGURATIONSTABELLE.KEY, KONFIGURATIONSTABELLE.VALUE)
                .values(WorkshopInformationStaticConfiguration.LOCATION, location)
                .onDuplicateKeyUpdate()
                .set(KONFIGURATIONSTABELLE.VALUE, location)
                .execute();
    }

    public void savePhone(int phone) {
        DSLContext dslContext = databaseManager.getDSLContext();
        dslContext.insertInto(KONFIGURATIONSTABELLE)
                .columns(KONFIGURATIONSTABELLE.KEY, KONFIGURATIONSTABELLE.VALUE)
                .values(WorkshopInformationStaticConfiguration.PHONE, ""+phone)
                .onDuplicateKeyUpdate()
                .set(KONFIGURATIONSTABELLE.VALUE, ""+phone)
                .execute();
    }

    public void saveEmail(String email) {
        DSLContext dslContext = databaseManager.getDSLContext();
        dslContext.insertInto(KONFIGURATIONSTABELLE)
                .columns(KONFIGURATIONSTABELLE.KEY, KONFIGURATIONSTABELLE.VALUE)
                .values(WorkshopInformationStaticConfiguration.EMAIL, email)
                .onDuplicateKeyUpdate()
                .set(KONFIGURATIONSTABELLE.VALUE, email)
                .execute();
    }

    public void saveWebsite(String website) {
        DSLContext dslContext = databaseManager.getDSLContext();
        dslContext.insertInto(KONFIGURATIONSTABELLE)
                .columns(KONFIGURATIONSTABELLE.KEY, KONFIGURATIONSTABELLE.VALUE)
                .values(WorkshopInformationStaticConfiguration.WEBSITE, website)
                .onDuplicateKeyUpdate()
                .set(KONFIGURATIONSTABELLE.VALUE, website)
                .execute();
    }

    public void saveVAT(String vat) {
        DSLContext dslContext = databaseManager.getDSLContext();
        dslContext.insertInto(KONFIGURATIONSTABELLE)
                .columns(KONFIGURATIONSTABELLE.KEY, KONFIGURATIONSTABELLE.VALUE)
                .values(WorkshopInformationStaticConfiguration.VAT, vat)
                .onDuplicateKeyUpdate()
                .set(KONFIGURATIONSTABELLE.VALUE, vat)
                .execute();
    }

    public void saveBusregnumber(String busregnumber) {
        DSLContext dslContext = databaseManager.getDSLContext();
        dslContext.insertInto(KONFIGURATIONSTABELLE)
                .columns(KONFIGURATIONSTABELLE.KEY, KONFIGURATIONSTABELLE.VALUE)
                .values(WorkshopInformationStaticConfiguration.BUSINESSREGNUMBER, busregnumber)
                .onDuplicateKeyUpdate()
                .set(KONFIGURATIONSTABELLE.VALUE, busregnumber)
                .execute();
    }

    public void saveIban(String iban) {
        DSLContext dslContext = databaseManager.getDSLContext();
        dslContext.insertInto(KONFIGURATIONSTABELLE)
                .columns(KONFIGURATIONSTABELLE.KEY, KONFIGURATIONSTABELLE.VALUE)
                .values(WorkshopInformationStaticConfiguration.IBAN, iban)
                .onDuplicateKeyUpdate()
                .set(KONFIGURATIONSTABELLE.VALUE, iban)
                .execute();
    }

    public void save(WorkshopInformation workshopInformation) {
        savePhone(workshopInformation.phone());
        saveBusregnumber(workshopInformation.businessRegNumber());
        saveEmail(workshopInformation.email());
        saveIban(workshopInformation.iban());
        saveLocation(workshopInformation.location());
        saveName(workshopInformation.name());
        saveVAT(workshopInformation.vat());
        saveWebsite(workshopInformation.website());
    }

    public WorkshopInformation getWerkstattInformation() {
        if(workshopInformation == null) {
            DSLContext dslContext = databaseManager.getDSLContext();
            Optional<String> name = dslContext.select(KONFIGURATIONSTABELLE.VALUE)
                    .from(KONFIGURATIONSTABELLE)
                    .where(KONFIGURATIONSTABELLE.KEY.eq(WorkshopInformationStaticConfiguration.NAME))
                    .fetchOptional(KONFIGURATIONSTABELLE.VALUE);

            Optional<String> location = dslContext.select(KONFIGURATIONSTABELLE.VALUE)
                    .from(KONFIGURATIONSTABELLE)
                    .where(KONFIGURATIONSTABELLE.KEY.eq(WorkshopInformationStaticConfiguration.LOCATION))
                    .fetchOptional(KONFIGURATIONSTABELLE.VALUE);

            Optional<String> phone = dslContext.select(KONFIGURATIONSTABELLE.VALUE)
                    .from(KONFIGURATIONSTABELLE)
                    .where(KONFIGURATIONSTABELLE.KEY.eq(WorkshopInformationStaticConfiguration.PHONE))
                    .fetchOptional(KONFIGURATIONSTABELLE.VALUE);

            Optional<String> email = dslContext.select(KONFIGURATIONSTABELLE.VALUE)
                    .from(KONFIGURATIONSTABELLE)
                    .where(KONFIGURATIONSTABELLE.KEY.eq(WorkshopInformationStaticConfiguration.EMAIL))
                    .fetchOptional(KONFIGURATIONSTABELLE.VALUE);

            Optional<String> website = dslContext.select(KONFIGURATIONSTABELLE.VALUE)
                    .from(KONFIGURATIONSTABELLE)
                    .where(KONFIGURATIONSTABELLE.KEY.eq(WorkshopInformationStaticConfiguration.WEBSITE))
                    .fetchOptional(KONFIGURATIONSTABELLE.VALUE);

            Optional<String> vat = dslContext.select(KONFIGURATIONSTABELLE.VALUE)
                    .from(KONFIGURATIONSTABELLE)
                    .where(KONFIGURATIONSTABELLE.KEY.eq(WorkshopInformationStaticConfiguration.VAT))
                    .fetchOptional(KONFIGURATIONSTABELLE.VALUE);

            Optional<String> busregnumber = dslContext.select(KONFIGURATIONSTABELLE.VALUE)
                    .from(KONFIGURATIONSTABELLE)
                    .where(KONFIGURATIONSTABELLE.KEY.eq(WorkshopInformationStaticConfiguration.BUSINESSREGNUMBER))
                    .fetchOptional(KONFIGURATIONSTABELLE.VALUE);

            Optional<String> iban = dslContext.select(KONFIGURATIONSTABELLE.VALUE)
                    .from(KONFIGURATIONSTABELLE)
                    .where(KONFIGURATIONSTABELLE.KEY.eq(WorkshopInformationStaticConfiguration.IBAN))
                    .fetchOptional(KONFIGURATIONSTABELLE.VALUE);

            workshopInformation = new WorkshopInformation(name.orElse(""), location.orElse(""), Integer.valueOf(phone.orElse("0")), email.orElse(""),
                    website.orElse(""), vat.orElse(""), busregnumber.orElse(""), iban.orElse(""));
        }
        return workshopInformation;
    }

    public void delete() {
        savePhone(0);
        saveBusregnumber("");
        saveEmail("");
        saveIban("");
        saveLocation("");
        saveName("");
        saveVAT("");
        saveWebsite("");
    }

    public boolean ifInformationExists() {
        DSLContext dslContext = databaseManager.getDSLContext();
        Optional<String> name = dslContext.select(KONFIGURATIONSTABELLE.VALUE)
                .from(KONFIGURATIONSTABELLE)
                .where(KONFIGURATIONSTABELLE.KEY.eq(WorkshopInformationStaticConfiguration.NAME))
                .fetchOptional(KONFIGURATIONSTABELLE.VALUE);
        if(!name.isPresent() || name.get().isEmpty()) return false;

        Optional<String> location = dslContext.select(KONFIGURATIONSTABELLE.VALUE)
                .from(KONFIGURATIONSTABELLE)
                .where(KONFIGURATIONSTABELLE.KEY.eq(WorkshopInformationStaticConfiguration.LOCATION))
                .fetchOptional(KONFIGURATIONSTABELLE.VALUE);
        if(!location.isPresent() || location.get().isEmpty()) return false;

        Optional<String> phone = dslContext.select(KONFIGURATIONSTABELLE.VALUE)
                .from(KONFIGURATIONSTABELLE)
                .where(KONFIGURATIONSTABELLE.KEY.eq(WorkshopInformationStaticConfiguration.PHONE))
                .fetchOptional(KONFIGURATIONSTABELLE.VALUE);
        if(!phone.isPresent() || phone.get().equals("0")) return false;

        Optional<String> email = dslContext.select(KONFIGURATIONSTABELLE.VALUE)
                .from(KONFIGURATIONSTABELLE)
                .where(KONFIGURATIONSTABELLE.KEY.eq(WorkshopInformationStaticConfiguration.EMAIL))
                .fetchOptional(KONFIGURATIONSTABELLE.VALUE);
        if(!email.isPresent() || email.get().isEmpty()) return false;

        Optional<String> website = dslContext.select(KONFIGURATIONSTABELLE.VALUE)
                .from(KONFIGURATIONSTABELLE)
                .where(KONFIGURATIONSTABELLE.KEY.eq(WorkshopInformationStaticConfiguration.WEBSITE))
                .fetchOptional(KONFIGURATIONSTABELLE.VALUE);
        if(!website.isPresent() || website.get().isEmpty()) return false;

        Optional<String> vat = dslContext.select(KONFIGURATIONSTABELLE.VALUE)
                .from(KONFIGURATIONSTABELLE)
                .where(KONFIGURATIONSTABELLE.KEY.eq(WorkshopInformationStaticConfiguration.VAT))
                .fetchOptional(KONFIGURATIONSTABELLE.VALUE);
        if(!vat.isPresent() || vat.get().isEmpty()) return false;

        Optional<String> busregnumber = dslContext.select(KONFIGURATIONSTABELLE.VALUE)
                .from(KONFIGURATIONSTABELLE)
                .where(KONFIGURATIONSTABELLE.KEY.eq(WorkshopInformationStaticConfiguration.BUSINESSREGNUMBER))
                .fetchOptional(KONFIGURATIONSTABELLE.VALUE);
        if(!busregnumber.isPresent() || busregnumber.get().isEmpty()) return false;

        Optional<String> iban = dslContext.select(KONFIGURATIONSTABELLE.VALUE)
                .from(KONFIGURATIONSTABELLE)
                .where(KONFIGURATIONSTABELLE.KEY.eq(WorkshopInformationStaticConfiguration.IBAN))
                .fetchOptional(KONFIGURATIONSTABELLE.VALUE);
        if(!iban.isPresent() || iban.get().isEmpty()) return false;
        return true;
    }
}
