import org.junit.Assert;
import org.junit.Test;

import java.lang.invoke.MethodHandles;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EDDoubleLinkedListTest {

    static private <T> List<List<T>> permutaciones(List<T> vec) {
        List<List<T>> resultado = new LinkedList<>();

        if (vec.size() > 0) {
            List<T> aux = new LinkedList<>();
            aux.add(vec.get(0));
            resultado.add(aux);

            for (int i = 1; i < vec.size(); i++) {
                while (resultado.get(0).size() == i) {
                    for (int k = 0; k <= resultado.get(0).size(); k++) {
                        aux = new LinkedList<>(resultado.get(0));
                        aux.add(k, vec.get(i));
                        resultado.add(aux);
                    }
                    resultado.remove(0);
                }
            }
        } else {
            List<T> aux = new LinkedList<>();
            resultado.add(aux);
        }
        return resultado;
    }

    static private boolean[] crearMascara(int talla){
        return new boolean[talla];
    }

    static private boolean incrementaMascara(boolean mascara[]) {
        boolean propagar = false;
        int pos = 0;
        do {
            if (mascara[pos] == true) {
                mascara[pos] = false;
                propagar = true;
            } else {
                mascara[pos] = true;
                propagar = false;
            }
            pos++;
        } while (propagar && (pos < mascara.length));

        return (!propagar || pos != mascara.length);
    }

    static private <T> List<List<T>> todasSublistas(List<T> semilla) {
        List<List<T>> resultado = new LinkedList<>();

        boolean mascara[] = crearMascara(semilla.size());

        do {
            List<T> aux = new LinkedList<>();
            for(int i= 0; i < mascara.length; i++) {
                if (mascara[i])
                    aux.add(semilla.get(i));
            }
            resultado.add(aux);

        } while(incrementaMascara(mascara));

        return resultado;

    }

    static private <T> List<List<T>> permutacionesCompletas(List<T> semilla){
        List<List<T>> resultado = new LinkedList<>();

        List<List<T>> sublistas = todasSublistas(semilla);
        for(List<T> sub: sublistas)
            resultado.addAll(permutaciones(sub));


        return resultado;
    }

    static private <T> List<T> aplanar(List<List<T>> listas) {
        List<T> resultado = new LinkedList<>();

        for(List<T> l: listas)
            resultado.addAll(l);

        return resultado;
    }

    static private <T> List<List<T>> convertirMatriz(T[][] vec)
    {
        List<List<T>> resultado = new LinkedList<>();
        for (T[] elem: vec)
            resultado.add(Arrays.asList(elem));

        return resultado;
    }

    private String[][] vSemillasResverseTest = {{"A"}, {"B", "C"}, {"X", "Y"}, {"D", "E", "F"}};

    static private List<String> invierteLista(List<String> l) {
        List<String> resultado = new LinkedList<>();
        for (String elem: l)
            resultado.add(0,elem);

        return resultado;
    }

    @org.junit.Test
    public void reverseTest () {
        List<List<String>> aux = convertirMatriz(vSemillasResverseTest);

        List<List<List<String>>> permutaciones = permutacionesCompletas(aux);

        int cuenta = 0;
        for (List<List<String>> perm: permutaciones) {
            List<String> caso = aplanar(perm);
            List<String> esperado = invierteLista(caso);

            for(int i = 0; i <= caso.size(); i++) {
                EDDoubleLinkedList<String> actual = new EDDoubleLinkedList<>(caso);

                System.out.println("\nPRUEBA " + cuenta);
                System.out.println("ESTADO INCIAL DE LA LISTA");
                System.out.println("  " + actual);
                System.out.println("ESTADO FINAL ESPERADO");
                System.out.println("  " + esperado + ": " + esperado.size());

                actual.reverse();
                System.out.println("ESTADO FINAL OBTENIDO:");
                System.out.println("  " + actual);

                //boolean iguales = Arrays.equals(esperado.toArray(), actual.toArray());
                assertTrue(Arrays.equals(esperado.toArray(), actual.toArray()));
                cuenta++;
            }
        }

        System.out.println("Se han probado " + cuenta + " casos\nFIN");
    }

    static private String[][] v1SemillasShuffleTest = {{"A"}, {"B", "C"}, {"D", "E", "F"}};
    static private String[]   v2SemillasShuffleTest = {"a", "b", "c", "d"};

    static private <T> List<T> calcularRessultadShuffle(List<T> l1, List<T> l2) {
        List<T> resultado = new ArrayList<>();

        int i1 = 0, i2 = 0;

        for (int j = 0; j < (l1.size() + l2.size()); j++)
            if (j%2 == 0)
                if (i1 < l1.size()) {
                    resultado.add(l1.get(i1));
                    i1++;
                } else {
                    resultado.add(l2.get(i2));
                    i2++;
                }
            else
                if (i2 < l2.size()) {
                    resultado.add(l2.get(i2));
                    i2++;
                } else {
                    resultado.add(l1.get(i1));
                    i1++;
                }


        return resultado;
    }

    @org.junit.Test
    public void shuffleTest() {
        List<List<String>> aux1 = convertirMatriz(v1SemillasShuffleTest);
        List<List<List<String>>> permutaciones1 = permutacionesCompletas(aux1);

        List<List<String>> permutaciones2 = permutacionesCompletas(Arrays.asList(v2SemillasShuffleTest));

        int cuenta = 1;
        for (List<List<String>> perm1: permutaciones1)
            for (List<String> caso1: permutaciones2 ) {
                List<String> caso2 = aplanar(perm1);

                EDDoubleLinkedList<String> actual = new EDDoubleLinkedList<>(caso1);

                System.out.println("\nPRUEBA " + cuenta);
                System.out.println("ESTADO INCIAL");
                System.out.println("  this:" + actual);
                System.out.println("  otra:" + caso2);
                System.out.println("  this.shuffle(otra)");

                List<String> esperado = calcularRessultadShuffle(caso1, caso2);
                System.out.println("ESTADO FINAL ESPERADO");
                System.out.println("  " + esperado + ": " + esperado.size());

                actual.shuffle(caso2);
                System.out.println("ESTADO FINAL OBTENIDO:");
                System.out.println("  " + actual);

                assertTrue(Arrays.equals(esperado.toArray(), actual.toArray()));

                cuenta++;
            }


    }

    static private <T> List<T> calcularResultadoRetain(List<T> l1, List<T> l2) {
        List<T> resultado = new ArrayList<>(l1);

        resultado.retainAll(l2);

        return resultado;
    }

    @org.junit.Test
    public void retainAllTest() {
        List<List<String>> aux = convertirMatriz(vSemillasResverseTest);

        List<List<List<String>>> permutaciones = permutacionesCompletas(aux);


        int cuenta = 1;
        for (List<List<String>> perm1: permutaciones)
            for (List<String> caso1: perm1 ) {
                List<String> caso2 = aplanar(perm1);

                EDDoubleLinkedList<String> actual = new EDDoubleLinkedList<>(caso1);

                System.out.println("\nPRUEBA " + cuenta);
                System.out.println("ESTADO INICIAL");
                System.out.println("  this:" + actual);
                System.out.println("  otra:" + caso2);
                System.out.println("  this.retainAll(otra)");

                List<String> esperado = calcularResultadoRetain(caso1, caso2);
                System.out.println("ESTADO FINAL ESPERADO");
                System.out.println("  " + esperado + ": " + esperado.size());

                actual.retainAll(caso2);
                System.out.println("ESTADO FINAL OBTENIDO:");
                System.out.println("  " + actual);

                assertTrue(Arrays.equals(esperado.toArray(), actual.toArray()));

                cuenta++;
            }
        for (List<List<String>> perm1: permutaciones)
            for (List<String> caso1: perm1 ) {
                //List<String> caso2 = aplanar(perm1);

                EDDoubleLinkedList<String> actual = new EDDoubleLinkedList<>(caso1);

                System.out.println("\nPRUEBA " + cuenta);
                System.out.println("ESTADO INICIAL");
                System.out.println("  this:" + actual);
                System.out.println("  otra:" + caso1);
                System.out.println("  this.retainAll(otra)");

                List<String> esperado = calcularResultadoRetain(caso1, caso1);
                System.out.println("ESTADO FINAL ESPERADO");
                System.out.println("  " + esperado + ": " + esperado.size());

                actual.retainAll(caso1);
                System.out.println("ESTADO FINAL OBTENIDO:");
                System.out.println("  " + actual);

                assertTrue(Arrays.equals(esperado.toArray(), actual.toArray()));

                cuenta++;
            }
        for (List<List<String>> perm1: permutaciones)
            for (List<String> caso1: perm1 ) {
                List<String> caso2 = new ArrayList<>();

                EDDoubleLinkedList<String> actual = new EDDoubleLinkedList<>(caso1);

                System.out.println("\nPRUEBA " + cuenta);
                System.out.println("ESTADO INICIAL");
                System.out.println("  this:" + actual);
                System.out.println("  otra:" + caso2);
                System.out.println("  this.retainAll(otra)");

                List<String> esperado = calcularResultadoRetain(caso1, caso2);
                System.out.println("ESTADO FINAL ESPERADO");
                System.out.println("  " + esperado + ": " + esperado.size());

                actual.retainAll(caso2);
                System.out.println("ESTADO FINAL OBTENIDO:");
                System.out.println("  " + actual);

                assertTrue(Arrays.equals(esperado.toArray(), actual.toArray()));

                cuenta++;
            }


    }

    @Test()
    public void testExceptionPrune() {
        List<List<String>> casos = new LinkedList<>();
        for (int i = 0; i<10; i++) {
            List<String> l = new LinkedList<>();
            for (int j = 0; j < i; j++)
                l.add(Character.toString('A' + j) );
            casos.add(l);
        }

        int cuenta = 1;
        for (List<String> caso : casos) {
            EDDoubleLinkedList<String> actual = new EDDoubleLinkedList<>(caso);
            int limites[][] = { {-caso.size() - 1,caso.size()*2+1},
                    {-caso.size() - 1, caso.size()},
                    {0, caso.size()*2+1},
                    {caso.size(), caso.size()},
                    {-caso.size()-1, 0}};

            for (int i = 0; i < limites.length; i++)
                for (int j = 0; j < limites[i].length; j++) {
                    int indiceInicial = limites[i][0];
                    int indiceFinal = limites[i][1];
                    System.out.println("\nPRUEBA " + cuenta);
                    System.out.println("ESTADO INICIAL");
                    System.out.println("  this = " + actual);
                    System.out.println("  this.prune(" + indiceInicial + ", " + indiceFinal + ")");

                    System.out.println("ESTADO FINAL ESPERADO: ArrayOutBoundException");

                    boolean excepcion = false;
                    try {
                        actual.prune(indiceInicial, indiceFinal);
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println(" ...OK");
                        excepcion = true;
                    }

                    assertTrue("Excepcion no lanzada", excepcion);
                    cuenta++;
                }
        }
    }


    private List<String> calcularResultadoPrune (List<String> caso, int inicio, int finali) {
        List<String> res=new ArrayList<>();

        ListIterator<String> it = caso.listIterator(inicio);
        for (int i = inicio; i < finali; i++)
                res.add(caso.get(i));
        return res;
    }

    @org.junit.Test
    public void pruneTest () {
        List<List<String>> aux = convertirMatriz(vSemillasResverseTest);

        int cuenta = 1;
        //casos especiales
        List<String> toda=new ArrayList<>();
        for (List<String> lista:aux)
            toda.addAll(lista);

        List<String> esperado = null;
        int indexI, indexF;
        boolean res, resEsperado;

        EDDoubleLinkedList<String> actual = new EDDoubleLinkedList<>(toda);

        cuenta++;
        toda.clear();
        for (List<String> perm1: aux) {

            toda.addAll(perm1);
            for (indexI=0; indexI<toda.size(); indexI++)
                for (indexF=toda.size(); indexF>0; indexF--) {
                        actual = new EDDoubleLinkedList<>(toda);

                        System.out.println("\nPRUEBA " + cuenta);
                        System.out.println("ESTADO INICIAL");
                        System.out.println("  this:" + actual + " indice inicial: " + indexI + " indice final " + indexF);
                        System.out.println("  this.prune(indice inicial, indice final)");

                        esperado = calcularResultadoPrune(toda, indexI, indexF);
                        System.out.println("ESTADO FINAL ESPERADO");
                        System.out.println("  " + esperado + ": " + esperado.size());
                        resEsperado=esperado.size()!=toda.size();

                        res = actual.prune(indexI, indexF);
                        System.out.println("ESTADO FINAL OBTENIDO:");
                        System.out.println("  " + actual );


                        assertTrue(Arrays.equals(esperado.toArray(), actual.toArray()));
                        assertEquals(resEsperado,res);

                        cuenta++;
                    }
            }

    }

}
