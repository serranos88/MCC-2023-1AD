import pandas as pd

# Función para leer registros desde un archivo CSV
def leer_registros():
    dataset = pd.read_csv('DATASET_ALTURAS_PESOS.csv', encoding='utf-8')
    #dataset = pd.read_csv('C:\Users\josek\Documents\gitMCC\MCC-2023-1AD\Dataset\DATASET_ALTURAS_PESOS.csv')
    return dataset


# Función para ordenar registros por nombre, peso o estatura
def ordenar_registros(registros, clave):
    return registros.sort_values(by=clave)

# Función para agregar nuevos registros a la lista ordenada
def agregar_registro(registros, nuevo_registro):
    return pd.concat([registros, pd.DataFrame([nuevo_registro])], ignore_index=True).sort_values(by='Nombre')

# Función para borrar un registro por nombre
def borrar_registro(registros, nombre):
    return registros[registros['Nombre'] != nombre]

# Función para escribir registros en un archivo CSV
def escribir_registros(registros):
    registros.to_csv('DATASET_ALTURAS_PESOS.csv', index=False)




datos = leer_registros()
pd.head(datos,30)
ordenados = ordenar_registros(datos, 'Nombre')
print(ordenados)
pd.head(datos,30)

ordenados = ordenar_registros(datos, 'Peso')
print(ordenados)
pd.head(datos,30)

ordenados = ordenar_registros(datos, 'Estatura')
print(ordenados)
pd.head(datos,30)

nuevo_registro = {'Nombre': 'Anselmo', 'Peso': 70, 'Estatura': 1.70}
datos = agregar_registro(datos, nuevo_registro)
print(datos)
pd.head(datos,30)

datos = borrar_registro(datos, 'Anselmo')
print(datos)
pd.head(datos,30)