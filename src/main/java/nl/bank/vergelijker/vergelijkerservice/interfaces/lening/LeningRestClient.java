package nl.bank.vergelijker.vergelijkerservice.interfaces.lening;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient (name= "lening-service", url="http://${lening-service.host}:${lening-service.port}")
public interface LeningRestClient {

    @RequestMapping(method = RequestMethod.GET, value = "/bank/lening", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    BankLening getMaxLening(
            @RequestParam("geboortedatum") String geboortedatum,
            @RequestParam("inkomen") String inkomen,
            @RequestParam("looptijdInMaanden") String looptijdInMaanden,
            @RequestParam("jaarrente") String jaarrente);
}
