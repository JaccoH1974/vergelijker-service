package nl.bank.vergelijker.vergelijkerservice.contracts;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import nl.bank.vergelijker.vergelijkerservice.interfaces.lening.BankLening;
import nl.bank.vergelijker.vergelijkerservice.interfaces.lening.LeningRestClient;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        properties = {"lening-service.port = 9090"})
@AutoConfigureStubRunner(stubsMode = StubRunnerProperties.StubsMode.LOCAL,
 ids = "nl.bank.lening:lening-service:+:stubs:9090")
@ActiveProfiles({"test"})
public class LeningServiceContractTest {

    @Autowired
    private LeningRestClient restClient;

    @Test
    public void goodRequest() throws Exception {
        String geboortedatumStr = "1984-11-11";
        String inkomenStr = "85000";
        String looptijdInMaanden ="360";
        String jaarrente = "2.5";
        BankLening lening = restClient.getMaxLening(geboortedatumStr, inkomenStr, looptijdInMaanden, jaarrente);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date geboortedatum = format.parse("1984-11-11 01:00:00");
        Double maximaalTeLenenBedrag = 340000d;
        Double inkomen = 85000d;
        Double annuiteit = 1500d;
        Double teVerzekerenRisico = 65000d;
        Double maandPremieLevensverzekering = 45d;
        BankLening expectedlening = new BankLening(geboortedatum, maximaalTeLenenBedrag, inkomen,annuiteit, teVerzekerenRisico, maandPremieLevensverzekering);
        Assert.assertEquals(expectedlening, lening);
    }

    @Test
    public void badRequest() throws Exception {
        try {
            String geboortedatumStr = "1984-11-11";
            String inkomenStr = "85000";
            String looptijdInMaanden ="360";
            BankLening lening = restClient.getMaxLening(geboortedatumStr, inkomenStr, looptijdInMaanden, null);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Assert.fail();
        } catch (FeignException e){
            log.info("Status {}", e.status());
            Assert.assertEquals(HttpStatus.SC_BAD_REQUEST, e.status());
        }
    }

}
