/**
 * Created by ivan on 03.03.17.
 * @author Ivan Sosin
 */

public class DictionaryImpl implements Dictionary {

    private static final int DEFAULT_TABLE_SIZE = 10;
    private static final int DEFAULT_MAX_LIST_LEN = 5;

    private final int maxListLen;
    private int size = 0;
    private MyLinkedList[] table;

    DictionaryImpl() {
        this(DEFAULT_TABLE_SIZE, DEFAULT_MAX_LIST_LEN);
    }

    DictionaryImpl(int tableSize) {
        this(tableSize, DEFAULT_MAX_LIST_LEN);
    }

    DictionaryImpl(int tableSize, int maxListLen) {
        this.maxListLen = maxListLen;
        table = new MyLinkedList[tableSize];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean contains(String key) {
        return (getListByHash(key) != null) && getListByHash(key).contains(key);
    }

    @Override
    public String get(String key) {
        MyLinkedList list = getListByHash(key);

        if (list != null) {
            return list.get(key);
        }
        return null;
    }

    @Override
    public String put(String key, String value) {
        MyLinkedList list = getListByHash(key);

        if (list == null) {
            list = new MyLinkedList();
            table[getHashModulo(key)] = list;
        }

        String oldValue = list.put(key, value);
        if (oldValue != null) {
            return oldValue;
        }

        size++;

        if (list.getSize() > maxListLen) {
            reHash();
        }

        return null;
    }

    @Override
    public String remove(String key) {
        if (contains(key)) {
            size--;
            return getListByHash(key).remove(key);
        }
        return null;
    }

    @Override
    public void clear() {
        table = new MyLinkedList[DEFAULT_TABLE_SIZE];
        size = 0;
    }

    int getTableSize() {
        return table.length;
    }

    private MyLinkedList getListByHash(String key) {
        return table[getHashModulo(key)];
    }

    private int getHashModulo(String key) {
        return key.hashCode() % table.length;
    }

    private void reHash() {
        int newTableSize = table.length * 2;
        DictionaryImpl newDict = new DictionaryImpl(newTableSize);
        for (int i = 0; i < table.length; i++) {
            for (Object key : table[i]) {
                newDict.put((String) key, table[i].get((String) key));
            }
        }
        table = newDict.table;
    }
}
