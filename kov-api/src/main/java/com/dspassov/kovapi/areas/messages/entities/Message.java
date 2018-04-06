package com.dspassov.kovapi.areas.messages.entities;

import com.dspassov.kovapi.areas.users.entities.User;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Entity
@Table(name = "messages")
public class Message {

    private static final int TITLE_MIN_LENGTH = 5;
    private static final int TITLE_MAX_LENGTH = 35;
    private static final int CONTENT_MIN_LENGTH = 5;
    private static final int CONTENT_MAX_LENGTH = 5000;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User recipient;

    @NotNull
    @Length(min = TITLE_MIN_LENGTH, max = TITLE_MAX_LENGTH)
    @Column(name = "title")
    private String title;

    @NotNull
    @Length(min = CONTENT_MIN_LENGTH, max = CONTENT_MAX_LENGTH)
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @NotNull
    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    @Column(name = "deleted_by_sender")
    private Boolean isDeletedBySender;

    @Column(name = "deleted_by_recipient")
    private Boolean isDeletedByRecipient;

    public Message() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Boolean getDeletedBySender() {
        return isDeletedBySender;
    }

    public void setDeletedBySender(Boolean deletedBySender) {
        isDeletedBySender = deletedBySender;
    }

    public Boolean getDeletedByRecipient() {
        return isDeletedByRecipient;
    }

    public void setDeletedByRecipient(Boolean deletedByRecipient) {
        isDeletedByRecipient = deletedByRecipient;
    }
}
