package example.kp.demo.domain.product;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ProductStateConverter implements AttributeConverter<ProductState, String> {

    @Override
    public String convertToDatabaseColumn(ProductState attribute) {
        return attribute.getName();
    }

    @Override
    public ProductState convertToEntityAttribute(String dbData) {
        return ProductState.stringToState(dbData);
    }

}
