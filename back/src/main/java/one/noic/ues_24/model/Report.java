package one.noic.ues_24.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import one.noic.ues_24.model.enums.ReportReason;

import java.time.LocalDate;

@Data
@Entity
public class Report {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private ReportReason reason;
    private LocalDate timestamp;
    private Boolean accepted;
}
