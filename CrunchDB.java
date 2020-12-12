import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.*;
import java.io.*;


/** 
 * This is responsible for the overall management of the database.
 * CrunchDB should deal with taking input from the user and displaying the correct
 * output while passing off the more complicated work to the corresponding
 * classes.
 */

public class CrunchDB {

	// TODO the following data structures are responsible for storing the
	// entries and snapshots related to this instance of the database. You can
	// modify them if you wish, as long as you do not change any method
	// signatures.
	private List<Entry> entries;
	private List<Snapshot> snapshots;
    private Integer snapshot_id;
    
	public CrunchDB() {
		// TODO: write the constructor.
        entries = new ArrayList<Entry>();
        snapshots = new ArrayList<Snapshot>();
        snapshot_id = 0;
	}
	/** 
	 * Displays all keys in current state.
	 */
    public List<Snapshot> get_snap(){
        return snapshots;
    }
    
	private void listKeys() {
		// if entry size is 0 automatically no keys 
        if (entries.size() == 0){
            System.out.println("no keys");
            return;
        }
        // else loop through and return the keys in each entry
        for (int i = entries.size()-1; i >= 0; i-=1 ){
            Entry e = entries.get(i);
            System.out.println(e.get_key());    
        }  
	}

	/**
	 * Displays all entries in the current state.
	 */
	private void listEntries()
    {
        // if size is 0 then no entries
        if (entries.size() == 0)
        {
            System.out.println("no entries");
            return;
        }
        else
        {
            Entry e = entries.get(0);
            //get first entry and calling Entry class
     
            String str = e.listAllEntries(entries);
            System.out.println(str);
            return ;
        }

    }
	/**
	 * Displays all snapshots in the current state.
	 */
	private void listSnapshot() {
		// if size 0 no snapshots
        
        int count = 0;
        if (snapshots.size() == 0)
        {
            System.out.println("no snapshots");
            return;
        }
        // else calling snapshot class function
        Snapshot s= new Snapshot(snapshot_id,entries);
        System.out.println(s.listAllSnapshots(snapshots)); 
	}

	/**
	 * Displays entry values.
	 *
	 * @param key the key of the entry
	 */
	private void get(String key) {
		// calling own function iskeyvalid to check is key exist
        // if it does print in value the key has
        if (isKeyValid(key))
        {
            Entry e = getEntryByKey(key);
            System.out.println(e.get());
            
        }
	}
	/**
	 * Deletes entry from current state.
	 *
	 * @param key the key of the entry
	 */
	private void del(String key) {
		// find is key exist
        if (isKeyValid(key))
        {
            Entry e = getEntryByKey(key);
            // remove the entry from the Entries
            entries.remove(e);
            System.out.println("ok");                  
        }
	}
	/**
	 * Deletes entry from current state and snapshots.
	 *
	 * @param key the key of the entry
	 */
	private void purge(String key) {
        
		// use self build function getEntryBykEY
        // remove the entry from Entries
        // call snapshot and remove snapshot 

        System.out.println("ok");
        Entry e = getEntryByKey(key);
        entries.remove(e);
        Snapshot s= new Snapshot(snapshot_id,entries);
        s.removeKey(key);
	}

	/**
	 * Sets entry values.
	 *
	 * @param key    the key of the entry
	 * @param values the values to set
	 */
	private void set(String key, List<Integer> values) {
		// pass in key and value 
        // key and value used to create new entry
        // if key does not exist add in else replace the entry with same key
        Entry m = new Entry(key,values);
        if (!IsKeyExist(key))
        {
            entries.add(m); 
        }
        else
        {
            //update existing key value by new values
            Entry e = getEntryByKey(key);
            e.set(values);
        }
        System.out.println("ok");

	}

	/**
	 * Pushes values to the front.
	 *
	 * @param key    the key of the entry
	 * @param values the values to push
	 */
	private void push(String key, List<Integer> values) {
		// if the key is valid, call Entry push function
        if (isKeyValid(key))
        {
            Entry e = getEntryByKey(key);
            e.push(values);
            System.out.println("ok");
        }
	}
	/**
	 * Appends values to the back.
	 *
	 * @param key    the key of the entry
	 * @param values the values to append
	 */
	private void append(String key, List<Integer> values) {
		// TODO: implement this
        if (isKeyValid(key))
        {
            Entry e = getEntryByKey(key);
            e.append(values);
            System.out.println("ok");
        }
	}

	/**
	 * Displays value at index.
	 *
	 * @param key   the key of the entry
	 * @param index the index to display
	 */
	private void pick(String key, int index) {
		// if key valid obtain the entry using key
        // call Entry pick function 
        
        if (isKeyValid(key))
        {
            
            Entry e = getEntryByKey(key);
            Integer num = e.pick(index) ;
            if (num !=null)
            {
                System.out.println(num); 
            }
            // else if return null it means index out of range
            else{
                System.out.println("index out of range");
            }
        }
	}	
	/**
	 * Displays and removes the value at index.
	 *
	 * @param key   the key of the entry
	 * @param index the index to pluck
	 */
	private void pluck(String key, int index) {
		// find if key valid call pluck function in Entry
        if (isKeyValid(key))
        {
            Entry e = getEntryByKey(key);
            Integer pNum = e.pluck(index);
            if( pNum != null){
                System.out.println(pNum);
            }
            // return null then index out of range
            else{
                System.out.println("index out of range");
            }
        }
	}
	/**
	 * Displays and removes the front value.
	 *
	 * @param key the key of the entry
	 */
	private void pop(String key) {
		// if key valid call Entry function pop
        if (isKeyValid(key)){
            Entry e = getEntryByKey(key);
            e.pop();
        }
	}

	/** 
	 * Deletes snapshot.
	 *
	 * @param id the id of the snapshot
	 */
	private void drop(int id) {
		// snapshot has to be bigger than id to be valid
        // get id and remove according id
        
        if (snapshots.size() >=id)
        {
            for(int i = 0; i<snapshots.size(); i++)
            {
                Snapshot s = snapshots.get(i);
                if (s.GetId() == id)
                    snapshots.remove(s);
            }            
        }
        else
            // if cant find no such snapshot
        {
            System.out.println("no such snapshot");
        }
	}
	/**
	 * Restores to snapshot and deletes newer snapshots.
	 *
	 * @param id the id of the snapshot
	 */
    // remove the other entries that isnt in snapshot 1 etc
	private void rollback(int id) 
    {  
        // inital set to false 
        boolean foundId = false;
        // loop through snapshot if id of snapshot equal rollback id
        for(int i =0; i< snapshots.size(); i++)
        {
            Snapshot s = snapshots.get(i);
            if (s.GetId() == id)
            {
                // set found ID to true
                foundId = true;
                //rollback current to found snapshot
                snapshot_id = s.GetId();
                entries =s.GetEntries();
                break;
            }
            
           
            
        }
        if (!foundId)
        {
            // if not found then no snapshot
            System.out.println("no such snapshot");
        }
        else if (id == snapshots.size())
        {
           System.out.println("ok"); 
        }
        else
        {
            //rollback to snapshot by given id
            
            //remove all snapshots that are newer
            for(int i = id; i<snapshots.size(); i++)
            {
                Snapshot s = snapshots.get(i);
                snapshots.remove(i); 
                System.out.println("ok");
            }                   
        }
 
	}

	/** 
	 * Replaces current state with a copy of snapshot.
	 *
	 * @param id the id of the snapshot
	 */
	private void checkout(int id) {
        // inital set to false
        boolean foundId = false;
        // loop through snapshot if id of snapshot match checkout id
        for(int i =0; i< snapshots.size(); i++)
        {
            Snapshot s = snapshots.get(i);
           
            if (s.GetId() == id)
            {
                // set to true
                foundId = true;
                //rollback current to found snapshot
                snapshot_id = s.GetId();
                entries = s.GetEntries();
                break;
            }
            
        }
        if (!foundId)
        {
            System.out.println("no such snapshot");
        }
        else if (id == snapshots.size())
        {
           System.out.println("ok"); 
        }
        else
        {
            for(int i = id; i<snapshots.size(); i++)
            {
                Snapshot s = snapshots.get(i);
                snapshot_id = s.GetId();
                entries = s.GetEntries();
                System.out.println("ok");
            }          
            
        }      
	}

	/** 
	 * Saves the current state as a snapshot.
	 */
	private void snapshot() {
		// call constructor
        CrunchDB c = new CrunchDB();
        // id increases
        snapshot_id ++;
        //creation of new snapshot
        Snapshot s= new Snapshot(snapshot_id,entries);
        //snapshot list add entry
        snapshots.add(s);
        System.out.println("saved as snapshot "+ snapshot_id);
	}

	/**
	 * Saves snapshot to file.
	 *
	 * @param id       the id of the snapshot 
	 * @param filename the name of the file
	 */
	private void archive(int id, String filename) {
		// new snapshot and pass filename in
         Snapshot s = new Snapshot(id,entries);
       
         s.archive(filename);

	}

	/**
	 * Loads and restores snapshot from file.
	 *
	 * @param filename the name of the file
	 */
	private void restore(String filename) {
		// TODO: implement this

	}

	/**
	 * Displays minimum value.
	 *
	 * @param key the key of the entry
	 */
	private void min(String key) {
		// if key valid get entry by given key
        // calling min function in Entry class
        if (isKeyValid(key))
        {
            Entry e = getEntryByKey(key);
            System.out.println(e.min());
        }
	}
	/**
	 * Displays maximum value.
	 *
	 * @param key the of the entry
	 */
	private void max(String key) {
		// if key valid 
        // call max via Entry class
        if (isKeyValid(key))
        {
            Entry e = getEntryByKey(key);
            System.out.println(e.max());
  
        }
	}

	/**
	 * Displays the sum of values.
	 *
	 * @param key the key of the entry
	 */
	private void sum(String key) {
        // if key valid
        if (isKeyValid(key))
        {
            // sum via Entry class
            Entry e = getEntryByKey(key);
            System.out.println(e.sum());    
        }
	}

	/**
	 * Displays the number of values.
	 *
	 * @param key the key of the entry
	 */
	private void len(String key) {
        if (isKeyValid(key))
        //key valid find len by Entry class
        {
            Entry e = getEntryByKey(key);
            System.out.println(e.len());

            
        }
		// TODO: implement this
	}

	/**
	 * Reverses the order of values.
	 *
	 * @param key the key of the entry
	 */
	private void rev(String key) {
		//key valid , rev by entry class
        
        if (isKeyValid(key))
        {
            Entry e = getEntryByKey(key);
            e.rev();
            System.out.println("ok");
            
        }
	}

	/**
	 * Removes repeated adjacent values.
	 *
	 * @param key the key of the entry
	 */
	private void uniq(String key) {
        if (isKeyValid(key))
        {
            Entry e = getEntryByKey(key);
            e.uniq();
            System.out.println("ok");
            
        }
    
		// TODO: implement this
	}

	/** 
	 * Sorts values in ascending order.
	 *
	 * @param key the key of the entry
	 */
	private void sort(String key) {
		// TODO: implement this
        if (isKeyValid(key))
        {
            Entry e = getEntryByKey(key);
            e.sort();
            System.out.println("ok");
        }
	}
 

	/**
	 * Displays set difference of values in keys. 
	 * Needs at least two keys.
	 *
	 * @param keys the keys of the entries
	 */
	private void diff(List<String> keys) {
		// TODO: implement this 
        //Prepre diff list for output
        List<Integer> diff = new ArrayList<Integer>();
        CrunchDB c = new CrunchDB();
        // Prepare Entries by given keys
        List<Entry> ce = new ArrayList<Entry>();
        for(int i = 0; i<keys.size();i++)
        {
            Entry e = getEntryByKey(keys.get(i));
            //contain self made function 
            if (!c.contains(ce,e))
            {
                ce.add(e);
            }
        }
        if (ce.size() >1)
        {
            Entry e = ce.get(0);
            diff = e.diff(ce);
            diff = e.sort_values(diff);
        }
        if (diff.size() == 0){
            System.out.println("[]");
        }

        for(int k = 0 ; k < diff.size(); k++){
            if ( k == 0){
                System.out.print("[" +diff.get(k) + " ");
            }
            else if ( k == diff.size()-1){
                System.out.println(diff.get(k) + "]");
                                
            }
            else{
                System.out.print(diff.get(k) + " ");           
            }
        }
        
	}

	/**
	 * Displays set intersection of values in keys.
	 * Needs at least two keys.
	 *
	 * @param keys the keys of the entries
	 */
	private void inter(List<String> keys) {
		// TODO: implement this
        
        //Prepre intersect list for output
        List<Integer> intersects = new ArrayList<Integer>();
        
        // Prepare Entries by given keys
        CrunchDB c = new CrunchDB();
        List<Entry> ce = new ArrayList<Entry>();
        for(int i = 0; i<keys.size();i++)
        {
            Entry e = getEntryByKey(keys.get(i));
            if (!c.contains(ce,e))
                ce.add(e);
        }
        if (ce.size() <1)
        {
            return;
        }
        else if (ce.size() == 1)
        {
            // inter key by itself
            Entry e = ce.get(0);
            List<Integer> lst = e.get_values();
            for(int n = 0; n<lst.size();n++)
            {
                if (!intersects.contains(lst.get(n)))
                {
                    intersects.add(lst.get(n));
                }
            }
            intersects = e.sort_values(intersects);
        }        
        else
        {
            Entry e = ce.get(0);
            intersects = e.inter(ce);
            intersects = e.sort_values(intersects);
        }
        
        if (intersects.size() == 1)
        {
            System.out.println("[" +intersects.get(0) + "]");
        }
        else
        {
            for(int k = 0 ; k < intersects.size(); k++)
            {
                if ( k == 0){
                    System.out.print("[" +intersects.get(k) + " ");
                }
                else if ( k == intersects.size()-1){
                    System.out.println(intersects.get(k) + "]");

                }
                else{
                    System.out.print(intersects.get(k) + " ");           
                }
            }
        }     
    }
        

	/**
	 * Displays set union of values in keys.
	 * Needs at least two keys.
	 *
	 * @param keys the keys of the entries
	 */
	private void union(List<String> keys) {
        //Prepre intersect list for output
        List<Integer> union = new ArrayList<Integer>();
        
        // Prepare Entries by given keys
        List<Entry> ce = new ArrayList<Entry>();
        for(int i = 0; i<keys.size();i++)
        {
            Entry e = getEntryByKey(keys.get(i));
            ce.add(e);
          
        }
        if (ce.size() >0)
        {
            Entry e = ce.get(0);
            union = e.union(ce);
            union= e.sort_values(union);
            
        }
        for(int k = 0 ; k < union.size(); k++){
            if ( k == 0){
                System.out.print("[" +union.get(k) + " ");
            }
            else if ( k == union.size()-1){
                System.out.println(union.get(k) + "]");
                                
            }
            else{
                System.out.print(union.get(k) + " ");           
            }
        }
        
	}

	/** 
	 * Displays cartesian product of the sets.
	 * Needs at least two keys.
	 *
	 * @param keys the keys of the entries
	 */
	private void cartprod(List<String> keys) {
        // Prepare Entries by given keys        
        
        List<List<Integer>> cartprods = new ArrayList<List<Integer>>();
        List<Entry> ce = new ArrayList<Entry>();
        for(int i = 0; i<keys.size();i++)
        {
            Entry e = getEntryByKey(keys.get(i));            
            ce.add(e);
        }        
        
        if (ce.size() >0)
        {
            Entry e = ce.get(0);
            cartprods = e.cartprod(ce);
        }

        if (cartprods.size() >0)
        {
            for(int k = 0 ; k < cartprods.size(); k++){
            String cart = "";
            List<Integer> lst = cartprods.get(k);
            for(int m =0; m<lst.size(); m++)
            {
                if (m == 0)
                    cart = cart + "[" + lst.get(m).toString().trim();
                else if (m == lst.size()-1)
                    cart = cart + " " + lst.get(m).toString().trim() + "]";
                else
                    cart = cart + " " + lst.get(m).toString().trim();
            }
                
            if ( k == 0){
                System.out.print("[ " + cart + " ");
            }
            else if ( k == cartprods.size()-1){
                System.out.println(cart + " ]");
                                
            }
            else{
                System.out.print(cart + " ");           
            }
        }
        


        }
	}
    
    private Entry getEntryByKey(String key){
        for (int i = 0; i<entries.size(); i++ ){
            Entry e = entries.get(i);
            if (e.get_key().equals(key)){
                return e;
            } 
        }
        return null;
    }
    
    //customised function
    private boolean isKeyValid(String key){
        for (int i = 0; i<entries.size(); i++ ){
            Entry e = entries.get(i);
            if (e.get_key().equals(key)){
                return true;
            } 
            continue;
        }
        System.out.println("no such key");
        
        return false;
    }
    
    private boolean IsKeyExist(String key){
        for (int i = 0; i<entries.size(); i++ ){
            Entry e = entries.get(i);
            if (e.get_key().equals(key)){
                return true;
            } 
            continue;
        }
        
        return false;
    }
    
    //customised function to check if given Entry is valid in existing Entries
    private boolean contains(List<Entry> ents, Entry ent)
    {
        for(int i =0; i<ents.size(); i++)
        {
            if (ent == ents.get(i))
            {
               return true;
            }           
        }
        return false;
    }
    
    public static boolean isNumeric(String strNum) 
    {
        try {
            Integer d = Integer.parseInt(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }
       

	private static final String HELP =
		"BYE   clear database and exit\n"+
		"HELP  display this help message\n"+
		"\n"+
		"LIST KEYS       displays all keys in current state\n"+
		"LIST ENTRIES    displays all entries in current state\n"+
		"LIST SNAPSHOTS  displays all snapshots in the database\n"+
		"\n"+
		"GET <key>    displays entry values\n"+
		"DEL <key>    deletes entry from current state\n"+
		"PURGE <key>  deletes entry from current state and snapshots\n"+
		"\n"+
		"SET <key> <value ...>     sets entry values\n"+
		"PUSH <key> <value ...>    pushes values to the front\n"+
		"APPEND <key> <value ...>  appends values to the back\n"+
		"\n"+
		"PICK <key> <index>   displays value at index\n"+
		"PLUCK <key> <index>  displays and removes value at index\n"+
		"POP <key>            displays and removes the front value\n"+
		"\n"+
		"DROP <id>      deletes snapshot\n"+
		"ROLLBACK <id>  restores to snapshot and deletes newer snapshots\n"+
		"CHECKOUT <id>  replaces current state with a copy of snapshot\n"+
		"SNAPSHOT       saves the current state as a snapshot\n"+
		"\n"+
		"ARCHIVE <id> <filename> saves snapshot to file\n"+
		"RESTORE <filename> loads snapshot from file\n"+
		"\n"+
		"MIN <key>  displays minimum value\n"+
		"MAX <key>  displays maximum value\n"+
		"SUM <key>  displays sum of values\n"+
		"LEN <key>  displays number of values\n"+
		"\n"+
		"REV <key>   reverses order of values\n"+
		"UNIQ <key>  removes repeated adjacent values\n"+
		"SORT <key>  sorts values in ascending order\n"+
		"\n"+
		"DIFF <key> <key ...>   displays set difference of values in keys\n"+
		"INTER <key> <key ...>  displays set intersection of values in keys\n"+
		"UNION <key> <key ...>  displays set union of values in keys\n"+
		"CARTPROD <key> <key ...>  displays set union of values in keys\n";
	
	public static void bye() {
		System.out.println("bye");
        CrunchDB c = new CrunchDB();
        c.entries.clear();
        


        
        return;
	}
	
	public static void help() {
		System.out.println(HELP);
	}
	
	
	public static void main(String[] args) {
		// TODO: Your main method here!
        CrunchDB c = new CrunchDB();
        Scanner inp = new Scanner(System.in);
        String str;
        String lowercase;

        while(true){
 
            System.out.print("> ");
            if (inp.hasNextLine()){
                str = inp.nextLine();
            }
            else{
                break;
            }
            
            String[] arr = str.split(" ");
            int id = 0;

            String command = new String();
            List<String> keys = new ArrayList<String>();
            ArrayList<Integer> ent =new ArrayList<Integer>();
            
            
            if (arr.length > 0){
                command = arr[0].toLowerCase();
                if (command.equals("bye")){
                    bye(); 
                    break;
                }
                else if (command.equals("help")){
                    help();
                    
                    continue;
                }                

            if (arr.length >1){
                if (command.equals("archive") && isNumeric(arr[1]))
                {
                    if (arr.length > 2)
                    {
                        String filename = arr[2];
                        c.archive(Integer.parseInt(arr[1]),filename);
                    }
                }
                else if (arr[1].toLowerCase().equals("entries") && command.equals("list")){
                  
                    c.listEntries();
                }
                else if (arr[1].toLowerCase().equals("keys") && command.equals("list")){
                    c.listKeys();
                }
                else if ( arr[1].toLowerCase().equals("snapshots") && command.equals("list")){
                    c.listSnapshot();
                }
                else
                {
                    if (!isNumeric(arr[1].trim()))
                    {
                        keys.add(arr[1].trim());
                    }
                }
            }
                
            if (arr.length >2){
                // convert input into interger list
                for(int i = 2; i<arr.length;i++){
                    if (isNumeric(arr[i]))
                    {
                        ent.add(Integer.parseInt(arr[i]));
                    }
                    else
                    {
                        keys.add(arr[i]);
                    }
                }
            }
        }
               
            // call function to process
            
            //System.out.println(command);           
            if (command.equals("set")){  
                c.set(keys.get(0),ent);
            }
            else if (command.equals("listkeys")){
                c.listKeys();
            }
            else if (command.equals("get")){
                c.get(keys.get(0));
            }
            else if (command.equals("uniq")){
                c.uniq(keys.get(0));                       
            }
            else if (command.equals("sort")){
                c.sort(keys.get(0));
            }
            else if( command.equals("del")){
                c.del(keys.get(0));
            }
            else if (command.equals("pop")){
                c.pop(keys.get(0));
            }
            else if (command.equals("min")){
                c.min(keys.get(0));
            }
            else if (command.equals("max")){
                c.max(keys.get(0));
            }
            else if (command.equals("sum")){
                c.sum(keys.get(0));
            }
            else if (command.equals("len")){
                c.len(keys.get(0));
            }
            else if (command.equals("purge")){
                c.purge(keys.get(0));
            }
            else if (command.equals("rev")){
                c.rev(keys.get(0));
            }
            else if (command.equals("pick")){
                c.pick(keys.get(0),ent.get(0));
            }
            else if (command.equals("pluck")){
                c.pluck(keys.get(0),ent.get(0));
                
            }
            else if (command.equals("append")){
                c.append(keys.get(0),ent);
            }
            else if (command.equals("push")){
                c.push(keys.get(0),ent);
            }
            else if (command.equals("snapshot")){
                c.snapshot();
            }
            else if (command.equals("inter")){
                c.inter(keys);
            }
            else if (command.equals("diff")){
                c.diff(keys);
            }
            else if (command.equals("union")){
                c.union(keys);
            }
            else if (command.equals("purge")){
                c.purge(keys.get(0));
            }
            else if (command.equals("drop")){
                c.drop(Integer.parseInt(arr[1]));
               
            }
            else if (command.equals("checkout")){
                c.checkout(Integer.parseInt(arr[1]));
            }
            else if (command.equals("cartprod")){
                 c.cartprod(keys);
            }
            else if (command.equals("rollback")){
                c.rollback(Integer.parseInt(arr[1]));
            }
        System.out.print("\n");
        }

	}
}
