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
package org.eclipse.ditto.services.thingsearch.persistence.write.impl;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.bson.conversions.Bson;
import org.eclipse.ditto.services.thingsearch.persistence.TestConstants;
import org.eclipse.ditto.signals.events.things.AttributeModified;
import org.junit.Test;

public final class MongoAttributeModifiedStrategyTest extends AbstractMongoEventToPersistenceStrategyTest {

    private final MongoAttributeModifiedStrategy strategy = new MongoAttributeModifiedStrategy();

    @Test
    public void thingUpdates() throws Exception {
        final List<Bson> updates = strategy.thingUpdates(thingEvent(), indexLengthRestrictionEnforcer);
        assertThat(updates).hasSize(3);
    }

    @Test
    public void policyUpdates() {
        final List<PolicyUpdate> updates = strategy.policyUpdates(thingEvent(), policyEnforcer);
        verifyPolicyUpdatesForSchemaVersion(updates, 1);
    }

    private AttributeModified thingEvent() {
        return setVersion(TestConstants.ThingEvent.ATTRIBUTE_MODIFIED);
    }

}