package ru.hard2code.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
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

    @MappedCollection(idColumn = "id")
    private UserRole role;

    @Builder.Default
    private boolean enabled = true;
}
