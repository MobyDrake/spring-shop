package ru.mobydrke.springshop.persistence.entities.utils;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@Data
@MappedSuperclass
public class PersistableEntity {
    @Id
    private UUID id;
}
