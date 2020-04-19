import java.util.LinkedList;

public class OpenHashTable extends AbstractHashTable {
	// Un array di Entry per la tabella
	private Entry[] table;
	// marcatore di cella vuota ma da esaminare durante probing
	private final Entry DEFUNCT = new Entry(null, 0); 
	
	// Costruttori
	public OpenHashTable(int cap, int p, double lambda) {
		super(cap, p, lambda);
	}
	public OpenHashTable(int cap, int p) {
		super(cap, p); // massimo fattore di carico predefinito
	}
	public OpenHashTable(int cap) {
		super(cap); // primo predefinito
	}
	public OpenHashTable() {
		super(); // capacità predefinita
	}
		
	// Metodi non implementati in AbstractHashTable

	// Inizializza una tabella hash vuota secondo i parametri passati al costruttore
	protected void createTable() {
		int c = this.getCapacity();
		this.table = new Entry[c];
		return;
	}


	private Entry psearch(String k){
		int hk = hashFunction(k);
		int n = 0;
		Entry e = table[n*n+hk];
		while(e != null){
			if(e.getKey() == k){
				return e;
			}
			n++;
			e = table[n*n+hk];
		}	
		return null;
	}
	// Restituisce il valore (intero) associato alla chiave k
	// Restituisce -1 se la chiave è assente
	public int get(String k) {
		if(this.isEmpty())	return -1;
		Entry e = psearch(k);
		if(e!=null) return e.getValue();
		return -1;
	}
	
	// Aggiorna il valore associato alla chiave k
	// Restituisce il vecchio valore o -1 se la chiave non è presente
	public int put(String k, int value) {
		Entry ef = new Entry(k, value);
		if(this.isEmpty()){
			this.incSize();
			this.table[0] = ef;
			return -1;
		}
		Entry e = psearch(k);
		if(e!=null){
			int v = e.getValue();
			e.setValue(value);
			return v;
		}
		int c1 = this.getCapacity();
		if(this.getMaxLambda()<(double)this.size()/c1){	
				
			this.resize(c1);
			int c = this.getCapacity();
			System.out.println((double)this.size()/c1);
			Entry[] nl = new Entry[c];
			for(int i = 0; i<c1; i++){
				if(this.table[i]!=null)
					nl[i] = this.table[i];
			}
			this.table = nl;
		}
		int hk = hashFunction(k);
		this.incSize();
		int n = 0;
		e = this.table[n*n+hk];
		while(e != null || e != DEFUNCT){
			n++;
			e = this.table[n*n+hk];
		}
		this.table[n*n+hk] = ef;
		return -1;
		
	}
	
	
	// Rimuove la coppia con chiave k
	// Restituisce il vecchio valore o -1 se la chiave non è presente
	public int remove(String k) {
		Entry e = psearch(k);
		if(e!=null){
			this.decSize();
			int v = e.getValue();
			e = DEFUNCT;
			return v;
		}
		return -1;
	}
	
	// Restituisce un oggetto Iterable contenente tutte le coppie presenti
	// nella tabella hash
	public Iterable<Entry> entrySet() {
		LinkedList<Entry> l = new LinkedList<Entry>();
		int x = 0;
		Entry e = null;
		while(x<this.getCapacity()){
			e = this.table[x];
			if(e!=null && e!=DEFUNCT)
				l.add(e);
			x++;
		}
		return l;
	}
	
}


