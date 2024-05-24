'''
Computacion Afectiva
Dataframe de emociones (bored, engaged, excited, focused, interested, relaxed) - MCCC TECNMM
Entrenamiento 3 etiquetas(categorias) 80%-20%
Modelo de MultiLayer Perceptron (MLP) para la detección de emociones
Accuracy 48.0%
3 Etiquetas MLP 
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


# Cargar el modelo MLP previamente entrenado para la detección de emociones
affective_MLP_model = load_model("C:\\Users\\josek\\Documents\\gitMCC\\MCC-2023-1AD\\AffectiveComputingAI\\affective_MLP_trainned_480acc_3emo.h5")
affective_MLP_model.summary()

# Etiquetas de las imágenes del dataset
#labels = ['bored', 'engaged', 'excited', 'focused', 'interested', 'relaxed']
labels = ['bored', 'engaged', 'excited']


def affectiveClassificationMLP(frame):
    # Escala de grises
    grayframe = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)

    # Redimensionar
    resized_frame = cv2.resize(grayframe, (150, 150))
    
    faces = face_recognition.face_locations(frame)
    
    try:
        face_landmarks_list = face_recognition.face_landmarks(resized_frame)

        transforomed_landmarks = []
        for landmarks in face_landmarks_list:
            for facial_feature in landmarks.values():
                transforomed_landmarks.extend(facial_feature)

        # rellenar con ceros si no se detectan landmarks
        while len(transforomed_landmarks) < 144:
            transforomed_landmarks.append([0, 0])
        
        # recortar la list a 144 elementos
        transforomed_landmarks = transforomed_landmarks[:144]

        # convertir a array de numpy
        landmarks_array = np.array(transforomed_landmarks).tolist()

        # Prediccion
        ROI_predicted = affective_MLP_model.predict(np.expand_dims(landmarks_array, axis=0))

        # Obtener el índice de la etiqueta con mayor probabilidad
        idx_label = np.argmax(ROI_predicted)

        # Obtener la etiqueta correspondiente
        label = "MLP 3 emociones: " + labels[idx_label]

        # Mostrar la etiqueta en la pantalla
        cv2.putText(frame, label, (20,20), cv2.FONT_HERSHEY_DUPLEX, 0.9, (0, 255, 0), 2)

    except Exception as e:
        print(e) 



##
##
# Iniciar la captura de video desde la cámara web
video_capture = cv2.VideoCapture(0)


while True:
    # Capturar un frame de la cámara
    ret, frame = video_capture.read()

    # Detectar y predecir emociones en el frame
    affectiveClassificationMLP(frame)

    # Mostrar el frame resultante
    cv2.imshow('Real time affective detection', frame)

    # Salir del bucle si se presiona la tecla 'x'
    if cv2.waitKey(1) & 0xFF == ord('x'):
        break

# Liberar la captura de video y cerrar las ventanas
video_capture.release()
cv2.destroyAllWindows()
