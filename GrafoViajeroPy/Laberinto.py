import tkinter as tk

class MouseMaze:
    def __init__(self, root, rows, cols):
        self.root = root
        self.rows = rows
        self.cols = cols

        # Inicializar la matriz del laberinto
        self.grid = [[0] * cols for _ in range(rows)]

        # Inicializar la casilla de inicio y destino
        self.start_cell = None
        self.end_cell = None

        # Configuración de la celda actual al hacer clic
        self.current_cell = None

        # Crear la interfaz gráfica
        self.create_widgets()

    def create_widgets(self):
        self.canvas = tk.Canvas(self.root, width=self.cols * 30, height=self.rows * 30)
        self.canvas.pack()

        # Manejar clics del ratón
        self.canvas.bind("<Button-1>", self.on_click)

        # Botón para resolver el laberinto
        solve_button = tk.Button(self.root, text="Resolver Laberinto", command=self.solve_maze)
        solve_button.pack()

        # Botón para reiniciar el laberinto
        restart_button = tk.Button(self.root, text="Reiniciar", command=self.restart_maze)
        restart_button.pack()

    def on_click(self, event):
        # Obtener la posición del clic en el lienzo
        x, y = event.x // 30, event.y // 30

        # Verificar si la posición está dentro de los límites del laberinto
        if 0 <= x < self.cols and 0 <= y < self.rows:
            # Actualizar la celda actual
            self.current_cell = (x, y)

            # Marcar la casilla de inicio si no está establecida
            if self.start_cell is None:
                self.start_cell = self.current_cell
                self.draw_cell(self.current_cell, "green")

            # Marcar la casilla de destino si no está establecida y no es la misma que la casilla de inicio
            elif self.end_cell is None and self.current_cell != self.start_cell:
                self.end_cell = self.current_cell
                self.draw_cell(self.current_cell, "red")

            # Marcar las casillas de obstáculos
            elif self.current_cell != self.start_cell and self.current_cell != self.end_cell:
                self.grid[y][x] = 1
                self.draw_cell(self.current_cell, "black")

    def draw_cell(self, cell, color):
        x, y = cell
        self.canvas.create_rectangle(x * 30, y * 30, (x + 1) * 30, (y + 1) * 30, fill=color)

    def solve_maze(self):
        # Implementar la lógica de resolución del laberinto aquí
        
        pass

    def restart_maze(self):
        # Reiniciar el laberinto
        self.root.destroy()
        root = tk.Tk()
        root.title("Laberinto Raton")
        maze = MouseMaze(root, rows=10, cols=10)
        root.mainloop()
        


def main():
    root = tk.Tk()
    root.title("Laberinto Raton")
    #maze = MouseMaze(root, rows=10, cols=10)
    maze = MouseMaze(root, rows=10, cols=10)
    root.mainloop()

if __name__ == "__main__":
    main()
