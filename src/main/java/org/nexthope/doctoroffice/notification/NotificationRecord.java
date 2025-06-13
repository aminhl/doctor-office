package org.nexthope.doctoroffice.notification;

import org.nexthope.doctoroffice.user.User;

import java.time.LocalDateTime;

public record NotificationRecord(Long id,
                                 String subject,
                                 String content,
                                 Boolean isSent,
                                 User recipient, // TODO: UserRecord
                                 LocalDateTime creationDate,
                                 LocalDateTime lastModifiedDate) {

    public Notification toNotification() {
        return new Notification()
                .setId(id)
                .setSubject(subject)
                .setContent(content)
                .setIsSent(isSent)
                .setRecipient(recipient)
                .setCreationDate(creationDate)
                .setModificationDate(lastModifiedDate);
    }
}
