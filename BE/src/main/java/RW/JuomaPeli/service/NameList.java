package RW.JuomaPeli.service;

import java.util.List;

public class NameList {
	private List<Name> male_names;
    private List<Name> female_names;

    public List<Name> getMaleNames() {
        return male_names;
    }

    public void setMaleNames(List<Name> male_names) {
        this.male_names = male_names;
    }

    public List<Name> getFemaleNames() {
        return female_names;
    }

    public void setFemaleNames(List<Name> female_names) {
        this.female_names = female_names;
    }
    
}
