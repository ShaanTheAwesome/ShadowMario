import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;

public class Level {

    // imports the properties files
    private String levelOneFile = ShadowMario.game_props.getProperty("level1File");
    private String levelTwoFile = ShadowMario.game_props.getProperty("level2File");
    private String levelThreeFile = ShadowMario.game_props.getProperty("level3File");
    private ArrayList<ArrayList<String>> levelOne = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> levelTwo = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> levelThree = new ArrayList<ArrayList<String>>();

    public Level() {

        // imports level one of the game
        try(BufferedReader br = new BufferedReader(new FileReader(levelOneFile))) {
            String text;
            while((text = br.readLine()) != null) {
                String[] columns = text.split(",");
                ArrayList<String> rowList = new ArrayList<String>();
                rowList.add(columns[0]);
                rowList.add(columns[1]);
                rowList.add(columns[2]);

                levelOne.add(rowList);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // imports level two of the game
        try(BufferedReader br = new BufferedReader(new FileReader(levelTwoFile))) {
            String text;
            while((text = br.readLine()) != null) {
                String[] columns = text.split(",");
                ArrayList<String> rowList = new ArrayList<String>();
                rowList.add(columns[0]);
                rowList.add(columns[1]);
                rowList.add(columns[2]);

                levelTwo.add(rowList);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // imports level three of the game
        try(BufferedReader br = new BufferedReader(new FileReader(levelThreeFile))) {
            String text;
            while((text = br.readLine()) != null) {
                String[] columns = text.split(",");
                ArrayList<String> rowList = new ArrayList<String>();
                rowList.add(columns[0]);
                rowList.add(columns[1]);
                rowList.add(columns[2]);

                levelThree.add(rowList);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // getter methods to retrieve all three level arrays
    public ArrayList<ArrayList<String>> getLevelOne() {
        return this.levelOne;
    }

    public ArrayList<ArrayList<String>> getLevelTwo() {
        return this.levelTwo;
    }

    public ArrayList<ArrayList<String>> getLevelThree() {
        return this.levelThree;
    }



}
