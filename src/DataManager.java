import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

/**
 * 
 */
public class DataManager {
    private int largestIDNumber = -1;

    /**
     * Getter method for Largest ID Number
     * 
     * @return value of largestIDNumber
     */
    public int getLargestIDNumber() {
        return largestIDNumber;
    }

    /**
     * 
     * 
     * @param csvFilePath
     * @return
     */
    public Hashtable<Integer, Player> loadPlayerFile(String csvFilePath) {
        Hashtable<Integer, Player> hashtable = new Hashtable<>();
        int IDIndex = 0;
        int nameIndex = 1;
        int cashIndex = 2;
        String line = "";

        try {
            File file = new File(csvFilePath);
            
            if (!file.exists()) {
                throw new FileNotFoundException("File at Path " + csvFilePath + 
                    " Could Not Be Found");
            }

            BufferedReader br = new BufferedReader(new FileReader(csvFilePath));
            
            String[] val;
            int ID;
            String name;
            int cash;
            Player player;
            while ((line = br.readLine()) != null) {
                val = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

                ID = Integer.parseInt(val[IDIndex].replaceAll("\"",""));
                if (ID > largestIDNumber) {
                    largestIDNumber = ID;
                }

                name = val[nameIndex].replaceAll("\"","").trim();
                cash = Integer.parseInt(val[cashIndex].replaceAll("\"",""));

                player = new Player(ID, name, cash);
                hashtable.put(player.getIDNumber(), player);
            }

            br.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            return null;
        } catch (IOException e2) {
            e2.printStackTrace();
            return null;
        }

        return hashtable;
    }

    /**
     * 
     * 
     * @param player
     */
    public void updateExistingPlayerInPlayerFile(Player player) {
        int ID = player.getIDNumber();
        String name = player.getName();
        int cash = player.getCash();
    }

    /**
     * 
     * 
     * @param player
     */
    public void createNewPlayerAndWriteToPlayerFile(Player player) { // TODO Make it so writing to file is handled in this file and putting into hashtable in backend
        int ID = player.getIDNumber();
        String name = player.getName();
        int cash = player.getCash();
    }
}
