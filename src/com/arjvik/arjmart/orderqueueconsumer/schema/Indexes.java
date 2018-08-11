/*
 * This file is generated by jOOQ.
 */
package com.arjvik.arjmart.orderqueueconsumer.schema;


import com.arjvik.arjmart.orderqueueconsumer.schema.tables.Inventory;
import com.arjvik.arjmart.orderqueueconsumer.schema.tables.Itemattributemaster;
import com.arjvik.arjmart.orderqueueconsumer.schema.tables.Itemmaster;
import com.arjvik.arjmart.orderqueueconsumer.schema.tables.Itemprice;
import com.arjvik.arjmart.orderqueueconsumer.schema.tables.Locationmaster;
import com.arjvik.arjmart.orderqueueconsumer.schema.tables.Order;
import com.arjvik.arjmart.orderqueueconsumer.schema.tables.Orderline;
import com.arjvik.arjmart.orderqueueconsumer.schema.tables.User;
import com.arjvik.arjmart.orderqueueconsumer.schema.tables.Userroles;

import javax.annotation.Generated;

import org.jooq.Index;
import org.jooq.OrderField;
import org.jooq.impl.Internal;


/**
 * A class modelling indexes of tables of the <code>arjmart</code> schema.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.2"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Indexes {

    // -------------------------------------------------------------------------
    // INDEX definitions
    // -------------------------------------------------------------------------

    public static final Index INVENTORY_INVENTORY_ITEMATTRIBUTEID_IDX = Indexes0.INVENTORY_INVENTORY_ITEMATTRIBUTEID_IDX;
    public static final Index INVENTORY_INVENTORY_LOCATIONID_IDX = Indexes0.INVENTORY_INVENTORY_LOCATIONID_IDX;
    public static final Index INVENTORY_PRIMARY = Indexes0.INVENTORY_PRIMARY;
    public static final Index ITEMATTRIBUTEMASTER_ITEMATTRIBUTE_UNIQUE = Indexes0.ITEMATTRIBUTEMASTER_ITEMATTRIBUTE_UNIQUE;
    public static final Index ITEMATTRIBUTEMASTER_PRIMARY = Indexes0.ITEMATTRIBUTEMASTER_PRIMARY;
    public static final Index ITEMMASTER_PRIMARY = Indexes0.ITEMMASTER_PRIMARY;
    public static final Index ITEMPRICE_PRIMARY = Indexes0.ITEMPRICE_PRIMARY;
    public static final Index LOCATIONMASTER_PRIMARY = Indexes0.LOCATIONMASTER_PRIMARY;
    public static final Index ORDER_ORDER_USERID_IDX = Indexes0.ORDER_ORDER_USERID_IDX;
    public static final Index ORDER_PRIMARY = Indexes0.ORDER_PRIMARY;
    public static final Index ORDERLINE_ORDERLINE_ORDERID_IDX = Indexes0.ORDERLINE_ORDERLINE_ORDERID_IDX;
    public static final Index ORDERLINE_ORDERLINE_SKU_ITEMATTRIBUTEID_IDX = Indexes0.ORDERLINE_ORDERLINE_SKU_ITEMATTRIBUTEID_IDX;
    public static final Index ORDERLINE_ORDERLINE_UNIQUE = Indexes0.ORDERLINE_ORDERLINE_UNIQUE;
    public static final Index ORDERLINE_PRIMARY = Indexes0.ORDERLINE_PRIMARY;
    public static final Index USER_EMAIL_UNIQUE = Indexes0.USER_EMAIL_UNIQUE;
    public static final Index USER_PRIMARY = Indexes0.USER_PRIMARY;
    public static final Index USERROLES_PRIMARY = Indexes0.USERROLES_PRIMARY;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Indexes0 {
        public static Index INVENTORY_INVENTORY_ITEMATTRIBUTEID_IDX = Internal.createIndex("Inventory_ItemAttributeID_idx", Inventory.INVENTORY, new OrderField[] { Inventory.INVENTORY.ITEMATTRIBUTEID }, false);
        public static Index INVENTORY_INVENTORY_LOCATIONID_IDX = Internal.createIndex("Inventory_LocationID_idx", Inventory.INVENTORY, new OrderField[] { Inventory.INVENTORY.LOCATIONID }, false);
        public static Index INVENTORY_PRIMARY = Internal.createIndex("PRIMARY", Inventory.INVENTORY, new OrderField[] { Inventory.INVENTORY.SKU, Inventory.INVENTORY.ITEMATTRIBUTEID, Inventory.INVENTORY.LOCATIONID }, true);
        public static Index ITEMATTRIBUTEMASTER_ITEMATTRIBUTE_UNIQUE = Internal.createIndex("ItemAttribute_Unique", Itemattributemaster.ITEMATTRIBUTEMASTER, new OrderField[] { Itemattributemaster.ITEMATTRIBUTEMASTER.SKU, Itemattributemaster.ITEMATTRIBUTEMASTER.COLOR, Itemattributemaster.ITEMATTRIBUTEMASTER.SIZE }, true);
        public static Index ITEMATTRIBUTEMASTER_PRIMARY = Internal.createIndex("PRIMARY", Itemattributemaster.ITEMATTRIBUTEMASTER, new OrderField[] { Itemattributemaster.ITEMATTRIBUTEMASTER.SKU, Itemattributemaster.ITEMATTRIBUTEMASTER.ITEMATTRIBUTEID }, true);
        public static Index ITEMMASTER_PRIMARY = Internal.createIndex("PRIMARY", Itemmaster.ITEMMASTER, new OrderField[] { Itemmaster.ITEMMASTER.SKU }, true);
        public static Index ITEMPRICE_PRIMARY = Internal.createIndex("PRIMARY", Itemprice.ITEMPRICE, new OrderField[] { Itemprice.ITEMPRICE.SKU, Itemprice.ITEMPRICE.ITEMATTRIBUTEID }, true);
        public static Index LOCATIONMASTER_PRIMARY = Internal.createIndex("PRIMARY", Locationmaster.LOCATIONMASTER, new OrderField[] { Locationmaster.LOCATIONMASTER.LOCATIONID }, true);
        public static Index ORDER_ORDER_USERID_IDX = Internal.createIndex("Order_UserID_idx", Order.ORDER, new OrderField[] { Order.ORDER.USERID }, false);
        public static Index ORDER_PRIMARY = Internal.createIndex("PRIMARY", Order.ORDER, new OrderField[] { Order.ORDER.ORDERID }, true);
        public static Index ORDERLINE_ORDERLINE_ORDERID_IDX = Internal.createIndex("OrderLine_OrderID_idx", Orderline.ORDERLINE, new OrderField[] { Orderline.ORDERLINE.ORDERID }, false);
        public static Index ORDERLINE_ORDERLINE_SKU_ITEMATTRIBUTEID_IDX = Internal.createIndex("OrderLine_SKU_ItemAttributeID_idx", Orderline.ORDERLINE, new OrderField[] { Orderline.ORDERLINE.SKU, Orderline.ORDERLINE.ITEMATTRIBUTEID }, false);
        public static Index ORDERLINE_ORDERLINE_UNIQUE = Internal.createIndex("OrderLine_Unique", Orderline.ORDERLINE, new OrderField[] { Orderline.ORDERLINE.ORDERID, Orderline.ORDERLINE.SKU, Orderline.ORDERLINE.ITEMATTRIBUTEID }, true);
        public static Index ORDERLINE_PRIMARY = Internal.createIndex("PRIMARY", Orderline.ORDERLINE, new OrderField[] { Orderline.ORDERLINE.ORDERID, Orderline.ORDERLINE.ORDERLINEID }, true);
        public static Index USER_EMAIL_UNIQUE = Internal.createIndex("Email_UNIQUE", User.USER, new OrderField[] { User.USER.EMAIL }, true);
        public static Index USER_PRIMARY = Internal.createIndex("PRIMARY", User.USER, new OrderField[] { User.USER.USERID }, true);
        public static Index USERROLES_PRIMARY = Internal.createIndex("PRIMARY", Userroles.USERROLES, new OrderField[] { Userroles.USERROLES.USERID, Userroles.USERROLES.ROLE }, true);
    }
}