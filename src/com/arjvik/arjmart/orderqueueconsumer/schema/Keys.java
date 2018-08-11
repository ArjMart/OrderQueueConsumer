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
import com.arjvik.arjmart.orderqueueconsumer.schema.tables.records.InventoryRecord;
import com.arjvik.arjmart.orderqueueconsumer.schema.tables.records.ItemattributemasterRecord;
import com.arjvik.arjmart.orderqueueconsumer.schema.tables.records.ItemmasterRecord;
import com.arjvik.arjmart.orderqueueconsumer.schema.tables.records.ItempriceRecord;
import com.arjvik.arjmart.orderqueueconsumer.schema.tables.records.LocationmasterRecord;
import com.arjvik.arjmart.orderqueueconsumer.schema.tables.records.OrderRecord;
import com.arjvik.arjmart.orderqueueconsumer.schema.tables.records.OrderlineRecord;
import com.arjvik.arjmart.orderqueueconsumer.schema.tables.records.UserRecord;
import com.arjvik.arjmart.orderqueueconsumer.schema.tables.records.UserrolesRecord;

import javax.annotation.Generated;

import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.UniqueKey;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables of 
 * the <code>arjmart</code> schema.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.2"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // IDENTITY definitions
    // -------------------------------------------------------------------------

    public static final Identity<LocationmasterRecord, Integer> IDENTITY_LOCATIONMASTER = Identities0.IDENTITY_LOCATIONMASTER;
    public static final Identity<OrderRecord, Integer> IDENTITY_ORDER = Identities0.IDENTITY_ORDER;
    public static final Identity<UserRecord, Integer> IDENTITY_USER = Identities0.IDENTITY_USER;

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<InventoryRecord> KEY_INVENTORY_PRIMARY = UniqueKeys0.KEY_INVENTORY_PRIMARY;
    public static final UniqueKey<ItemattributemasterRecord> KEY_ITEMATTRIBUTEMASTER_PRIMARY = UniqueKeys0.KEY_ITEMATTRIBUTEMASTER_PRIMARY;
    public static final UniqueKey<ItemattributemasterRecord> KEY_ITEMATTRIBUTEMASTER_ITEMATTRIBUTE_UNIQUE = UniqueKeys0.KEY_ITEMATTRIBUTEMASTER_ITEMATTRIBUTE_UNIQUE;
    public static final UniqueKey<ItemmasterRecord> KEY_ITEMMASTER_PRIMARY = UniqueKeys0.KEY_ITEMMASTER_PRIMARY;
    public static final UniqueKey<ItempriceRecord> KEY_ITEMPRICE_PRIMARY = UniqueKeys0.KEY_ITEMPRICE_PRIMARY;
    public static final UniqueKey<LocationmasterRecord> KEY_LOCATIONMASTER_PRIMARY = UniqueKeys0.KEY_LOCATIONMASTER_PRIMARY;
    public static final UniqueKey<OrderRecord> KEY_ORDER_PRIMARY = UniqueKeys0.KEY_ORDER_PRIMARY;
    public static final UniqueKey<OrderlineRecord> KEY_ORDERLINE_PRIMARY = UniqueKeys0.KEY_ORDERLINE_PRIMARY;
    public static final UniqueKey<OrderlineRecord> KEY_ORDERLINE_ORDERLINE_UNIQUE = UniqueKeys0.KEY_ORDERLINE_ORDERLINE_UNIQUE;
    public static final UniqueKey<UserRecord> KEY_USER_PRIMARY = UniqueKeys0.KEY_USER_PRIMARY;
    public static final UniqueKey<UserRecord> KEY_USER_EMAIL_UNIQUE = UniqueKeys0.KEY_USER_EMAIL_UNIQUE;
    public static final UniqueKey<UserrolesRecord> KEY_USERROLES_PRIMARY = UniqueKeys0.KEY_USERROLES_PRIMARY;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<InventoryRecord, ItemattributemasterRecord> INVENTORY_SKU_ITEMATTRIBUTEID = ForeignKeys0.INVENTORY_SKU_ITEMATTRIBUTEID;
    public static final ForeignKey<InventoryRecord, LocationmasterRecord> INVENTORY_LOCATIONID = ForeignKeys0.INVENTORY_LOCATIONID;
    public static final ForeignKey<ItemattributemasterRecord, ItemmasterRecord> ITEMATTRIBUTE_SKU = ForeignKeys0.ITEMATTRIBUTE_SKU;
    public static final ForeignKey<ItempriceRecord, ItemattributemasterRecord> ITEMPRICE_SKU_ITEMATTRIBUTEID = ForeignKeys0.ITEMPRICE_SKU_ITEMATTRIBUTEID;
    public static final ForeignKey<OrderRecord, UserRecord> ORDER_USERID = ForeignKeys0.ORDER_USERID;
    public static final ForeignKey<OrderlineRecord, OrderRecord> ORDERLINE_ORDERID = ForeignKeys0.ORDERLINE_ORDERID;
    public static final ForeignKey<OrderlineRecord, ItemattributemasterRecord> ORDERLINE_SKU_ITEMATTRIBUTEID = ForeignKeys0.ORDERLINE_SKU_ITEMATTRIBUTEID;
    public static final ForeignKey<UserrolesRecord, UserRecord> USERROLES_USERID = ForeignKeys0.USERROLES_USERID;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Identities0 {
        public static Identity<LocationmasterRecord, Integer> IDENTITY_LOCATIONMASTER = Internal.createIdentity(Locationmaster.LOCATIONMASTER, Locationmaster.LOCATIONMASTER.LOCATIONID);
        public static Identity<OrderRecord, Integer> IDENTITY_ORDER = Internal.createIdentity(Order.ORDER, Order.ORDER.ORDERID);
        public static Identity<UserRecord, Integer> IDENTITY_USER = Internal.createIdentity(User.USER, User.USER.USERID);
    }

    private static class UniqueKeys0 {
        public static final UniqueKey<InventoryRecord> KEY_INVENTORY_PRIMARY = Internal.createUniqueKey(Inventory.INVENTORY, "KEY_Inventory_PRIMARY", Inventory.INVENTORY.SKU, Inventory.INVENTORY.ITEMATTRIBUTEID, Inventory.INVENTORY.LOCATIONID);
        public static final UniqueKey<ItemattributemasterRecord> KEY_ITEMATTRIBUTEMASTER_PRIMARY = Internal.createUniqueKey(Itemattributemaster.ITEMATTRIBUTEMASTER, "KEY_ItemAttributeMaster_PRIMARY", Itemattributemaster.ITEMATTRIBUTEMASTER.SKU, Itemattributemaster.ITEMATTRIBUTEMASTER.ITEMATTRIBUTEID);
        public static final UniqueKey<ItemattributemasterRecord> KEY_ITEMATTRIBUTEMASTER_ITEMATTRIBUTE_UNIQUE = Internal.createUniqueKey(Itemattributemaster.ITEMATTRIBUTEMASTER, "KEY_ItemAttributeMaster_ItemAttribute_Unique", Itemattributemaster.ITEMATTRIBUTEMASTER.SKU, Itemattributemaster.ITEMATTRIBUTEMASTER.COLOR, Itemattributemaster.ITEMATTRIBUTEMASTER.SIZE);
        public static final UniqueKey<ItemmasterRecord> KEY_ITEMMASTER_PRIMARY = Internal.createUniqueKey(Itemmaster.ITEMMASTER, "KEY_ItemMaster_PRIMARY", Itemmaster.ITEMMASTER.SKU);
        public static final UniqueKey<ItempriceRecord> KEY_ITEMPRICE_PRIMARY = Internal.createUniqueKey(Itemprice.ITEMPRICE, "KEY_ItemPrice_PRIMARY", Itemprice.ITEMPRICE.SKU, Itemprice.ITEMPRICE.ITEMATTRIBUTEID);
        public static final UniqueKey<LocationmasterRecord> KEY_LOCATIONMASTER_PRIMARY = Internal.createUniqueKey(Locationmaster.LOCATIONMASTER, "KEY_LocationMaster_PRIMARY", Locationmaster.LOCATIONMASTER.LOCATIONID);
        public static final UniqueKey<OrderRecord> KEY_ORDER_PRIMARY = Internal.createUniqueKey(Order.ORDER, "KEY_Order_PRIMARY", Order.ORDER.ORDERID);
        public static final UniqueKey<OrderlineRecord> KEY_ORDERLINE_PRIMARY = Internal.createUniqueKey(Orderline.ORDERLINE, "KEY_OrderLine_PRIMARY", Orderline.ORDERLINE.ORDERID, Orderline.ORDERLINE.ORDERLINEID);
        public static final UniqueKey<OrderlineRecord> KEY_ORDERLINE_ORDERLINE_UNIQUE = Internal.createUniqueKey(Orderline.ORDERLINE, "KEY_OrderLine_OrderLine_Unique", Orderline.ORDERLINE.ORDERID, Orderline.ORDERLINE.SKU, Orderline.ORDERLINE.ITEMATTRIBUTEID);
        public static final UniqueKey<UserRecord> KEY_USER_PRIMARY = Internal.createUniqueKey(User.USER, "KEY_User_PRIMARY", User.USER.USERID);
        public static final UniqueKey<UserRecord> KEY_USER_EMAIL_UNIQUE = Internal.createUniqueKey(User.USER, "KEY_User_Email_UNIQUE", User.USER.EMAIL);
        public static final UniqueKey<UserrolesRecord> KEY_USERROLES_PRIMARY = Internal.createUniqueKey(Userroles.USERROLES, "KEY_UserRoles_PRIMARY", Userroles.USERROLES.USERID, Userroles.USERROLES.ROLE);
    }

    private static class ForeignKeys0 {
        public static final ForeignKey<InventoryRecord, ItemattributemasterRecord> INVENTORY_SKU_ITEMATTRIBUTEID = Internal.createForeignKey(com.arjvik.arjmart.orderqueueconsumer.schema.Keys.KEY_ITEMATTRIBUTEMASTER_PRIMARY, Inventory.INVENTORY, "Inventory_SKU_ItemAttributeID", Inventory.INVENTORY.SKU, Inventory.INVENTORY.ITEMATTRIBUTEID);
        public static final ForeignKey<InventoryRecord, LocationmasterRecord> INVENTORY_LOCATIONID = Internal.createForeignKey(com.arjvik.arjmart.orderqueueconsumer.schema.Keys.KEY_LOCATIONMASTER_PRIMARY, Inventory.INVENTORY, "Inventory_LocationID", Inventory.INVENTORY.LOCATIONID);
        public static final ForeignKey<ItemattributemasterRecord, ItemmasterRecord> ITEMATTRIBUTE_SKU = Internal.createForeignKey(com.arjvik.arjmart.orderqueueconsumer.schema.Keys.KEY_ITEMMASTER_PRIMARY, Itemattributemaster.ITEMATTRIBUTEMASTER, "ItemAttribute_SKU", Itemattributemaster.ITEMATTRIBUTEMASTER.SKU);
        public static final ForeignKey<ItempriceRecord, ItemattributemasterRecord> ITEMPRICE_SKU_ITEMATTRIBUTEID = Internal.createForeignKey(com.arjvik.arjmart.orderqueueconsumer.schema.Keys.KEY_ITEMATTRIBUTEMASTER_PRIMARY, Itemprice.ITEMPRICE, "ItemPrice_SKU_ItemAttributeID", Itemprice.ITEMPRICE.SKU, Itemprice.ITEMPRICE.ITEMATTRIBUTEID);
        public static final ForeignKey<OrderRecord, UserRecord> ORDER_USERID = Internal.createForeignKey(com.arjvik.arjmart.orderqueueconsumer.schema.Keys.KEY_USER_PRIMARY, Order.ORDER, "Order_UserID", Order.ORDER.USERID);
        public static final ForeignKey<OrderlineRecord, OrderRecord> ORDERLINE_ORDERID = Internal.createForeignKey(com.arjvik.arjmart.orderqueueconsumer.schema.Keys.KEY_ORDER_PRIMARY, Orderline.ORDERLINE, "OrderLine_OrderID", Orderline.ORDERLINE.ORDERID);
        public static final ForeignKey<OrderlineRecord, ItemattributemasterRecord> ORDERLINE_SKU_ITEMATTRIBUTEID = Internal.createForeignKey(com.arjvik.arjmart.orderqueueconsumer.schema.Keys.KEY_ITEMATTRIBUTEMASTER_PRIMARY, Orderline.ORDERLINE, "OrderLine_SKU_ItemAttributeID", Orderline.ORDERLINE.SKU, Orderline.ORDERLINE.ITEMATTRIBUTEID);
        public static final ForeignKey<UserrolesRecord, UserRecord> USERROLES_USERID = Internal.createForeignKey(com.arjvik.arjmart.orderqueueconsumer.schema.Keys.KEY_USER_PRIMARY, Userroles.USERROLES, "UserRoles_UserID", Userroles.USERROLES.USERID);
    }
}