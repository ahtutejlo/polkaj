package io.emeraldpay.polkaj.scaletypes;

import io.emeraldpay.polkaj.scaletypes.v14.Lookup;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Runtime Metadata, which defines all available actions and types for the blockchain.
 * Available through state_getMetadata RPC.
 *
 * Reference: https://github.com/polkadot-js/api/blob/master/packages/types/src/interfaces/metadata/definitions.ts
 */
public class MetadataV14 {

    private Integer magic;
    private Integer version;
    private List<Pallet> pallets;
    private Lookup lookup;

    public byte[] findCallIndex(String palletName, String extrinsicName) {
        AtomicReference<Integer> callTypeRef = new AtomicReference<>();
        AtomicReference<Integer> callIndexRef = new AtomicReference<>();
        pallets.forEach(pallet -> {
            if (pallet.getName().equals(palletName)) {
                callTypeRef.set(pallet.getIndex());
                if (pallet.getCalls() != null) {
                    int type = pallet.getCalls().getType();
                    lookup.getTypes().stream()
                            .filter(t -> t.getId() == type)
                            .findFirst()
                            .ifPresentOrElse(t -> {
                                Lookup.Type.TypeInfo.Definition def = t.getType().getDef();
                                if (def.getVariant() != null) {
                                    List<Lookup.Type.TypeInfo.Definition.Variant.TypeDefVariant> variants = def.getVariant().getVariants();
                                    if (!variants.isEmpty()) {
                                        variants.stream()
                                                .filter(variant -> variant.getName().equals(extrinsicName))
                                                .findFirst()
                                                .ifPresentOrElse(variant -> callIndexRef.set(variant.getIndex()), () -> {
                                                    throw new RuntimeException("Variant not found");
                                                });
                                    } else {
                                        throw new RuntimeException("Variant is empty");
                                    }
                                }
                            }, () -> {
                                throw new RuntimeException("Type not found");
                            });
                } else {
                    throw new RuntimeException("Call not found");
                }
            }
        });

        byte[] bytes = new byte[2];
        bytes[0] = callTypeRef.get().byteValue();
        bytes[1] = callIndexRef.get().byteValue();
        return bytes;
    }

    public Integer getMagic() {
        return magic;
    }

    public void setMagic(Integer magic) {
        this.magic = magic;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public List<Pallet> getPallets() {
        return pallets;
    }

    public void setPallets(List<Pallet> pallets) {
        this.pallets = pallets;
    }

    public Optional<Pallet> findPallet(String name) {
        if (pallets == null) {
            return Optional.empty();
        }
        return getPallets().stream()
                .filter(it -> it.getName().equals(name))
                .findAny();
    }

    public Lookup getLookup() {
        return lookup;
    }

    public void setLookup(Lookup lookup) {
        this.lookup = lookup;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MetadataV14)) return false;
        MetadataV14 metadata = (MetadataV14) o;
        return Objects.equals(magic, metadata.magic) &&
                Objects.equals(version, metadata.version) &&
                Objects.equals(pallets, metadata.pallets);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(magic, version, pallets);
    }

    public static class Pallet {
        private String name;
        private Storage storage;
        private Integer call;
        private Calls calls;
        private Events events;
        private List<Constant> constants;
        private Errors errors;
        private Integer index;

        public Integer getCall() {
            return call;
        }

        public void setCall(Integer call) {
            this.call = call;
        }

        @Override
        public String toString() {
            return "Pallet{" +
                    "name='" + name + '\'' +
                    ", storage=" + storage +
                    ", call=" + call +
                    ", calls=" + calls +
                    ", events=" + events +
                    ", constants=" + constants +
                    ", errors=" + errors +
                    ", index=" + index +
                    '}';
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

        public Calls getCalls() {
            return calls;
        }

        public void setCalls(Calls calls) {
            this.calls = calls;
        }

        public Events getEvents() {
            return events;
        }

        public void setEvents(Events events) {
            this.events = events;
        }

        public List<Constant> getConstants() {
            return constants;
        }

        public void setConstants(List<Constant> constants) {
            this.constants = constants;
        }

        public Errors getErrors() {
            return errors;
        }

        public void setErrors(Errors errors) {
            this.errors = errors;
        }

        public Integer getIndex() {
            return index;
        }

        public void setIndex(Integer index) {
            this.index = index;
        }

        @Override
        public final boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Pallet)) return false;
            Pallet pallet = (Pallet) o;
            return Objects.equals(name, pallet.name) &&
                    Objects.equals(storage, pallet.storage) &&
                    Objects.equals(calls, pallet.calls) &&
                    Objects.equals(events, pallet.events) &&
                    Objects.equals(constants, pallet.constants) &&
                    Objects.equals(errors, pallet.errors) &&
                    Objects.equals(index, pallet.index);
        }

        @Override
        public final int hashCode() {
            return Objects.hash(name, storage, calls, events, constants, errors, index);
        }
    }

    public static class Storage {
        private String prefix;
        private List<Entry> entries;

        public String getPrefix() {
            return prefix;
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }

        public List<Entry> getEntries() {
            return entries;
        }

        public void setEntries(List<Entry> entries) {
            this.entries = entries;
        }

        @Override
        public final boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Storage)) return false;
            Storage storage = (Storage) o;
            return Objects.equals(prefix, storage.prefix) &&
                    Objects.equals(entries, storage.entries);
        }

        @Override
        public final int hashCode() {
            return Objects.hash(prefix, entries);
        }

        @Override
        public String toString() {
            return "Storage{" +
                    "prefix='" + prefix + '\'' +
                    ", entries=" + entries +
                    '}';
        }

        public static class Entry {
            private String name;
            private Modifier modifier;
            private Type<?> type;
            private byte[] defaults;
            private List<String> documentation;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Modifier getModifier() {
                return modifier;
            }

            public void setModifier(Modifier modifier) {
                this.modifier = modifier;
            }

            public Type<?> getType() {
                return type;
            }

            public void setType(Type<?> type) {
                this.type = type;
            }

            public byte[] getDefaults() {
                return defaults;
            }

            public void setDefaults(byte[] defaults) {
                this.defaults = defaults;
            }

            public List<String> getDocumentation() {
                return documentation;
            }

            public void setDocumentation(List<String> documentation) {
                this.documentation = documentation;
            }

            @Override
            public final boolean equals(Object o) {
                if (this == o) return true;
                if (!(o instanceof Entry)) return false;
                Entry entry = (Entry) o;
                return Objects.equals(name, entry.name) &&
                        modifier == entry.modifier &&
                        Objects.equals(type, entry.type) &&
                        Arrays.equals(defaults, entry.defaults) &&
                        Objects.equals(documentation, entry.documentation);
            }

            @Override
            public final int hashCode() {
                int result = Objects.hash(name, modifier, type, documentation);
                result = 31 * result + Arrays.hashCode(defaults);
                return result;
            }

            @Override
            public String toString() {
                return "Entry{" +
                        "name='" + name + '\'' +
                        ", modifier=" + modifier +
                        ", type=" + type +
                        ", defaults=" + Arrays.toString(defaults) +
                        ", documentation=" + documentation +
                        '}';
            }
        }

        public static enum Modifier {
            OPTIONAL, DEFAULT
        }

        public static enum Hasher {
            BLAKE2_128,
            BLAKE2_256,
            BLAKE2_256_CONCAT,
            TWOX_128,
            TWOX_256,
            TWOX_64_CONCAT,
            IDENTITY
        }

        public static enum TypeId {
            PLAIN(Integer.class),
            MAP(MapDefinition.class),
            DOUBLEMAP(DoubleMapDefinition.class);

            private final Class<?> clazz;

            TypeId(Class<?> clazz) {
                this.clazz = clazz;
            }

            public Class<?> getClazz() {
                return clazz;
            }
        }

        public abstract static class Type<T> {
            private final T value;

            public Type(T value) {
                this.value = value;
            }

            public abstract TypeId getId();

            public T get() {
                return value;
            }

            @SuppressWarnings("unchecked")
            public <X> Type<X> cast(Class<X> clazz) {
                if (clazz.isAssignableFrom(getId().getClazz())) {
                    return (Type<X>) this;
                }
                throw new ClassCastException("Cannot cast " + getId().getClazz() + " to " + clazz);
            }

            @Override
            public final boolean equals(Object o) {
                if (this == o) return true;
                if (!(o instanceof Type)) return false;
                Type<?> type = (Type<?>) o;
                return Objects.equals(value, type.value);
            }

            @Override
            public final int hashCode() {
                return Objects.hash(value);
            }

            @Override
            public String toString() {
                return "Type{" +
                        "value=" + value +
                        '}';
            }
        }

        public static class PlainType extends Type<Integer> {
            public PlainType(Integer value) {
                super(value);
            }

            @Override
            public TypeId getId() {
                return TypeId.PLAIN;
            }
        }

        public static class MapDefinition {
            private List<Integer> hashers;
            private Integer key;
            private Integer value;

            public List<Integer> getHashers() {
                return hashers;
            }

            public void setHashers(List<Integer> hashers) {
                this.hashers = hashers;
            }

            public Integer getKey() {
                return key;
            }

            public void setKey(Integer key) {
                this.key = key;
            }

            public Integer getValue() {
                return value;
            }

            public void setValue(Integer value) {
                this.value = value;
            }

            @Override
            public String toString() {
                return "MapDefinition{" +
                        "hashers=" + hashers +
                        ", key=" + key +
                        ", value=" + value +
                        '}';
            }
        }

        public static class MapType extends Type<MapDefinition> {
            public MapType(MapDefinition value) {
                super(value);
            }

            @Override
            public TypeId getId() {
                return TypeId.MAP;
            }
        }

        public static class DoubleMapDefinition {
            private Hasher firstHasher;
            private String firstKey;
            private Hasher secondHasher;
            private String secondKey;
            private String type;

            public Hasher getFirstHasher() {
                return firstHasher;
            }

            public void setFirstHasher(Hasher firstHasher) {
                this.firstHasher = firstHasher;
            }

            public String getFirstKey() {
                return firstKey;
            }

            public void setFirstKey(String firstKey) {
                this.firstKey = firstKey;
            }

            public String getSecondKey() {
                return secondKey;
            }

            public void setSecondKey(String secondKey) {
                this.secondKey = secondKey;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public Hasher getSecondHasher() {
                return secondHasher;
            }

            public void setSecondHasher(Hasher secondHasher) {
                this.secondHasher = secondHasher;
            }

            @Override
            public final boolean equals(Object o) {
                if (this == o) return true;
                if (!(o instanceof DoubleMapDefinition)) return false;
                DoubleMapDefinition that = (DoubleMapDefinition) o;
                return firstHasher == that.firstHasher &&
                        Objects.equals(firstKey, that.firstKey) &&
                        Objects.equals(secondKey, that.secondKey) &&
                        Objects.equals(type, that.type) &&
                        secondHasher == that.secondHasher;
            }

            @Override
            public final int hashCode() {
                return Objects.hash(firstHasher, firstKey, secondKey, type, secondHasher);
            }
        }

        public static class DoubleMapType extends Type<DoubleMapDefinition> {
            public DoubleMapType(DoubleMapDefinition value) {
                super(value);
            }

            @Override
            public TypeId getId() {
                return TypeId.DOUBLEMAP;
            }
        }
    }


    public static class Calls {
        private int type;

        @Override
        public String toString() {
            return "Calls{" +
                    "index=" + type +
                    '}';
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

    public static class Events {
        private int type;

        @Override
        public String toString() {
            return "Events{" +
                    "index=" + type +
                    '}';
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

    public static class Constant {
        private String name;
        private Integer type;
        private byte[] value;
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

        public byte[] getValue() {
            return value;
        }

        public void setValue(byte[] value) {
            this.value = value;
        }

        public List<String> getDocs() {
            return docs;
        }

        public void setDocs(List<String> docs) {
            this.docs = docs;
        }

        @Override
        public final boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Constant)) return false;
            Constant constant = (Constant) o;
            return Objects.equals(name, constant.name) &&
                    Objects.equals(type, constant.type) &&
                    Arrays.equals(value, constant.value) &&
                    Objects.equals(docs, constant.docs);
        }

        @Override
        public final int hashCode() {
            int result = Objects.hash(name, type, docs);
            result = 31 * result + Arrays.hashCode(value);
            return result;
        }

        @Override
        public String toString() {
            return "Constant{" +
                    "name='" + name + '\'' +
                    ", type=" + type +
                    ", value=" + Arrays.toString(value) +
                    ", docs=" + docs +
                    '}';
        }
    }

    public static class Errors {
        private int type;

        @Override
        public String toString() {
            return "Error{" +
                    "type=" + type +
                    '}';
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

    @Override
    public String toString() {
        return "Metadata{" +
                "magic=" + magic +
                ", version=" + version +
                '}';
    }
}
