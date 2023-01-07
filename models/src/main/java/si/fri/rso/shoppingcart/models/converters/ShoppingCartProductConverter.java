package si.fri.rso.shoppingcart.models.converters;

import si.fri.rso.shoppingcart.lib.ShoppingCartProduct;
import si.fri.rso.shoppingcart.models.entities.ShoppingCartProductEntity;

public class ShoppingCartProductConverter {

    public static ShoppingCartProduct toDto(ShoppingCartProductEntity entity) {

        ShoppingCartProduct dto = new ShoppingCartProduct();
        dto.setId(entity.getId());
        dto.setCartId(entity.getCartId());
        dto.setProductId(entity.getProductId());
        dto.setQuantity(entity.getQuantity());

        return dto;
    }

    public static ShoppingCartProductEntity toEntity(ShoppingCartProduct dto) {

        ShoppingCartProductEntity entity = new ShoppingCartProductEntity();
        entity.setId(dto.getId());
        entity.setCartId(dto.getCartId());
        entity.setProductId(dto.getProductId());
        entity.setQuantity(dto.getQuantity());

        return entity;
    }
}
