package com.use.dto;

/**
 * Created by julius on 02/05/2018.
 */
public class PassSettingRequestDTO {

  private boolean hasSymbols = true;
  private String service;
  private int passwordLength;
  private String secretKey;

  public PassSettingRequestDTO() {
  }

  public PassSettingRequestDTO(Builder builder) {
    this.hasSymbols = builder.hasSymbols;
    this.service = builder.service;
    this.passwordLength = builder.passwordLength;
    this.secretKey = builder.secretKey;
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

  public String getSecretKey() {
    return secretKey;
  }

  public void setSecretKey(String secretKey) {
    this.secretKey = secretKey;
  }

  public static class Builder {

    private boolean hasSymbols;
    private String service;
    private int passwordLength;
    private String secretKey;

    public Builder(String secretKey) {
      this.secretKey = secretKey;
    }

    public Builder service(String service) {
      this.service = service;
      return this;
    }

    public Builder passwordLength(int passwordLength) {
      this.passwordLength = passwordLength;
      return this;
    }

    public Builder applySymbols() {
      this.hasSymbols = Boolean.TRUE;
      return this;
    }

    public PassSettingRequestDTO build() {
      return new PassSettingRequestDTO(this);
    }
  }

}
