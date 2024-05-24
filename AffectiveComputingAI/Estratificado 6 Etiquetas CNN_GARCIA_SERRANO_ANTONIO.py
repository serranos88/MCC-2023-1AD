'''
Computacion Afectiva
Dataframe de emociones (bored, engaged, excited, focused, interested, relaxed) - MCCC TECNMM
Entrenamiento ESTRATIFICADO por etiquetas(categorias) 80%-20%
Modelo de Red Neuronal Convolucional (CNN) para la detección de emociones
Accuracy 68%
Estratificado 6 Etiquetas CNN 
Optimizador Adam


'''
import cv2
import numpy as np
import tensorflow as tf
from tensorflow import keras
from keras.models import load_model
import face_recognition

# Set TensorFlow to use only CPU devices
tf.config.set_visible_devices([], 'GPU')

# Cargar el modelo previamente entrenado para la detección de emociones
affective_CNN_model = load_model("C:\\Users\\josek\\Documents\\gitMCC\\MCC-2023-1AD\\AffectiveComputingAI\\affective_CNN_trainned_680acc969_6emo_stratified.h5")


# Etiquetas de las imágenes del dataset
labels = ['bored', 'engaged', 'excited', 'focused', 'interested', 'relaxed']



# Función para detectar ROSTROS y predecir EMOCIONES en tiempo real
def affectiveClassificationCNN(frame):
    
    # Escala de grises - frame completo
    grayframe = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
    
    # Detectar rostros en el frame
    faces = face_recognition.face_locations(grayframe, model='hog')
    
    # Si se detectan rostros realizar el procesamiento de la imagen y la predicción de emociones
    try:
        for (top, right, bottom, left) in faces:
            
            # Region Of Interest (ROI) rostro
            ROI = grayframe[top:bottom, left:right]
            cv2.imshow('ROI', ROI)

            # Redimensionar
            ROI_resized = cv2.resize(ROI, (150, 150))

            # Expandir las dimensiones para agregar una dimensión de canal
            ROI_expanded = np.expand_dims(ROI_resized, axis=-1)

            # Agregar una dimensión de lote
            ROI_expanded = np.expand_dims(ROI_expanded, axis=0)

            # Prediccion
            ROI_predicted = affective_CNN_model.predict(ROI_expanded)

            # Obtener el índice de la etiqueta con mayor probabilidad
            idx_label = np.argmax(ROI_predicted)
            print(np.argmax(ROI_predicted))

            # Obtener la etiqueta correspondiente
            label = 'CNN estratificada: ' + labels[idx_label]

            # Mostrar la etiqueta en la pantalla
            cv2.putText(frame, label, (left, top - 10), cv2.FONT_HERSHEY_DUPLEX, 0.9, (0, 255, 0), 2)
            
            # Dibujar un rectángulo alrededor de la cara detectada
            cv2.rectangle(frame, (left, top), (right, bottom), (0, 255, 0), 2)

    except Exception as e:
        print (e)    





###################################################
###################################################
# Iniciar la captura de video desde la cámara web

video_capture = cv2.VideoCapture(0)


while True:
    # Capturar un frame de la cámara
    ret, frame = video_capture.read()

    # Detectar y predecir emociones en el frame
    affectiveClassificationCNN(frame)

    # Mostrar el frame resultante
    cv2.imshow('Real time affective detection', frame)

    # Salir del bucle si se presiona la tecla 'x'
    if cv2.waitKey(1) & 0xFF == ord('x'):
        break

# Liberar la captura de video y cerrar las ventanas
video_capture.release()
cv2.destroyAllWindows()
