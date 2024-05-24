import pandas as pd
import numpy as np
import cv2
import tensorflow as tf
import matplotlib.pyplot as plt
import seaborn as sns
from sklearn.metrics import confusion_matrix
from tensorflow.keras.callbacks import TensorBoard
import datetime
import io

from sklearn.model_selection import train_test_split
from tensorflow.keras import layers,models


# Hiperparametros
kernel_size = 3
#epocas = 100
epocas = 50
batch_tam = 15
optimizador = 'adam'

##########################################################################################
##########################################################################################
#    CONFIGURACION DE CALLBACKS TENSORBOARD, MATRIZ DE CONFUSION Y GRAFICA DE PRECISION
##########################################################################################
##########################################################################################

class ConfusionMatrixCallback(tf.keras.callbacks.Callback):
    def __init__(self, log_dir, x_val, y_val):
        super().__init__()
        self.log_dir = log_dir
        self.x_val = x_val
        self.y_val = y_val
        self.file_writer_cm = tf.summary.create_file_writer(log_dir + '/cm')

    def on_epoch_end(self, epoch, logs=None):
        val_pred = np.argmax(self.model.predict(self.x_val), axis=1)
        cm = confusion_matrix(self.y_val, val_pred)
        labels_class = ['angry' ,'disgust' ,'fear' ,'happy', 'neutral' ,'sad' ,'surprise']
        
        figure = plt.figure(figsize=(8, 8))
        sns.heatmap(cm, annot=True, fmt="d", cmap=plt.cm.Blues, xticklabels=labels_class, yticklabels=labels_class)
        plt.title("Confusion Matrix")
        plt.ylabel('True Label')
        plt.xlabel('Predicted Label')
        
        cm_image = self.plot_to_image(figure)
        
        with self.file_writer_cm.as_default():
            tf.summary.image("Confusion Matrix", cm_image, step=epoch)

    def plot_to_image(self, figure):
        buf = io.BytesIO()
        plt.savefig(buf, format='png')
        plt.close(figure)
        buf.seek(0)
        image = tf.image.decode_png(buf.getvalue(), channels=4)
        image = tf.expand_dims(image, 0)
        return image



##########################################################################################
##########################################################################################




gpus = tf.config.experimental.list_physical_devices('GPU')
if gpus:
    try:
        for gpu in gpus:
            tf.config.experimental.set_memory_growth(gpu, True)
    except RuntimeError as e:
        print(e)


#df = pd.read_pickle('C:\\Users\\josek\\Documents\\gitMCC\\MCC-2023-1AD\\AffectiveComputingAI\\3Emociones.pkl')
#df = pd.read_pickle('C:\\Users\\josek\\Documents\\gitMCC\\MCC-2023-1AD\\AffectiveComputingAI\\affective_dataset_v2.pkl')
df = pd.read_pickle('C:\\Users\\josek\\Documents\\gitMCC\\MCC-2023-1AD\\Affective_FER\\affective_dataset_v2.pkl')


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
    imgArr = np.array(img) / 255.0
    return np.array(imgArr)

# Funcion para normalizar una imagen
def normalizar_imagen(x):
    return x / 255.0

# Definir el tamaño al que quieres redimensionar todas las imágenes
# El dataset FER2013 tiene imágenes de 48x48 píxeles
nuevo_ancho = 48
nuevo_alto = 48

# Redimensionar todas las imágenes a la misma forma y convertirlas en arreglos numpy
X = np.array([redimensionar_imagen(imagen, nuevo_ancho, nuevo_alto) for imagen in df['face_closeup']])


#X = X /255.0
#X = np.array([normalizar_imagen(imagen) for imagen in X])



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


#########################################
#########################################
#               CALLBACKS
#########################################
#########################################

# Configurar TensorBoard
log_dir = "Affective_FER/logs/fit/" + datetime.datetime.now().strftime("%Y%m%d-%H%M%S")
tensorboard_callback = TensorBoard(log_dir=log_dir, histogram_freq=1)

# Configurar el callback de la matriz de confusión
confusion_matrix_callback = ConfusionMatrixCallback(log_dir, X_test, y_test)

# Entrenar el modelo
#model.fit(X_train, y_train, epochs=5, validation_data=(X_test, y_test), callbacks=[tensorboard_callback, confusion_matrix_callback])

###########################################
###########################################
#               TENSORBOARD




#model.fit(X_train, y_train,batch_size=batch_tam, epochs=epocas, validation_data=(X_test, y_test),callbacks=[back])
model.fit(X_train, y_train,batch_size=batch_tam, epochs=epocas, validation_data=(X_test, y_test),callbacks=[tensorboard_callback, confusion_matrix_callback])

model.save('Affective_FER/affective_model_FER2013_CNN_000.h5')

from sklearn.metrics import accuracy_score, confusion_matrix

# Calcular la precisión del modelo en el conjunto de prueba
test_loss, test_accuracy = model.evaluate(X_test, y_test)

print("Precisión del modelo en el conjunto de prueba:", test_accuracy)

# Generar la matriz de confusión
y_pred = model.predict(X_test)
confusion_mat = confusion_matrix(y_test, np.argmax(y_pred, axis=1))

print("Matriz de confusión:")
print(confusion_mat)

# Mostrar logs de TensorBoard
#tensorboard --logdir Affective_FER/logs/fit/

#################################################################################
#################################################################################
#################################################################################