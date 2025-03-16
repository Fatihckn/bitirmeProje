package com.bitirmeproje.helper.dto;

public interface IEntityDtoConvert<Entity,Dto> {
    Dto convertToDTO(Entity entity);  // Entity -> DTO dönüşümü
    Entity convertToEntity(Dto dto);  // DTO -> Entity dönüşümü
}
