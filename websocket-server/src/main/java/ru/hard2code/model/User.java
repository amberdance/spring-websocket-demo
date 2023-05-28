package ru.hard2code.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("users")
@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@ToString
public class User {

    @Id
    private Integer id;

    @NonNull
    private String username, email, firstName, lastName;

    @Builder.Default
    private Role role = Role.USER;

    @Builder.Default
    private boolean enabled = true;
}
