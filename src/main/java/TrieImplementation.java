import java.util.Map;
import java.util.TreeMap;

/**
 * Created by ivan on 22.02.17.
 * @author Ivan Sosin
 */

public class TrieImplementation implements Trie {

    private class Node {
        Map<Character, Node> nexts;
        boolean terminal;
        int numberOfPrefixes;

        Node() {
            nexts = new TreeMap<>();
            terminal = false;
            numberOfPrefixes = 0;
        }

        boolean containsChar(char character) {
            return nexts.containsKey(character);
        }

        Node getNext(char character) {
            return nexts.get(character);
        }

        void addNext(char character) {
            nexts.put(character, new Node());
        }

        void removeNext(char character) {
            nexts.remove(character);
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

        void increasePrefixes() {
            numberOfPrefixes++;
        }

        void decreasePrefixes() {
            numberOfPrefixes--;
        }
    }

    Node root;

    TrieImplementation() {
        root = new Node();
    }

    private void addRecursive(Node node, String element, int index) {

        node.increasePrefixes();

        if (index == element.length()) {
            node.setTerminal();
            return;
        }

        char nextChar = element.charAt(index);

        if (node.containsChar(nextChar)) {
            addRecursive(node.getNext(nextChar), element, ++index);
        } else {
            node.addNext(nextChar);
            addRecursive(node.getNext(nextChar), element, ++index);
        }
    }

    @Override
    public boolean add(String element) {

        if (contains(element)) {
            return false;
        }

        addRecursive(root, element, 0);

        return true;
    }

    private boolean containsRecursive(Node node, String element, int index) {

        if (index == element.length()) {
            if (node.isTerminal()) {
                return true;
            } else {
                return false;
            }
        }

        char nextChar = element.charAt(index);

        if (node.containsChar(nextChar)) {
            return containsRecursive(node.getNext(nextChar), element, ++index);
        } else {
            return false;
        }
    }

    @Override
    public boolean contains(String element) {
        if (element.length() == 0) {
            return true;
        }
        return containsRecursive(root, element, 0);
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

    @Override
    public int howManyStartsWithPrefix(String prefix) {
        return prefixesRecursive(root, prefix, 0);
    }
}
