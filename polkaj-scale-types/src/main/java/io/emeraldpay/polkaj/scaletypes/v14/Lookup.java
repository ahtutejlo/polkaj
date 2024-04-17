package io.emeraldpay.polkaj.scaletypes.v14;

import java.util.List;

public class Lookup {

    private List<Type> types;

    public static class Type {
        private int id;
        private TypeInfo type;

        public static class TypeInfo {
            private List<String> path;
            private List<Param> params;
            private Definition def;
            private List<String> docs;

            public static class Param {
                private String name;
                private int type;

                public Param() {
                }

                public Param(String name, int type) {
                    this.name = name;
                    this.type = type;
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
            }

            public static class Definition {
                private Composite composite;
                private Array array;
                private Primitive primitive;

                public static class Composite {
                    private List<Field> fields;

                    public static class Field {
                        private String name;
                        private int type;
                        private String typeName;
                        private List<String> docs;

                        public Field(String name, int type, String typeName, List<String> docs) {
                            this.name = name;
                            this.type = type;
                            this.typeName = typeName;
                            this.docs = docs;
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
                    }

                    public Composite(List<Field> fields) {
                        this.fields = fields;
                    }

                    public List<Field> getFields() {
                        return fields;
                    }

                    public void setFields(List<Field> fields) {
                        this.fields = fields;
                    }
                }

                public static class Array {
                    private Integer len;
                    private Integer type;

                    public Array(int len, int type) {
                        this.len = len;
                        this.type = type;
                    }

                    public int getLen() {
                        return len;
                    }

                    public void setLen(int len) {
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
                    private String primitive;

                    public Primitive(String primitive) {
                        this.primitive = primitive;
                    }

                    public String getPrimitive() {
                        return primitive;
                    }

                    public void setPrimitive(String primitive) {
                        this.primitive = primitive;
                    }
                }

                public Definition(Composite composite, Array array, Primitive primitive) {
                    this.composite = composite;
                    this.array = array;
                    this.primitive = primitive;
                }

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
            }

            public TypeInfo(List<String> path, List<Param> params, Definition def, List<String> docs) {
                this.path = path;
                this.params = params;
                this.def = def;
                this.docs = docs;
            }

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

        public Type(int id, TypeInfo type) {
            this.id = id;
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
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
