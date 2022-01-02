public class App {
    public static void main(String[] args) {
        DataManager dataManager = new DataManager();
        dataManager.loadPlayerFile();

        BackEnd engine = new BackEnd(dataManager);

        FrontEnd ui = new FrontEnd(engine);
        ui.run();
    }
}
