package wns.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import wns.constants.TypeClients;

import javax.persistence.Column;
import java.time.LocalDate;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ClientIndividualDTO {
    private long id;
    private TypeClients typeClient;
    private String fullName;
    private int discount;
    private String phoneNumber;
    private String email;
    private String fromComing;
    private int limited;
    private String note;
    private LocalDate birthday;
    private boolean inBlackList;
    private String directorOfPhotography;
    private String production;
    private String numberPassport;
    private String issuedBy;
    private LocalDate dateIssuePassport;
    private String addressReal;
    private LocalDate dateCreating;
    private long rented;
}
