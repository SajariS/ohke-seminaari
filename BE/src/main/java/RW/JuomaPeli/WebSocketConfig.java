package RW.JuomaPeli;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	
	//Määrittää lähetyksen ja vastaanoton osoitteen
	// simplebroker ottaa vastaan viestit, eli tarkemmin tässä menee /topic/input
	// jatko; simplebroker = tila/instanssi mistä käyttäjä "tilaa" viestejä
	//
	// ApplicationDestinationPrefixes lähettää, ja tarkemmin /app/courier
	// tarkennukset osoitteisiin löytyy .web WebSocketController:sta
	// topic, app, ja ws sijalla voi olla mitä tahansa
	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("/topic", "/lobby", "/lobby/event", "/game");
		config.setApplicationDestinationPrefixes("/app");
	}
	
	//Määrittää yhteyden ws yhteyden millä client yhdistää palveluun
	//Yhdistetään clientissä SockJS:llä /ws polkuun 
	//AllowedOrigins pakko olla määritetty, ei saa olla globaali "*"
	//Ainakin chrome vittuilee ankarasti jos näin ei ole
	//Osoitteita voi lisätä setAllowedOrigins kohtaan pilkulla eli
	// setAllowedOrigins("osoite", "toinen osoite") jne
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/ws")
			.setAllowedOrigins("http://localhost:5173/", "http://localhost:81/", "https://sajaris.github.io/")
			.withSockJS();
	}
    
}
