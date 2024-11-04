package ar.itr.utn.goldsgym.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@Data
@Table(name="routine")
@NoArgsConstructor
@AllArgsConstructor
public class Routine {
    @Id
    private Long id;
    private String name;
    private String description;
    private int duration;
    private int weeklyFrequency;
    private boolean active;
}
