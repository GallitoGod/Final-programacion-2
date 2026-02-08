--  Parte practica de Haskell Unidad 4:

{-
    A mi entender, la practica, de esta unidad, para el final se centra en:
    
        1_ Los tipos basicos y la declaracion de funciones.
        2_ El polimorfismo en la declaracion de funciones, sus definiciones por pattern 
    matching y los conceptos del paradigma que los envuelven.
        3_ El juego de la currificcion y la evaluacion perezosa y no perezosa.
        4_ Tipos de datos compuestos, sus sintaxis y sus capacidades.
        5_ La composicion de funciones y como de ello se puede terminar en funciones de orden superior.
        6_ La creacion de operadores y tipos propios.

    Aunque la capacidad para surtir aprietos en el final esta unicamente en 
la practica de los conceptos, el catalogarlos en 6 items brinda un enfoque claro para
el entendimiento de todo lo relacionado con el paradigma funcional implementado en Haskell.
-}


------------------------------------------------------------------------------------------------------------------------


--  1_ Los tipos de datos y la declaracion de funciones:

{-
    1_ Int: entero fijo, tiene overflow, su rango es aproximadamente [1*10(29), 1*10(-29)-1].
    2_ Integer: entero arbitrariamente grande, no tiene overflow, obviamente es mas lento.
    3_ Float/Double: coma flotante.
    4_ Rational: fracciones exactas (Nunca lo use).
    5_ Bool: true|false.
    6_ String: cadena de caracteres. Tambien es un tipo de dato complejo.
-}

--  Declaracion de funciones:

doble:: Int -> Int -- <---- Aca se declara la funcion
doble x = 2*x   -- <---- Y aca se define

-- Tambien se pueden usar funciones como constantes de esta forma:

saludo:: String
saludo = "Hola!" 


------------------------------------------------------------------------------------------------------------------------


--  2_ Funciones (mas a fondo):

{-
    En Haskell existe el polimorfismo cuando se declara una funcion de esta forma:
-}
espejo :: a -> a
espejo x = x 

--  Como tal, esta funcion puede recibir cualquier tipo de dato. En su declaracion no se especifica el tipo
--que recibe ni el que devuelve.

--  Una funcion puede tener mas de una unica definicion y esta es elegida a traves del pattern matching:

long :: [Int] -> Int
long [] = 0
long (x: xs) = 1+ long xs

{-  
    En esta funcion ademas de explicar el pattern matching ,toma la defincion que corresponda con las expresiones que tiene,
tambien esta aplicando recursividad, se llama a si misma, haciendo bucles sin expresar sentencias que lo hagan (las cuales 
no existen en Haskell). Tambien se observa el concepto de trasnparecia referencial, donde en la delcaracion se expresa el
resultado como un tipo Int pero puede ingresar una funcion sumada a un numero, esto es asi porque 
expresion de funcion y resultado para Haskell es lo mismo. Al fin y al cabo sabe que esa funcion devuelve un Int.
    Todo lo relacionado con listas en esta funcion se explican mas adelante.
-}

-- Pattern Matching:

safeHead :: [a] -> a
safeHead [] = error "Lista vacia" --    Asi de simple se hacen errores, solo tene en cuenta que se pueda devolver con polimorfismo
safeHead (x:_) = x



--  Hay dos formas de condicionar en Haskell:
--  Guards:

signo :: Int -> Int 
signo x 
    | x < 0 = -1
    | x == 0 = 0
    | otherwise = 1

-- O tambien:

signo2 :: Int -> Int
signo2 0 = 0
signo2 x = if x < 0 then -1 else 1

abs'guards :: Int -> Int
abs'guards x 
    | x < 0 = -x 
    | otherwise = x

abs'ifs :: Int -> Int
abs'ifs x = if x < 0 then -x else x



--  Definiciones where:

hipotenusa :: Double -> Double -> Double
hipotenusa a b = sqrt (a2 + b2)
    where
        a2 = a*a
        b2 = b*b

--  Simplifica ecuaciones llamandolas por un nombre. Util cuando hay expresiones matematicas recurrentes en una funcion.


------------------------------------------------------------------------------------------------------------------------


--  3_  Currificcion:

suma :: Int -> Int -> Int
suma a b = a+b

{-
    Esta funcion parece pedir dos argumentos para devolver un valor. En realidad, en Haskell no existen funciones
que usen mas de un argumento. En el caso de la funcion suma, simplemente se llama a otra funcion con el otro
argumento para poder resolver la funcion.
-}

sumaEq :: Int -> (Int -> Int)
sumaEq a b = a+b

--  Esto es basicamente lo mismo a la funcion suma. Esa es la funcion de la currificcion, es como que si se tratara de
--una pipeline de "eventos" que van sucediendo para llegar al resultado necesario.

--  Entonces el currying es una aplicacion parcial natural en Haskell.

{-
    Evalueacion ansiosa: Es la forma de evaluacion de funciones varios lenguajes, como lo pueden ser 
Java, C, etc. Se basa en evaluar la expresion, o parametro, de la funcion antes de utilizarla.
    Por ejemplo: f(g(3)) donde primero evalua g(3), si g(3) es cosotoso lo paga y si falla en el f(...) no
se ejecuta.
-}

{-
    Evaluacion perezosa: Es la forma de evaluacion natural de Haskell, donde los argumentos son evaluados 
solo cuando son necesarios. 
-}

constt :: a -> b -> a 
constt x _ = x
--  Si yo llamase a constt asi: constt 5 (error "pum"). No reventaria, solo devolveria 5, mientras que en 
--la evaluacion ansiosa explotaria igual

naturales :: [Int]
naturales = [0..]   --Infinito
-- Si pusiera: take 5 naturales, me devolveria [0,1,2,3,4], solo por la evaluacion perezosa.


------------------------------------------------------------------------------------------------------------------------


--  4_  Tipos de datos compuesto:

{-
    Como tal existen 3 tipos de datos compuestos: 
        1_listas
        2_tuplas
        3_Strings
    Pero el mas importante es la lista. Del String no voy a hacer nada, ya que 
es un tipo de dato compuesto porque cuenta como cadena de caracteres:
        String === [Char]
    Pero de las tuplas y listas si, empezando con las tuplas:
-}



--  Tuplas:
--      Sintaxis:

{-
    (1, "hola") --par 
    (1, "hola", 2) --triple
    () --vacia
-}

--  Funciones:
--solo con tuplas pares:
--      fst :: (a,b) -> a
--      snd :: (a,b) -> b

--  No hay mas de tuplas, son muy simples. Aunque pueden hacer algo util con las listas.



--  Listas:
--      Sintaxis:

{-
    [] --vacia
    [1,2,3,4] --literal
    1 : [2,3] --agrega adelante => [1,2,3]
    (++) --concatenacion de listas
    [1..5] -- => [1,2,3,4,5]
    [1,3..5] -- => [1,3,4,5]
-}

--  Patrones para pattern matching:

{-
    f [] => sin elementos
    f (x:xs) => al menos la cebeza
    f (x:y:xs) => al menos dos elementos
    f [x] => unico elemento
-}

--  Comprension de listas:

{-
    [(x,y) | x <- [1..5], y <- [1,2]] -- => [(1,1), (1,2), (2,1), (2,2), (3,1), ..., (5,2)]
    [x+2 | x <- [1,4..6]] -- => [3,6,7,8]
    [x | x <- [1,2,3,4], even x] -- => [2,4]
    --  En general todo se da con razon a la primera sentencia
-}

--  Funciones (las mas utiles):

{-
    En general, las mas importantes son:
        1_head
        2_tail
        3_last
        4_take
        5_drop
        6_zip
        7_unzip
        8_dropWhile
        9_takeWhile
    A las primeras 3 hay que darles como argumento la propia lista, para take y drop un valor o cantidad 
para tomar o quitar de la sacar, zip y unzip son funciones para trabajar listas con tuplas y dropWhile
y takeWhile necesitan de una funcion que condicione cuando hacer sus acciones. Obviamente que las funciones
de orden superior siempre funcionan con las listas, funciones como map, filter, foldr,...
Ejemplos:
    head [1,2,3,4,5] => 1
    map (+2) [1..5] => [3,4,5,6,7]
    filter even [1..5] => [2,4]
    dropWhile (<5) [1..5] => [5]
    take 5 [1..10] => [1,2,3,4,5]
    zip [1..3] (a,b) => [(1,a), (1,b), (2,a), (2,b), (3,a), (3,b)]
    unzip [(1,a), (1,b), (2,a), (2,b), (3,a), (3,b)] => [1,2,3] (a,b)
-}

--  Ejercicios:

safeSecond :: [a] -> a 
sefaSecond [] = error "lista vacia"
safeSecond [x] = error "Solo contiene un elemento"
safeSecond (x:y:xs) = y 

swapp :: (a,b) -> (b,a)
swapp (x,y) = (y,x)

length'' :: [a] -> Int
length'' [] = 0
length'' (x:xs) = 1 + length'' xs


------------------------------------------------------------------------------------------------------------------------


--  5_  Composicion de funciones

--  El operador de composicion es (.): f.g === f(g(x))

listasPares :: [Int] -> Bool
listasPares [] = False
listasPares lista = (even . length) lista

--  Aca hay un puente logico: la operacion (.) recibe funciones como argumentos y devuelve una funcion,
--por lo que la composicion genera funciones de orden superior.

--  Las funciones de orden superior ya dadas por la catedra son map y filter, donde:

-- map (*2) [1..10] => aplica una funcion (*2) a la lista.
-- filter even [1..10] => devuelve una lista con los valores filtrados de la funcion (even).


------------------------------------------------------------------------------------------------------------------------


--  6_  Creacion de operadores y tipos:

--  Los operadores necesitan conocer su asociatividad y su prioridad:

infix 4 ~= 
(~=) :: Float -> Float -> Bool 
x ~= y = abs(x-y) < 0.0001

-- La asociatividad la da infix cambiandolo a infixr o infixl
-- Basicamente si el operador (OP) es asociativo a la derecha (infixr), va a hacer esto: a OP b OP c => a OP (b OP c)
-- Si es a la izquierda (infixl), va a hacer esto: a OP b OP c => (a OP b) OP c
--  Tambien se podria poner en formato preorden: (~=) a b === a ~= b



--  Tipos propios:

--  Existen dos formas de definir un tipo:
--      1_data
--      2_record syntax

--  1:

data Persona = Persona String Int Double Bool
--  Donde se encuentra el tipo (persona) esta creado (despues del igual) por el constructor
--persona con 4 tipos internos (nombre, edad, altura, sexo)
--  Para conocer sus valores se deben crear funciones como:

nombre (Persona n _ _ _) = n 
edad (Persona _ e _ _) = e 

--  Para crearlos se debe dar el nombre del dato igual al constructor y sus valores:
-- persona1 = Persona "Alguien" 25 1.82 True (True es alguno de los dos sexos)


--  2:

data Persona1 = Persona1 {
    nombre1 :: String, --  Llevan 1 al final del nombre porque se repiten en la funcion de arriba
    edad1 :: Int,
    altura :: Double,
    sexo :: Bool
}   --  Aparte de ser mas legible, genera automaticamente funciones para conocer valores especificos
--  Se pueden crear tipos de la misma forma que con data y aparte se pueden crear asi:
{-
    persona2 = Persona1 {
        nombre1 = "Alguien",
        edad1 = 25,
        altura = 1.82,
        sexo = True
    }

    Y se podria usar nombre1 persona2, edad1 persona2, altura persona2, sexo persona2.
-}
