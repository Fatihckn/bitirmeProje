package com.bitirmeproje.helper.dto;

import com.bitirmeproje.exception.CustomException;
import org.springframework.http.HttpStatus;

public interface IEntityDtoConverter<Entity,Dto> {
    default Dto convertToDTO(Entity entity){// Entity -> DTO dönüşümü
        throw new CustomException(HttpStatus.BAD_REQUEST,"ConvertToDto metodu bu sınıfta desteklenmiyor.");
    }

    default Entity convertToEntity(Dto dto){// DTO -> Entity dönüşümü
        throw new CustomException(HttpStatus.BAD_REQUEST,"ConvertToEntity metodu bu sınıfta desteklenmiyor.");
    }
}
