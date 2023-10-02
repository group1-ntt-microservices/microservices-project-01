package com.ntt.microserviceaccounts.domain.model.enity;


import lombok.*;

import javax.persistence.*;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "currentaccounts")
public class CurrentAccount extends BankAccount{
    @Id
    private String id;
    private float maintenanceFee;

    @ElementCollection
    @CollectionTable(name = "account_holder_ids", joinColumns = @JoinColumn(name = "current_account_id"))
    private List<String> accountHolderIds;

    @ElementCollection
    @CollectionTable(name = "authorized_signatory_ids", joinColumns = @JoinColumn(name = "current_account_id"))
    private List<String> authorizedSignatoryIds;

}
