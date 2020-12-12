import java.util.List;
import java.util.*;
import java.io.*;
/**
 * Snapshot deals with storing the id and current state of the database. 
 * As well as storing this data, the Snapshot class should manage operations
 * related to snapshots.
 */

public class Snapshot {
	private int id;
	private List<Entry> entries;


	public Snapshot(int id, List<Entry> entries) {
        this.id = id;
        this.entries = entries;      
		// TODO: write this constructor
               
	}
    
    public int GetId()
    {
        return this.id;
    }
    
    public List<Entry> GetEntries()
    {
        return this.entries;
    }

	/**
	 * Finds and removes the key.
	 *
	 * @param key the key to remove
	 */
	public void removeKey(String key) 
    {      
        for (int i = 0 ; i < this.entries.size();i++)
        {
            Entry e = this.entries.get(i);
            if (e.get_key().equals(key))
            {
                entries.remove(e);               
            }
        }       
        
    }

		// TODO: implement this
    
    
	/**
	 * Finds the list of entries to restore.
	 *
	 * @return the list of entries in the restored state
	 */
	public List<Entry> rollback() {
		// TODO: implement this    
		return this.entries;
	}


	/**
	 * Saves the snapshot to file.
	 *
	 * @param filename the name of the file
	 */
	public void archive(String filename) {
        System.out.println("ok");
		// TODO: implement this
        String key;
        String values;
        String sentence;
        try{
            PrintWriter writer = new PrintWriter(filename); 
            for (int i = 0 ;i < entries.size(); i++){
                Entry e = entries.get(i);
                key = e.get_key();
                values = e.get_nb();
                // System.out.println(key + "|" + values);
              
               
                

                writer.println(key + "|" + values);

            }
            writer.close(); 
    
        }
        catch(Exception e){
            System.out.println(e);
        }
        
	}

	/**
	 * Loads and restores a snapshot from file.
	 *
	 * @param  filename the name of the file
	 * @return          the list of entries in the restored state
	 */
	public static List<Entry> restore(String filename) {
		// TODO: implement this
        String fileName = filename;
        String line = null;
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null) {

                System.out.println(line);
            }   
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'");                  
        }
        
		return null;
	}

	/**
	 * Formats all snapshots for display.
	 *
	 * @param  snapshots the snapshots to display
	 * @return           the snapshots ready to display
	 */
	public static String listAllSnapshots(List<Snapshot> snapshots) {
		// TODO: implement this
        String snaps = "";
        if (snapshots.size()==0){
            return "no snapshots";
        }
        int count = snapshots.size();
       while (count != 0){
           if (count !=1){
               snaps += count;
               snaps += "\n";
           }
           else{
               snaps += count;
           }
    
        count -=1;
       }

		return snaps; //??? have to return whut
	}
}
