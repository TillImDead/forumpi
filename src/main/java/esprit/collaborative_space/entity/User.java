package esprit.collaborative_space.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    String email;
    String password;

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String img) {
        this.password = password;
    }

    @ManyToMany
    List<Formation>formations;
    @ManyToMany
    List<Room>rooms;
    @Getter
    @OneToMany(mappedBy = "user")
    List<Post>posts;

}
