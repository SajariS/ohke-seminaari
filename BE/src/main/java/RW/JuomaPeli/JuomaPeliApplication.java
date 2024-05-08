package RW.JuomaPeli;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import RW.JuomaPeli.domain.Card;
import RW.JuomaPeli.domain.CardRepository;
import RW.JuomaPeli.domain.Character;
import RW.JuomaPeli.domain.CharacterRepository;
import RW.JuomaPeli.domain.Game;
import RW.JuomaPeli.domain.GameRepository;
import RW.JuomaPeli.domain.Player;
import RW.JuomaPeli.domain.PlayerRepository;
import RW.JuomaPeli.domain.User;
import RW.JuomaPeli.domain.UserRepository;
import RW.JuomaPeli.service.CardService;

@SpringBootApplication
public class JuomaPeliApplication {
	
	

	public static void main(String[] args) {
		SpringApplication.run(JuomaPeliApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner demo(CardRepository cRepo, PlayerRepository pRepo, CharacterRepository characterRepo, GameRepository gRepo,
			UserRepository uRepo, CardService cService) {
	    return (args) -> {
//	        for (int i = 0; i < 100; i++) {
//	        	if(i % 2 == 0) {
//	        		Card card = new Card("Description " + (i + 1), false, true);
//	        		cRepo.save(card);
//	        	}
//	        	else {
//	        		Card card = new Card("Description " + (i + 1), false, false);
//	        		cRepo.save(card);
//	        	}
//	            
//	        }
	        /*
	        gRepo.save(new Game("123456"));
	        pRepo.save(new Player("pelaaja", gRepo.findByCode("123456")));
	        characterRepo.save(new Character("Kumppani", 30, null, pRepo.findByUserName("pelaaja"))); */
	        //admin & admin, vaihdetaan myöhemmin
	        uRepo.save(new User("admin", "$2y$10$CI3EBB65FEVSvuEQRj/ok.edyG8OL7HjkP3KpUZFxFzems2aDUcda", "ADMIN"));
	        
	        //Poista jos käytössä ulkoinen DB
	        cService.generateCards();
	        
	    };
	}
}



