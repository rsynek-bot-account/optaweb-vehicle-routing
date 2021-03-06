/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.optaweb.vehiclerouting.plugin.websocket;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.optaweb.vehiclerouting.domain.Coordinates;

import static org.assertj.core.api.Assertions.assertThat;

public class PortableCoordinatesTest {

    @Test
    public void conversion_from_domain() {
        Coordinates coordinates = Coordinates.valueOf(0.04687, -88.8889);
        PortableCoordinates portableCoordinates = PortableCoordinates.fromCoordinates(coordinates);
        assertThat(portableCoordinates.getLatitude()).isEqualTo(coordinates.latitude());
        assertThat(portableCoordinates.getLongitude()).isEqualTo(coordinates.longitude());
    }

    @Test
    public void should_reduce_scale_if_needed() {
        Coordinates coordinates = Coordinates.valueOf(0.123450001, -88.999999999);
        Coordinates scaledDown = Coordinates.valueOf(0.12345, -89);
        PortableCoordinates portableCoordinates = PortableCoordinates.fromCoordinates(coordinates);
        assertThat(portableCoordinates.getLatitude()).isEqualTo(scaledDown.latitude());
        assertThat(portableCoordinates.getLongitude()).isEqualByComparingTo(scaledDown.longitude());
        // This would surprisingly fail because actual is -89 and expected is -89.0
//        assertThat(portableCoordinates.getLongitude()).isEqualTo(scaledDown.longitude());
    }

    @Test
    public void equals_hashCode_toString() {
        BigDecimal lat1 = BigDecimal.valueOf(10.0101);
        BigDecimal lat2 = BigDecimal.valueOf(20.2323);
        BigDecimal lon1 = BigDecimal.valueOf(-8.7);
        BigDecimal lon2 = BigDecimal.valueOf(-7.8);
        PortableCoordinates portableCoordinates = new PortableCoordinates(lat1, lon1);

        // equals()
        assertThat(portableCoordinates).isNotEqualTo(null);
        assertThat(portableCoordinates).isNotEqualTo(new Coordinates(lat1, lon1));
        assertThat(portableCoordinates).isNotEqualTo(new PortableCoordinates(lat1, lon2));
        assertThat(portableCoordinates).isNotEqualTo(new PortableCoordinates(lat2, lon1));
        assertThat(portableCoordinates).isEqualTo(portableCoordinates);
        assertThat(portableCoordinates).isEqualTo(new PortableCoordinates(lat1, lon1));

        // hasCode()
        assertThat(portableCoordinates).hasSameHashCodeAs(new PortableCoordinates(lat1, lon1));

        // toString()
        assertThat(portableCoordinates.toString()).contains(
                lat1.toPlainString(),
                lon1.toPlainString());
    }
}
