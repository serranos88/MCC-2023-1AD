import cv2
import numpy as np
import tensorflow as tf
from tensorflow import keras
from keras.models import load_model
import face_recognition

# Set TensorFlow to use only CPU devices
tf.config.set_visible_devices([], 'GPU')

# Cargar el modelo previamente entrenado para la detección de emociones
affective_CNN_model = load_model("C:\\Users\\josek\\Documents\\gitMCC\\MCC-2023-1AD\\AffectiveComputingAI\\affective_CNN_trainned_652acc.h5")

# Cargar el modelo MLP previamente entrenado para la detección de emociones
affective_MLP_model = load_model("C:\\Users\\josek\\Documents\\gitMCC\\MCC-2023-1AD\\AffectiveComputingAI\\affective_trained_mlp_43acc.h5")

# Etiquetas de las imágenes del dataset
labels = ['bored', 'engaged', 'excited', 'focused', 'interested', 'relaxed']

# Función para detectar caras y predecir emociones en tiempo real
def affectiveClassification(frame, modelo):
    # Escala de grises
    grayframe = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
    
    #faces = face_cascade.detectMultiScale(grayframe, 1.1, 4)
    #faces2 = face_recognition.face_locations(grayframe, model='cnn')
    faces2 = face_recognition.face_locations(grayframe, model='hog')
    #if len(faces2) > 0:
    try:
        for (top, right, bottom, left) in faces2:
            
            # Region Of Interest (ROI) rostro
            ROI = frame[top:bottom, left:right]
            cv2.imshow('ROI', ROI)
            
            
            # Redimensionar
            ROI_resized = cv2.resize(ROI, (150, 150))

            # Normalizar la imagen (dividiendo por 255)
            ROI_normalized = ROI_resized / 255.0

            # Prediccion
            ROI_predicted = modelo.predict(np.expand_dims(ROI_normalized, axis=0))

            # Obtener el índice de la etiqueta con mayor probabilidad
            idx_label = np.argmax(ROI_predicted)

            # Obtener la etiqueta correspondiente
            label = labels[idx_label]

            # Mostrar la etiqueta en la pantalla
            cv2.putText(frame, label, (left, top - 10), cv2.FONT_HERSHEY_DUPLEX, 0.9, (0, 255, 0), 2)
            
            # Dibujar un rectángulo alrededor de la cara detectada
            cv2.rectangle(frame, (left, top), (right, bottom), (0, 255, 0), 2)
    except Exception as e:
        pass     



def affectiveClassificationMLP(frame, modelo):
    # Escala de grises
    #modelo.summary()
    grayframe = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
    
    #faces = face_cascade.detectMultiScale(grayframe, 1.1, 4)
    #faces2 = face_recognition.face_locations(grayframe, model='cnn')
    faces2 = face_recognition.face_locations(grayframe, model='hog')
    #if len(faces2) > 0:
    try:
        for (top, right, bottom, left) in faces2:
            
            # Region Of Interest (ROI) rostro
            ROI = frame[top:bottom, left:right]
            cv2.imshow('ROI', ROI)
            print(ROI.shape)
            
            
            # Redimensionar
            ROI_resized = cv2.resize(ROI, (150, 150))

            # Normalizar la imagen (dividiendo por 255)
            ROI_normalized = ROI_resized / 255.0

            # Prediccion
            #ROI_predicted = modelo.predict(np.expand_dims(ROI_normalized, axis=0))
            ROI_predicted = modelo.predict(np.expand_dims(axis=0))
            print(ROI_predicted)
            #ROI_predicted = modelo.predict_classes(np.expand_dims(ROI_normalized, axis=0))

            # Obtener el índice de la etiqueta con mayor probabilidad
            idx_label = np.argmax(ROI_predicted)
            print(ROI_predicted)

            # Obtener la etiqueta correspondiente
            label = labels[idx_label]

            # Mostrar la etiqueta en la pantalla
            cv2.putText(frame, label, (left, top - 10), cv2.FONT_HERSHEY_DUPLEX, 0.9, (0, 255, 0), 2)
            
            # Dibujar un rectángulo alrededor de la cara detectada
            cv2.rectangle(frame, (left, top), (right, bottom), (0, 255, 0), 2)
    except Exception as e:
        print(e) 

##
##
# Iniciar la captura de video desde la cámara web
video_capture = cv2.VideoCapture(0)



# Cargar el clasificador de Haar para la detección de caras
#face_cascade = cv2.CascadeClassifier(cv2.data.haarcascades + 'haarcascade_frontalface_default.xml')

while True:
    # Capturar un frame de la cámara
    ret, frame = video_capture.read()

    # Detectar y predecir emociones en el frame
    #affectiveClassification(frame, affective_CNN_model)
    affectiveClassificationMLP(frame, affective_MLP_model)

    # Mostrar el frame resultante
    cv2.imshow('Real time affective detection', frame)

    # Salir del bucle si se presiona la tecla 'x'
    if cv2.waitKey(1) & 0xFF == ord('x'):
        break

# Liberar la captura de video y cerrar las ventanas
video_capture.release()
cv2.destroyAllWindows()
