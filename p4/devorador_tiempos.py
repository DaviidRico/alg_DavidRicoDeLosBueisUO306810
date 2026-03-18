import json
import time
import os
import sys  

from coloreado_grafo import realizar_voraz 

def ejecutar_pruebas_tiempo():
    repeticiones = int(sys.argv[1])
    tamanos = {4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768, 65536}
    
    print(f"Medición en Python (Repeticiones: {repeticiones})")
    print(f"{'n':<10} | {'t Coloreado (ms)':<15}")
    print("-" * 30)

    for n in tamanos:
        ruta = f'sols/g{n}.json'
        
        if os.path.exists(ruta):
            with open(ruta, 'r') as f:
                datos = json.load(f)
                grafo = datos["grafo"]
            t_acumulado = 0

            for _ in range(repeticiones):  
                inicio = time.perf_counter()
                realizar_voraz(grafo)
                fin = time.perf_counter()
                t_acumulado += (fin - inicio)

            # Convertimos a ms el total acumulado
            print(f"n={n:<8} | {t_acumulado * 1000:<15.4f}")  

if __name__ == "__main__":
    ejecutar_pruebas_tiempo()