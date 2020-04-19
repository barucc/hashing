import java.util.LinkedList.*;
import java.util.ArrayList.*;
import java.util.*;

public class ChainHashTable extends AbstractHashTable {
	// Un array di LinkedList per le liste di trabocco 
	// L'array table implementa la tabella hash
	private LinkedList<Entry> [] table;
	
	// Costruttori
	public ChainHashTable(int cap, int p, double lambda) {
		super(cap, p, lambda);
	}
	public ChainHashTable(int cap, int p) {
		super(cap, p); // massimo fattore di carico predefinito
	}
	public ChainHashTable(int cap) {
		super(cap); // primo predefinito
	}
	public ChainHashTable() {
		super(); // capacità predefinita
	}
	
	// Metodi non implementati in AbstractHashTable

	// Inizializza una tabella hash vuota secondo i parametri passati al costruttore
	protected void createTable() {
		int c = this.getCapacity();
		//~ System.out.println(c);
		this.table = new LinkedList[c];
		for(int i = 0; i<c; i++){
			table[i] = new LinkedList<Entry>();
		}
		return;
	}

	// Restituisce il valore (intero) associato alla chiave k
	// Restituisce -1 se la chiave è assente
	public int get(String k) {
		if(this.isEmpty())	return -1;
		int hk = hashFunction(k);
		LinkedList<Entry> l = this.table[hk];
		Iterator<Entry> it = l.iterator();
		Entry e = null;
		int x = 0;
		while(it.hasNext()){
			e = it.next();
			if(e.getKey() == k){
				return e.getValue();
			}
			x++;
		}
		return -1;	
	}
	
	// Aggiorna il valore associato alla chiave k
	// Restituisce il vecchio valore o -1 se la chiave non è presente
	public int put(String k, int value) {
		int hk = hashFunction(k);
		int ret = this.get(k);
		Entry e = new Entry(k,value);
		LinkedList<Entry> l = this.table[hk];
		
		if(this.isEmpty()){
			this.incSize();
			l.add(e);
			return -1;
		}
		
		if(ret!=-1){
			Iterator<Entry> it = l.iterator();
			Entry e1 = null;
			while(it.hasNext()){
				e1 = it.next();
				if(e1.getKey() == k){
					e1.setValue(value);
					break;
				}
			}
		}
		else{
			int c1 = this.getCapacity();
			if(this.getMaxLambda()<this.size()/c1){		
				this.resize(c1);
				int c = this.getCapacity();
				//~ System.out.println(c);
				LinkedList<Entry>[] nl = new LinkedList[c];
				for(int i = 0; i<c1; i++){
					nl[i] = table[i];
				}
				this.table = nl;
			}
			this.incSize();
			l.add(e);
		}
		
		return ret;
	}
	
	
	// Rimuove la coppia con chiave k
	// Restituisce il vecchio valore o -1 se la chiave non è presente
	public int remove(String k) {
		if(this.isEmpty())	return -1;
		int hk = hashFunction(k);
		LinkedList<Entry> l = this.table[hk];
		Iterator<Entry> it = l.iterator();
		Entry e = null;
		int x = 0;
		while(it.hasNext()){
			e = it.next();
			if(e.getKey() == k){
				this.decSize();
				e = l.remove(x);
				return e.getValue();
			}
			x++;
		}
		return -1;
	}
	
	// Restituisce un oggetto Iterable contenente tutte le coppie presenti
	// nella tabella hash
	public Iterable<Entry> entrySet() {
		LinkedList<Entry> l = new LinkedList<Entry>();
		int x = 0;
		while(x<this.getCapacity()){
			LinkedList<Entry> ll = this.table[x];
			Iterator<Entry> it = ll.iterator();
			Entry e = null;
			while(it.hasNext()){
				e = it.next();
				l.add(e);
			}
			x++;
		}
		return l;
	}

}


