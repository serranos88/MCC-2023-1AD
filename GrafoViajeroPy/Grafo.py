
class Grafo_dirigido:
    def __init__(self):
        self.grafo_dict = {}
    def add_vertice(self, vertice):
        if vertice in self.grafo_dict:
            return "Vertice ya esta en el grafo"
        self.grafo_dict[vertice] = []
    def add_arista(self, arista):
        v1 = arista.get_v1()
        v2 = arista.get_v2()
        if v1 not in self.grafo_dict:
            raise ValueError(f'Vertice {v1.get_name()} no presente en el grafo')
        if v2 not in self.grafo_dict:
            raise ValueError(f'Vertice {v2.get_name()} no presente en el grafo')
        self.grafo_dict[v1].append(v2)
    
    def is_vertice_in(self, vertice):
        return vertice in self.grafo_dict
    
    def get_vertice(self, vertice_name):
        for v in self.grafo_dict:
            if vertice_name == v.get_name(): return v
        print(f'El nodo {vertice_name} no existe')
        




class Arista:
    def __init__(self, v1, v2):
        self.v1 = v1
        self.v2 = v2
    def get_v1(self):
        return self.v1
    def get_v2(self):
        return self.v2
    def __str__(self):
        return self.v1.get_name() + ' ---> ' + self.v2.get_name()
    
class Vertice:
    def __init__(self, name):
        self.name = name
    def get_name(self):
        return self.name
    def __str__(self):
        return self.name
    
    
    
    