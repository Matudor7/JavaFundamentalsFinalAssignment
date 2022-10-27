package librarymanager.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Member implements Serializable {
    static final long serialVersionUID = 1L;

    int memberId;
    String firstName;
    String lastName;
    LocalDate birthdate;

    public Member(String firstName, String lastname, LocalDate birthdate) {
        this.firstName = firstName;
        this.lastName = lastname;
        this.birthdate = birthdate;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
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

    public void setLastName(String lastname) {
        this.lastName = lastname;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }
}
