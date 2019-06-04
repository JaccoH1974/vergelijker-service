package nl.bank.vergelijker.vergelijkerservice;

import nl.bank.vergelijker.vergelijkerservice.domain.BankVergelijking;
import nl.bank.vergelijker.vergelijkerservice.domain.LeningVergelijking;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
@ActiveProfiles({"test"})
public class VergelijkerControllerTest {

    @InjectMocks
    VergelijkerController controller;

    @Mock
    private VergelijkerService service;

    private static Double inkomen = 75000d;
    private static Integer looptijdInMaanden = 360;
    private static Double jaarrente = 3.1d;
    private static String stringDate = "1999-09-06";

    @Test
    public void getVergelijking() {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date geboortedatum = format.parse(stringDate);
            String volledigeNaam = "Jan Klaassen";

            LeningVergelijking vergelijking = createLeningVergelijking(geboortedatum, volledigeNaam);

            Mockito.when(service.getMaxLeningVergelijking("Jan Klaassen", inkomen, geboortedatum, looptijdInMaanden, jaarrente)).thenReturn(vergelijking);

        ResponseEntity<LeningVergelijking> response = controller.getVergelijking(volledigeNaam, geboortedatum, inkomen, looptijdInMaanden, jaarrente);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(org.springframework.http.HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isEqualTo(vergelijking);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private LeningVergelijking createLeningVergelijking(Date geboortedatum, String volledigeNaam) {
        BankVergelijking bank = new BankVergelijking();
        bank.setNaamBank("Number one Bank");
        bank.getPersoonlijkeInformatie().setVolledigeNaam(volledigeNaam);
        bank.getPersoonlijkeInformatie().setInkomen(inkomen);
        bank.getPersoonlijkeInformatie().setGeboortedatum(geboortedatum);
        bank.getLeninginformatie().setAnnuiteit(new Double(1281.0491967140752d));
        bank.getLeninginformatie().setMaximaalTeLenenBedrag(new Double (300000d));
        bank.getLevensverzekeringInformatie().setMaandPremieLevensverzekering(new Double(33.333333333333336));
        bank.getLevensverzekeringInformatie().setTeVerzekerenRisico(new Double(60000));
        LeningVergelijking vergelijking = new LeningVergelijking();
        vergelijking.getVergelekenleningen().add(bank);
        return vergelijking;
    }
}
