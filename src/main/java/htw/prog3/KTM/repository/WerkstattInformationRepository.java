package htw.prog3.KTM.repository;

import htw.prog3.KTM.database.DatabaseManager;
import htw.prog3.KTM.model.werkstattInformation.WerkstattInformation;
import htw.prog3.KTM.model.werkstattInformation.WerkstattInformationStaticConfiguration;
import org.jooq.DSLContext;

import java.util.Optional;

import static htw.prog3.KTM.generated.Tables.KONFIGURATIONSTABELLE;

public class WerkstattInformationRepository {

    private final DatabaseManager databaseManager;
    private WerkstattInformation werkstattInformation;
    public WerkstattInformationRepository(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public void saveName(String name) {
        DSLContext dslContext = databaseManager.getDSLContext();
        dslContext.insertInto(KONFIGURATIONSTABELLE)
                .columns(KONFIGURATIONSTABELLE.KEY, KONFIGURATIONSTABELLE.VALUE)
                .values(WerkstattInformationStaticConfiguration.NAME, name)
                .onDuplicateKeyUpdate()
                .set(KONFIGURATIONSTABELLE.VALUE, name)
                .execute();
    }

    public void saveLocation(String location) {
        DSLContext dslContext = databaseManager.getDSLContext();
        dslContext.insertInto(KONFIGURATIONSTABELLE)
                .columns(KONFIGURATIONSTABELLE.KEY, KONFIGURATIONSTABELLE.VALUE)
                .values(WerkstattInformationStaticConfiguration.LOCATION, location)
                .onDuplicateKeyUpdate()
                .set(KONFIGURATIONSTABELLE.VALUE, location)
                .execute();
    }

    public void savePhone(int phone) {
        DSLContext dslContext = databaseManager.getDSLContext();
        dslContext.insertInto(KONFIGURATIONSTABELLE)
                .columns(KONFIGURATIONSTABELLE.KEY, KONFIGURATIONSTABELLE.VALUE)
                .values(WerkstattInformationStaticConfiguration.PHONE, ""+phone)
                .onDuplicateKeyUpdate()
                .set(KONFIGURATIONSTABELLE.VALUE, ""+phone)
                .execute();
    }

    public void saveEmail(String email) {
        DSLContext dslContext = databaseManager.getDSLContext();
        dslContext.insertInto(KONFIGURATIONSTABELLE)
                .columns(KONFIGURATIONSTABELLE.KEY, KONFIGURATIONSTABELLE.VALUE)
                .values(WerkstattInformationStaticConfiguration.EMAIL, email)
                .onDuplicateKeyUpdate()
                .set(KONFIGURATIONSTABELLE.VALUE, email)
                .execute();
    }

    public void saveWebsite(String website) {
        DSLContext dslContext = databaseManager.getDSLContext();
        dslContext.insertInto(KONFIGURATIONSTABELLE)
                .columns(KONFIGURATIONSTABELLE.KEY, KONFIGURATIONSTABELLE.VALUE)
                .values(WerkstattInformationStaticConfiguration.WEBSITE, website)
                .onDuplicateKeyUpdate()
                .set(KONFIGURATIONSTABELLE.VALUE, website)
                .execute();
    }

    public void saveVAT(String vat) {
        DSLContext dslContext = databaseManager.getDSLContext();
        dslContext.insertInto(KONFIGURATIONSTABELLE)
                .columns(KONFIGURATIONSTABELLE.KEY, KONFIGURATIONSTABELLE.VALUE)
                .values(WerkstattInformationStaticConfiguration.VAT, vat)
                .onDuplicateKeyUpdate()
                .set(KONFIGURATIONSTABELLE.VALUE, vat)
                .execute();
    }

    public void saveBusregnumber(String busregnumber) {
        DSLContext dslContext = databaseManager.getDSLContext();
        dslContext.insertInto(KONFIGURATIONSTABELLE)
                .columns(KONFIGURATIONSTABELLE.KEY, KONFIGURATIONSTABELLE.VALUE)
                .values(WerkstattInformationStaticConfiguration.BUSINESSREGNUMBER, busregnumber)
                .onDuplicateKeyUpdate()
                .set(KONFIGURATIONSTABELLE.VALUE, busregnumber)
                .execute();
    }

    public void saveIban(String iban) {
        DSLContext dslContext = databaseManager.getDSLContext();
        dslContext.insertInto(KONFIGURATIONSTABELLE)
                .columns(KONFIGURATIONSTABELLE.KEY, KONFIGURATIONSTABELLE.VALUE)
                .values(WerkstattInformationStaticConfiguration.IBAN, iban)
                .onDuplicateKeyUpdate()
                .set(KONFIGURATIONSTABELLE.VALUE, iban)
                .execute();
    }

    public void save(WerkstattInformation werkstattInformation) {
        savePhone(werkstattInformation.phone());
        saveBusregnumber(werkstattInformation.businessRegNumber());
        saveEmail(werkstattInformation.email());
        saveIban(werkstattInformation.iban());
        saveLocation(werkstattInformation.location());
        saveName(werkstattInformation.name());
        saveVAT(werkstattInformation.vat());
        saveWebsite(werkstattInformation.website());
    }

    public WerkstattInformation getWerkstattInformation() {
        if(werkstattInformation == null) {
            DSLContext dslContext = databaseManager.getDSLContext();
            Optional<String> name = dslContext.select(KONFIGURATIONSTABELLE.VALUE)
                    .from(KONFIGURATIONSTABELLE)
                    .where(KONFIGURATIONSTABELLE.KEY.eq(WerkstattInformationStaticConfiguration.NAME))
                    .fetchOptional(KONFIGURATIONSTABELLE.VALUE);

            Optional<String> location = dslContext.select(KONFIGURATIONSTABELLE.VALUE)
                    .from(KONFIGURATIONSTABELLE)
                    .where(KONFIGURATIONSTABELLE.KEY.eq(WerkstattInformationStaticConfiguration.LOCATION))
                    .fetchOptional(KONFIGURATIONSTABELLE.VALUE);

            Optional<String> phone = dslContext.select(KONFIGURATIONSTABELLE.VALUE)
                    .from(KONFIGURATIONSTABELLE)
                    .where(KONFIGURATIONSTABELLE.KEY.eq(WerkstattInformationStaticConfiguration.PHONE))
                    .fetchOptional(KONFIGURATIONSTABELLE.VALUE);

            Optional<String> email = dslContext.select(KONFIGURATIONSTABELLE.VALUE)
                    .from(KONFIGURATIONSTABELLE)
                    .where(KONFIGURATIONSTABELLE.KEY.eq(WerkstattInformationStaticConfiguration.EMAIL))
                    .fetchOptional(KONFIGURATIONSTABELLE.VALUE);

            Optional<String> website = dslContext.select(KONFIGURATIONSTABELLE.VALUE)
                    .from(KONFIGURATIONSTABELLE)
                    .where(KONFIGURATIONSTABELLE.KEY.eq(WerkstattInformationStaticConfiguration.WEBSITE))
                    .fetchOptional(KONFIGURATIONSTABELLE.VALUE);

            Optional<String> vat = dslContext.select(KONFIGURATIONSTABELLE.VALUE)
                    .from(KONFIGURATIONSTABELLE)
                    .where(KONFIGURATIONSTABELLE.KEY.eq(WerkstattInformationStaticConfiguration.VAT))
                    .fetchOptional(KONFIGURATIONSTABELLE.VALUE);

            Optional<String> busregnumber = dslContext.select(KONFIGURATIONSTABELLE.VALUE)
                    .from(KONFIGURATIONSTABELLE)
                    .where(KONFIGURATIONSTABELLE.KEY.eq(WerkstattInformationStaticConfiguration.BUSINESSREGNUMBER))
                    .fetchOptional(KONFIGURATIONSTABELLE.VALUE);

            Optional<String> iban = dslContext.select(KONFIGURATIONSTABELLE.VALUE)
                    .from(KONFIGURATIONSTABELLE)
                    .where(KONFIGURATIONSTABELLE.KEY.eq(WerkstattInformationStaticConfiguration.IBAN))
                    .fetchOptional(KONFIGURATIONSTABELLE.VALUE);

            werkstattInformation = new WerkstattInformation(name.orElse(""), location.orElse(""), Integer.valueOf(phone.orElse("0")), email.orElse(""),
                    website.orElse(""), vat.orElse(""), busregnumber.orElse(""), iban.orElse(""));
        }
        return werkstattInformation;
    }


}
