/*
 * Copyright 2016 Open Networking Laboratory
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
package org.onosproject.ecord.carrierethernet.app;

import org.onosproject.ecord.metro.api.MetroPathEvent;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

import static com.google.common.base.MoreObjects.toStringHelper;

/**
 * Representation of a CE Forwarding Construct.
 */
public class CarrierEthernetForwardingConstruct {

    protected String fcId;
    protected String fcCfgId;
    protected String evcId;
    protected CarrierEthernetVirtualConnection.Type evcType;
    protected Set<CarrierEthernetLogicalTerminationPoint> ltpSet;
    protected Duration latency;
    protected CarrierEthernetMetroConnectivity metroConnectivity;
    protected boolean congruentPaths;

    // FIXME: Temporary solution
    protected CarrierEthernetVirtualConnection evcLite;

    // Set to true if both directions should use the same path
    private static final boolean CONGRUENT_PATHS = true;

    private static final Duration DEFAULT_LATENCY = Duration.ofMillis(50);

    // TODO: Maybe fcCfgId and evcId are not needed?
    // Note: fcId should be provided only when updating an existing FC
    public CarrierEthernetForwardingConstruct(String fcId, String fcCfgId,
                                              String evcId, CarrierEthernetVirtualConnection.Type evcType,
                                              Set<CarrierEthernetLogicalTerminationPoint> ltpSet) {
        this.fcId = fcId;
        this.fcCfgId = (fcCfgId == null? fcId : fcCfgId);
        this.evcId = evcId;
        this.evcType = evcType;
        this.ltpSet = new HashSet<>();
        this.ltpSet.addAll(ltpSet);
        this.congruentPaths = CONGRUENT_PATHS;
        this.latency = DEFAULT_LATENCY;
        this.metroConnectivity = new CarrierEthernetMetroConnectivity(null, MetroPathEvent.Type.PATH_REMOVED);

        // FIXME: Temporary solution: Create a lightweight EVC out of the FC which can be used with existing methods
        Set<CarrierEthernetUni> uniSet = new HashSet<>();
        ltpSet.forEach(ltp -> {
            if (ltp.ni() instanceof CarrierEthernetUni) {
                uniSet.add((CarrierEthernetUni) ltp.ni());
            }
        });
        this.evcLite = new CarrierEthernetVirtualConnection(fcId, fcCfgId, evcType, null, uniSet);
    }

    // TODO: Create constructor using the End-to-End service and a set of LTPs

    public String toString() {

        return toStringHelper(this)
                .add("id", fcId)
                .add("cfgId", fcCfgId)
                .add("evcId", evcId)
                .add("evcType", evcType)
                .add("metroConnectId", (metroConnectivity.id() == null ? "null" : metroConnectivity.id().value()))
                .add("LTPs", ltpSet).toString();
    }

    /**
     * Returns the id of the FC.
     *
     * @return id of the FC
     */
    public String id() {
        return fcId;
    }

    /**
     * Returns the set of LTPs associated with the FC.
     *
     * @return set of LTPs associated with the FC
     */
    public Set<CarrierEthernetLogicalTerminationPoint> ltpSet() {
        return ltpSet;
    }

    /**
     * Returns the type of the EVC associated with the FC.
     *
     * @return type of associated EVC
     */
    public CarrierEthernetVirtualConnection.Type evcType() {
        return evcType;
    }

    /**
     * Returns the "EVC" associated with FC.
     *
     * @return the "EVC" associated with FC
     */
    public CarrierEthernetVirtualConnection evcLite() {
        return evcLite;
    }

    /**
     * Sets the id of the FC.
     *
     * @param id the id to set to the FC
     */
    public void setId(String id) {
        this.fcId = id;
    }

}