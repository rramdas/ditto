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
package org.eclipse.ditto.signals.commands.amqpbridge;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.concurrent.Immutable;

import org.eclipse.ditto.model.amqpbridge.AmqpBridgeException;
import org.eclipse.ditto.model.amqpbridge.ConnectionUriInvalidException;
import org.eclipse.ditto.model.base.exceptions.DittoRuntimeException;
import org.eclipse.ditto.signals.base.AbstractErrorRegistry;
import org.eclipse.ditto.signals.base.JsonParsable;
import org.eclipse.ditto.signals.commands.amqpbridge.exceptions.ConnectionFailedException;
import org.eclipse.ditto.signals.commands.amqpbridge.exceptions.ConnectionNotAccessibleException;
import org.eclipse.ditto.signals.commands.amqpbridge.exceptions.ConnectionUnavailableException;
import org.eclipse.ditto.signals.commands.base.CommonErrorRegistry;

/**
 * A {@link org.eclipse.ditto.signals.base.ErrorRegistry} aware of all {@link AmqpBridgeException}s.
 */
@Immutable
public final class AmqpBridgeErrorRegistry extends AbstractErrorRegistry<DittoRuntimeException> {

    private AmqpBridgeErrorRegistry(final Map<String, JsonParsable<DittoRuntimeException>> parseStrategies) {
        super(parseStrategies);
    }

    /**
     * Returns a new {@code AmqpBridgeErrorRegistry}.
     *
     * @return the error registry.
     */
    public static AmqpBridgeErrorRegistry newInstance() {
        return newInstance(Collections.emptyMap());
    }

    /**
     * Returns a new {@code AmqpBridgeErrorRegistry} providing {@code additionalParseStrategies} as argument - that way
     * the user of this AmqpBridgeErrorRegistry can register additional parsers for his own extensions of {@link
     * org.eclipse.ditto.model.base.exceptions.DittoRuntimeException}.
     *
     * @param additionalParseStrategies a map containing of DittoRuntimeException ERROR_CODE as amqpBridges and
     * JsonParsable of DittoRuntimeException as values.
     * @return the error registry.
     */
    public static AmqpBridgeErrorRegistry newInstance(
            final Map<String, JsonParsable<DittoRuntimeException>> additionalParseStrategies) {
        final Map<String, JsonParsable<DittoRuntimeException>> parseStrategies =
                new HashMap<>(additionalParseStrategies);

        final CommonErrorRegistry commonErrorRegistry = CommonErrorRegistry.newInstance();
        commonErrorRegistry.getTypes().forEach(type -> parseStrategies.put(type, commonErrorRegistry));

        parseStrategies.put(ConnectionUriInvalidException.ERROR_CODE, ConnectionUriInvalidException::fromJson);
        parseStrategies.put(ConnectionNotAccessibleException.ERROR_CODE, ConnectionNotAccessibleException::fromJson);
        parseStrategies.put(ConnectionUnavailableException.ERROR_CODE, ConnectionUnavailableException::fromJson);
        parseStrategies.put(ConnectionFailedException.ERROR_CODE, ConnectionFailedException::fromJson);

        return new AmqpBridgeErrorRegistry(parseStrategies);
    }
}
