package wns.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import wns.constants.ClassificationProject;
import wns.constants.StatusProject;
import wns.constants.TypeLease;
import wns.entity.Client;
import wns.entity.Project;
import wns.entity.Tools;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProjectDTO {
    private long id;
    private long number;
    private ClassificationProject classification;
    private StatusProject status;
    private String name;
    private TypeLease typeLease;
    private int quantity;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime created;
    private String employee;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime start;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime end;
    private long client_id;
    private String phoneNumber;
    private Set<String> photos;
    private int discount;
    private String note;
    private long sum;
    private long finalSumUsn;
    private int priceTools;
    private long priceWork;
    private int discountByProject;
    private long sumWithDiscount;
    private long received;
    private long remainder;
    private Set<Long> tools_id = new HashSet<>();


    public Project createProjectFromDTO(ProjectDTO dto)
    {
        Project project = new Project();
        project.setClassification(dto.getClassification());
        project.setStatus(dto.getStatus());
        project.setName(dto.getName());
        project.setTypeLease(dto.getTypeLease());
        project.setQuantity(dto.getQuantity());
        project.setCreated(dto.getCreated());
        project.setEmployee(dto.getEmployee());
        project.setStart(dto.getStart());
        project.setEnd(dto.getEnd());
        //пропуск клиента
        project.setPhoneNumber(dto.getPhoneNumber());
        project.setPhotos(dto.getPhotos());
        project.setDiscount(dto.getDiscount());
        project.setNote(dto.getNote());
        project.setSum(dto.getSum());
        project.setFinalSumUsn(dto.getFinalSumUsn());
        project.setPriceTools(dto.getPriceTools());
        project.setPriceWork(dto.getPriceWork());
        project.setDiscountByProject(dto.getDiscountByProject());
        project.setSumWithDiscount(dto.getSumWithDiscount());
        project.setReceived(dto.getReceived());
        project.setRemainder(dto.getRemainder());
        //пропуск оборудования
        return project;
    }

}
