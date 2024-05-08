package RW.JuomaPeli.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

import com.google.gson.Gson;

import org.springframework.stereotype.Service;

@Service
public class NameService {

	public NameList getAllNames() {
		try {
			String json = new String(Files.readAllBytes(Paths.get("names.json")));
			Gson gson = new Gson();
			NameList names = gson.fromJson(json, NameList.class);
			return names;
		} catch (IOException e) {
			 System.err.println("Error reading file: " + e.getMessage());
		}
		return null;
	}

	public String generateMaleName(){
		Random random = new Random();
		int index = random.nextInt(50);
		NameList names = getAllNames();
		Name name =  names.getMaleNames().get(index);
		String strName = name.getName();
		return strName;
	}
	
	public String generateFemaleName(){
		Random random = new Random();
		int index = random.nextInt(50);
		NameList names = getAllNames();
		Name name =  names.getFemaleNames().get(index);
		String strName = name.getName();
		return strName;
	}

}