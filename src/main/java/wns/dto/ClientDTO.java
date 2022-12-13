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
import java.util.stream.Collectors;

@NoArgsConstructor
@Data
public class ClientDTO {
    private long id;
    private TypeClients typeClient;
    private String account;
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
    private List<Long> projects_id;
    private Set<String> photos;
    private long rented;


    public ClientDTO(Client client)
    {
        this.id = client.getId();
        this.account = client.getAccount();
        this.typeClient = client.getTypeClient();
        this.fullName = client.getFullName();
        this.legalName = client.getLegalName();
        this.discount = client.getDiscount();
        this.phoneNumber = client.getPhoneNumber();
        this.email = client.getEmail();
        this.fromComing = client.getFromComing();
        this.limited = client.getLimited();
        this.note = client.getNote();
        this.birthday = client.getBirthday();
        this.inBlackList = client.isInBlackList();
        this.directorOfPhotography = client.getDirectorOfPhotography();
        this.production = client.getProduction();
        this.numberPassport = client.getNumberPassport();
        this.issuedBy = client.getIssuedBy();
        this.dateIssuePassport = client.getDateIssuePassport();
        this.addressReal = client.getAddressReal();
        this.addressLegal = client.getAddressLegal();
        this.inn = client.getInn();
        this.kpp = client.getKpp();
        this.ogrn = client.getOgrn();
        this.fullNameSupervisor = client.getFullNameSupervisor();
        this.jobTitleSupervisor = client.getJobTitleSupervisor();
        this.inFace = client.getInFace();
        this.based = client.getBased();
        this.rs = client.getRs();
        this.bank = client.getBank();
        this.ks = client.getKs();
        this.bik = client.getBik();
        this.dateCreating = client.getDateCreating();
        this.projects_id = client.getProjects().stream().map(Project::getId).collect(Collectors.toList());
        this.photos = client.getPhotos();
        this.rented = client.getRented();
    }

    public Client createClient()
    {
        Client client = new Client();
        client.setTypeClient(typeClient);
        client.setFullName(fullName);
        client.setAccount(account);
        client.setLegalName(legalName);
        client.setDiscount(discount);
        client.setPhoneNumber(phoneNumber);
        client.setEmail(email);
        client.setFromComing(fromComing);
        client.setLimited(limited);
        client.setNote(note);
        client.setBirthday(birthday);
        client.setInBlackList(inBlackList);
        client.setDirectorOfPhotography(directorOfPhotography);
        client.setProduction(production);
        client.setNumberPassport(numberPassport);
        client.setIssuedBy(issuedBy);
        client.setDateIssuePassport(dateIssuePassport);
        client.setAddressReal(addressReal);
        client.setAddressLegal(addressLegal);
        client.setInn(inn);
        client.setKpp(kpp);
        client.setOgrn(ogrn);
        client.setFullNameSupervisor(fullNameSupervisor);
        client.setJobTitleSupervisor(jobTitleSupervisor);
        client.setInFace(inFace);
        client.setBased(based);
        client.setRs(rs);
        client.setBank(bank);
        client.setKs(ks);
        client.setBik(bik);
        client.setDateCreating(dateCreating);
        client.setPhotos(photos);
        client.setRented(rented);
        return client;
    }


    @Override
    public String toString() {
        return "ClientDTO{" +
                "id=" + id +
                ", typeClient=" + typeClient +
                ", account=" + account +
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
