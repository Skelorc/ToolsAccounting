package wns.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import wns.entity.Contact;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactDTO {

    private long id;
    private String name;
    private String numberPassport;
    private String issuedBy;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateIssuePassport;
    private long roleContact;
    private String comment;
    private String photos;

    public ContactDTO(Contact contact) {
        this.id = contact.getId();
        this.name = contact.getName();
        this.numberPassport = contact.getNumberPassport();
        this.issuedBy = contact.getIssuedBy();
        this.dateIssuePassport = contact.getDateIssuePassport();
        this.roleContact = contact.getRoleContact().getId();
        this.photos = contact.getPhotos().stream().collect(Collectors.joining(","));
        this.comment = contact.getComment().getText();
    }
}
