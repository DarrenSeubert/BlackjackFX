/**
 * Main method that runs the Blackjack program
 * 
 * @author Darren Seubert
 */
public class App {
    public static void main(String[] args) {
        DataManager dataManager = new DataManager();
        dataManager.loadPlayerFile();

        BackEnd engine = new BackEnd(dataManager);

        FrontEnd ui = new FrontEnd();
        ui.run(engine);
    }
}
