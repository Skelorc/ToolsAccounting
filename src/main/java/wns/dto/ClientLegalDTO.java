package wns.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import wns.constants.TypeClients;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ClientLegalDTO {
    private long id;
    private TypeClients typeClient;
    private String fullName;
    private String legalName;
    private int discount;
    private String phoneNumber;
    private String email;
    private String fromComing;
    private int limited;
    private String note;
    private boolean inBlackList;
    private String addressReal;
    private String addressLegal;
    private String inn;
    private String kpp;
    private String ogrn;
    private String fullNameSupervisor;
    private String jobTitleSupervisor;
    private String inFace;
    private String based;
    private String rs;
    private String bank;
    private String ks;
    private String bik;
    private LocalDate dateCreating;
    private long rented;


}
