/**
 * Created by ivan on 03.03.17.
 * @author Ivan Sosin
 */

public class DictionaryImpl implements Dictionary {
    private int tableSize = 10;
    private int maxListLen = 5;
    private int size = 0;
    private MyLinkedList[] table;

    DictionaryImpl() {
        table = new MyLinkedList[tableSize];
    }

    DictionaryImpl(int tableSize) {
        this.tableSize = tableSize;
        table = new MyLinkedList[tableSize];
    }

    DictionaryImpl(int tableSize, int maxListLen) {
        this.tableSize = tableSize;
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
        if (contains(key)) {
            return getListByHash(key).get(key);
        }
        return null;
    }

    @Override
    public String put(String key, String value) {
        if (contains(key)) {
            return getListByHash(key).put(key, value);
        } else {
            if (getListByHash(key) == null) {
                table[getHashModulo(key)] = new MyLinkedList();
            }

            getListByHash(key).put(key, value);
            size++;

            if (getListByHash(key).getSize() > maxListLen) {
                reHash();
            }
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
        table = new MyLinkedList[tableSize];
        size = 0;
    }

    int getTableSize() {
        return tableSize;
    }

    private MyLinkedList getListByHash(String key) {
        return table[getHashModulo(key)];
    }

    private int getHashModulo(String key) {
        return key.hashCode() % tableSize;
    }

    private void reHash() {
        int newTableSize = tableSize * 2;
        DictionaryImpl newDict = new DictionaryImpl(newTableSize);
        for (int i = 0; i < tableSize; i++) {
            for (Object key : table[i]) {
                newDict.put((String) key, table[i].get((String) key));
            }
        }
        tableSize = newTableSize;
        table = newDict.table;
    }
}
