import json

import matplotlib.pyplot as plt
import networkx as nx
import random
import math

def realizar_voraz(grafo):
    colores_disponibles = ["red", "blue", "green", "yellow", "orange", "purple", "cyan", "magenta", "lime"]
    solucion = {}

    for nodo, vecinos in grafo.items():
        # Buscamos qué colores tienen ya los vecinos coloreados
        colores_vecinos = {solucion[str(v)] for v in vecinos if str(v) in solucion}
        
        # Asignamos el primer color que no esté pillado 
        for color in colores_disponibles:
            if color not in colores_vecinos:
                solucion[nodo] = color
                break
    return solucion

if __name__ == "__main__":
    n = 100 # Puedes subir este número para ver grafos más grandes
    mapa = generar_mapa_grafo(n)
    
    # Ejecutamos el algoritmo en Python
    solucion = realizar_voraz(mapa["grafo"])

    if solucion:
        print("Solución encontrada con Python")
        dibujar_mapa_coloreado(mapa, solucion)
        # Guardamos en la carpeta de soluciones [cite: 24]
        with open(f'sols/solucion_py_{n}.json', 'w') as f:
            json.dump(solucion, f)
    else:
        print("No se encontró solución.")

def generar_mapa_grafo(n):
    # Calcular dimensiones óptimas de la cuadrícula
    lado = math.ceil(math.sqrt(n))
    ancho, alto = lado, lado

    # Generar posiciones únicas en una cuadrícula
    posiciones = random.sample([(x, y) for x in range(ancho) for y in range(alto)], n)
    nodos = {i: posiciones[i] for i in range(n)}

    grafo = {str(i): set() for i in range(n)}

    # Crear conexiones respetando la proximidad geográfica y garantizar conectividad
    nodos_conectados = set()
    for i in range(n):
        x1, y1 = nodos[i]
        vecinos = []
        for j in range(n):
            if i != j:
                x2, y2 = nodos[j]
                # Incluir direcciones cardinales y diagonales
                # Cardinales: distancia Manhattan = 1
                # Diagonales: distancia Euclidiana <= sqrt(2) y distancia Manhattan = 2
                manhattan_dist = abs(x1 - x2) + abs(y1 - y2)
                euclidean_dist = math.sqrt((x1 - x2) ** 2 + (y1 - y2) ** 2)

                if manhattan_dist == 1 or (euclidean_dist <= math.sqrt(2) and manhattan_dist == 2):
                    vecinos.append(j)

        # Asegurar al menos una conexión
        if vecinos:
            nodo_conectar = random.choice(vecinos)
            grafo[str(i)].add(nodo_conectar)
            grafo[str(nodo_conectar)].add(i)
            nodos_conectados.add(i)
            nodos_conectados.add(nodo_conectar)

        # Aumentar densidad de conexiones
        num_conexiones = min(random.randint(0, 8), len(vecinos))
        conexiones = random.sample(vecinos, num_conexiones) if len(vecinos) >= num_conexiones else vecinos

        for c in conexiones:
            if c not in grafo[str(i)]:
                grafo[str(i)].add(c)
                grafo[str(c)].add(i)

    for nodo in grafo:
        grafo[nodo] = list(grafo[nodo])

    # Verificar si todos los nodos están conectados

    def dfs(grafo, nodo_inicial):
        visitados = set()
        pila = [nodo_inicial]

        while pila:
            nodo = pila.pop()
            if nodo not in visitados:
                visitados.add(nodo)
                for vecino in grafo[str(nodo)]:
                    if vecino not in visitados:
                        pila.append(vecino)

        return visitados

    visitados = dfs(grafo, 0)

    # Si hay nodos aislados, conectarlos manualmente
    nodos_no_conectados = set(range(n)) - visitados
    while nodos_no_conectados:
        nodo = nodos_no_conectados.pop()
        nodo_conectar = random.choice(list(visitados))
        grafo[str(nodo)].append(nodo_conectar)
        grafo[str(nodo_conectar)].append(nodo)
        visitados.add(nodo)

    mapa = {"nodos": nodos, "grafo": grafo}
    with open(f'sols/g{n}.json', 'w') as f:
        json.dump(mapa, f)
        f.close()
    return mapa


def dibujar_mapa_coloreado(grafo, colores):
    G = nx.Graph()

    # Añadir nodos con sus posiciones y aristas al grafo
    pos = {str(nodo): (x, y) for nodo, (x, y) in grafo["nodos"].items()}
    for region, vecinos in grafo["grafo"].items():
        G.add_node(region)
        for vecino in vecinos:
            G.add_edge(region, str(vecino))

    # Asignar colores a los nodos
    color_map = [colores.get(region, "gray") for region in G.nodes()]

    # Dibujar el grafo
    plt.figure(figsize=(8, 8))
    nx.draw(G, pos, node_color=color_map, with_labels=True, node_size=500, font_size=10, font_color='black',
            edge_color='gray')
    plt.show()


if __name__ == "__main__":

    # Generar un mapa con 100 nodos
    mapa = generar_mapa_grafo(100)
    print(mapa)

    """
    # Podemos cargar la información desde archivos JSON, si se prefiere generar la solución en Java.
    with open('sols/graph.json') as f:
        mapa = json.load(f)
        f.close()
        
    with open('sols/solucion.json') as f:
        colores_nodos = json.load(f)
        f.close()
    """
    colores_nodos = {}
    for nodo in mapa["grafo"].keys():
        colores_nodos[nodo] = "red"
    # Visualizar el mapa
    dibujar_mapa_coloreado(mapa, colores_nodos)

    # Mostrar información del grafo
    print(f"Número de nodos: {len(mapa['nodos'])}")
    print(f"Conexiones:")
    for nodo, vecinos in mapa["grafo"].items():
        print(f"  Nodo {nodo}: conectado con {vecinos}")
