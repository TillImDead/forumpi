package esprit.collaborative_space;

import esprit.collaborative_space.entity.User;
import esprit.collaborative_space.repository.IUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CollaborativeSpaceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CollaborativeSpaceApplication.class, args);
	}
	@Bean
	public CommandLineRunner demo(IUserRepository userRepository) {
		return (args) -> {
			// Créez un utilisateur par défaut
			User user = new User();
			user.setName("John Doe");
			user.setEmail("john.doe@example.com");
			user.setPassword("password123");
			// Associer à un forum et d'autres relations si nécessaire
			// Forum forum = ... (récupérer un forum existant ou en créer un)

			// Sauvegarder l'utilisateur dans la base de données
			userRepository.save(user);

			System.out.println("Utilisateur créé : " + user);
		};
	}
}
