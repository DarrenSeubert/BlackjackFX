import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Class that manages the hashtable of players and the players csv file
 * 
 * @author Darren Seubert
 */
public class DataManager {
    public Hashtable<Integer, Player> playerTable;
    private int largestIDNumber;

    /**
     * Constructor for the Data Manager
     */
    public DataManager() {
        playerTable = new Hashtable<Integer, Player>();
        largestIDNumber = 0;
    }

    /**
     * Getter method for largest ID number in csv file
     * 
     * @return value of largestIDNumber
     */
    public int getLargestIDNumber() {
        return largestIDNumber;
    }

    /**
     * Method that increments largestIDNumber by one
     */
    public void incrementLargestIDNumber() {
        largestIDNumber++;
    }

    /**
     * Method that loads the players file into the players hash table
     */
    public void loadPlayersFile() {
        int IDIndex = 0;
        int nameIndex = 1;
        int cashIndex = 2;
        String line = "";

        try {
            File file = new File(Constants.playerCSVFilePath);
            
            if (!file.exists()) {
                throw new FileNotFoundException("File at Path " + Constants.playerCSVFilePath + 
                    " Could Not Be Found");
            }
            
            BufferedReader br = new BufferedReader(new FileReader(Constants.playerCSVFilePath));
            br.readLine(); // Reads header line of csv

            String[] val;
            int ID;
            String name;
            double cash;
            Player player;
            while ((line = br.readLine()) != null) {
                val = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

                ID = Integer.parseInt(val[IDIndex].replaceAll("\"",""));
                if (ID > largestIDNumber) {
                    largestIDNumber = ID;
                }

                name = val[nameIndex].replaceAll("\"","").trim();
                cash = Double.parseDouble(val[cashIndex].replaceAll("\"",""));

                player = new Player(ID, name, cash);
                playerTable.put(player.getID(), player);
            }

            br.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    /**
     * Method that updates an existing player in the players file from the players table
     * 
     * @param playerID The ID number of the player
     * @param cash The amount of cash that the player has
     */
    public void updateExistingPlayerInPlayersFile(Player player) {
        String readLine = "";

        try {
            File file = new File(Constants.playerCSVFilePath);
            
            if (!file.exists()) {
                throw new FileNotFoundException("File at Path " + Constants.playerCSVFilePath + 
                    " Could Not Be Found");
            }
            
            BufferedReader br = new BufferedReader(new FileReader(Constants.playerCSVFilePath));

            List<String[]> fileCont = new ArrayList<>();
            String[] val = null;

            br.readLine();
            int lineCount = 1;
            int lineToEdit = -1;
            while ((readLine = br.readLine()) != null) {
                lineCount++;

                val = readLine.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                fileCont.add(val);

                if (Integer.parseInt(val[0]) == player.getID()) {
                    lineToEdit = lineCount;
                }
            }
                
            br.close();

            if (lineToEdit == -1) {
                throw new NoSuchElementException("Error: Player to update is not in csv file");
            }
            
            PrintWriter pr = new PrintWriter(file);
            
            pr.write("ID,Name,Cash\n"); // Line 1

            for (int i = 0; i < fileCont.size(); i++) { // Starts at Line 2
                if (i + 2 == lineToEdit) {
                    pr.write(player.getID() + ",");
                    pr.write(player.getName() + ",");
                    pr.write(player.getCash() + "\n");
                    continue;
                }

                for (int j = 0; j < val.length; j++) {
                    if (j + 1 == (val.length)) {
                        pr.write(fileCont.get(i)[j] + "\n");
                    } else {
                        pr.write(fileCont.get(i)[j] + ",");
                    }
                }
            }

            pr.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        } catch (NoSuchElementException e3) {
            e3.printStackTrace();
        }
    }

    /**
     * A method that writes a new player into the players file
     * 
     * @param player The player to write to file
     */
    public void writeNewPlayerToFile(Player player) {
        int ID = player.getID();
        String name = player.getName();
        double cash = player.getCash();

        String readLine = "";

        try {
            File file = new File(Constants.playerCSVFilePath);
            
            if (!file.exists()) {
                throw new FileNotFoundException("File at Path " + Constants.playerCSVFilePath + 
                    " Could Not Be Found");
            }
            
            BufferedReader br = new BufferedReader(new FileReader(Constants.playerCSVFilePath));

            List<String[]> fileCont = new ArrayList<>();
            String[] val = null;
            while ((readLine = br.readLine()) != null) {
                val = readLine.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                fileCont.add(val);
            }
                
            br.close();

            PrintWriter pr = new PrintWriter(file);

            for (int i = 0; i < fileCont.size(); i++) { // Starts at Line 1
                for (int j = 0; j < val.length; j++) {
                    if (j + 1 == (val.length)) {
                        pr.write(fileCont.get(i)[j] + "\n");
                    } else {
                        pr.write(fileCont.get(i)[j] + ",");
                    }
                }
            }

            pr.write(ID + "," + name + "," + cash + "\n");

            pr.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        } catch (NoSuchElementException e3) {
            e3.printStackTrace();
        }
    }

    /**
     * A method that looks up player IDs given the name to search
     * 
     * @param nameToSearch The name to search
     * @return An int array of all the ID numbers that have that name
     */
    public int[] lookupPlayerID(String nameToSearch) {
        ArrayList<Integer> outputIDs = new ArrayList<Integer>();
        int IDIndex = 0;
        int nameIndex = 1;
        String line = "";

        try {
            File file = new File(Constants.playerCSVFilePath);
            
            if (!file.exists()) {
                throw new FileNotFoundException("File at Path " + Constants.playerCSVFilePath + 
                    " Could Not Be Found");
            }
            
            BufferedReader br = new BufferedReader(new FileReader(Constants.playerCSVFilePath));
            br.readLine(); // Reads header line of csv

            String[] val;
            String currentName;
            while ((line = br.readLine()) != null) {
                val = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

                currentName = val[nameIndex].replaceAll("\"","").trim().toLowerCase();
                if (currentName.contains(nameToSearch.toLowerCase())) {
                    outputIDs.add(Integer.parseInt(val[IDIndex].replaceAll("\"","")));
                }
            }

            br.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }

        int[] result = new int[outputIDs.size()];
        for (int i = 0; i < outputIDs.size(); i++) {
            result[i] = outputIDs.get(i);
        }
        
        return result;
    }
}
