
import java.io.*;

/**
 * Created by ivan on 22.02.17.
 * @author Ivan Sosin
 */

public class TrieImplementation implements Trie, StreamSerializable {
    private Node root;

    TrieImplementation() {
        root = new Node();
    }

    @Override
    public boolean add(String element) {

        if (contains(element)) {
            return false;
        }

        addRecursive(root, element, 0);

        return true;
    }

    @Override
    public boolean contains(String element) {
        return element.isEmpty() || containsRecursive(root, element, 0);
    }

    @Override
    public boolean remove(String element) {

        if (contains(element)) {
            removeRecursive(root, element, 0);
            return true;
        }

        return false;
    }

    @Override
    public int size() {
        return root.getNumberOfPrefixes();
    }

    @Override
    public int howManyStartsWithPrefix(String prefix) {
        return prefixesRecursive(root, prefix, 0);
    }

    @Override
    public void serialize(OutputStream out) throws IOException {
        recSerialize(new DataOutputStream(out), root);
    }

    @Override
    public void deserialize(InputStream in) throws IOException {
        root = new Node();
        recDeserialize(new DataInputStream(in), root);
    }

    private void addRecursive(Node node, String element, int index) {

        node.increasePrefixes();

        if (index == element.length()) {
            node.setTerminal();
            return;
        }

        char nextChar = element.charAt(index);

        if (!node.containsChar(nextChar)) {
            node.addNext(nextChar);
        }

        addRecursive(node.getNext(nextChar), element, ++index);
    }

    private boolean containsRecursive(Node node, String element, int index) {

        if (index == element.length()) {
            return node.isTerminal();
        }

        char nextChar = element.charAt(index);

        return node.containsChar(nextChar) && containsRecursive(node.getNext(nextChar), element, ++index);
    }

    private void removeRecursive(Node node, String element, int index) {

        node.decreasePrefixes();

        if (index == element.length()) {
            node.setNotTerminal();
            return;
        }

        char nextChar = element.charAt(index);
        Node next = node.getNext(nextChar);

        if (next.getNumberOfPrefixes() == 1) {
            node.removeNext(nextChar);
        } else {
            removeRecursive(node.getNext(nextChar), element, ++index);
        }
    }

    private int prefixesRecursive(Node node, String element, int index) {

        if (index == element.length()) {
            return node.getNumberOfPrefixes();
        }

        char nextChar = element.charAt(index);

        if (node.containsChar(nextChar)) {
            return prefixesRecursive(node.getNext(nextChar), element, ++index);
        } else {
            return 0;
        }
    }

    private void recDeserialize(DataInputStream dataInputStream, Node node) throws IOException {
        long mask = dataInputStream.readLong();
        boolean isTerminal = dataInputStream.readBoolean();
        int numberOfPrefixes = dataInputStream.readInt();

        if (isTerminal) {
            node.setTerminal();
        } else {
            node.setNotTerminal();
        }
        node.setNumberOfPrefixes(numberOfPrefixes);

        Node[] nexts = node.getNexts();
        long index = 1;
        index <<= 51;

        for (int i = 0; i < nexts.length; i++) {
            if ((mask & index) == index) {
                nexts[i] = new Node();
                recDeserialize(dataInputStream, nexts[i]);
            }
            mask <<= 1;
        }
    }

    private void recSerialize(DataOutputStream dataOutputStream, Node node) throws IOException {
        Node[] nexts = node.getNexts();
        long mask = generateMask(nexts);

        dataOutputStream.writeLong(mask);
        dataOutputStream.writeBoolean(node.isTerminal());
        dataOutputStream.writeInt(node.getNumberOfPrefixes());

        for (Node next : nexts) {
            if (next != null) {
                recSerialize(dataOutputStream, next);
            }
        }
    }

    private long generateMask(Node[] nexts) {
        long mask = 0;

        for (Node next : nexts) {
            mask <<= 1;
            if (next != null) {
                mask |= 1;
            }
        }
        return mask;
    }

    private class Node {
        private Node[] nexts;
        private boolean terminal;
        private int numberOfPrefixes;

        Node() {
            nexts = new Node[52];
            terminal = false;
            numberOfPrefixes = 0;
        }

        int charToIndex(char character) {
            // Capitals go first

            if (character >= 97) {
                return character - 97 + 26;
            } else if (character >= 65) {
                return character - 65;
            }
            return -1;
        }

        boolean containsChar(char character) {
            return nexts[charToIndex(character)] != null;
        }

        Node[] getNexts() {
            return nexts;
        }

        Node getNext(char character) {
            return nexts[charToIndex(character)];
        }

        void addNext(char character) {
            nexts[charToIndex(character)] = new Node();
        }

        void removeNext(char character) {
            nexts[charToIndex(character)] = null;
        }

        void setTerminal() {
            terminal = true;
        }

        void setNotTerminal() {
            terminal = false;
        }

        boolean isTerminal() {
            return terminal;
        }

        int getNumberOfPrefixes() {
            return numberOfPrefixes;
        }

        void setNumberOfPrefixes(int value) {
            numberOfPrefixes = value;
        }

        void increasePrefixes() {
            numberOfPrefixes++;
        }

        void decreasePrefixes() {
            numberOfPrefixes--;
        }
    }
}
