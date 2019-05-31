package nl.bank.vergelijker.vergelijkerservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LevensverzekeringInformatie {

    private Double teVerzekerenRisico;
    private Double maandPremieLevensverzekering;
}
