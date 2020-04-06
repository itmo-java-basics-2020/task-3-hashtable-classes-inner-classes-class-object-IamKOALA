package ru.itmo.java;

public class HashTable {
    private int amount;
    private Entry[] table;
    private int capacity;
    private double loadFactor;

    class Entry {
        private Object key, value;

        Entry(Object key, Object value) {
            this.key = key;
            this.value = value;
        }

        Object getKey() {
            return key;
        }

        Object getValue() {
            return value;
        }
    }

    HashTable(int capacity, double loadFactor) {
        this.capacity = capacity;
        this.loadFactor = loadFactor;
        this.table = new Entry[capacity];
        this.amount = 0;
    }

    HashTable(int capacity) {
        this(capacity, 0.5);
    }

    private void expandTable() {
        if (amount >= (int) (loadFactor * capacity)) {
            Entry[] prevTable = this.table;
            this.table = new Entry[capacity * 2];
            this.capacity *= 2;
            this.amount = 0;

            for (Entry entry : prevTable) {
                if (entry != null && entry.getKey() != null) {
                    put(entry.getKey(), entry.getValue());
                }
            }
        }
    }

    private int getHashIndex(Object key) {
        return (key.hashCode() % capacity + capacity) % capacity;
    }

    private int getNextHashIndex(int ind) {
        return (ind + 9257) % capacity;
    }

    private int getIndexRm(Object key) {
        int ind = getHashIndex(key);
        while (table[ind] != null && (table[ind].getKey() != null)) {
            ind = getNextHashIndex(ind);
        }
        return ind;
    }

    private int getIndex(Object key) {
        int ind = getHashIndex(key);
        while (table[ind] != null && (table[ind].getKey() == null || !table[ind].getKey().equals(key))) {
            ind = getNextHashIndex(ind);
        }
        return ind;
    }

    private Object getValue(int index) {
        return table[index] == null ? null : table[index].getValue();
    }


    Object put(Object key, Object value) {
        expandTable();

        int ind = getIndex(key);
        if (table[ind] == null) {
            ind = getIndexRm(key);
        }

        Object prevValue = getValue(ind);
        amount += (prevValue == null ? 1 : 0);

        table[ind] = new Entry(key, value);

        return prevValue;
    }

    Object get(Object key) {
        int ind = getIndex(key);

        return getValue(ind);
    }

    Object remove(Object key) {
        expandTable();

        int ind = getIndex(key);
        if (table[ind] == null) {
            return null;
        }

        Object prevValue = getValue(ind);
        amount -= (prevValue == null ? 0 : 1);
        table[ind] = new Entry(null, null);

        return prevValue;
    }

    int size() {
        return amount;
    }
}
