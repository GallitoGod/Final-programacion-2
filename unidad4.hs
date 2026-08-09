-- LEAN EL README NO SEAN GILES (CONSEJO: USEN ALT+Z EN EL EDITOR)

-- ===========================================================================================
-- PROGRAMACION 2 - UNIDAD 4 GUIA DEFINITIVA SUPREMA 4K (HASKELL Y PARADIGMA FUNCIONAL)
-- ===========================================================================================

{-
    PREGUNTAS REALES DE EXAMEN FINAL (UNIDAD 4 - HASKELL):
    
    1. ¿Que son las funciones de orden superior? Explique una.
       R: Son funciones que cumplen al menos una de dos condiciones: reciben otra funcion como argumento, o devuelven una funcion como resultado. 
       Ejemplo: 'map' recibe una funcion y una lista, y le aplica esa funcion a cada elemento.
       Firma de map: map :: (a -> b) -> [a] -> [b]


    2. ¿Que es el Pattern Matching (Encaje de Patrones)?
       R: Es una herramienta que permite evaluar la forma o estructura de un dato para decidir que definicion de una funcion ejecutar. Sirve para desarmar datos (como separar la cabeza y la cola de una lista con (x:xs)).


    3. ¿Que es la Currificacion (Currying)?
       R: Es el principio teorico que dicta que en Haskell TODAS las funciones reciben exactamente UN SOLO argumento. Si una funcion parece recibir dos (ej. suma a b), en realidad recibe el primero ('a') y devuelve una funcion nueva que esta esperando recibir el segundo ('b').


    4. ¿Que es la Evaluacion Perezosa (Lazy Evaluation) vs Ansiosa?
       R: La evaluacion ansiosa (Java, C) evalua los argumentos antes de enviarlos a la funcion. 
       La evaluacion perezosa (Haskell) NO evalua nada hasta que es estrictamente necesario para imprimir un resultado. Esto permite crear estructuras infinitas (como [1..]) y que el programa no colapse.

       - Ejemplo (Estructuras infinitas):
            natural :: [Int]
            natural = [1..]
            -- En GHCi: >>> take 5 natural
            -- Resultado: [1,2,3,4,5]. Solo evalua y devuelve los 5 primeros valores, no intenta recorrer la lista infinita (cosa que haria colapsar a un lenguaje ansioso).

       
    5. Escriba una funcion recursiva usando sintaxis de listas y explique Pattern Matching y Currificacion sobre ella. (EJERCICIO REAL DE FINAL)
       R: 
        length' :: [a] -> Int
        length' [] = 0
        length' (_:xs) = 1 + length' xs

        - Explicando Pattern Matching aca: La funcion evalua la estructura de la lista de entrada. Si detecta la forma '[]', ejecuta el caso base (0). Si detecta la forma '(_:xs)', ignora la cabeza y extrae la cola 'xs' para enviarla a la llamada recursiva.

        - Explicando Currificacion aca: El operador (+) es en realidad una funcion currificada. En '1 + length' xs', el (+) recibe el 1 y devuelve una funcion nueva, la cual queda a la espera de recibir el entero que devuelva 'length' xs' para completar la operacion.
-}

{-
    A mi entender, la practica, de esta unidad, para el final se centra en:
        1_ Los tipos basicos y la declaracion de funciones.
        2_ El polimorfismo en la declaracion de funciones, sus definiciones por pattern matching y los conceptos del paradigma que los envuelven.
        3_ El juego de la currificcion y la evaluacion perezosa y no perezosa.
        4_ Tipos de datos compuestos, sus sintaxis y sus capacidades.
        5_ La composicion de funciones y como de ello se puede terminar en funciones de orden superior.
        6_ La creacion de operadores y tipos propios.

    Aunque la capacidad para surtir aprietos en el final esta unicamente en la practica de los conceptos, el catalogarlos en 6 items brinda un enfoque claro para el entendimiento de todo lo relacionado con el paradigma funcional implementado en Haskell.
-}


-- ===========================================================================================
-- 1. TIPOS BASICOS Y DECLARACION DE FUNCIONES
-- ===========================================================================================

{-
    Tipos de datos:
    - Int: entero fijo (tiene limite/overflow).su rango es aproximadamente [1*10^(29), 1*^10(-29)-1].
    - Integer: entero arbitrariamente grande (no tiene overflow, pero gasta mas memoria),obviamente es mas lento
    - Float/Double: coma flotante.
    - Rational: fracciones exactas (Nunca lo use).
    - Bool: True | False.
    - Char: un solo caracter (ej: 'a').
    - String: cadena de caracteres. En realidad es una lista de Char (String === [Char]),Tambien es un tipo de dato complejo.
-}

-- DECLARACION DE FUNCIONES(Firma y Definicion)
doble:: Int -> Int -- 'firma': recibe un Int, devuelve un Int (Aca se declara la funcion
doble x = 2*x   -- definicion: que hace con esa 'x'


-- las constantes son simplemente funciones sin argumentos:
saludo:: String
saludo = "Hola!" 


-- ===========================================================================================
-- 2. POLIMORFISMO, PATTERN MATCHING Y RECURSIVIDAD
-- ===========================================================================================

-- #POLIMORFISMO parametrico: La 'a' es una variable de tipo. Funciona con CUALQUIER tipo de dato.Como tal, esta funcion puede recibir cualquier tipo de dato. En su declaracion no se especifica el tipo que recibe ni el que devuelve.
espejo :: a -> a
espejo x = x 


-- recursividad + Pattern Matching: una funcion puede tener mas de una unica definicion y esta es elegida a traves del pattern matching:
long :: [Int] -> Int
long [] = 0 --caso base: Si la lista esta vacia, da 0.
long (_: xs) = 1 + long xs --caso recursivo: Ignora la cabeza (_), suma 1 y repite con la cola (xs).


-- #PATTERN MATCHING simple para manejo de errores:
safeHead :: [a] -> a
safeHead [] = error "Lista vacia" -- asi de simple se hacen errores, solo tene en cuenta que se pueda devolver con polimorfismo
safeHead (x:_) = x --toma el primer elemento (x) e ignora el resto (_)



-- CONDICIONALES (Dos formas de hacer lo mismo):

-- 'forma 1': guardas (ideal para multiples condiciones matematicas)
signo :: Int -> Int 
signo x 
    | x < 0 = -1
    | x == 0 = 0
    | otherwise = 1

-- 'forma 2': If / Then / Else (En Haskell el 'else' es OBLIGATORIO)
signo2 :: Int -> Int
signo2 0 = 0
signo2 x = if x < 0 then -1 else 1


-- clausula WHERE: simplifica calculos llamandolos por un nombre (variables locales)util cuando hay expresiones matematicas recurrentes en una funcion.
hipotenusa :: Double -> Double -> Double
hipotenusa a b = sqrt (a2 + b2)
    where
        a2 = a*a
        b2 = b*b


-- ===========================================================================================
-- 3. CURRIFICACION Y EVALUACION PEREZOSA (LA MAGIA DE HASKELL)
-- ===========================================================================================

-- CURRIFICACION en la practica:
{-
    Esta funcion parece pedir dos argumentos para devolver un valor. En realidad, en Haskell no existen funciones que usen mas de un argumento. En el caso de la funcion suma, simplemente se llama a otra funcion con el otro argumento para poder resolver la funcion.
-}
suma :: Int -> Int -> Int
suma a b = a+b

-- Esta firma es matematicamente equivalente a la de arriba:
{-    
    Esto es basicamente lo mismo a la funcion suma. Esa es la funcion de la currificcion, es como que si se tratara de una pipeline de "eventos" que van sucediendo para llegar al resultado necesario. Entonces el currying es una aplicacion parcial natural en Haskell.
-}
sumaEq :: Int -> (Int -> Int)
sumaEq a b = a+b


-- EVALUACION PEREZOSA (Lazy Evaluation) demostrada:
constt :: a -> b -> a 
constt x _ = x
-- Prueba en GHCi:si yo llamata a una constante: `constante 5 (error "Explota")`
-- Resultado: Devuelve 5 y NO explota, porque el error (b) nunca se uso ni evaluo,mientras que en la evaluacion ansiosa explotaria igual

naturales :: [Int]
naturales = [0..] -- Crea una lista infinita.
--Prueba en GHCi: `take 5 naturales`
--Resultado: [0,1,2,3,4]. Haskell no intenta crear el infinito, solo calcula los 5 que le pediste.


-- ===========================================================================================
-- 4. ESTRUCTURAS COMPUESTAS: TUPLAS Y LISTAS
-- ===========================================================================================

{- 
    TUPLAS: Tamaño fijo, tipos mezclados.

    - (1, "hola") --par
      (1, "hola", 2) --triple
      () --vacia 
    - Funciones nativas (Solo para tuplas de 2 elementos): fst (primero), snd (segundo).
    -  No hay mas de tuplas, son muy simples.
-}
swapp :: (a,b) -> (b,a)
swapp (x,y) = (y,x)


{-
    LISTAS: Tamaño dinamico, un solo tipo de dato obligatorio.

    [] --vacia
    [1,2,3,4] --literal
    1 : [2,3] --agrega adelante => [1,2,3]
    (++) --concatenacion de listas
    [1..5] -- => [1,2,3,4,5]
    [1,3..5] -- => [1,3,4,5]
-}

{-
    Patrones para pattern matching:

    f [] => sin elementos
    f (x:xs) => al menos la cebeza
    f (x:y:xs) => al menos dos elementos
    f [x] => unico elemento
-}

{-
    List Comprehension (Listas por comprension, similar a notacion de conjuntos en matematica)
    [(x,y) | x <- [1..5], y <- [1,2]] -- => [(1,1), (1,2), (2,1), (2,2), (3,1), ..., (5,2)]
    [x+2 | x <- [1,4..6]] -- => [3,6,7,8]
    [x | x <- [1,2,3,4], even x] -- => [2,4]
    --  En general todo se da con razon a la primera sentencia

    [ x*2 | x <- [1..5] ]                 => [2,4,6,8,10]
    [ x | x <- [1..10], even x, x > 5 ]   => [6,8,10] (Con filtros aplicados)
-}

{-
    Funciones (las mas utiles): en general, las mas importantes son:
        - head [1,2,3]    => 1
        - tail [1,2,3]    => [2,3]
        - take 2 [1,2,3]  => [1,2]
        - drop 1 [1,2,3]  => [2,3]
        
        - zip [1,2,3] ['a','b','c'] => [(1,'a'), (2,'b'), (3,'c')] (Une dos listas en una lista de tuplas)
        - unzip [(1,'a'), (2,'b')]  => ([1,2], ['a','b']) (Separa tuplas en una tupla de dos listas)
        
        - dropWhile (<5) [1..5] => [5]
        - takeWhile
            # dropWhile y takeWhile necesitan de una funcion que condicione cuando hacer sus acciones. 
    
    #Obviamente que las funciones de orden superior siempre funcionan con las listas, funciones como map, filter, foldr,...
    Ejemplos:
        -map (+2) [1..5] => [3,4,5,6,7]
        -filter even [1..5] => [2,4]
     
-}


-- ===========================================================================================
-- 5. COMPOSICION DE FUNCIONES Y ORDEN SUPERIOR
-- ===========================================================================================

-- el operador de composicion es (el punto '.'): Junta funciones encadenando la salida de una con la entrada de otra. Matematicamente: (f . g) x = f (g x)

listasPares :: [Int] -> Bool
listasPares = even . length
-- uso: listaEsPar [1,2,3,4] => Primero calcula el length (4), luego pasa el 4 a even => True.

-- aca hay un puente logico: la operacion (.) recibe funciones como argumentos y devuelve una funcion,por lo que la composicion genera funciones de orden superior.

-- Ejemplos Clasicos de Orden Superior (Definidos por la catedra):
{-
    map (*2) [1..5]     => [2,4,6,8,10] (Transforma cada elemento,aplica una funcion (*2) a la lista)
    filter even [1..5]  => [2,4] (Filtra y deja solo los que cumplen la condicion de la funcion (even))
-}


-- ===========================================================================================
-- 6. OPERADORES Y TIPOS DE DATOS PROPIOS (DATA Y RECORD SYNTAX)
-- ===========================================================================================

--  Los OPERADORES necesitan conocer su asociatividad y su prioridad:
infix 4 ~= 
(~=) :: Float -> Float -> Bool 
x ~= y = abs(x-y) < 0.0001
{-
    La asociatividad la da infix cambiandolo a infixr o infixl
    Basicamente si el operador (OP) es asociativo a la derecha (infixr), va a hacer esto: a OP b OP c => a OP (b OP c)
    Si es a la izquierda (infixl), va a hacer esto: a OP b OP c => (a OP b) OP c
    Tambien se podria poner en formato preorden: (~=) a b === a ~= b

-}

--TIPOS DE DATOS PROPIOS:

-- 'FORMA 1': Constructor Data Estandar
data Persona = Persona String Int Double Bool
--  Donde se encuentra el tipo (persona) esta creado (despues del igual) por el constructor persona con 4 tipos internos (nombre, edad, altura, sexo). Para conocer sus valores se deben crear funciones como:

obtenerNombre :: Persona -> String
obtenerNombre (Persona n _ _ _) = n 

obtenerEdad :: Persona -> Int
obtenerEdad (Persona _ e _ _) = e 

-- Para crearlos se debe dar el nombre del dato igual al constructor y sus valores: 
-- persona1 = Persona "Alguien" 25 1.82 True (True es alguno de los dos sexos)


-- 'FORMA 2': Record Syntax (La forma limpia y superior)
-- Crea el tipo y AUTOGENERA las funciones (getters) para sacar la informacion:
data PersonaModerna = Desarrollador {
    nombre :: String, 
    edad :: Int,
    altura :: Double,
    sexo :: Bool
}   
--  Aparte de ser mas legible, genera automaticamente funciones para conocer valores especificos
--  Se pueden crear tipos de la misma forma que con data y aparte se pueden crear asi:
{-
    dev = Desarrollador {
            nombre = "Belicho", 
            edad = 22, 
            altura = 1.75, 
            sexo = True}
    -- Si quiero la edad, simplemente uso la funcion autogenerada: edad dev => 22
-}

-- ===========================================================================================
-- PUNTO DE ENTRADA (Para que el archivo compile sin errores con runghc)
-- ===========================================================================================
main :: IO ()
main = do
    putStrLn "La Guia Definitiva Suprema 4K compilo perfectamente!"
    putStrLn "Para probar las funciones, abri la terminal integrada y ejecuta: ghci unidad4.hs"