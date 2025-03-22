/*
 * This file is generated by jOOQ.
 */
package htw.prog3.KTM.generated;


import htw.prog3.KTM.generated.tables.Car;
import htw.prog3.KTM.generated.tables.Customer;
import htw.prog3.KTM.generated.tables.Konfigurationstabelle;
import htw.prog3.KTM.generated.tables.Repairjob;
import htw.prog3.KTM.generated.tables.Servicejob;
import htw.prog3.KTM.generated.tables.records.CarRecord;
import htw.prog3.KTM.generated.tables.records.CustomerRecord;
import htw.prog3.KTM.generated.tables.records.KonfigurationstabelleRecord;
import htw.prog3.KTM.generated.tables.records.RepairjobRecord;
import htw.prog3.KTM.generated.tables.records.ServicejobRecord;

import org.jooq.ForeignKey;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables in the
 * default schema.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<CarRecord> CAR__PK_CAR = Internal.createUniqueKey(Car.CAR, DSL.name("pk_Car"), new TableField[] { Car.CAR.ID }, true);
    public static final UniqueKey<CustomerRecord> CUSTOMER__PK_CUSTOMER = Internal.createUniqueKey(Customer.CUSTOMER, DSL.name("pk_Customer"), new TableField[] { Customer.CUSTOMER.ID }, true);
    public static final UniqueKey<KonfigurationstabelleRecord> KONFIGURATIONSTABELLE__PK_KONFIGURATIONSTABELLE = Internal.createUniqueKey(Konfigurationstabelle.KONFIGURATIONSTABELLE, DSL.name("pk_Konfigurationstabelle"), new TableField[] { Konfigurationstabelle.KONFIGURATIONSTABELLE.KEY }, true);
    public static final UniqueKey<RepairjobRecord> REPAIRJOB__PK_REPAIRJOB = Internal.createUniqueKey(Repairjob.REPAIRJOB, DSL.name("pk_RepairJob"), new TableField[] { Repairjob.REPAIRJOB.ID }, true);
    public static final UniqueKey<ServicejobRecord> SERVICEJOB__PK_SERVICEJOB = Internal.createUniqueKey(Servicejob.SERVICEJOB, DSL.name("pk_ServiceJob"), new TableField[] { Servicejob.SERVICEJOB.ID }, true);

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<CarRecord, CustomerRecord> CAR__FK_CAR_PK_CUSTOMER = Internal.createForeignKey(Car.CAR, DSL.name("fk_Car_pk_Customer"), new TableField[] { Car.CAR.CUSTOMER_ID }, Keys.CUSTOMER__PK_CUSTOMER, new TableField[] { Customer.CUSTOMER.ID }, true);
}
