package com.infoshare.domain;

import java.util.Objects;
import java.util.UUID;

public class Volunteer {

    private String name;
    private String location;
    private String email;
    private String phone;
    private TypeOfHelp typeOfHelp;
    private boolean isAvailable;
    private final UUID uuid;

    public Volunteer(String name, String location, String email, String phone, TypeOfHelp typeOfHelp,
        boolean isAvailable, UUID uuid) {
        this.name = name;
        this.location = location;
        this.email = email;
        this.phone = phone;
        this.typeOfHelp = typeOfHelp;
        this.isAvailable = isAvailable;
        this.uuid = uuid;
    }

  public UUID getUuid() {
    return uuid;
  }

  @Override  //TODO zrobic toString to itsAvalibe
    public String toString() {
        return "Volunteer{" + "name='" + name + '\'' + ", location='" + location + '\'' + ", email='" + email + '\''
            + ", phone='" + phone + '\'' + ", typeOfHelp='" + typeOfHelp + '\'' + ", isAvailable=" + isAvailable + '}';
    }

    public String printDescription() {
        return "Imię wolonatriusza: " + name + ", lokalizacja: " + location + ", adres email: " + email
            + ", telefon: " + phone + ", rodzaj pomocy: " + typeOfHelp.getType() + ", czy dostępny: " + isAvailable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Volunteer volunteer = (Volunteer) o;
        return isAvailable == volunteer.isAvailable && name.equals(volunteer.name) && location
            .equals(volunteer.location) && email.equals(volunteer.email) && phone
            .equals(volunteer.phone) && typeOfHelp.equals(volunteer.typeOfHelp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, location, email, phone, typeOfHelp, isAvailable);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public TypeOfHelp getTypeOfHelp() {
        return typeOfHelp;
    }

    public void setTypeOfHelp(TypeOfHelp typeOfHelp) {
        this.typeOfHelp = typeOfHelp;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
