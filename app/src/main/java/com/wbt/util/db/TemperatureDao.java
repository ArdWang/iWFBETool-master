package com.wbt.util.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.wbt.bean.Temperature;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "TEMPERATURE".
*/
public class TemperatureDao extends AbstractDao<Temperature, Long> {

    public static final String TABLENAME = "TEMPERATURE";

    /**
     * Properties of entity Temperature.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Tpid = new Property(0, Long.class, "tpid", true, "_id");
        public final static Property Tpdvid = new Property(1, String.class, "tpdvid", false, "TPDVID");
        public final static Property Tpname = new Property(2, String.class, "tpname", false, "TPNAME");
        public final static Property Tptemp = new Property(3, String.class, "tptemp", false, "TPTEMP");
        public final static Property Tpsymbol = new Property(4, String.class, "tpsymbol", false, "TPSYMBOL");
        public final static Property Tphum = new Property(5, String.class, "tphum", false, "TPHUM");
        public final static Property Tptime = new Property(6, Long.class, "tptime", false, "TPTIME");
    }


    public TemperatureDao(DaoConfig config) {
        super(config);
    }
    
    public TemperatureDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"TEMPERATURE\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: tpid
                "\"TPDVID\" TEXT," + // 1: tpdvid
                "\"TPNAME\" TEXT," + // 2: tpname
                "\"TPTEMP\" TEXT," + // 3: tptemp
                "\"TPSYMBOL\" TEXT," + // 4: tpsymbol
                "\"TPHUM\" TEXT," + // 5: tphum
                "\"TPTIME\" INTEGER);"); // 6: tptime
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"TEMPERATURE\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Temperature entity) {
        stmt.clearBindings();
 
        Long tpid = entity.getTpid();
        if (tpid != null) {
            stmt.bindLong(1, tpid);
        }
 
        String tpdvid = entity.getTpdvid();
        if (tpdvid != null) {
            stmt.bindString(2, tpdvid);
        }
 
        String tpname = entity.getTpname();
        if (tpname != null) {
            stmt.bindString(3, tpname);
        }
 
        String tptemp = entity.getTptemp();
        if (tptemp != null) {
            stmt.bindString(4, tptemp);
        }
 
        String tpsymbol = entity.getTpsymbol();
        if (tpsymbol != null) {
            stmt.bindString(5, tpsymbol);
        }
 
        String tphum = entity.getTphum();
        if (tphum != null) {
            stmt.bindString(6, tphum);
        }
 
        Long tptime = entity.getTptime();
        if (tptime != null) {
            stmt.bindLong(7, tptime);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Temperature entity) {
        stmt.clearBindings();
 
        Long tpid = entity.getTpid();
        if (tpid != null) {
            stmt.bindLong(1, tpid);
        }
 
        String tpdvid = entity.getTpdvid();
        if (tpdvid != null) {
            stmt.bindString(2, tpdvid);
        }
 
        String tpname = entity.getTpname();
        if (tpname != null) {
            stmt.bindString(3, tpname);
        }
 
        String tptemp = entity.getTptemp();
        if (tptemp != null) {
            stmt.bindString(4, tptemp);
        }
 
        String tpsymbol = entity.getTpsymbol();
        if (tpsymbol != null) {
            stmt.bindString(5, tpsymbol);
        }
 
        String tphum = entity.getTphum();
        if (tphum != null) {
            stmt.bindString(6, tphum);
        }
 
        Long tptime = entity.getTptime();
        if (tptime != null) {
            stmt.bindLong(7, tptime);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Temperature readEntity(Cursor cursor, int offset) {
        Temperature entity = new Temperature( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // tpid
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // tpdvid
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // tpname
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // tptemp
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // tpsymbol
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // tphum
            cursor.isNull(offset + 6) ? null : cursor.getLong(offset + 6) // tptime
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Temperature entity, int offset) {
        entity.setTpid(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setTpdvid(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setTpname(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setTptemp(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setTpsymbol(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setTphum(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setTptime(cursor.isNull(offset + 6) ? null : cursor.getLong(offset + 6));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Temperature entity, long rowId) {
        entity.setTpid(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Temperature entity) {
        if(entity != null) {
            return entity.getTpid();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Temperature entity) {
        return entity.getTpid() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
