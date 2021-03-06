/*
 * This file is generated by jOOQ.
 */
package com.arjvik.arjmart.orderqueueconsumer.schema.tables.records;


import com.arjvik.arjmart.orderqueueconsumer.schema.tables.Itemprice;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;


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
public class ItempriceRecord extends UpdatableRecordImpl<ItempriceRecord> implements Record3<Integer, Integer, Double> {

    private static final long serialVersionUID = -1534968232;

    /**
     * Setter for <code>arjmart.ItemPrice.SKU</code>.
     */
    public void setSku(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>arjmart.ItemPrice.SKU</code>.
     */
    public Integer getSku() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>arjmart.ItemPrice.ItemAttributeID</code>.
     */
    public void setItemattributeid(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>arjmart.ItemPrice.ItemAttributeID</code>.
     */
    public Integer getItemattributeid() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>arjmart.ItemPrice.Price</code>.
     */
    public void setPrice(Double value) {
        set(2, value);
    }

    /**
     * Getter for <code>arjmart.ItemPrice.Price</code>.
     */
    public Double getPrice() {
        return (Double) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record2<Integer, Integer> key() {
        return (Record2) super.key();
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<Integer, Integer, Double> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<Integer, Integer, Double> valuesRow() {
        return (Row3) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return Itemprice.ITEMPRICE.SKU;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return Itemprice.ITEMPRICE.ITEMATTRIBUTEID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Double> field3() {
        return Itemprice.ITEMPRICE.PRICE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component1() {
        return getSku();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component2() {
        return getItemattributeid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double component3() {
        return getPrice();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getSku();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value2() {
        return getItemattributeid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double value3() {
        return getPrice();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ItempriceRecord value1(Integer value) {
        setSku(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ItempriceRecord value2(Integer value) {
        setItemattributeid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ItempriceRecord value3(Double value) {
        setPrice(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ItempriceRecord values(Integer value1, Integer value2, Double value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ItempriceRecord
     */
    public ItempriceRecord() {
        super(Itemprice.ITEMPRICE);
    }

    /**
     * Create a detached, initialised ItempriceRecord
     */
    public ItempriceRecord(Integer sku, Integer itemattributeid, Double price) {
        super(Itemprice.ITEMPRICE);

        set(0, sku);
        set(1, itemattributeid);
        set(2, price);
    }
}
