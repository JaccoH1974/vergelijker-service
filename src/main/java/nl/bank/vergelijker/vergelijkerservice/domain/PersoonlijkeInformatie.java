package nl.bank.vergelijker.vergelijkerservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersoonlijkeInformatie {
    private String VolledigeNaam;
    private Date geboortedatum ;
    private Double inkomen;
}
