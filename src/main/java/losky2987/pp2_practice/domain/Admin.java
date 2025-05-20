package losky2987.pp2_practice.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;

@Table
public class Admin {
    @Id
    private final Long id;
    private final long oauth2Id;

    @PersistenceCreator
    public Admin(Long id, Long oauth2Id) {
        this.id = id;
        this.oauth2Id = oauth2Id;
    }

    public Long getId() {
        return id;
    }

    public long getOauth2Id() {
        return oauth2Id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Admin admin = (Admin) o;
        return Objects.equals(id, admin.id) && Objects.equals(oauth2Id, admin.oauth2Id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, oauth2Id);
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", oauth2_id=" + oauth2Id +
                '}';
    }
}
