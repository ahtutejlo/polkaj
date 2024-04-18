package io.emeraldpay.polkaj.scaletypes.v14;

import java.util.List;

public class Lookup {

    private List<Type> types;

    public static class Type {
        private Integer id;
        private TypeInfo type;

        @Override
        public String toString() {
            return "Type{" +
                    "id=" + id +
                    ", type=" + type +
                    '}';
        }

        public static class TypeInfo {
            private List<String> path;
            private List<Param> params;
            private Definition def;
            private List<String> docs;

            @Override
            public String toString() {
                return "TypeInfo{" +
                        "path=" + path +
                        ", params=" + params +
                        ", def=" + def +
                        ", docs=" + docs +
                        '}';
            }

            public static class Param {
                private String name;
                private Integer type;
                private Boolean hasType;

                @Override
                public String toString() {
                    return "Param{" +
                            "name='" + name + '\'' +
                            ", type=" + type +
                            ", hasType=" + hasType +
                            '}';
                }

                public Param() {
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public Boolean getHasType() {
                    return hasType;
                }

                public void setHasType(Boolean hasType) {
                    this.hasType = hasType;
                }

            }

            public static class Definition {
                private Composite composite;
                private Array array;
                private Primitive primitive;
                private Compact compact;
                private Sequence sequence;
                private Variant variant;
                private Tuple tuple;

                @Override
                public String toString() {
                    return "Definition{" +
                            "composite=" + composite +
                            ", array=" + array +
                            ", primitive=" + primitive +
                            ", compact=" + compact +
                            '}';
                }

                public static class Tuple {
                    private List<Integer> types;

                    @Override
                    public String toString() {
                        return "Tuple{" +
                                "types=" + types +
                                '}';
                    }

                    public Tuple() {}

                    public List<Integer> getTypes() {
                        return types;
                    }

                    public Tuple setTypes(List<Integer> types) {
                        this.types = types;
                        return this;
                    }
                }

                public static class Variant {
                    private List<TypeDefVariant> variants;

                    @Override
                    public String toString() {
                        return "Variant{" +
                                "variants=" + variants +
                                '}';
                    }

                    public Variant() {
                    }

                    public List<TypeDefVariant> getVariants() {
                        return variants;
                    }

                    public void setVariants(List<TypeDefVariant> variants) {
                        this.variants = variants;
                    }

                    public static class TypeDefVariant {
                        private String name;
                        private List<Composite.Field> fields;
                        private int index;
                        private List<String> docs;

                        @Override
                        public String toString() {
                            return "TypeDefVariant{" +
                                    "name='" + name + '\'' +
                                    ", fields=" + fields +
                                    ", index=" + index +
                                    ", docs=" + docs +
                                    '}';
                        }

                        public TypeDefVariant() {
                        }

                        public String getName() {
                            return name;
                        }

                        public void setName(String name) {
                            this.name = name;
                        }

                        public List<Composite.Field> getFields() {
                            return fields;
                        }

                        public void setFields(List<Composite.Field> fields) {
                            this.fields = fields;
                        }

                        public int getIndex() {
                            return index;
                        }

                        public void setIndex(int index) {
                            this.index = index;
                        }

                        public List<String> getDocs() {
                            return docs;
                        }

                        public void setDocs(List<String> docs) {
                            this.docs = docs;
                        }
                    }
                }

                public static class Sequence {
                    private int type;

                    @Override
                    public String toString() {
                        return "Sequence{" +
                                "type=" + type +
                                '}';
                    }

                    public Sequence() {}

                    public int getType() {
                        return type;
                    }

                    public void setType(int type) {
                        this.type = type;
                    }
                }

                public static class Compact {
                    private int type;

                    @Override
                    public String toString() {
                        return "Compact{" +
                                "type=" + type +
                                '}';
                    }

                    public Compact() {}

                    public int getType() {
                        return type;
                    }

                    public void setType(int type) {
                        this.type = type;
                    }
                }

                public static class Composite {
                    private List<Field> fields;

                    @Override
                    public String toString() {
                        return "Composite{" +
                                "fields=" + fields +
                                '}';
                    }

                    public static class Field {
                        private Boolean hasName;
                        private String name;
                        private int type;
                        private Boolean hasTypeName;
                        private String typeName;
                        private List<String> docs;

                        @Override
                        public String toString() {
                            return "Field{" +
                                    "hasName=" + hasName +
                                    ", name='" + name + '\'' +
                                    ", type=" + type +
                                    ", hasTypeName=" + hasTypeName +
                                    ", typeName='" + typeName + '\'' +
                                    ", docs=" + docs +
                                    '}';
                        }

                        public Field() {}

                        public String getName() {
                            return name;
                        }

                        public void setName(String name) {
                            this.name = name;
                        }

                        public int getType() {
                            return type;
                        }

                        public void setType(int type) {
                            this.type = type;
                        }

                        public String getTypeName() {
                            return typeName;
                        }

                        public void setTypeName(String typeName) {
                            this.typeName = typeName;
                        }

                        public List<String> getDocs() {
                            return docs;
                        }

                        public void setDocs(List<String> docs) {
                            this.docs = docs;
                        }

                        public Boolean getHasName() {
                            return hasName;
                        }

                        public void setHasName(Boolean hasName) {
                            this.hasName = hasName;
                        }

                        public Boolean getHasTypeName() {
                            return hasTypeName;
                        }

                        public void setHasTypeName(Boolean hasTypeName) {
                            this.hasTypeName = hasTypeName;
                        }
                    }

                    public Composite() {}

                    public List<Field> getFields() {
                        return fields;
                    }

                    public void setFields(List<Field> fields) {
                        this.fields = fields;
                    }
                }

                public static class Array {
                    private Long len;
                    private Integer type;

                    @Override
                    public String toString() {
                        return "Array{" +
                                "len=" + len +
                                ", type=" + type +
                                '}';
                    }

                    public Array() {}

                    public long getLen() {
                        return len;
                    }

                    public void setLen(long len) {
                        this.len = len;
                    }

                    public int getType() {
                        return type;
                    }

                    public void setType(int type) {
                        this.type = type;
                    }
                }

                public static class Primitive {
                    private byte primitive;

                    @Override
                    public String toString() {
                        return "Primitive{" +
                                "primitive=" + primitive +
                                '}';
                    }

                    public Primitive() {}

                    public byte getPrimitive() {
                        return primitive;
                    }

                    public void setPrimitive(byte primitive) {
                        this.primitive = primitive;
                    }
                }

//                public Definition() {}

                public Composite getComposite() {
                    return composite;
                }

                public void setComposite(Composite composite) {
                    this.composite = composite;
                }

                public Array getArray() {
                    return array;
                }

                public void setArray(Array array) {
                    this.array = array;
                }

                public Primitive getPrimitive() {
                    return primitive;
                }

                public void setPrimitive(Primitive primitive) {
                    this.primitive = primitive;
                }

                public Compact getCompact() {
                    return compact;
                }

                public void setCompact(Compact compact) {
                    this.compact = compact;
                }

                public Sequence getSequence() {
                    return sequence;
                }

                public void setSequence(Sequence sequence) {
                    this.sequence = sequence;
                }

                public Variant getVariant() {
                    return variant;
                }

                public void setVariant(Variant variant) {
                    this.variant = variant;
                }

                public Tuple getTuple() {
                    return tuple;
                }

                public void setTuple(Tuple tuple) {
                    this.tuple = tuple;
                }
            }

            public TypeInfo() {}

            public List<String> getPath() {
                return path;
            }

            public void setPath(List<String> path) {
                this.path = path;
            }

            public List<Param> getParams() {
                return params;
            }

            public void setParams(List<Param> params) {
                this.params = params;
            }

            public Definition getDef() {
                return def;
            }

            public void setDef(Definition def) {
                this.def = def;
            }

            public List<String> getDocs() {
                return docs;
            }

            public void setDocs(List<String> docs) {
                this.docs = docs;
            }
        }

        public Type() {}

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public TypeInfo getType() {
            return type;
        }

        public void setType(TypeInfo type) {
            this.type = type;
        }
    }

    public Lookup(List<Type> types) {
        this.types = types;
    }

    public Lookup() {
    }

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }
}
