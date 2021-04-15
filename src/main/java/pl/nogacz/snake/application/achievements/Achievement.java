package pl.nogacz.snake.application.achievements;

public class Achievement {
    String achievementName;
    boolean achieved;
    int tier; // eat 10,  eat 25,  eat 100     (tier 1, 2, 3) etc.
    int [] tierRequirements;
    int progress; // ate 7 in total  etc.
    String description; // Turn in the same direction 9 times
    String des ;
    @Override
    public String toString() {
        if(achieved){
            return achievementName + " "+romen(3)+"\n\t"+description+" (Completed)\n";
        }
        return achievementName + " "+romen(tier)+"\n\t"+description+" ("+progress+"/"+tierRequirements[tier - 1]+")\n";
    }

    public Achievement(String achievementName, boolean achieved, int tier, int[] tierRequirements, int progress) {
        this.achievementName = achievementName;
        this.achieved = achieved;
        this.tier = tier;
        this.tierRequirements = tierRequirements;
        this.progress = progress;
    }

    public void completedTier(){
        //System.out.println(achievementName + " "+romen(tier)+ " completed !");
        tier ++;
        updateDescription();
    }

    public void updateProgress(){
        progress ++;
        completeMessage();
        if(tier == 1 || tier == 2){
            if(progress >= tierRequirements[tier - 1]){
                completedTier();
                completeMessage();
            }
        }else if(tier == 3){
            if(progress >= tierRequirements[tier - 1]){
                completedTier();
            }
            achieved = true;
            //tier = 4  there is no tier 4 achievements, it is for indicating completing all tiers and no more completion messages or actions will be executed
        }
    }public void updateDescription(){
        int t = tier;
        if(t == 1 ||t == 2 || t ==3 ){
            description = des.replace("<x>",tierRequirements[t - 1]+"");
        }else{
            description = des.replace("<x>",tierRequirements[2] +"");
        }
    }
    public void initializeDescription(Achievement a){
        a.des = a.description;
         int t = a.tier;
         if(t == 1 ||t == 2 || t == 3 ){
             a.description = a.description.replace("<x>",tierRequirements[t - 1]+"");
         }else{
             a.description = a.description.replace("<x>",tierRequirements[2] +"");
         }
    }
    public String romen(int tier){
        String result = "";
        for (int i = 0; i <tier ; i++) {
            result +="I";
        }
        return result;
    }
    public void completeMessage(){
        AchievementControl.updateContent();
    }
}
