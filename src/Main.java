import java.util.concurrent.Semaphore;
class PersonConstructor extends Thread{
    public static volatile boolean isOccupied = false;
    public static volatile String currentSex;
    public int countf = 0;
    public int countm = 0;
    Semaphore personEnters;
    public String sex;

    PersonConstructor(String sex, Semaphore personEnters){
        this.sex = sex;
        this.personEnters = personEnters;
    }

    @Override
    public void run(){
        try {
            if (isOccupied == false && sex.equals("Male") || sex.equals("Female")) {
                personEnters.acquire();
                System.out.println(sex + " has entered.");
                currentSex = sex;
                isOccupied = true;
                incrementCount();
                Thread.sleep(10);
                if (isOccupied == true && currentSex.equals("Male") && sex.equals("Male")){
                    System.out.println("Another Males Enters");
                    incrementCount();
                    personEnters.acquire();
                }else if (isOccupied == true && currentSex.equals("Male") && sex.equals("Female")){
                    womenWantsToEnter();

                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //functions
    public void womenWantsToEnter(){
        System.out.println("Women wants to enter.");
        try{

        }
    }
    public void manWantsToEnter(){
        System.out.println("Man wants to enter.");
        try{

        }
    }
    public void manLeaves(){
        System.out.println("A man leaves.");
        personEnters.release();
    }
    public void womenLeaves(){
        System.out.println("A woman leaves.");
        personEnters.release();
    }

    public void incrementCount(){
        if(sex.equals("Male")){
            countm++;
        }else{
            countf++;
        }
    }

}

public class Main{
    public static Semaphore personEnters = new Semaphore(5);
    public static void main(String[] args) throws InterruptedException {

        // write your code here
        PersonConstructor PersonA = new PersonConstructor("Female", personEnters);
        PersonConstructor PersonB = new PersonConstructor("Male", personEnters);
        PersonConstructor PersonC = new PersonConstructor("Male", personEnters);

        PersonA.start();
        PersonB.start();
        PersonC.start();


        PersonA.join();
        PersonB.join();
    }
}
