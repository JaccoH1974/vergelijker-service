package nl.bank.vergelijker.vergelijkerservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankVergelijking {
    private String naamBank;
    private PersoonlijkeInformatie persoonlijkeInformatie = new PersoonlijkeInformatie();
    private LeningInformatie leninginformatie = new LeningInformatie();
    private LevensverzekeringInformatie levensverzekeringInformatie = new LevensverzekeringInformatie();

}
