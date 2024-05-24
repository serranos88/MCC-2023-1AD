import numpy as np
import pandas as pd
from sklearn.model_selection import train_test_split
from tensorflow import keras
from keras.models import Sequential
from keras.layers import LSTM, Dense

# Cargar los datos desde el archivo CSV
data = pd.read_csv("C:\\Users\\josek\\Documents\\gitMCC\\MCC-2023-1AD\\LSTM\\sector8TS.csv")

# Convertir los datos a un array de NumPy
datos = data["Columna"].values


# Normalizar los datos (opcional)
# datos = (datos - datos.mean()) / datos.std()

# Definir la ventana de tiempo para la secuencia de entrada
ventana_tiempo = 10

# Crear secuencias de datos y sus correspondientes etiquetas
secuencias = []
etiquetas = []
for i in range(len(datos) - ventana_tiempo):
    secuencias.append(datos[i:i + ventana_tiempo])
    etiquetas.append(datos[i + ventana_tiempo])

# Convertir las secuencias y etiquetas a arrays de NumPy
X = np.array(secuencias)
y = np.array(etiquetas)

# Dividir los datos en conjuntos de entrenamiento y prueba
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

# Reshape de los datos para que tengan la forma adecuada para LSTM (número de muestras, ventana de tiempo, número de características)
X_train = np.reshape(X_train, (X_train.shape[0], ventana_tiempo, 1))
X_test = np.reshape(X_test, (X_test.shape[0], ventana_tiempo, 1))

# Crear el modelo LSTM
modelo = Sequential()
modelo.add(LSTM(50, input_shape=(ventana_tiempo, 1)))
modelo.add(Dense(1))

# Compilar el modelo
modelo.compile(optimizer='adam', loss='mean_squared_error')

# Entrenar el modelo
modelo.fit(X_train, y_train, epochs=100, batch_size=32, verbose=1)

# Evaluar el modelo
puntuacion = modelo.evaluate(X_test, y_test, verbose=0)
print("Puntuación de pérdida:", puntuacion)

# Hacer predicciones
predicciones = modelo.predict(X_test)

# Imprimir algunas predicciones
print("Predicciones:")
for i in range(5):
    print("Predicción:", predicciones[i], "Etiqueta real:", y_test[i])
