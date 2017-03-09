import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by ivan on 03.03.17.
 * @author Ivan Sosin
 */

class MyLinkedList implements Iterable {
    private Node start;
    private int size;

    MyLinkedList() {
        start = new Node();
        size = 0;
    }

    public Iterator iterator() {
        return new MyIterator();
    }

    boolean contains(String key) {
        Node curNode = start;

        for (int i = 0; i < size; i++) {
            if (curNode.getKey().equals(key)) {
                return true;
            }
            curNode = curNode.getNext();
        }

        return false;
    }

    String get(String key) {
        Node curNode = start;
        for (int i = 0; i < size; i++) {
            if (curNode.getKey().equals(key)) {
                return curNode.getValue();
            }
            curNode = curNode.getNext();
        }
        return null;
    }

    String remove(String key) {
        Node curNode = start;
        for (int i = 0; i < size; i++) {
            if (curNode.getKey().equals(key)) {
                removeNode(curNode);
                return curNode.getValue();
            }
            curNode = curNode.getNext();
        }

        return null;
    }

    String put(String key, String value) {
        if (size == 0) {
            start = new Node(key, value);
            size++;
            return null;
        }

        Node curNode = start;

        for (int i = 0; i < size; i++) {
            if (curNode.getKey().equals(key)) {
                String oldValue = curNode.getValue();
                curNode.setValue(value);
                return oldValue;
            }
            if (i != size - 1) {
                curNode = curNode.getNext();
            }

        }

        Node lastNode = new Node(key, value);
        curNode.setNext(lastNode);
        lastNode.setPrev(curNode);
        size++;
        return null;
    }

    int getSize() {
        return size;
    }

    private void removeNode(Node node) {
        if (size == 1) {
            start = new Node();
        } else if (node.getPrev() == null) {
            start = node.getNext();
            start.setPrev(null);
        } else if (node.getNext() == null) {
            Node prevToEnd = node.getPrev();
            prevToEnd.setNext(null);
        } else {
            Node prevNode = node.getPrev();
            Node nextNode = node.getNext();
            prevNode.setNext(nextNode);
            nextNode.setPrev(prevNode);
        }
        size--;
    }

    class MyIterator implements Iterator {
        private Node cursor;

        MyIterator() {
            cursor = start;
        }

        public boolean hasNext() {
            return cursor != null;
        }

        public String next() {
            String current = cursor.getKey();
            if (hasNext()) {
                cursor = cursor.getNext();
            } else {
                throw new NoSuchElementException("nothing left");
            }
            return current;
        }
    }

    private class Node {
        private Node prev;
        private Node next;
        private String key;
        private String value;

        Node() {}

        Node(String key, String value) {
            this.key = key;
            this.value = value;
            prev = null;
            next = null;
        }

        Node getPrev() {
            return prev;
        }

        Node getNext() {
            return next;
        }

        void setPrev(Node node) {
            prev = node;
        }

        void setNext(Node node) {
            next = node;
        }

        String getKey() {
            return key;
        }

        String getValue() {
            return value;
        }

        void setValue(String value) {
            this.value = value;
        }
    }


}
