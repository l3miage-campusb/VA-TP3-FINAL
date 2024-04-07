package fr.uga.l3miage.spring.tp3.endpoints;

import fr.uga.l3miage.spring.tp3.exceptions.CandidatNotFoundResponse;
import fr.uga.l3miage.spring.tp3.request.SessionCreationRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Tag(name = "Gestion des TestCenters")
@RestController
@RequestMapping("/api/testCenters")
public interface TestCenterEndpoints {


    @Operation(description = "Ajouter à un centre de test une collection d'étudiants.")
    @ApiResponse(responseCode = "202 ", description = "Les étudiants ont bien été éjoutés")
    @ApiResponse(responseCode = "404", description = "Le centre de test ou le candidat n'a pas été trouvé" , content = @Content(schema = @Schema(implementation = CandidatNotFoundResponse.class),mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ApiResponse(responseCode = "400")
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{testCenterId}/addCandidates")
    Boolean addCandidates(@PathVariable Long testCenterId, @RequestBody Set<Long> request);
}
