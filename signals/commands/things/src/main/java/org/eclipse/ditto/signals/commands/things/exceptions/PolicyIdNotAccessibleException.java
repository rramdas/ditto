/*
 * Copyright (c) 2017 Bosch Software Innovations GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/org/documents/epl-2.0/index.php
 *
 * Contributors:
 *    Bosch Software Innovations GmbH - initial contribution
 */
package org.eclipse.ditto.signals.commands.things.exceptions;

import java.net.URI;
import java.text.MessageFormat;

import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.NotThreadSafe;

import org.eclipse.ditto.json.JsonObject;
import org.eclipse.ditto.model.base.common.HttpStatusCode;
import org.eclipse.ditto.model.base.exceptions.DittoRuntimeException;
import org.eclipse.ditto.model.base.exceptions.DittoRuntimeExceptionBuilder;
import org.eclipse.ditto.model.base.headers.DittoHeaders;
import org.eclipse.ditto.model.things.ThingException;

/**
 * Thrown if the Policy ID of the Thing was either not present at all or if the requester had insufficient permissions
 * to access it.
 */
@Immutable
public final class PolicyIdNotAccessibleException extends DittoRuntimeException implements ThingException {

    /**
     * Error code of this exception.
     */
    public static final String ERROR_CODE = ERROR_CODE_PREFIX + "policyId.notfound";

    private static final String MESSAGE_TEMPLATE =
            "The Policy ID ''{0}'' could not be found or requester had " + "insufficient permissions to access it.";

    private static final String DEFAULT_DESCRIPTION =
            "Check if the ID of your requested Thing was correct and you have sufficient permissions.";

    private static final long serialVersionUID = -623037881914361095L;

    private PolicyIdNotAccessibleException(final DittoHeaders dittoHeaders, final String message,
            final String description, final Throwable cause, final URI href) {
        super(ERROR_CODE, HttpStatusCode.NOT_FOUND, dittoHeaders, message, description, cause, href);
    }

    /**
     * A mutable builder for a {@code PolicyIdNotAccessibleException}.
     *
     * @param thingId the ID of the thing.
     * @return the builder.
     */
    public static PolicyIdNotAccessibleException.Builder newBuilder(final String thingId) {
        return new PolicyIdNotAccessibleException.Builder(thingId);
    }

    /**
     * Constructs a new {@code PolicyIdNotAccessibleException} object with given message.
     *
     * @param message detail message. This message can be later retrieved by the {@link #getMessage()} method.
     * @param dittoHeaders the headers of the command which resulted in this exception.
     * @return the new PolicyIdNotAccessibleException.
     */
    public static PolicyIdNotAccessibleException fromMessage(final String message,
            final DittoHeaders dittoHeaders) {
        return new PolicyIdNotAccessibleException.Builder()
                .dittoHeaders(dittoHeaders)
                .message(message)
                .build();
    }

    /**
     * Constructs a new {@code PolicyIdNotAccessibleException} object with the exception message extracted from the
     * given JSON object.
     *
     * @param jsonObject the JSON to read the {@link JsonFields#MESSAGE} field from.
     * @param dittoHeaders the headers of the command which resulted in this exception.
     * @return the new PolicyIdNotAccessibleException.
     * @throws org.eclipse.ditto.json.JsonMissingFieldException if the {@code jsonObject} does not have the {@link
     * JsonFields#MESSAGE} field.
     */
    public static PolicyIdNotAccessibleException fromJson(final JsonObject jsonObject,
            final DittoHeaders dittoHeaders) {
        return fromMessage(readMessage(jsonObject), dittoHeaders);
    }

    /**
     * A mutable builder with a fluent API for a {@link PolicyIdNotAccessibleException}.
     *
     */
    @NotThreadSafe
    public static final class Builder extends DittoRuntimeExceptionBuilder<PolicyIdNotAccessibleException> {

        private Builder() {
            description(DEFAULT_DESCRIPTION);
        }

        private Builder(final String thingId) {
            this();
            message(MessageFormat.format(MESSAGE_TEMPLATE, thingId));
        }

        @Override
        protected PolicyIdNotAccessibleException doBuild(final DittoHeaders dittoHeaders, final String message,
                final String description, final Throwable cause, final URI href) {
            return new PolicyIdNotAccessibleException(dittoHeaders, message, description, cause, href);
        }
    }
}
