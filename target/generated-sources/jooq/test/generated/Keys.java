/*
 * This file is generated by jOOQ.
 */
package test.generated;


import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;

import test.generated.tables.Kunde;
import test.generated.tables.records.KundeRecord;


/**
 * A class modelling foreign key relationships and constraints of tables in the
 * default schema.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<KundeRecord> KUNDE__PK_KUNDE = Internal.createUniqueKey(Kunde.KUNDE, DSL.name("pk_Kunde"), new TableField[] { Kunde.KUNDE.ID }, true);
}
