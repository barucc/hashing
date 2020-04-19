#include <stdio.h>
#include <stdlib.h>
#define MAXN 294967296
//4294967296
int main() // il programma principale di solito e' intero
{
	
	
	 long int* n = (long int*) calloc(MAXN,sizeof(long int));
	 long int j, k;

	
	//~ for (j=0;j<MAXN; j++) {
	//~ n[j]=0; // azzero tutti gli elementi del vettore
	//~ }
	// printf("Sono qui");
	for (j=2; j<=MAXN; j++) { // ora comincia il ciclo principale
		if (n[j]==1) continue ; // salto se il numero non e' primo
	// printf("%ld ",j);
		for (k=2*j; k<MAXN; k+=j) { // escludo tutti i multipli di j
			n[k]=1;
		}
	}
	 printf("Sono qui");
	  FILE * fp;
  
   /* open the file for writing*/
   fp = fopen ("primes.txt","w");
 
  for (j=1; j<MAXN; j++) {
	 if (n[j]==0) fprintf(fp, "%ld\n", j); // stampo i risultati
	}
 
   /* close the file*/  
   fclose (fp);
   
   free(n);
  
	 
	 
	 
	 
	 return 0; // main() deve ritornare un intero
}
