import cv2
import numpy as np
from tensorflow import keras
from keras.models import load_model

# Cargar el modelo previamente entrenado para la detección de emociones
affective_CNN_model = load_model("C:\\Users\\josek\\Documents\\gitMCC\\MCC-2023-1AD\\AffectiveComputingAI\\affective_CNN_trainned_652acc.h5")

# Etiquetas de las imágenes del dataset
labels = ['bored', 'engaged', 'excited', 'focused', 'interested', 'relaxed']

# Función para detectar caras y predecir emociones en tiempo real
def affectiveClassification(frame, modelo):
    # Convertir el frame a escala de grises
    gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
    
    # Detectar caras en el frame
    faces = face_cascade.detectMultiScale(gray, 1.1, 4)
    
    # Para cada cara detectada
    for (x, y, w, h) in faces:
        # Region Of Interest (ROI) rostro
        ROI = frame[y:y+h, x:x+w]
        
        # Redimensionar la ROI a 150x150 (para que coincida con el input_shape del modelo)
        ROI_resized = cv2.resize(ROI, (150, 150))
        
        # Normalizar la imagen (dividiendo por 255)
        ROI_normalized = ROI_resized / 255.0
        
        # Realizar la predicción de emociones en la ROI
        ROI_predicted = modelo.predict(np.expand_dims(ROI_normalized, axis=0))#[0]
        
        # Obtener el índice de la etiqueta con mayor probabilidad
        idx_label = np.argmax(ROI_predicted)
        
        # Obtener la etiqueta correspondiente
        label = labels[idx_label]
        
        # Mostrar la etiqueta en la pantalla
        cv2.putText(frame, label, (x, y - 10), cv2.FONT_HERSHEY_DUPLEX, 0.9, (0, 255, 0), 2)
        
        
        # Dibujar un rectángulo alrededor de la cara detectada
        #cv2.rectangle(frame, (x, y), (x + w, y + h), (0, 255, 0), 2)
        cv2.rectangle(frame, (x, y), (x + w, y + h), (200, 0, 119), 4)

##
##
# Iniciar la captura de video desde la cámara web
video_capture = cv2.VideoCapture(0)

# Cargar el clasificador de Haar para la detección de caras
face_cascade = cv2.CascadeClassifier(cv2.data.haarcascades + 'haarcascade_frontalface_default.xml')

while True:
    # Capturar un frame de la cámara
    ret, frame = video_capture.read()

    # Detectar y predecir emociones en el frame
    affectiveClassification(frame, affective_CNN_model)

    # Mostrar el frame resultante
    cv2.imshow('Real time affective detection', frame)

    # Salir del bucle si se presiona la tecla 'x'
    if cv2.waitKey(1) & 0xFF == ord('x'):
        break

# Liberar la captura de video y cerrar las ventanas
video_capture.release()
cv2.destroyAllWindows()
