package example.kp.demo.domain.product;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.Arrays;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ProductState{
    
    INVESTING(0, "진행중"),
    FINISHED(1, "종료");

    private int type;
    private String name;

    ProductState(int type, String name){
        this.type = type;
        this.name = name;
    }
    
    public static ProductState stringToState(String name){
        return Arrays.stream(ProductState.values())
            .filter(v -> v.getName().equals(name))
            .findAny()
            .orElseThrow(()-> new ProductStateException());
    }
}