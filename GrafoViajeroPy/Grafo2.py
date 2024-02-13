# Using a basic dictionary object
G = {'S':['A', 'B', 'C', 'D']}
grafo_ciudades = {'S', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I'}

class Directed_Graph:
    def __init__(self):
        # Se crea un diccionario para el grafo
        self.graph_dict = {}
    # Agrega los Nodos
    def add_vertex(self, vertex):
        # G = {v1:[]}  se le agregan objetos de lista vacia por Nodo
        if vertex in self.graph_dict:
            return "Nodo ya existente en el grafo"
        self.graph_dict[vertex] = []
    # Metodo para agregar Aristas, recibe el grafo y un objeto Arista
    def add_edge(self, edge):
        v1 = edge.get_v1()
        v2 = edge.get_v2()
        # G{v1:[v2]}  se agrega el Nodo destino v2 a la lista de vecinos del Nodo v1 
        if v1 not in self.graph_dict:
            raise ValueError(f'Nodo {v1.get_name()} no esta en el grafo')
        if v2 not in self.graph_dict:
            raise ValueError(f'Nodo {v2.get_name()} no esta en el grafo')
        self.graph_dict[v1].append(v2)
    # Metodo para verificar si un Nodo esta en el grafo
    def is_vertex_in(self, vertex):
        return vertex in self.graph_dict
    # Metodo para obtener un Nodo del grafo
    def get_vertex(self, vertex_name):
        for v in self.graph_dict:
            if vertex_name == v.get_name(): 
                return v
        print(f'El nodo {vertex_name} no existe')
    # Metodo para obtener los vecinos de un Nodo
    def get_neighbors(self, vertex):
        return self.graph_dict[vertex]
    def __str__(self):
        all_edges = ''
        for v1 in self.graph_dict:
            for v2 in self.graph_dict[v1]:
                all_edges += f'{v1.get_name()} ---> {v2.get_name()}\n'
        return all_edges

# Heredando la clase Directed_Graph para crear un grafo no dirigido
class Undirected_Graph(Directed_Graph):
    def add_edge(self, edge):
        Directed_Graph.add_edge(self, edge)
        edge_inverted = Edge(edge.get_v2(), edge.get_v1())
        Directed_Graph.add_edge(self, edge_inverted)

# E1 = {C,B} una Arista esta dada por la lista de conexion entre dos nodos, el primer elemento es el Nodo actual y el segundo Nodo destino
# E1 = {v1, v2}
class Edge:
    def __init__(self, v1, v2):
        self.v1 = v1
        self.v2 = v2
    def get_v1(self):
        return self.v1
    def get_v2(self):
        return self.v2
    def __str__(self):
        return self.v1.get_name() + ' ---> ' + self.v2.get_name()
    
class Vertex:
    def __init__(self, name):
        self.name = name
    def get_name(self):
        return self.name
    def __str__(self):
        return self.name

def build_graph(graph):
    g = graph()
    # Agregando los Nodos
    for v in grafo_ciudades:
        g.add_vertex(Vertex(v))
    # Agregando las Aristas
    g.add_edge(Edge(g.get_vertex('S'), g.get_vertex('A')))
    g.add_edge(Edge(g.get_vertex('S'), g.get_vertex('B')))
    g.add_edge(Edge(g.get_vertex('S'), g.get_vertex('C')))
    g.add_edge(Edge(g.get_vertex('A'), g.get_vertex('D')))
    return g

g1 = build_graph(Undirected_Graph)
print(g1)

# ALGORITMOS DE BUSQUEDA RECORRIDO
# Busqueda en profundidad (DFS)
def dfs(graph, start, visited=None):
    # Si no se ha visitado ningun Nodo, se crea un conjunto vacio
    if visited is None:
        visited = set()
    visited.add(start)
    print(start.get_name())
    for next in graph.get_neighbors(start):
        if next not in visited:
            dfs(graph, next, visited)
    return visited