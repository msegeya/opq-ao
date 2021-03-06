/*
  This file is part of opq-ao.

  opa-ao is free software: you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.

  opa-ao is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.

  You should have received a copy of the GNU General Public License
  along with opq-ao.  If not, see <http://www.gnu.org/licenses/>.

  Copyright 2013 Anthony Christe
 */

package models;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains methods relating to the persisted Person entity.
 */
@Entity
public class Person extends Model {
  /**
   * Primary key.
   */
  @Id
  private long primaryKey;

  /**
   * First name.
   */
  @Required
  private String firstName;

  /**
   * Last name.
   */
  @Required
  private String lastName;

  /**
   * E-mail address.
   */
  @Required
  private String email;

  /**
   * Password. The password itself should never be stored to the DB. This field is simply used as a temp. location while
   * the password hash is created.
   */
  private String password;

  /**
   * Password hash.
   */
  @Required
  private byte[] passwordHash;

  /**
   * Password salt. The salt is a random 32 byte value generated at account creating or password update.
   */
  @Required
  private byte[] passwordSalt;

  /**
   * State.
   */
  @Required
  private String state;

  /**
   * City.
   */
  private String city;

  /**
   * Zip code.
   */
  private String zip;

  /**
   * Street name.
   */
  private String streetName;

  /**
   * Street number.
   */
  private String streetNumber;

  /**
   * Convenience constructor for creating Person objects while testing.
   *
   * @param firstName    The first name of the person.
   * @param lastName     The last name of the person.
   * @param email        The email of the person.
   * @param passwordHash The password hash of the person.
   * @param state        The state of the person.
   * @param city         The city of the person.
   * @param zip          The zip of the person.
   * @param streetName   The street name that the person lives on.
   * @param streetNumber The street number that the person lives at.
   */
  public Person(String firstName, String lastName, String email, byte[] passwordHash, String state, String city,
                String zip, String streetName, String streetNumber) {
    this.setFirstName(firstName);
    this.setLastName(lastName);
    this.setEmail(email);
    this.setPasswordHash(passwordHash);
    this.setState(state);
    this.setCity(city);
    this.setZip(zip);
    this.setStreetName(streetName);
    this.setStreetNumber(streetNumber);
  }

  /**
   * Devices that this person is associated with.
   * <p/>
   * Each person may be associated with multiple devices.
   */
  @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
  private List<OpqDevice> devices = new ArrayList<>();

  /**
   * Finder for filtering persisted person entities.
   *
   * @return New finder for filtering persisted person entities.
   */
  public static Finder<Long, Person> find() {
    return new Finder<>(Long.class, Person.class);
  }

  /**
   * Gets the primary key.
   *
   * @return The primary key.
   */
  public long getPrimaryKey() {
    return primaryKey;
  }

  /**
   * Sets the primary key.
   *
   * @param primaryKey The primary key.
   */
  public void setPrimaryKey(long primaryKey) {
    this.primaryKey = primaryKey;
  }

  /**
   * Gets the first name.
   *
   * @return Person's first name.
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * Sets person's first name.
   *
   * @param firstName Person's first name.
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * Gets the person's last name.
   *
   * @return Person's last name.
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * Sets the person's last name.
   *
   * @param lastName Person's last name.
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * Gets the person's e-mail.
   *
   * @return The person's e-mail.
   */
  public String getEmail() {
    return email;
  }

  /**
   * Sets the person's e-mail.
   *
   * @param email Person's e-mail.
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Return the password.
   *
   * @return The person.
   */
  public String getPassword() {
    return this.password;
  }

  /**
   * Uses the supplied password to create a password hash.
   *
   * @param password The plain-text password to generate a hash for.
   */
  public void setPassword(String password) {
    this.password = password;
    this.setPasswordSalt(utils.FormUtils.generateRandomSalt());
    this.setPasswordHash(utils.FormUtils.hashPassword(this.getPassword(), this.getPasswordSalt()));
    this.password = null;
  }

  /**
   * Gets person's password hash.
   *
   * @return Person's password hash.
   */
  public byte[] getPasswordHash() {
    return passwordHash;
  }

  /**
   * Sets the person's password hash.
   *
   * @param passwordHash The password hash for this person.
   */
  public void setPasswordHash(byte[] passwordHash) {
    this.passwordHash = passwordHash;
  }

  /**
   * Returns this person's salt value.
   *
   * @return This person's salt value.
   */
  public byte[] getPasswordSalt() {
    return this.passwordSalt;
  }

  /**
   * Set this person's salt.
   *
   * @param passwordSalt 32-bit random salt.
   */
  public void setPasswordSalt(byte[] passwordSalt) {
    this.passwordSalt = passwordSalt;
  }

  /**
   * Gets state person resides in.
   *
   * @return State person resides in.
   */
  public String getState() {
    return state;
  }

  /**
   * Sets state person resides in.
   *
   * @param state State person resides in.
   */
  public void setState(String state) {
    this.state = state;
  }

  /**
   * Gets city person resides in.
   *
   * @return City person resides in.
   */
  public String getCity() {
    return city;
  }

  /**
   * Sets city person resides in.
   *
   * @param city City person resides in.
   */
  public void setCity(String city) {
    this.city = city;
  }

  /**
   * Gets zip code person resides in.
   *
   * @return Zip code person resides in.
   */
  public String getZip() {
    return zip;
  }

  /**
   * Sets zip code person resides in.
   *
   * @param zip Zip code person resides in.
   */
  public void setZip(String zip) {
    this.zip = zip;
  }

  /**
   * Gets street name person resides in.
   *
   * @return Street name person resides in.
   */
  public String getStreetName() {
    return streetName;
  }

  /**
   * Sets street name person resides in.
   *
   * @param streetName Street name person resides in.
   */
  public void setStreetName(String streetName) {
    this.streetName = streetName;
  }

  /**
   * Gets street number person resides in.
   *
   * @return Street number person resides in.
   */
  public String getStreetNumber() {
    return streetNumber;
  }

  /**
   * Set street number person resides in.
   *
   * @param streetNumber Street number person resides at.
   */
  public void setStreetNumber(String streetNumber) {
    this.streetNumber = streetNumber;
  }

  /**
   * Devices associated with this person.
   *
   * @return Devices associated with this person.
   */
  public List<OpqDevice> getDevices() {
    return devices;
  }

  /**
   * Set devices associated with this person.
   *
   * @param devices Devices associated with this person.
   */
  public void setDevices(List<OpqDevice> devices) {
    this.devices = devices;
  }
}
