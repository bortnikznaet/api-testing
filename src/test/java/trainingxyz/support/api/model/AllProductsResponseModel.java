package trainingxyz.support.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import models.Product;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllProductsResponseModel {
    private List<Product> records;
}

