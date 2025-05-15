package losky2987.pp2_practice.domain;

import org.springframework.data.relational.core.mapping.Table;

@Table
public class Admin {
    private final String id;

    public Admin(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
