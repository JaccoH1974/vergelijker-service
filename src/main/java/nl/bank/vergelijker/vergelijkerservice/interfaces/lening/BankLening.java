package nl.bank.vergelijker.vergelijkerservice.interfaces.lening;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankLening {

    private Date geboortedatum;
    private Double maximaalTeLenenBedrag;
    private Double inkomen;
    private Double annuiteit;
    private Double teVerzekerenRisico;
    private Double maandPremieLevensverzekering;

}
