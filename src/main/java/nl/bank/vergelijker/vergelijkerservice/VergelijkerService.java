package nl.bank.vergelijker.vergelijkerservice;

import lombok.NoArgsConstructor;
import nl.bank.vergelijker.vergelijkerservice.domain.*;
import nl.bank.vergelijker.vergelijkerservice.interfaces.lening.BankLening;
import nl.bank.vergelijker.vergelijkerservice.interfaces.lening.LeningRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@NoArgsConstructor
public class VergelijkerService {

    @Autowired
    private LeningRestClient restClient;

    public LeningVergelijking getMaxLeningVergelijking(String volledigeNaam, Double inkomen, Date geboortedatum, Integer looptijdInMaanden, Double rentepercentage) {
        LeningVergelijking leningVergelijking = new LeningVergelijking();
        BankVergelijking bankVergelijking = getNumberOneBank(volledigeNaam, inkomen, geboortedatum, looptijdInMaanden, rentepercentage);
        leningVergelijking.getVergelekenleningen().add(bankVergelijking);
        return leningVergelijking;
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
