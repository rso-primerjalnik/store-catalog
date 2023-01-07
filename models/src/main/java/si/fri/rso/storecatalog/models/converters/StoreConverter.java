package si.fri.rso.storecatalog.models.converters;

import si.fri.rso.storecatalog.lib.Store;
import si.fri.rso.storecatalog.models.entities.StoreEntity;

import java.util.stream.Collectors;

public class StoreConverter {

    public static Store toDto(StoreEntity entity) {

        Store dto = new Store();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setUrl(entity.getUrl());

        return dto;
    }

    public static StoreEntity toEntity(Store dto) {

        StoreEntity entity = new StoreEntity();
        entity.setName(dto.getName());
        entity.setUrl(dto.getUrl());

        return entity;
    }
}
