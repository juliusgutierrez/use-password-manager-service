package com.use.dto;

/**
 * Created by julius on 02/05/2018.
 */
public class PassSettingRequestDTO {

  private String service;
  private boolean hasSymbols;
  private int passwordLength;

  public PassSettingRequestDTO() {
  }

  public PassSettingRequestDTO(String service, boolean hasSymbols, int passwordLength) {
    this.service = service;
    this.hasSymbols = hasSymbols;
    this.passwordLength = passwordLength;
  }

  public PassSettingRequestDTO(String service, int passwordLength) {
    this.service = service;
    this.passwordLength = passwordLength;
  }

  public String getService() {
    return service;
  }

  public void setService(String service) {
    this.service = service;
  }

  public boolean hasSymbols() {
    return hasSymbols;
  }

  public void setHasSymbols(boolean hasSymbols) {
    this.hasSymbols = hasSymbols;
  }

  public int getPasswordLength() {
    return passwordLength;
  }

  public void setPasswordLength(int passwordLength) {
    this.passwordLength = passwordLength;
  }
}
