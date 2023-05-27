package ru.hard2code.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("roles")
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@ToString
public class UserRole {

    @Id
    private Integer id;

    private Role name;


    public enum Role {

        ADMIN,
        USER

    }

}
