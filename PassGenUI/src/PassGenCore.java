import java.util.Random;

public class PassGenCore {

	private String[] alph = {"ABCDEFGHIJKLMNOPQRSTUVWXYZ", "abcdefghijklmnopqrstuvwxyzz", "0123456789", "ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏĞÑÒÓÔÕÖ×ØÙÚÛÜİŞßàáâãäåæçèéêëìíîïğñòóôõö÷øùúûüışÿ", 
			"¢£¤¥¦§¨©ª«¬­®¯°±²³´µ¶·¸¹º»¼½¾¿", "!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~¡"};
	private int length=0, entropy=0;
	private boolean allowReps=true;
	private String alsoIncluded="";

	/*
	public static void main(String[] args){
		try{
			PassGenCore psc=new PassGenCore();
			String pass="";

			psc.setAllowReps(true);
			psc.setLength(16);
			psc.setAlsoIncluded("");
			pass=psc.generatePassword(new boolean[] {true,true,true,false,false,true});
			System.out.println(pass);
			System.out.println(entropy);

		}catch(Exception e){e.printStackTrace();}
	}
	*/

	public void setLength(int length) throws Exception{
		try{
			if(length<0){
				throw new Exception();
			}
			this.length=length;
		}catch(Exception e){}
	}

	public void setAllowReps(boolean allowReps){
		this.allowReps=allowReps;
	}

	public void setAlsoIncluded(String alsoIncluded){
		this.alsoIncluded=alsoIncluded;
		int s=randInt(0,5);
		this.alph[s]+=alsoIncluded;
	}

	public String generatePassword(boolean[] charset){
		//charset[0] - yes/no uppercase
		//charset[1] - yes/no lowercase
		//charset[2] - yes/no digits
		//charset[3] - yes/no special letters
		//charset[4] - yes/no special fuckups
		//charset[5] - yes/no special usual

		//selected length = 0
		if(this.length==0){this.entropy=0; return "";}
		else{
			String ret="";
			int n=0, dictionaryLength=0;
			for(int i=0; i<6; i++){
				if(charset[i]) n++;
			}

			//no charset was selected and nothing was included
			if(n==0 && (this.alsoIncluded=="" || this.alsoIncluded==null)){this.entropy=0; return "";}
			
			//no charset was included BUT something was added
			else if(n==0 && this.alsoIncluded!=null && this.alsoIncluded!=""){
				
				//impossible to make password
				if(length>alsoIncluded.length() && !allowReps){this.entropy=0; return "";}
				//possible to make password
				else{
					for(int i=0; i<length; i++){
						int selectedChar = randInt(0,alsoIncluded.length()-1);
						char selected = alsoIncluded.charAt(selectedChar);
						while(!allowReps && ret.indexOf(selected)!=-1){
							selectedChar = randInt(0,alsoIncluded.length()-1);
							selected = alsoIncluded.charAt(selectedChar);
						}
						ret+=selected;
					}
					//entropy calculation
					entropy=(int)entropyCalculation(length, alsoIncluded.length());
					return ret;
				}
				
			}
			//some charset was included (it doesn't matter here if something was added or not)
			else{
				String[] alphs;
				int max=0;

				alphs=new String[n];
				max=n;

				int j=0;
				for(int i=0; i<6; i++){
					if(charset[i]){
						alphs[j]=alph[i];
						j++;
					}
				}

				//if chosen length is grater than the dictionary length and repetitions are not allowed, return ""
				for(int i=0; i<alphs.length; i++){
					for(int k=0; k<alphs[i].length(); k++){
						dictionaryLength++;
					}
				}
				
				//if dictionary length is lower than result password length and no repetitions are allowed -> impossible to form password
				if(length>dictionaryLength && !allowReps){this.entropy=0; return "";}
				else{
					//filling up the return string
					for(int i=0; i<length; i++){
						char selected = selectChar(alphs, max);
						while(!allowReps && ret.indexOf(selected)!=-1){
							selected = selectChar(alphs, max);
						}
						ret+=selected;
					}
				}
			}
			entropy=(int)entropyCalculation(length, dictionaryLength);
			return ret;
		}
	}

	private char selectChar(String[] alphs, int max){
		int selectedAlph = randInt(0, max-1);
		int selectedChar = randInt(0, alphs[selectedAlph].length()-1);
		return alphs[selectedAlph].charAt(selectedChar);
	}

	private int randInt(int min, int max){
		Random rand = new Random();
		int randomNum = rand.nextInt((max-min)+1)+min;
		return randomNum;
	}
	
	public int retEntropy(){
		return this.entropy;
	}
	
	private int entropyCalculation(double passLength, double charsetLength){
		double log2;
		double pow=Math.pow(charsetLength, passLength);
		log2=Math.log(pow)/Math.log(2);
		return (int) log2;
	}
}