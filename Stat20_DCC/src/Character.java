import java.util.Random;

public class Character {
	String playerName;					//Store as the full string
	String characterName;				//Store as the full string
	int gender;							//Store as 0/1 for m/f
	int occupationNo;					//Store as 0/1/2... for any of 100 occupations.
	int alignment;						//Store as 0/1/2 for law, neutral, chaos
	int hp = 0;							//Health Points, stored as an int
	int cp = 0;							//Copper Pieces, stored as an int
	int[] abilityScores = new int[6];	//The contents thereof will be stored strength/agility/stamina/personality/luck/intelligence.

	//Creator method
	public Character() {
	}

	//Setters for each member field
	//All but the abilityScores setters are straightforward--give a value, now it's stored in
	//the character's relevant field.
	//AbilityScores has two setters--one for entering one specific score somewhere, and one for
	//passing and copying in a whole array.
	public void setPlayerName(String p) {
		this.playerName = p;
	}
	public void setCharacterName(String c) {
		this.characterName = c;
	}
	public void setGender(int g) {
		this.gender = g;
	}
	public void setOccupation(int o) {
		this.occupationNo = o;
	}
	public void setAlignment(int a) {
		this.alignment = a;
	}
	public void setHP(int hp) {
		this.hp = hp;
	}
	public void setCP(int cp) {
		this.cp = cp;
	}
	public void setAbilityScore(int score, int location) {
		this.abilityScores[location] = score;
	}
	public void setAbilityScores(int[] scores) {
		this.abilityScores = scores;
	}

	//Getters for each member field.
	//Here, the goal is simply to return how the value was stored--any further qualification of the
	//results, i.e., turning 0/1 to male/female, etc., will be handled by the GUI class.
	public String getPlayerName() {
		return this.playerName;
	}
	public String getCharacterName() {
		return this.characterName;
	}
	public int getGender() {
		return this.gender;
	}
	public int getOccupation() {
		return this.occupationNo;
	}
	public int getAlignment() {
		return this.alignment;
	}
	public int getHP() {
		return this.hp;
	}
	public int getCP() {
		return this.cp;
	}

	//A getter specifically designed to show the modifier for any given ability score.
	//Takes advantage of the fallthrough property of switch statements!
	public int getModifier(int ablScore) {
		int mod = -777;
		switch (ablScore) {
		case 3:
			mod = -3; break;
		case 4:
		case 5:
			mod = -2; break;
		case 6:
		case 7:
		case 8:
			mod = -1; break;
		case 9:
		case 10:
		case 11:
		case 12:
			mod = 0; break;
		case 13:
		case 14:
		case 15:
			mod = 1; break;
		case 16:
		case 17:
			mod = 2; break;
		case 18:
			mod = 3; break;
		default: break;
		}
		return mod;
	}

	//Special methods for extra functionality.

	//Basic random die roller...
	public int randomRoll(int min, int max) {
		Random random = new Random();
		int randInt =  random.nextInt(max + 1 - min) + min;
		return randInt;
	}

	//There are 100 occupation results at 0 level, which also determine beginning weapons and trade goods.
	public void randOccupation() {
		this.occupationNo = randomRoll(1, 100);
	}

	//There are 3 alignments in DCC--lawful, neutral, and chaotic--and they are stored as a 0/1/2.
	public void randAlignment() {
		this.alignment = randomRoll(0, 2);
	}

	//Rolling for ability scores is described as 3d6, once per each score (of which there are 6).
	//Therefore, we can think of randomRoll as a 6-sided die, call it thrice per, and slap each
	//sum down in each of the 6 array spots as needed.
	//It is technically possible to double-nest and recursively call randomRoll three times per
	//score, but I just felt this way makes it easier to look at.
	public void randAbilityScores() {
		for (int v = 0; v < 6; v++){
		this.abilityScores[v] = (randomRoll(1, 6) + randomRoll(1, 6) + randomRoll(1, 6));
		}
	}

	//Each level 0 player starts with 5d12 copper pieces.
	//Uses a recursive call that just adds each roll to the previously calculated cp; since we
	//initialize cp at 0, the first call is 0 plus a d12 roll, second call is d12 #1 + d12 #2,
	//and so on.
	public void randCP() {
		for (int v = 0; v < 5; v++) {
			this.cp = this.cp + randomRoll(1, 12);
		}
	}

	//HP is calculated by 1d4 roll, modified by the character's stamina.
	//Here this is given by a randomRoll call for a d4 result, added to the result of the getModifier
	//call given the parameter of the '2' slot in the abilityScores array of the character.
	//The '2' slot is where the character's stamina score is held, so passing it to getModifier will
	//return the modifier for Stamina.
	public void randHP() {
		this.hp = randomRoll(1, 4) + getModifier(this.abilityScores[2]);
	}
}