/*
 * This file is generated by jOOQ.
 */
package com.arjvik.arjmart.orderqueueconsumer.schema.tables;


import com.arjvik.arjmart.orderqueueconsumer.schema.Arjmart;
import com.arjvik.arjmart.orderqueueconsumer.schema.Indexes;
import com.arjvik.arjmart.orderqueueconsumer.schema.Keys;
import com.arjvik.arjmart.orderqueueconsumer.schema.tables.records.ItempriceRecord;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.2"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Itemprice extends TableImpl<ItempriceRecord> {

    private static final long serialVersionUID = -1899487597;

    /**
     * The reference instance of <code>arjmart.ItemPrice</code>
     */
    public static final Itemprice ITEMPRICE = new Itemprice();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ItempriceRecord> getRecordType() {
        return ItempriceRecord.class;
    }

    /**
     * The column <code>arjmart.ItemPrice.SKU</code>.
     */
    public final TableField<ItempriceRecord, Integer> SKU = createField("SKU", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>arjmart.ItemPrice.ItemAttributeID</code>.
     */
    public final TableField<ItempriceRecord, Integer> ITEMATTRIBUTEID = createField("ItemAttributeID", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>arjmart.ItemPrice.Price</code>.
     */
    public final TableField<ItempriceRecord, Double> PRICE = createField("Price", org.jooq.impl.SQLDataType.DOUBLE.nullable(false), this, "");

    /**
     * Create a <code>arjmart.ItemPrice</code> table reference
     */
    public Itemprice() {
        this(DSL.name("ItemPrice"), null);
    }

    /**
     * Create an aliased <code>arjmart.ItemPrice</code> table reference
     */
    public Itemprice(String alias) {
        this(DSL.name(alias), ITEMPRICE);
    }

    /**
     * Create an aliased <code>arjmart.ItemPrice</code> table reference
     */
    public Itemprice(Name alias) {
        this(alias, ITEMPRICE);
    }

    private Itemprice(Name alias, Table<ItempriceRecord> aliased) {
        this(alias, aliased, null);
    }

    private Itemprice(Name alias, Table<ItempriceRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> Itemprice(Table<O> child, ForeignKey<O, ItempriceRecord> key) {
        super(child, key, ITEMPRICE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Arjmart.ARJMART;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.ITEMPRICE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ItempriceRecord> getPrimaryKey() {
        return Keys.KEY_ITEMPRICE_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ItempriceRecord>> getKeys() {
        return Arrays.<UniqueKey<ItempriceRecord>>asList(Keys.KEY_ITEMPRICE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<ItempriceRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<ItempriceRecord, ?>>asList(Keys.ITEMPRICE_SKU_ITEMATTRIBUTEID);
    }

    public Itemattributemaster itemattributemaster() {
        return new Itemattributemaster(this, Keys.ITEMPRICE_SKU_ITEMATTRIBUTEID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Itemprice as(String alias) {
        return new Itemprice(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Itemprice as(Name alias) {
        return new Itemprice(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Itemprice rename(String name) {
        return new Itemprice(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Itemprice rename(Name name) {
        return new Itemprice(name, null);
    }
}
