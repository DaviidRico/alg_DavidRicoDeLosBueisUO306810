package p0 ;
import java.util.ArrayList;

public class JavaA3
{

public static boolean primoA3(int m)
{
// Algoritmo A3: detecta divisores desde 2 hasta m/2
for (int i=2; i<=m/2; i++)
    if (m%i==0)
        return false;
return true;
}

public static void listadoPrimos (int n)
{
ArrayList<Integer> lista = new ArrayList<Integer>();
lista.add(2); 
int contPrimos=1;

for (int i=3; i<=n; i+=2)
   if (primoA3(i))  
       { lista.add(i);
         contPrimos++;}

System.out.println("Hay "+contPrimos+" primos hasta "+n); 

}


public static void main (String arg [] )
{

System.out.println("TIEMPO EN JAVA DEL ALGORITMO A3");

long t1,t2;                   

for (int n=5000, casos=0; casos<8; n*=2, casos++)
   {
    t1=System.currentTimeMillis();

    listadoPrimos(n); 

    t2=System.currentTimeMillis();	

    System.out.println(t1+"///"+t2);

    System.out.println ("n="+n+"**** tiempo = "+(t2-t1)+" milisegundos \n");
    
   }
}

}