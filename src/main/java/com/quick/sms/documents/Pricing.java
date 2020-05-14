package com.quick.sms.documents;

import com.fasterxml.jackson.annotation.JsonView;
import com.quick.sms.view.Views;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Document(collection = "Pricing")
@Data
@NoArgsConstructor
@AllArgsConstructor
//@Accessors(chain = true)
public class Pricing implements Serializable {
    private static final long serialVersionUID = 3L;

    @Id
    @JsonView(Views.Public.class)
    private String id;

    @NotEmpty(message = "Pricing name can not be empty, it should be any name like : [\"Basic Pricing\", \"Extreme Pricing\"]")
    @NotNull(message = "Pricing name can not be null, it should be any name like : [\"Basic Pricing\", \"Extreme Pricing\"]")
    @JsonView(Views.Internal.class)
    private String name = "Basic Pricing";

    @NotEmpty(message = "Pricing name can not be empty, it should be any name like : [\"Per SMS Cost\", \"Per Credit\"]")
    @NotNull(message = "Pricing name can not be null, it should be any name like : [\"Per SMS Cost\", \"Per Credit\"]")
    @JsonView(Views.Internal.class)
    private String description = "Per SMS Cost"; //["Per SMS Cost, "Per Credit"]

    @NotEmpty(message = "amount can not be empty, It should contain value like [\"6 paisa\", \"1 Credit\"]  ")
    @NotNull(message = "amount can not be null, It should contain value like [\"6 paisa\", \"1 Credit\"]  ")
    @JsonView(Views.Internal.class)
    private String amount;

    public Pricing(String name, String description, String amount) {
        this.name = name;
        this.description = description;
        this.amount = amount;
    }
}
