package io.emeraldpay.polkaj.scaletypes.v14;

import io.emeraldpay.polkaj.scaletypes.Metadata;

import java.util.List;
import java.util.Map;

public class Pallet {

    private String name;
    private Storage storage;
    private Map<String, Integer> calls;
    private Map<String, Integer> events;
    private List<Constant> constants;
    private Map<String, Integer> errors;
    private int index;

    public Pallet() {}

    public Pallet(String name, Storage storage, Map<String, Integer> calls, Map<String, Integer> events,
                  List<Constant> constants, Map<String, Integer> errors, int index) {
        this.name = name;
        this.storage = storage;
        this.calls = calls;
        this.events = events;
        this.constants = constants;
        this.errors = errors;
        this.index = index;
    }

    public static class Storage {
        private String prefix;
        private List<Item> items;

        public Storage(String prefix, List<Item> items) {
            this.prefix = prefix;
            this.items = items;
        }

        public Storage() {
        }

        public static class Item {
            private String name;
            private Metadata.Storage.Modifier modifier;
            private Type type;
            private String fallback;
            private List<String> docs;

            public static class Type {
                private Map<String, Object> map;
                private Integer plain;

                public Type() {
                }

                public Type(Map<String, Object> map, Integer plain) {
                    this.map = map;
                    this.plain = plain;
                }

                public Map<String, Object> getMap() {
                    return map;
                }

                public void setMap(Map<String, Object> map) {
                    this.map = map;
                }

                public Integer getPlain() {
                    return plain;
                }

                public void setPlain(Integer plain) {
                    this.plain = plain;
                }
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Metadata.Storage.Modifier getModifier() {
                return modifier;
            }

            public void setModifier(Metadata.Storage.Modifier modifier) {
                this.modifier = modifier;
            }

            public Type getType() {
                return type;
            }

            public void setType(Type type) {
                this.type = type;
            }

            public String getFallback() {
                return fallback;
            }

            public void setFallback(String fallback) {
                this.fallback = fallback;
            }

            public List<String> getDocs() {
                return docs;
            }

            public void setDocs(List<String> docs) {
                this.docs = docs;
            }
        }

        public String getPrefix() {
            return prefix;
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }

        public List<Item> getItems() {
            return items;
        }

        public void setItems(List<Item> items) {
            this.items = items;
        }
    }

    public static class Constant {
        private String name;
        private Integer type;
        private String value;
        private List<String> docs;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public List<String> getDocs() {
            return docs;
        }

        public void setDocs(List<String> docs) {
            this.docs = docs;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public Map<String, Integer> getCalls() {
        return calls;
    }

    public void setCalls(Map<String, Integer> calls) {
        this.calls = calls;
    }

    public Map<String, Integer> getEvents() {
        return events;
    }

    public void setEvents(Map<String, Integer> events) {
        this.events = events;
    }

    public List<Constant> getConstants() {
        return constants;
    }

    public void setConstants(List<Constant> constants) {
        this.constants = constants;
    }

    public Map<String, Integer> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, Integer> errors) {
        this.errors = errors;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
