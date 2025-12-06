package com.airportmanagement.domain.model;

public class Aeroporto {
  private final Integer idAeroporto;
  private final String nomeAeroporto;
  private final String codigoIata;
  private final String cidade;
  private final String codigoPaisIso;
  private final Double latitude;
  private final Double longitude;
  private final Double altitude;

  public Aeroporto(Integer idAeroporto, String nomeAeroporto, String codigoIata, String cidade,
                   String codigoPaisIso, Double latitude, Double longitude, Double altitude) {
    this.idAeroporto = idAeroporto;
    this.nomeAeroporto = nomeAeroporto;
    this.codigoIata = codigoIata;
    this.cidade = cidade;
    this.codigoPaisIso = codigoPaisIso;
    this.latitude = latitude;
    this.longitude = longitude;
    this.altitude = altitude;
  }

  public Integer getIdAeroporto() { return idAeroporto; }
  public String getNomeAeroporto() { return nomeAeroporto; }
  public String getCodigoIata() { return codigoIata; }
  public String getCidade() { return cidade; }
  public String getCodigoPaisIso() { return codigoPaisIso; }
  public Double getLatitude() { return latitude; }
  public Double getLongitude() { return longitude; }
  public Double getAltitude() { return altitude; }
}

