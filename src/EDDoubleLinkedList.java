import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


/**
 * Implementación incompleta de una lista usando una cadena no circular de nodos
 * doblemente enlazados.
 */
public class EDDoubleLinkedList<T> implements List<T> {
    private class Node {
        private T data;
        private Node next;
        private Node prev;

        public Node(T data) { this.data = data;};
    }

    private Node first = null;
    private Node last = null;
    private int size = 0;

    public EDDoubleLinkedList(Collection<T> col) {
        for (T elem: col) {
            Node n = new Node(elem);
            if (first == null)
                first = last = n;
            else {
                n.prev = last;
                last.next = n;
                last = n;
            }
        }
        size = col.size();
    }

    /**
     * Invierte el orden de los elementos de la lista.
     */
    public void reverse() {
        
    }

    /**
     *  Añade los elementos de la lista intercalándolo con la lista actual.
     *
     *  @param lista lista con los elementos que deben ser intercalados
     */
    public void shuffle(List<T> lista) {
        
    }

    /**
     * Elimina los elementos de la lista tales que permanecerán los que se encuentren entre las en posiciones
     * firstIndex(inlcuido) y lastIndex (excluido). Los elmentos supervivientes deben manterner el orden previo.
     *
     * Es decir, si firstIndex = 3 lastIndex=8, la lista se quedará con los elementos en las posiciones 3, 4, 5, 6 y 7.
     *
     * @param firstIndex Primer elemento que permanecerá
     * @param lastIndex Siguiente al último elemento que permanecerà
     * @return True si se ha modificado la lista, y false en caso contrario.
     * @throws IndexOutOfBoundsException si firstIndex < 0 o >= size y lastIndex <0 o > size
     */
    public boolean prune(int firstIndex, int lastIndex) throws IndexOutOfBoundsException {
        
    }

    /**
     * retainAll(c): Calcula la intersección de la lista actual con una colección c
     * @param c: colección de elementos con los que calcula la intersección
     * @return True si la lista actual ha sido modificada, false en caso contrario
     */
    @Override
    public boolean retainAll(Collection<?> c) {

      
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<T> iterator() { throw new UnsupportedOperationException(); }

    @Override
    public Object[] toArray() {
        Object[] v = new Object[size];

        Node n = first;
        int i = 0;
        while(n != null) {
            v[i] = n.data;
            n = n.next;
            i++;
        }

        return v;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(T t) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }



    @Override
    public void clear() {
        first = last = null;
        size = 0;
    }

    @Override
    public T get(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T set(int index, T element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, T element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<T> listIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();

        if (isEmpty())
            sb.append("[]");
        else {
            sb.append("[");
            Node ref = first;
            while (ref != null) {
                sb.append(ref.data);
                ref = ref.next;
                if (ref == null)
                    sb.append("]");
                else
                    sb.append(", ");
            }
        }

        sb.append(": ");
        sb.append(size);

        return sb.toString();
    }
}
