package nl.bank.vergelijker.vergelijkerservice;

import com.github.tomakehurst.wiremock.client.WireMock;
import nl.bank.vergelijker.vergelijkerservice.domain.*;
import nl.bank.vergelijker.vergelijkerservice.interfaces.lening.BankLening;
import nl.bank.vergelijker.vergelijkerservice.interfaces.lening.LeningRestClient;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
@ActiveProfiles({"test"})
public class VergelijkerServiceTest {

    @Autowired
    VergelijkerService service;

    @Autowired
    LeningRestClient restClient;

    @Before
    public void setUp() {
        WireMock.stubFor(WireMock.get(
                WireMock.urlPathEqualTo("/bank/lening"))
                .withQueryParam("geboortedatum", WireMock.equalTo("1999-09-06"))
                .withQueryParam("inkomen", WireMock.equalTo("75000.0"))
                .withQueryParam("looptijdInMaanden", WireMock.equalTo("360"))
                .withQueryParam("jaarrente", WireMock.equalTo("3.1"))
                .willReturn(WireMock.aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withStatus(HttpStatus.SC_OK)
                        .withBody("{\n" +
                                "    \"geboortedatum\": \"1999-09-06T00:00:00.000+0000\",\n" +
                                "    \"maximaalTeLenenBedrag\": 300000,\n" +
                                "    \"inkomen\": 75000,\n" +
                                "    \"annuiteit\": 1281.0491967140752,\n" +
                                "    \"teVerzekerenRisico\": 60000,\n" +
                                "    \"maandPremieLevensverzekering\": 33.333333333333336\n" +
                                "}")));
    }

    @Test
    public void getMaxLeningVergelijking_Test() {
        try {
            String volledigeNaam = "Jan Klaassen";
            Double inkomen = 75000d;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date geboortedatum = format.parse("1999-09-06");
            Integer looptijdInMaanden = 360;
            Double rentepercentage = 3.1d;
            LeningVergelijking vergelijkiing = service.getMaxLeningVergelijking(volledigeNaam, inkomen, geboortedatum, looptijdInMaanden, rentepercentage );
            LeningVergelijking expectedVergelijking = null;
            Assertions.assertThat(vergelijkiing.equals(expectedVergelijking));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    private BankVergelijking getNumberOneBank(String volledigeNaam, Double inkomen, Date geboortedatum, Integer looptijdInMaanden, Double rentepercentage) {

        BankVergelijking bankVergelijking = new BankVergelijking();

        bankVergelijking.setNaamBank("Number One Bank");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strGeboortedatum = dateFormat.format(geboortedatum);
        BankLening lening = restClient.getMaxLening(strGeboortedatum, inkomen.toString(), looptijdInMaanden.toString(), rentepercentage.toString());

        PersoonlijkeInformatie persoonlijkeInformatie = new PersoonlijkeInformatie();
        persoonlijkeInformatie.setGeboortedatum(geboortedatum);
        persoonlijkeInformatie.setInkomen(inkomen);
        persoonlijkeInformatie.setVolledigeNaam(volledigeNaam);
        bankVergelijking.setPersoonlijkeInformatie(persoonlijkeInformatie);

        LeningInformatie leningInfo = new LeningInformatie();
        leningInfo.setMaximaalTeLenenBedrag(lening.getMaximaalTeLenenBedrag());
        leningInfo.setAnnuiteit(lening.getAnnuiteit());
        bankVergelijking.setLeninginformatie(leningInfo);

        LevensverzekeringInformatie levensverzekeringInformatie = new LevensverzekeringInformatie();
        levensverzekeringInformatie.setTeVerzekerenRisico(lening.getTeVerzekerenRisico());
        levensverzekeringInformatie.setMaandPremieLevensverzekering(lening.getMaandPremieLevensverzekering());
        bankVergelijking.setLevensverzekeringInformatie(levensverzekeringInformatie);

        return bankVergelijking;
    }

}
