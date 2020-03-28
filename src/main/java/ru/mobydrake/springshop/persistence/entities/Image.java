package ru.mobydrke.springshop.persistence.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.mobydrke.springshop.persistence.entities.utils.PersistableEntity;

import javax.persistence.Entity;
import java.io.Serializable;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Image extends PersistableEntity implements Serializable {

    private static final long SUID = 1L;

    private String name;

}
