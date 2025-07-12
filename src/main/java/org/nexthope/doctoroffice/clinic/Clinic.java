package org.nexthope.doctoroffice.clinic;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.nexthope.doctoroffice.commons.BaseAudit;
import org.nexthope.doctoroffice.user.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity(name = "clinic")
@Getter
@Setter
@Accessors(chain = true)
@ToString(exclude = "users")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Clinic extends BaseAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clinic_seq")
    @SequenceGenerator(name = "clinic_seq", sequenceName = "clinic_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToMany(mappedBy = "clinic", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<User> users;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Clinic clinic = (Clinic) o;
        return Objects.equals(id, clinic.id) && Objects.equals(name, clinic.name) && Objects.equals(address, clinic.address) && Objects.equals(city, clinic.city) && Objects.equals(phoneNumber, clinic.phoneNumber) && Objects.equals(users, clinic.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, city, phoneNumber, users);
    }

    public Clinic setCreationDate(LocalDateTime creationDate) {
        super.setCreationDate(creationDate);
        return this;
    }

    public Clinic setModificationDate(LocalDateTime modificationDate) {
        super.setModificationDate(modificationDate);
        return this;
    }

    public ClinicRecord toRecord() {
        return new ClinicRecord(id, name, address, city, phoneNumber, getCreationDate(), getModificationDate());
    }

}
