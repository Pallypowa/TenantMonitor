package com.bigfoot.tenantmonitor.enums;

import lombok.Getter;

@Getter
public enum HeatingType {
    ANY("ANY"),                                                                                     // mindegy
    GAS_CONVECTOR("GAS_CONVECTOR"),                                                                 // gáz (konvektor)
    CENTRAL_HEATING("CENTRAL_HEATING"),                                                             // házközponti
    CENTRAL_HEATING_WITH_INDIVIDUAL_MEASUREMENT("CENTRAL_HEATING_WITH_INDIVIDUAL_MEASUREMENT"),     // házközponti egyedi méréssel
    DISTRICT_HEATING("DISTRICT_HEATING"),                                                           // távfűtés
    DISTRICT_HEATING_WITH_INDIVIDUAL_MEASUREMENT("DISTRICT_HEATING_WITH_INDIVIDUAL_MEASUREMENT"),   // távfűtés egyedi méréssel
    ELECTRIC_CONVECTOR("ELECTRIC_CONVECTOR"),                                                       // elektromos konvektor
    ELECTRIC_HEATING_PANEL("ELECTRIC_HEATING_PANEL"),                                               // elektromos fűtőpanel
    ELECTRIC_BOILER("ELECTRIC_BOILER"),                                                             // elektromos kazán
    AIR_CONDITIONING_WITH_HEATING("AIR_CONDITIONING_WITH_HEATING"),                                 // hűtő - fűtő klíma
    INFRARED_HEATING("INFRARED_HEATING"),                                                           // infrafűtés
    FIREPLACE("FIREPLACE"),                                                                         // kandalló
    STOVE("STOVE"),                                                                                 // kályha
    FAN_COIL("FAN_COIL"),                                                                           // fan-coil
    GAS_BOILER("GAS_BOILER"),                                                                       // gázkazán
    MIXED_FUEL_BOILER("MIXED_FUEL_BOILER"),                                                         // vegyes tüzelésű kazán
    OTHER_BOILER("OTHER_BOILER"),                                                                   // egyéb kazán
    TILE_STOVE("TILE_STOVE"),                                                                       // cserépkályha
    FLOOR_HEATING("FLOOR_HEATING"),                                                                 // padlófűtés
    WALL_HEATING("WALL_HEATING"),                                                                   // falfűtés
    CEILING_COOLING_AND_HEATING("CEILING_COOLING_AND_HEATING"),                                     // mennyezeti hűtés-fűtés
    HEAT_PUMP("HEAT_PUMP"),                                                                         // hőszivattyú
    OTHER("OTHER"),                                                                                 // egyéb
    NONE("NONE");                                                                                   // nincs

    private final String heatingType;

    HeatingType(String heatingType) {
        this.heatingType = heatingType;
    }
}
