import java.util.List;
import java.util.HashMap;
import java.util.*;

/**
 * Entry deals with storing the key and value associated with entries in the
 * database. 
 * As well as storing the data the entry class should manage operations
 * associated with any Entry.
 */

public class Entry {
	private String key;
	private List<Integer> values;

    
	public Entry(String key, List<Integer> values) {
        this.key = key;
        this.values = values;
	}
    

	/**
	 * Formats the Entry for display
	 *
	 * @return  the String of values
	 */
    
	public String get() {
        // go through list this.value get each value
        String s = "[";
        for (int i=0; i<this.values.size();i++)
        {
            s = s + this.values.get(i);
            if (i < this.values.size()-1)
                s = s +  " "; 
        }
        s = s + "]";
		return s;
	}
    
    public String get_nb(){
        // return string values without brackets used in archive
        String s = "";
        for ( int i = 0;i < this.values.size(); i++){
            s = s + this.values.get(i);
            if (i < this.values.size()-1)
                s = s +  ","; 
        
        }
        return s;
    }
    
    public String get_key(){
        return this.key;
    }
    
    public List<Integer> get_values()
    {
        return this.values;
    }
    
    //customised function to check if given value is valid in values list
    private boolean IsContain(List<Integer> vs, Integer v)
    {
        for(int i =0; i<vs.size(); i++)
        {                       
            if (v.equals(vs.get(i)))
            {
                return true;
            }           
        }
        return false;
    }

	/**
	 * Sets the values of this Entry.
	 *
	 * @param values the values to set
	 */
	public void set(List<Integer> values) {

        this.values = values;

	}

	/**
	 * Adds the values to the start.
	 *
	 * @param values the values to add
	 */
	public void push(List<Integer> values) {
		// for each value in List values add to front
        for (int i = 0; i < values.size() ;i++){
            this.values.add(0,values.get(i));
        }
	}

	/**
	 * Adds the values to the end.
	 *
	 * @param values the values to add
	 */
	public void append(List<Integer> values) {
		// for each value in List values add
        for (int i = 0; i < values.size() ;i++){
            this.values.add(values.get(i));
        }
	}

	/**
	 * Finds the value at the given index.
	 *
	 * @param  index the index
	 * @return       the value found 
	 */
	public Integer pick(int index) {
		//invalid cases
        if (index<1 || index > this.values.size()){
            //System.out.println("index out of range");
            return null;
        }
        //get index
        return this.values.get(index-1);
	}

	/**
	 * Finds and removes the value at the given index.
	 *
	 * @param  index the index
	 * @return       the value found
	 */
	public Integer pluck(int index) {
		// TODO: implement this
        Integer num;
        //invaid cases
        if (index<1 || index > this.values.size()){
            //System.out.println("index out of range");
            return null;
        }
        //pluck given index
        num = this.values.get(index-1);
        values.remove(index-1);
		return num;
	}

	/**
	 * Finds and removes the first value.
	 *
	 * @return the first value
	 */
	public Integer pop() {
		// TODO: implement this
        if (this.values.size() ==0){
            System.out.println("nil");
            return -1;
        }
        Integer rtn = this.values.get(0);
        System.out.println(rtn);
        this.values.remove(0);
		return rtn;           
	}

	/**
	 * Finds the minimum value.
	 *
	 * @return the minimum value
	 */
	public Integer min() {

        if (this.values.size() ==0){
            return null;
        }
        //make first one smallest
        int smallest = this.values.get(0);
        //compare through for loop if smaller replace
        for (int i = 0; i < this.values.size(); i++){
            if (this.values.get(i)<smallest){
                smallest = this.values.get(i);
            }
        }
		return smallest;
	}

	/**
	 * Finds the maximum value.
	 *
	 * @return the maximum value
	 */
	public Integer max() {
        if (this.values.size() ==0){
            return null;
        }
        //biggests be first if found bigger replace
        int biggest = this.values.get(0);
        for (int i = 0; i < this.values.size(); i++){
            if (this.values.get(i)>biggest){
                biggest= this.values.get(i);
            }
        }
		return biggest;
		
	}

	/**
	 * Computes the sum of all values.
	 *
	 * @return the sum
	 */
	public Integer sum() {
        if (this.values.size() ==0){
            return null;
        }
		//go through values list and add
        int count = 0;
        for (int i = 0 ; i < this.values.size(); i ++){
            count += this.values.get(i);
        }
		return count;
	}

	/**
	 * Finds the number of values.
	 *
	 * @return the number of values.
	 */
	public Integer len() {
		return this.values.size();
	}

	/**
	 * Reverses the order of values.
	 */
	public void rev() {
        List<Integer> result = new ArrayList<Integer>();
        for(int i = this.values.size() - 1 ; i >= 0 ; i--)
            result.add(this.values.get(i));
        this.values.removeAll(result);
        for(int digit:result){
            this.values.add(digit);
        }
		// TODO: implement this
	}
	
	/**
	 * Removes adjacent duplicate values.
	 */
	public void uniq() {
      
        
         List <Integer> new_list = new ArrayList<Integer>();  
        for(int i=0; i<this.values.size()-1; i++){
            if(this.values.get(i)!=this.values.get(i+1))
                new_list.add(this.values.get(i));   //keep adjacent distinct value
        }
        new_list.add(this.values.get(this.values.size()-1)); 
        this.values.clear();
        for (Integer num : new_list){
            this.values.add(num);
        }

  
		// TODO: implement this
	}

	/**
	 * Sorts the list in ascending order.
	 */
	public void sort() {
		//swapping indexs
        int tempI;
        int tempJ;
        for ( int i = 0 ; i < this.values.size(); i++)
        {
            for(int j=i+1; j<this.values.size();j++)
            {
                if (this.values.get(i)>this.values.get(j))
                {
                     tempI = this.values.get(i);
                     tempJ = this.values.get(j);
                    
                    this.values.set(i, tempJ);
                    this.values.set(j,tempI);
                }
            }
        }
	}
    
    public List<Integer> sort_values(List<Integer> values) {

        List<Integer> lst = new ArrayList<Integer>();
        int tempI;
        int tempJ;
        for ( int i = 0 ; i < values.size(); i++)
        {
            for(int j=i+1; j<values.size();j++)
            {
                if (values.get(i)>values.get(j))
                {
                     tempI = values.get(i);
                     tempJ = values.get(j);
                    
                    values.set(i, tempJ);
                    values.set(j,tempI);
                }
            }
        }
        lst = values;
        return lst;
	}

	/**
	 * Computes the set difference of the entries.
	 *
	 * @param  entries the entries
	 * @return         the resulting values
	 */
	public static List<Integer> diff(List<Entry> entries) {
		// TODO: implement this
//Prepre intersect list for output
        List<Integer> diff = new ArrayList<Integer>();        

        //for each candidate Entry
        for(int j =0; j<entries.size(); j++)
        {
            Entry e = entries.get(j);
            //get search Entry values
            List<Integer> v = e.get_values();
            
            for(int k=0;k<v.size();k++)
            {
                boolean unique = true;                
                //compare with other candiate Entries in prepared Entries
                for(int m=0; m<entries.size();m++)
                {
                    //check each other candidate Entry
                    Entry e1 = entries.get(m);
              
                    // get each other candidate entry values
                    List<Integer> v1 = e1.get_values();
                    
                    //check if other Entry values contains search Entry value 
                    if (e.get_key() != e1.get_key() && e1.IsContain(v1, v.get(k)))
                    {
                        //if found match, add into intersect list                        
                        unique = false;
                    }
                }
                if(unique && !e.IsContain(diff, v.get(k)))
                    diff.add(v.get(k));
            }    
        }
		return diff;
	}
	
	/**
	 * Computes the set intersection of the entries.
	 *
	 * @param  entries the entries
	 * @return         the resulting values
	 */
	public static List<Integer> inter(List<Entry> entries) {
		// TODO: implement this
        //Prepre intersect list for output
        List<Integer> intersects = new ArrayList<Integer>();
                
        //for each candidate Entry
        for(int j =0; j<entries.size(); j++)
        {
            Entry e = entries.get(j);
            //get search Entry values
            List<Integer> v = e.get_values();
                       
            //compare with other candiate Entries in prepared Entries
            for(int k=0;k<v.size();k++)
            {
                int repeat = 0;
                for(int m=0; m<entries.size();m++)
                {
                    //check each other candidate Entry
                    Entry e1 = entries.get(m);
                    // get each other candidate entry values
                    List<Integer> v1 = e1.get_values();

                    //check if other Entry values contains search Entry value 
                    if (e.get_key() != e1.get_key() && v1.contains(v.get(k)))
                    {
                        //if found match, add into intersect list
                        repeat++;
                        //System.out.println(e1.get_key() + " matches " + v.get(k));                      
                    }
                }
                
                if (repeat == entries.size()-1)
                {
                    if (!intersects.contains(v.get(k)))
                            intersects.add(v.get(k));
                }
            }
        }
		return intersects;
	}

	/**
	 * Computes the set union of the entries.
	 *
	 * @param  entries the entries
	 * @return         the resulting values
	 */
	public static List<Integer> union(List<Entry> entries) {
		// TODO: implement this
        List < Integer> whole_list = new ArrayList <Integer>();
        for(int j =0; j<entries.size(); j++)
        {
            Entry e = entries.get(j);
            //get values of each Entry
            List<Integer> v = e.get_values();
            for(int k=0;k<v.size();k++)
            {
               // add each integer into whole integer list
                if (!whole_list.contains(v.get(k)))
                        whole_list.add(v.get(k));
                
            }
        
        }
        for ( int i = 0 ; i < whole_list.size(); i++){
            int count = 0;
    
            for ( int j = 0 ; j < whole_list.size(); j++){
                if (whole_list.get(i) == whole_list.get(j)){
                    count +=1;
                 
                    if (count >1){
                        whole_list.remove(j);
                        
                    }
                    
                }
            }
        }

        //whole_list.remove(0);
        return whole_list;

	}

	/**
	 * Computes the Cartesian product of the entries.
	 *
	 * @param  entries the entries
	 * @return         the resulting values
	 */
	public static List<List<Integer>> cartprod(List<Entry> entries) {
		// TODO: implement this
        List<List<Integer>> cartprod_list = new ArrayList<List<Integer>>();
        
        if (entries.size()>0)
        {
            //get Entry by key and get values of the Entry            
            Entry e = entries.get(0); // 'key a'
            List<Integer> v = e.get_values();

            //for each value in values array of the above Entry
            for(int k=0;k<v.size();k++) //'a values loop'
            {
               //combine seed Entry value to other Entry value as cartprod result
                List<Integer> cartprod;
                
                // get each value from value array
                Integer v_int = v.get(k); // each int of a value

                if (entries.size() == 1)
                {
                    // if there is only one key
                    cartprod = new ArrayList<Integer>();
                    cartprod.add(v_int);
                    cartprod_list.add(cartprod);
                }
                else
                {
                    // if more than one key found
                    if (entries.size()>1)
                    {
                        // compare other each Entry
                        Entry e1 = entries.get(1); // 2nd Ertry b
                        List<Integer> v1 = e1.get_values(); // b value list

                        for(int n = 0; n<v1.size();n++)
                        { 
                            Integer v1_int = v1.get(n); //each int of b value
                            if (entries.size() == 2)
                            {
                                cartprod = new ArrayList<Integer>();
                                cartprod.add(v_int);
                                cartprod.add(v1_int);
                                cartprod_list.add(cartprod);                                
                            }
                            else
                            {
                                // more than 2 keys found
                                if (entries.size() == 3)
                                {
                                    Entry e2 = entries.get(2); //Entry c
                                    List<Integer> v2 = e2.get_values();

                                    for(int q=0; q<v2.size(); q++) //for each c value
                                    {
                                        Integer v2_int = v2.get(q);
                                        cartprod = new ArrayList<Integer>();
                                        cartprod.add(v_int);
                                        cartprod.add(v1_int);
                                        cartprod.add(v2_int);
                                        
                                        cartprod_list.add(cartprod);                                       
                                    }
                                    
                                }
                            } 
                        }
                    }
                }
            }
        
        }
        //System.out.println("cartprod list output:" + cartprod_list);
		return cartprod_list;
	}

	/**
	 * Formats all the entries for display.
	 *
	 * @param  entries the entries to display
	 * @return         the entries with their values
	 */
	public static String listAllEntries(List<Entry> entries) { 
        String ent = "";

        for (int i =entries.size()-1 ; i>=0; i -=1)
        {
            if (i == 0){
                Entry e = entries.get(i);
                ent = ent + e.get_key() + " " + e.get();
            }
            else{
                Entry e = entries.get(i);
                ent = ent + e.get_key() + " " + e.get() + "\n";
            }
            
        } 

		return ent;
	}

}
