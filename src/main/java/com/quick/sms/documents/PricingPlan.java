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
//@Accessors(chain = true)
public class PricingPlan implements Serializable {
    private static final long serialVersionUID = 123456L;
    @Id
    private String id;

    /*

     ===============================================================================================================
    |  priceingId    | CreatedUserId      |  priceInPaisa  |  planName         | gstPercentage    | netPrice
	|==========================================================================|==================================
    |   01           |   1                |  18p           |  Basic            | 18%              | = 18p + 18% = 0.21p<== If gst exclude
	|   02           |   1                |  16p           |  Popular          |                                        <=====SUPER_ADMIN
	|   03           |   1                |  10p           |  Premium          |
	| -------------------------------------------------------------------------|----------------------------------
    |   04           |   101              |  18p           |  MyPlan1          | 18%              | 18p - 18% = 14p<== If gst include
	|   05           |   101              |  106           |  MyPlan2          |<======ADMIN
	| -------------------------------------------------------------------------|
    |   06           |   102              |  10p           |  Premium          |<======RESELLER1
	| -------------------------------------------------------------------------|
    |   07           |   103              |  11p           |  IncrediblePlan   |
	|   08           |   103              |  12p           |  MyPlan           |<======RESELLER2
	|   09           |   103              |  20p           |  PremiumPlan      |
	| ------------------------------------------------------------------------ |
    |   10           |   104              |  50p           |  Basic            |<======RESELLER3
     ==========================================================================


     */


    private String planName;
    private int priceInPaisa = 1;
    private float gstPercentage;
    private float netPrice = 0.0f;
    private String hsnNo;
    private String createdUserId;

    public PricingPlan(String createdUserId, int priceInPaisa, String planName, float gstPercentage) {
        this.createdUserId = createdUserId;
        this.priceInPaisa = priceInPaisa;
        this.planName = planName;
        this.gstPercentage = gstPercentage;
    }
}
