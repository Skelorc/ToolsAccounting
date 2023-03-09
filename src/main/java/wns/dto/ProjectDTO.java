package wns.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import wns.constants.ClassificationProject;
import wns.constants.StatusProject;
import wns.constants.TypeLease;
import wns.entity.Project;
import wns.entity.Tools;
import wns.entity.User;
import wns.entity.WorkingShift;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@NoArgsConstructor
@Data
public class ProjectDTO {
    private long id;
    private long number;
    private ClassificationProject classification;
    private String classification_name;
    private StatusProject status;
    private String status_value;
    private String status_color;
    private String name;
    private TypeLease typeLease;
    private int quantity;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime created;
    private String employee;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime start;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime end;
    private long client_id;
    private String client_name;
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
    private long estimate_id;
    private Set<Long> items = new HashSet<>();
    private List<WorkingShiftDTO> workingShifts = new ArrayList<>();

    public ProjectDTO(Project project)
    {
        this.id = project.getId();
        this.classification = project.getClassification();
        this.classification_name = project.getClassification().getValue();
        this.status = project.getStatus();
        this.status_value = project.getStatus().getStatus();
        this.status_color = project.getStatus().getColor();
        this.name = project.getName();
        this.typeLease = project.getTypeLease();
        this.quantity = project.getQuantity();
        this.created = project.getCreated();
        User authentication = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        this.employee = authentication.getFullName();
        this.start = project.getStart();
        this.end = project.getEnd();
        this.client_id = project.getClient().getId();
        this.client_name = project.getClient().getFullName();
        this.phoneNumber = project.getPhoneNumber();
        this.photos = project.getPhotos();
        this.discount = project.getDiscount();
        this.note = project.getNote();
        this.sum = project.getSum();
        this.finalSumUsn = project.getFinalSumUsn();
        this.priceTools = project.getPriceTools();
        this.priceWork = project.getPriceWork();
        this.discountByProject = project.getDiscountByProject();
        this.sumWithDiscount = project.getSumWithDiscount();
        this.received = project.getReceived();
        this.remainder = project.getRemainder();
        this.estimate_id = project.getEstimate().getId();
        this.items = project.getTools().stream().map(Tools::getId).collect(Collectors.toSet());
    }

    public Project createProjectFromDTO()
    {
        Project project = new Project();
        project.setId(id);
        project.setName(name);
        project.setNumber(number);
        project.setStatus(status);
        project.setTypeLease(typeLease);
        project.setQuantity(quantity);
        project.setCreated(created);
        User authentication = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        project.setEmployee(authentication.getFullName());
        project.setPhotos(photos);
        project.setDiscount(discount);
        project.setNote(note);
        project.setSum(sum);
        project.setFinalSumUsn(finalSumUsn);
        project.setPriceTools(priceTools);
        project.setDiscountByProject(discountByProject);
        project.setSumWithDiscount(sumWithDiscount);
        project.setReceived(received);
        project.setRemainder(remainder);
        project.setClassification(classification);
        project.setPhoneNumber(phoneNumber);
        project.setPriceWork(priceWork);
        return project;
    }


    public Project updateProjectFromDTO(Project project) {
        project.setStart(start);
        project.setEnd(end);
        project.setName(name);
        project.setStatus(status);
        project.setPhotos(photos);
        project.setTypeLease(typeLease);
        project.setClassification(classification);
        project.setCreated(created);
        project.setNote(note);
        return project;
    }
}
