package ru.stqa.pft.addressbook.model;

import java.util.Objects;

public class ContactData {
  private int id;
  private  String firstname;
  private final String middlename;
  private String lastname;
  private final String nickname;
  private final String company;
  private String address;
  private String mobile;
  private String group;
  private final String day;
  private final String month;
  private final String year;
  private final String notes;

  public ContactData(int id, String firstname, String middlename, String lastname,
                     String nickname, String company, String address, String mobile,
                     String group, String day, String month, String year, String notes) {
    this.id = id;
    this.firstname = firstname;
    this.middlename = middlename;
    this.lastname = lastname;
    this.nickname = nickname;
    this.company = company;
    this.address = address;
    this.mobile = mobile;
    this.group = group;
    this.day = day;
    this.month = month;
    this.year = year;
    this.notes = notes;
  }

  public ContactData(String firstname, String middlename,
                     String lastname, String nickname, String company,
                     String address, String mobile, String group,
                     String day, String month, String year, String notes) {
    this.id = Integer.MAX_VALUE;
    this.firstname = firstname;
    this.middlename = middlename;
    this.lastname = lastname;
    this.nickname = nickname;
    this.company = company;
    this.address = address;
    this.mobile = mobile;
    this.group = group;
    this.day = day;
    this.month = month;
    this.year = year;
    this.notes = notes;
  }

  public String getFirstname() {
    return firstname;
  }

  public String getMiddlename() {
    return middlename;
  }

  public String getLastname() {
    return lastname;
  }

  public String getNickname() {
    return nickname;
  }

  public String getCompany() {
    return company;
  }

  public String getAddress() {
    return address;
  }

  public String getMobile() {
    return mobile;
  }

  public String getDay() {
    return day;
  }

  public String getMonth() {
    return month;
  }

  public String getYear() {
    return year;
  }

  public String getNotes() {
    return notes;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public String getGroup() {
    return group;

  }

  public void setAddress(String address) {
    this.address = address;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ContactData that = (ContactData) o;
    return Objects.equals(firstname, that.firstname) &&
            Objects.equals(lastname, that.lastname);
  }

  @Override
  public int hashCode() {
    return Objects.hash(firstname, lastname);
  }

  @Override
  public String toString() {
    return "ContactData{" +
            "id=" + id +
            ", firstname='" + firstname + '\'' +
            ", lastname='" + lastname + '\'' +
            '}';
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }
}
