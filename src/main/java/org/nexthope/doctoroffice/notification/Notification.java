package org.nexthope.doctoroffice.notification;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.nexthope.doctoroffice.commons.BaseAudit;
import org.nexthope.doctoroffice.user.User;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "notification")
@Getter
@Setter
@Accessors(chain = true)
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Notification extends BaseAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notification_seq")
    @SequenceGenerator(name = "notification_seq", sequenceName = "notification_seq", allocationSize = 1)
    private Long id;

    private String subject;

    private String content;

    private Boolean isSent;

    @ManyToOne
    private User recipient;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Notification that = (Notification) o;
        return Objects.equals(id, that.id) && Objects.equals(subject, that.subject) && Objects.equals(content, that.content) && Objects.equals(isSent, that.isSent) && Objects.equals(recipient, that.recipient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, subject, content, isSent, recipient);
    }

    @Override
    public Notification setCreationDate(LocalDateTime creationDate) {
        super.setCreationDate(creationDate);
        return this;
    }

    @Override
    public Notification setModificationDate(LocalDateTime modificationDate) {
        super.setModificationDate(modificationDate);
        return this;
    }

    public NotificationRecord toRecord() {
        return new NotificationRecord(id, subject, content, isSent, recipient, getCreationDate(), getModificationDate());
    }

}
