package ar.itr.utn.goldsgym.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Data
@Table(name="athlete")
@NoArgsConstructor
@AllArgsConstructor
public class Athlete {
    @Id
    private Long id;
    private String name;
    private int age;
    private double weight;
    private int height;
    private LocalDate startingDate;
    private Goal goal;
    private Level level;
    private boolean active = true;
}
