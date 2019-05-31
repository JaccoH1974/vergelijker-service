package nl.bank.vergelijker.vergelijkerservice;

import lombok.extern.slf4j.Slf4j;
import nl.bank.vergelijker.vergelijkerservice.domain.LeningVergelijking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.QueryParam;
import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/bank/vergelijker")
public class VergelijkerController {

    @Autowired
    private VergelijkerService service;

    @RequestMapping
    ResponseEntity<LeningVergelijking> getVergelijking(
            @QueryParam("volledigeNaam") String volledigeNaam,
            @QueryParam("geboortedatum") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date geboortedatum,
            @QueryParam("inkomen") Double inkomen,
            @QueryParam("looptijdInMaanden") Integer looptijdInMaanden,
            @QueryParam("jaarrente") Double jaarrente) {
        log.debug("Request received");
        if (volledigeNaam == null || geboortedatum == null || inkomen == null || looptijdInMaanden == null || jaarrente == null) {
            return ResponseEntity.badRequest().build();
        } else {
            LeningVergelijking vergelijking = service.getMaxLeningVergelijking(volledigeNaam, inkomen, geboortedatum, looptijdInMaanden, jaarrente);
            return ResponseEntity.ok(vergelijking);
        }
    }
}
