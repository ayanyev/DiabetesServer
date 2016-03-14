package org.coursera.capstone.T1DTeens.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.coursera.capstone.T1DTeens.entities.enums.UserGender;
import org.coursera.capstone.T1DTeens.entities.enums.UserType;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@RestResource
@Table(name="users", schema = "", catalog = "t1dteens")
public class User {

    @Id
    @Column(name="user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name_first")
    private String firstName;

    @Column(name="name_last")
    private String lastName;

    @Column(name="medical_record")
    private Integer medicalRecord;

    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;

    @Column(name="enabled")
    private Boolean enabled;

    @Column(name="date_of_birth")
    @JsonFormat(shape= JsonFormat.Shape.NUMBER)
    private Date dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(name="gender")
    private UserGender gender;

    @Enumerated(EnumType.STRING)
    @Column(name="user_type")
    private UserType userType;

    @Column(name="timestamp")
    private Date timestamp;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private List<CheckIn> checkIns = new ArrayList<>();

    @Column(name="policy")
    private String sharePolicy;

    public User() {
    }

    public User(String userType) {
        this.userType = UserType.valueOf(userType);
    }

    public User(String firstName, String lastName, int medicalRecord, String username, String password, Boolean enabled, Date dateOfBirth, UserGender gender, String userType, Date timestamp, List<CheckIn> checkIns) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.medicalRecord = medicalRecord;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.userType = UserType.valueOf(userType);
        this.timestamp = timestamp;
        this.checkIns = checkIns;
    }

    public String getSharePolicy() {
        return sharePolicy;
    }

    public void setSharePolicy(String sharePolicy) {
        this.sharePolicy = sharePolicy;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUsername(String login) {
        this.username = login;
    }

    public String getUsername() {
        return username;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {


        this.dateOfBirth = dateOfBirth;
    }

    public UserGender getGender() {
        return gender;
    }

    public void setGender(UserGender gender) {
        this.gender = gender;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    public List<CheckIn> getCheckIns() {
        return checkIns;
    }

    public void setCheckIns(List<CheckIn> checkIns) {
        this.checkIns = checkIns;
    }

    public Integer getMedicalRecord() {
        return medicalRecord;
    }

    public void setMedicalRecord(Integer medicalRecord) {
        this.medicalRecord = medicalRecord;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void enforceSharePolicy(){

        String[] rules = this.sharePolicy.split("(?!^)");

        if (rules[0].equals("0")) this.firstName = "";
        if (rules[1].equals("0")) this.lastName = "";
        if (rules[3].equals("0")) this.dateOfBirth = null;
        if (rules[2].equals("0")) this.medicalRecord = null;
        if (rules[4].equals("0")) this.gender = null;
        if (rules[5].equals("0")) this.checkIns = new ArrayList<>();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }
}
