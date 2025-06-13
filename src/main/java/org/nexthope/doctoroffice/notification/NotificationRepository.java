package org.nexthope.doctoroffice.notification;

import org.nexthope.doctoroffice.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    boolean existsBySubjectAndRecipientAndCreationDate(String subject, User recipient, LocalDateTime creationDate);

}
