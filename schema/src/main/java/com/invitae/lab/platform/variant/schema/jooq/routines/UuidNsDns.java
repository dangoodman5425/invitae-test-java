/*
 * This file is generated by jOOQ.
 */
package com.invitae.lab.platform.variant.schema.jooq.routines;


import com.invitae.lab.platform.variant.schema.jooq.Public;

import java.util.UUID;

import org.jooq.Parameter;
import org.jooq.impl.AbstractRoutine;
import org.jooq.impl.Internal;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UuidNsDns extends AbstractRoutine<UUID> {

    private static final long serialVersionUID = -1722989884;

    /**
     * The parameter <code>public.uuid_ns_dns.RETURN_VALUE</code>.
     */
    public static final Parameter<UUID> RETURN_VALUE = Internal.createParameter("RETURN_VALUE", org.jooq.impl.SQLDataType.UUID, false, false);

    /**
     * Create a new routine call instance
     */
    public UuidNsDns() {
        super("uuid_ns_dns", Public.PUBLIC, org.jooq.impl.SQLDataType.UUID);

        setReturnParameter(RETURN_VALUE);
    }
}
