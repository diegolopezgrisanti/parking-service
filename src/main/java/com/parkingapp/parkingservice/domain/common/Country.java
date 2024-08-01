package com.parkingapp.parkingservice.domain.common;

public enum Country {
    // European Countries
    DEU("Deutschland", "GERMANY", "DE", 276),
    AUT("Österreich", "AUSTRIA", "AT", 40),
    BEL("Belgien", "BELGIUM", "BE", 56),
    FRA("Frankreich", "FRANCE", "FR", 250),
    GBR("Vereinigtes Königreich", "UNITED KINGDOM", "GB", 826),
    ITA("Italien", "ITALY", "IT", 380),
    NLD("Niederlande", "NETHERLANDS", "NL", 528),
    ESP("Spanien", "SPAIN", "ES", 724),
    SWE("Schweden", "SWEDEN", "SE", 752),
    NOR("Norwegen", "NORWAY", "NO", 578),
    DNK("Dänemark", "DENMARK", "DK", 208),
    CHE("Schweiz", "SWITZERLAND", "CH", 756),
    FIN("Finnland", "FINLAND", "FI", 246),
    GRC("Griechenland", "GREECE", "GR", 300),
    POL("Polen", "POLAND", "PL", 616),
    PRT("Portugal", "PORTUGAL", "PT", 620),
    RUS("Russland", "RUSSIA", "RU", 643),

    // American Countries
    USA("United States of America", "UNITED STATES", "US", 840),
    CAN("Canada", "CANADA", "CA", 124),
    MEX("México", "MEXICO", "MX", 484),
    BRA("Brasil", "BRAZIL", "BR", 76),
    ARG("Argentina", "ARGENTINA", "AR", 32),
    CHL("Chile", "CHILE", "CL", 152),
    COL("Colombia", "COLOMBIA", "CO", 170),
    PER("Perú", "PERU", "PE", 604),
    VEN("Venezuela", "VENEZUELA", "VE", 862),
    URY("Uruguay", "URUGUAY", "UY", 858),
    BOL("Bolivia", "BOLIVIA", "BO", 68),
    ECU("Ecuador", "ECUADOR", "EC", 218),
    PRY("Paraguay", "PARAGUAY", "PY", 600);

    private final String countryName;
    private final String isoCountryName;
    private final String iso2Name;
    private final int isoCode;

    Country(String countryName, String isoCountryName, String iso2Name, int isoCode) {
        this.countryName = countryName;
        this.isoCountryName = isoCountryName;
        this.iso2Name = iso2Name;
        this.isoCode = isoCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getIsoCountryName() {
        return isoCountryName;
    }

    public String getIso2Name() {
        return iso2Name;
    }

    public int getIsoCode() {
        return isoCode;
    }
}

