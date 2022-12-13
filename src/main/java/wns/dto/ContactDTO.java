package wns.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import wns.entity.Comments;
import wns.entity.Contact;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Data
public class ContactDTO {

    private long id;
    private String name;
    private String numberPassport;
    private String issuedBy;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateIssuePassport;
    private String roleClient;
    private List<String> commentsList;
    private Set<String> photos;

    public ContactDTO(Contact contact) {
        this.id = contact.getId();
        this.name = contact.getName();
        this.numberPassport = contact.getNumberPassport();
        this.issuedBy = contact.getIssuedBy();
        this.dateIssuePassport = contact.getDateIssuePassport();
        this.roleClient = contact.getRoleContact().getRole();
        this.commentsList = contact.getCommentsList().stream().map(Comments::getText).collect(Collectors.toList());
        this.photos = contact.getPhotos();
    }
}
