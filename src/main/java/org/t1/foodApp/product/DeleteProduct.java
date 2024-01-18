package org.t1.foodApp.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteProduct {
        @NotBlank(message = "Barcode cannot be blank")
        @NotNull(message = "Barcode cannot be null")
        private String barcode;

        @NotBlank(message = "storageId cannot be blank")
        @NotNull(message = "storageId cannot be null")
        private String storageId;
}
