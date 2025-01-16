# EditableBufferedReader
SAD Practica1 : Programació de readline amb capacitats d’edició (Model/View/Controller)

Practica1(V1): versió de 

Este proyecto implementa una clase EditableBufferedReader que extiende BufferedReader, permitiendo la lectura de líneas de texto con capacidades de edición básicas en la consola. Está diseñado siguiendo las pautas de la práctica de programación orientada al patrón Model/View/Controller (MVC).

Características principales

Funcionalidades implementadas:

    Edición de texto:
      - Teclas de cursor: Soporte para mover el cursor hacia la derecha o izquierda en la línea actual.
      - Inicio y fin de línea: Uso de las teclas Home y End para navegar al principio o al final de la línea.
      - Borrar texto:
            Del: Borra el carácter actual.
            Backspace: Borra el carácter anterior.
      - Modo Insertar:
            Insert: Activa o desactiva el modo insertar
