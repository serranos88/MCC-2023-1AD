import cv2
import numpy as np
import tensorflow as tf
from tensorflow import keras
from keras.models import load_model
import face_recognition

# Set TensorFlow to use only CPU devices
tf.config.set_visible_devices([], 'GPU')

# Cargar el modelo previamente entrenado para la detección de emociones
affective_CNN_model = load_model("C:\\Users\\josek\\Documents\\gitMCC\\MCC-2023-1AD\\AffectiveComputingAI\\affective_CNN_trainned_7632acc_3emotions.h5")
#affective_CNN_model = load_model("C:\\Users\\josek\\Documents\\gitMCC\\MCC-2023-1AD\\AffectiveComputingAI\\affective_CNN_trainned_680acc969_5emo_stratified.h5")


# Cargar el modelo MLP previamente entrenado para la detección de emociones
affective_MLP_model = load_model("C:\\Users\\josek\\Documents\\gitMCC\\MCC-2023-1AD\\AffectiveComputingAI\\affective_trained_mlp_stratifiedtraining_491acc.h5")

# Etiquetas de las imágenes del dataset
labels = ['bored', 'engaged', 'excited', 'focused', 'interested', 'relaxed']

def toArrayFacialLandmarks(landmarks_list):
    #Convierte los diccionarios en un array de numpy. Ignora las keys y solo toma los valores.
    
    landmarks_array = []
    for face_landmarks in landmarks_list:
        landmarks = []
        for facial_feature in face_landmarks.keys():
            landmarks.extend(face_landmarks[facial_feature])
        landmarks_array.append(landmarks)
    return np.array(landmarks_array)

# Función para detectar caras y predecir emociones en tiempo real
def affectiveClassification(frame):
    # Escala de grises
    
    
    grayframe = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
    
    #faces = face_cascade.detectMultiScale(grayframe, 1.1, 4)
    #faces2 = face_recognition.face_locations(grayframe, model='cnn')
    faces = face_recognition.face_locations(grayframe, model='hog')
    #if len(faces2) > 0:
    try:
        for (top, right, bottom, left) in faces:
            
            # Region Of Interest (ROI) rostro
            #ROI = frame[top:bottom, left:right]
            ROI = grayframe[top:bottom, left:right]
            cv2.imshow('ROI', ROI)
            
            #image_array = preprocesar_imagen(ROI)
            image_array = cv2.resize(ROI, (150, 150))
            
            ''''''
            # Redimensionar
            ROI_resized = cv2.resize(ROI, (150, 150))

            # Expand dimensions to add a channel dimension
            ROI_expanded = np.expand_dims(ROI_resized, axis=-1)

            # Add batch dimension
            ROI_expanded = np.expand_dims(ROI_expanded, axis=0)

            #matriz_final = matriz_inicial[:, :, :, 0, :]
            #ROI_final = ROI_expanded[:, :, :, 0, :]

            # Normalizar la imagen (dividiendo por 255)
            #ROI_normalized = ROI_resized / 255.0

            # Prediccion
            #ROI_predicted = modelo.predict(np.expand_dims(ROI_normalized, axis=0))
            #ROI_predicted = affective_CNN_model.predict(np.expand_dims(ROI_expanded, axis=0))
            
            
            #ROI_predicted = affective_CNN_model.predict(ROI_final)
            ROI_predicted = affective_CNN_model.predict(ROI_expanded)
            
            #ROI_predicted = affective_CNN_model.predict(image_array)

            # Obtener el índice de la etiqueta con mayor probabilidad
            idx_label = np.argmax(ROI_predicted)
            print(np.argmax(ROI_predicted))

            # Obtener la etiqueta correspondiente
            label = labels[idx_label]

            # Mostrar la etiqueta en la pantalla
            cv2.putText(frame, label, (left, top - 10), cv2.FONT_HERSHEY_DUPLEX, 0.9, (0, 255, 0), 2)
            
            # Dibujar un rectángulo alrededor de la cara detectada
            cv2.rectangle(frame, (left, top), (right, bottom), (0, 255, 0), 2)
    except Exception as e:
        print (e)    



def affectiveClassificationMLP(frame):
    # Escala de grises
    #modelo.summary()
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
        #ROI_predicted = modelo.predict(np.expand_dims(landmarks_array, axis=0))
        ROI_predicted = affective_MLP_model.predict(np.expand_dims(landmarks_array, axis=0))

        # Obtener el índice de la etiqueta con mayor probabilidad
        idx_label = np.argmax(ROI_predicted)

        # Obtener la etiqueta correspondiente
        label = labels[idx_label] + " MLP"

        # Mostrar la etiqueta en la pantalla
        #text_result = "Modelo: MLP " + label
        cv2.putText(frame, label, (20,20), cv2.FONT_HERSHEY_DUPLEX, 0.9, (0, 255, 0), 2)
        #cv2.putText(frame, label, (0, 0), cv2.FONT_HERSHEY_DUPLEX, 0.9, (0, 255, 0), 2)

    except Exception as e:
        print("Error MLP" + e) 


def preprocesar_imagen(imagen):
    #imagen = Image.open(ruta_imagen).convert('L')  # Convertir a escala de grises si es necesario
    imagen = imagen.resize((150, 150))
    imagen_array = np.array(imagen)
    imagen_array = np.expand_dims(imagen_array, axis=0)  # Agregar una dimensión de lote
    imagen_array = np.expand_dims(imagen_array, axis=3)  # Agregar una dimensión de canal (para escala de grises)
    return imagen_array

##
##
# Iniciar la captura de video desde la cámara web
video_capture = cv2.VideoCapture(0)


while True:
    # Capturar un frame de la cámara
    ret, frame = video_capture.read()

    # Detectar y predecir emociones en el frame
    affectiveClassification(frame)
    affectiveClassificationMLP(frame)

    # Mostrar el frame resultante
    cv2.imshow('Real time affective detection', frame)

    # Salir del bucle si se presiona la tecla 'x'
    if cv2.waitKey(1) & 0xFF == ord('x'):
        break

# Liberar la captura de video y cerrar las ventanas
video_capture.release()
cv2.destroyAllWindows()
