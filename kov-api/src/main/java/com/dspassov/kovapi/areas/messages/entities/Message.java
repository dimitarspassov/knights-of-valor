package com.dspassov.kovapi.areas.messages.entities;

import com.dspassov.kovapi.areas.users.entities.User;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User recipient;

    @Column(name = "title")
    private String title;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    @Column(name = "deleted_by_sender")
    private Boolean isDeletedBySender;

    @Column(name = "deleted_by_recipient")
    private Boolean isDeletedByRecipient;
}
