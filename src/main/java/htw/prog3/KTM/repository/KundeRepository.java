package htw.prog3.KTM.repository;

import htw.prog3.KTM.generated.tables.records.KundeRecord;
import htw.prog3.KTM.model.Kunde.Kunde;
import htw.prog3.KTM.database.DatabaseManager;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static htw.prog3.KTM.generated.Tables.KUNDE;


public class KundeRepository {

    private final DatabaseManager databaseManager;

    public KundeRepository(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    // Holen Sie sich eine Verbindung zur SQLite-Datenbank
    private Connection getConnection() throws SQLException {
        return databaseManager.getConnection();
    }

    // Alle Kunden abrufen
    public List<Kunde> findAll() {
        try (Connection conn = getConnection()) {
            DSLContext create = DSL.using(conn);
            Result<Record> result = create.select().from(KUNDE).fetch();

            return result.map(r -> {
                Integer id = r.getValue(KUNDE.ID);
                String name = r.getValue(KUNDE.NAME);
                String address = r.getValue(KUNDE.ADDRESS);
                String phone = r.getValue(KUNDE.PHONE);
                return new Kunde(id, name, address, phone);
            });
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Abrufen der Kunden", e);
        }
    }

    // Einen Kunden nach ID abrufen
    public Optional<Kunde> findById(int id) {
        try (Connection conn = getConnection()) {
            DSLContext create = DSL.using(conn);
            KundeRecord kundeRecord = create.selectFrom(KUNDE).where(KUNDE.ID.eq(id)).fetchOne();

            return Optional.ofNullable(kundeRecord)
                    .map(record -> new Kunde(record.getId(), record.getName(), record.getAddress(), record.getPhone()));
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Abrufen des Kunden", e);
        }
    }

    // Einen neuen Kunden erstellen
    public void save(Kunde kunde) {
        try (Connection conn = getConnection()) {
            DSLContext create = DSL.using(conn);
            KundeRecord kundeRecord = create.newRecord(KUNDE, kunde);
            create.executeInsert(kundeRecord);
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Speichern des Kunden", e);
        }
    }

    // Einen Kunden aktualisieren
    public void update(Kunde kunde) {
        try (Connection conn = getConnection()) {
            DSLContext create = DSL.using(conn);
            KundeRecord kundeRecord = create.newRecord(KUNDE, kunde);
            create.executeUpdate(kundeRecord);
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Aktualisieren des Kunden", e);
        }
    }

    // Einen Kunden löschen
    public void delete(int id) {
        try (Connection conn = getConnection()) {
            DSLContext create = DSL.using(conn);
            int deletedCount = create.deleteFrom(KUNDE)
                    .where(KUNDE.ID.eq(id))
                    .execute();
            if (deletedCount == 0) {
                throw new RuntimeException("Kunde mit ID " + id + " nicht gefunden.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Löschen des Kunden", e);
        }
    }
}
