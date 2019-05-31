package nl.bank.vergelijker.vergelijkerservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeningVergelijking {

    public List<BankVergelijking> vergelekenleningen = new ArrayList<>();
}
