package ar.itr.utn.goldsgym.controller;

import ar.itr.utn.goldsgym.model.dto.AthleteDTO;
import ar.itr.utn.goldsgym.usecase.MasterUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/master")
public class MasterController {

    private final MasterUseCase masterUseCase;

    public MasterController(MasterUseCase masterUseCase) {
        this.masterUseCase = masterUseCase;
    }

    @Operation(summary = "Obtener todos los atletas", description = "Devuelve una lista de todos los atletas registrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de atletas obtenida satisfactoriamente",
                    content = @Content(schema = @Schema(implementation = AthleteDTO.class)))
    })
    @GetMapping
    public List<AthleteDTO> getAllAthletes() {
        return masterUseCase.getAllAthletes();
    }

    @Operation(summary = "Obtener un atleta por ID", description = "Devuelve los detalles de un atleta específico por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atleta encontrado",
                    content = @Content(schema = @Schema(implementation = AthleteDTO.class))),
            @ApiResponse(responseCode = "404", description = "Atleta no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AthleteDTO> getAthleteById(
            @Parameter(description = "ID del atleta a buscar") @PathVariable Long id) {
        AthleteDTO athleteDTO = masterUseCase.getAthleteById(id);
        if (athleteDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(athleteDTO);
    }
}


