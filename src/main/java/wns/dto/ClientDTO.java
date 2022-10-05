package wns.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import wns.constants.TypeClients;
import wns.entity.Client;
import wns.entity.Project;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Data
public class ClientDTO {
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
    private LocalDate birthday;
    private boolean inBlackList;
    private String directorOfPhotography;
    private String production;
    private String numberPassport;
    private String issuedBy;
    private LocalDate dateIssuePassport;
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
    private List<Project> projects;
    private Set<String> photos;
    private long rented;

    @Override
    public String toString() {
        return "ClientDTO{" +
                "id=" + id +
                ", typeClient=" + typeClient +
                ", fullName='" + fullName + '\'' +
                ", legalName='" + legalName + '\'' +
                ", discount=" + discount +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", fromComing='" + fromComing + '\'' +
                ", limited=" + limited +
                ", note='" + note + '\'' +
                ", birthday=" + birthday +
                ", inBlackList=" + inBlackList +
                ", directorOfPhotography='" + directorOfPhotography + '\'' +
                ", production='" + production + '\'' +
                ", numberPassport='" + numberPassport + '\'' +
                ", issuedBy='" + issuedBy + '\'' +
                ", dateIssuePassport=" + dateIssuePassport +
                ", addressReal='" + addressReal + '\'' +
                ", addressLegal='" + addressLegal + '\'' +
                ", inn='" + inn + '\'' +
                ", kpp='" + kpp + '\'' +
                ", ogrn='" + ogrn + '\'' +
                ", fullNameSupervisor='" + fullNameSupervisor + '\'' +
                ", jobTitleSupervisor='" + jobTitleSupervisor + '\'' +
                ", inFace='" + inFace + '\'' +
                ", based='" + based + '\'' +
                ", rs='" + rs + '\'' +
                ", bank='" + bank + '\'' +
                ", ks='" + ks + '\'' +
                ", bik='" + bik + '\'' +
                ", dateCreating=" + dateCreating +
                ", rented=" + rented +
                '}';
    }
}
