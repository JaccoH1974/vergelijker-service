package nl.bank.vergelijker.vergelijkerservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeningInformatie {

    private Double maximaalTeLenenBedrag;
    private Double annuiteit;
}
