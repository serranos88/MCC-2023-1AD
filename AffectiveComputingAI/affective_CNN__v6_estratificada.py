import pandas as pd
import numpy as np
import cv2
import tensorflow as tf

from sklearn.model_selection import train_test_split
from tensorflow.keras import layers,models

# Hiperparametros
kernel_size = 5
#epocas = 100
epocas = 40
batch_tam = 20
optimizador = 'adam'


#df = pd.read_pickle('C:\\Users\\josek\\Documents\\gitMCC\\MCC-2023-1AD\\AffectiveComputingAI\\3Emociones.pkl')
df = pd.read_pickle('C:\\Users\\josek\\Documents\\gitMCC\\MCC-2023-1AD\\AffectiveComputingAI\\affective_dataset_v2.pkl')


df.info()


print(df.head())

################################################################
#################################################################
################################################################

print(df['face_closeup'][0])

import numpy as np
from PIL import Image

# Función para redimensionar una imagen
def redimensionar_imagen(imagen, nuevo_ancho, nuevo_alto):
    img = Image.fromarray(imagen)
    img = img.resize((nuevo_ancho, nuevo_alto))
    return np.array(img)

# Definir el tamaño al que quieres redimensionar todas las imágenes
nuevo_ancho = 150
nuevo_alto = 150

# Redimensionar todas las imágenes a la misma forma y convertirlas en arreglos numpy
X = np.array([redimensionar_imagen(imagen, nuevo_ancho, nuevo_alto) for imagen in df['face_closeup']])


X = X /255.0


etiquetas = df['label'].astype('category').cat.codes

print(etiquetas)


#X_train, X_test, y_train, y_test = train_test_split(X, etiquetas, test_size=0.2, random_state=42)
X_train, X_test, y_train, y_test = train_test_split(X, etiquetas, test_size=0.3,stratify=etiquetas, random_state=42)



# Definir el modelo CNN
model = models.Sequential([
    layers.Conv2D(256, (kernel_size, kernel_size), activation='relu', input_shape=(nuevo_ancho, nuevo_alto, 1)),
    layers.MaxPooling2D((2, 2)),
    layers.Conv2D(128, (kernel_size, kernel_size), activation='relu'),
    layers.MaxPooling2D((2, 2)),
    layers.Conv2D(64, (kernel_size, kernel_size), activation='relu'),
    layers.Flatten(),
    layers.Dense(32, activation='relu'),
    layers.Dense(len(etiquetas), activation='softmax')
])


class myCallback(tf.keras.callbacks.Callback):
    def on_epoch_end(self, epoch, logs={}):
        if logs.get('accuracy') >= 85:
            print("\nReached 85% accuracy so cancelling training!")
            self.model.stop_training = True
            
back = myCallback() 


#compilar el modelo
#model.compile(optimizer='adam',
#              loss='sparse_categorical_crossentropy',
#              metrics=['accuracy'])

model.compile(optimizer=optimizador,
              loss='sparse_categorical_crossentropy',
              metrics=['accuracy'])


model.fit(X_train, y_train,batch_size=batch_tam, epochs=epocas, validation_data=(X_test, y_test),callbacks=[back])


from sklearn.metrics import accuracy_score, confusion_matrix

# Calcular la precisión del modelo en el conjunto de prueba
test_loss, test_accuracy = model.evaluate(X_test, y_test)

print("Precisión del modelo en el conjunto de prueba:", test_accuracy)

# Generar la matriz de confusión
y_pred = model.predict(X_test)
confusion_mat = confusion_matrix(y_test, np.argmax(y_pred, axis=1))

print("Matriz de confusión:")
print(confusion_mat)


#################################################################################
#################################################################################
#################################################################################


'''
print(df['Imagen con zoom'][0])

import numpy as np
from PIL import Image

# Función para redimensionar una imagen
def redimensionar_imagen(imagen, nuevo_ancho, nuevo_alto):
    img = Image.fromarray(imagen)
    img = img.resize((nuevo_ancho, nuevo_alto))
    return np.array(img)

# Definir el tamaño al que quieres redimensionar todas las imágenes
nuevo_ancho = 150
nuevo_alto = 150

# Redimensionar todas las imágenes a la misma forma y convertirlas en arreglos numpy
X = np.array([redimensionar_imagen(imagen, nuevo_ancho, nuevo_alto) for imagen in df['Imagen con zoom']])


X = X /255.0


etiquetas = df['Etiquetas'].astype('category').cat.codes

print(etiquetas)


X_train, X_test, y_train, y_test = train_test_split(X, etiquetas, test_size=0.2, random_state=42)



# Definir el modelo CNN
model = models.Sequential([
    layers.Conv2D(256, (kernel_size, kernel_size), activation='relu', input_shape=(nuevo_ancho, nuevo_alto, 1)),
    layers.MaxPooling2D((2, 2)),
    layers.Conv2D(128, (kernel_size, kernel_size), activation='relu'),
    layers.MaxPooling2D((2, 2)),
    layers.Conv2D(64, (kernel_size, kernel_size), activation='relu'),
    layers.Flatten(),
    layers.Dense(32, activation='relu'),
    layers.Dense(len(etiquetas), activation='softmax')
])


class myCallback(tf.keras.callbacks.Callback):
    def on_epoch_end(self, epoch, logs={}):
        if logs.get('accuracy') >= 85:
            print("\nReached 85% accuracy so cancelling training!")
            self.model.stop_training = True
            
back = myCallback() 


#compilar el modelo
#model.compile(optimizer='adam',
#              loss='sparse_categorical_crossentropy',
#              metrics=['accuracy'])

model.compile(optimizer=optimizador,
              loss='sparse_categorical_crossentropy',
              metrics=['accuracy'])


model.fit(X_train, y_train,batch_size=batch_tam, epochs=epocas, validation_data=(X_test, y_test),callbacks=[back])


from sklearn.metrics import accuracy_score, confusion_matrix

# Calcular la precisión del modelo en el conjunto de prueba
test_loss, test_accuracy = model.evaluate(X_test, y_test)

print("Precisión del modelo en el conjunto de prueba:", test_accuracy)

# Generar la matriz de confusión
y_pred = model.predict(X_test)
confusion_mat = confusion_matrix(y_test, np.argmax(y_pred, axis=1))

print("Matriz de confusión:")
print(confusion_mat)


model.save('C:\\Users\\josek\\Documents\\gitMCC\\MCC-2023-1AD\\AffectiveComputingAI\\affective_CNN_trainned_000acc_3emotions.h5')


'''