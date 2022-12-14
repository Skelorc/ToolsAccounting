package wns.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.context.SecurityContextHolder;
import wns.constants.StatusTools;
import wns.entity.Status;
import wns.entity.Tools;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusToolDTO {
    private long id;
    private long tools_id;
    private String data;
    private String tools;
    private StatusTools statusTools;
    private String status_value;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime created;
    private String employee;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate start;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate end;
    private String executor;
    private String phone_number;
    private String note;
    private Set<String> photos;
    private List<Identifiers> items;
    private long price;
    private long priceRepair;
    private long priceSell;
    private long priceOff;

    public static Status createStatusWithTools(Tools tools, StatusTools status_tool)
    {
        Status status = new Status();
        status.setStatusTools(status_tool);
        status.setTools(tools);
        status.setCreated(LocalDateTime.now());
        status.setEmployee(SecurityContextHolder.getContext().getAuthentication().getName());
        status.setPhotos(tools.getPhotos());
        return status;
    }
    public StatusToolDTO (Status status)
    {
        this.id = status.getId();
        this.tools_id = status.getTools().getId();
        this.tools = status.getTools().getName();
        this.statusTools = status.getStatusTools();
        this.status_value = status.getStatusTools().getValue();
        this.created = status.getCreated();
        this.employee = status.getEmployee();
        this.start = status.getStart();
        this.end = status.getEnd();
        this.executor = status.getExecutor();
        this.phone_number = status.getPhone_number();
        this.note = status.getNote();
        this.photos = status.getPhotos();
        this.priceRepair = status.getPriceRepair();
        this.priceSell = status.getPriceSell();
        this.priceOff = status.getPriceOff();
    }



    @Override
    public String toString() {
        return "StatusToolsDTO{" +
                "id=" + id +
                ", statusTools=" + statusTools +
                ", created=" + created +
                ", employee='" + employee + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", executor='" + executor + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", note='" + note + '\'' +
                ", photos_status=" + photos +
                ", priceRepair=" + priceRepair +
                ", priceSell=" + priceSell +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatusToolDTO that = (StatusToolDTO) o;
        return id == that.id && tools_id == that.tools_id && price == that.price && priceRepair == that.priceRepair && priceSell == that.priceSell && priceOff == that.priceOff && Objects.equals(data, that.data) && Objects.equals(tools, that.tools) && statusTools == that.statusTools && Objects.equals(status_value, that.status_value) && Objects.equals(created, that.created) && Objects.equals(employee, that.employee) && Objects.equals(start, that.start) && Objects.equals(end, that.end) && Objects.equals(executor, that.executor) && Objects.equals(phone_number, that.phone_number) && Objects.equals(note, that.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tools_id, data, tools, statusTools, status_value, created, employee, start, end, executor, phone_number, note, price, priceRepair, priceSell, priceOff);
    }
}
