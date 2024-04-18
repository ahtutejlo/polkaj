package io.emeraldpay.polkaj.scaletypes;

import io.emeraldpay.polkaj.scale.ScaleCodecReader;
import io.emeraldpay.polkaj.scale.ScaleReader;
import io.emeraldpay.polkaj.scale.reader.EnumReader;
import io.emeraldpay.polkaj.scale.reader.ListReader;
import io.emeraldpay.polkaj.scale.reader.UnionReader;
import io.emeraldpay.polkaj.scaletypes.v14.Lookup;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public class MetadataV14Reader implements ScaleReader<MetadataV14> {


    public static final ListReader<MetadataV14.Pallet> PALLET_LIST_READER = new ListReader<>(new PalletsReader());
    public static final LookupReader LOOKUP_READER = new LookupReader();
    public static final ListReader<String> STRING_LIST_READER = new ListReader<>(ScaleCodecReader.STRING);
    public static final ListReader<Integer> INT_LIST_READER = new ListReader<>(ScaleCodecReader.COMPACT_UINT);
    public static final EnumReader<MetadataV14.Storage.Hasher> HASHER_ENUM_READER = new EnumReader<>(MetadataV14.Storage.Hasher.values());

    @Override
    public MetadataV14 read(ScaleCodecReader rdr) {
        MetadataV14 result = new MetadataV14();
        result.setMagic(ScaleCodecReader.INT32.read(rdr));
        result.setVersion(rdr.readUByte());
        if (result.getVersion() != 14) {
            throw new IllegalStateException("Unsupported metadata version: " + result.getVersion());
        }
        result.setLookup(LOOKUP_READER.read(rdr));
        result.setPallets(PALLET_LIST_READER.read(rdr));
        List<MetadataV14.Pallet> modules = result.getPallets();

        for (MetadataV14.Pallet m : modules) {
            List<MetadataV14.Call> calls = m.getCalls();
            if (calls != null) {
                for (int j = 0; j < calls.size(); j++) {
                    calls.get(j).setIndex((m.getIndex() << 8) + j);
                }
            }
        }
        return result;
    }

    static class LookupReader implements ScaleReader<Lookup> {
        public static final ListReader<Lookup.Type> TYPE_LIST_READER = new ListReader<>(new TypeReader());

        @Override
        public Lookup read(ScaleCodecReader rdr) {
            Lookup result = new Lookup();
            result.setTypes(TYPE_LIST_READER.read(rdr));
            return result;
        }

        static class TypeReader implements ScaleReader<Lookup.Type> {
            public static final TypeInfoReader TYPE_INFO_READER = new TypeInfoReader();

            @Override
            public Lookup.Type read(ScaleCodecReader rdr) {
                Lookup.Type result = new Lookup.Type();
                BigInteger typeId = rdr.read(ScaleCodecReader.COMPACT_BIGINT);
                System.out.println("\ntypes.id: " + typeId.toString());
                result.setId(typeId.intValue());
                Lookup.Type.TypeInfo typeInfo = TYPE_INFO_READER.read(rdr);
                System.out.println("types.type: " + typeInfo);
                result.setType(typeInfo);
                return result;
            }

            static class TypeInfoReader implements ScaleReader<Lookup.Type.TypeInfo> {

                public static final ListReader<Lookup.Type.TypeInfo.Param> PARAM_LIST_READER = new ListReader<>(new ParamReader());
                public static final DefinitionReader DEFINITION_READER = new DefinitionReader();

                @Override
                public Lookup.Type.TypeInfo read(ScaleCodecReader rdr) {
                    Lookup.Type.TypeInfo result = new Lookup.Type.TypeInfo();
                    result.setPath(STRING_LIST_READER.read(rdr));
                    System.out.println("types.type.path: " + result.getPath().toString());
                    List<Lookup.Type.TypeInfo.Param> paramList = PARAM_LIST_READER.read(rdr);
                    System.out.println("types.type.params: " + paramList.toString());
                    result.setParams(paramList);
                    Lookup.Type.TypeInfo.Definition definition = DEFINITION_READER.read(rdr);
                    System.out.println("types.type.definition: " + definition);
                    result.setDef(definition);
                    List<String> docs = STRING_LIST_READER.read(rdr);
                    System.out.println("types.type.docs: " + docs.toString());
                    result.setDocs(docs);
                    return result;
                }

                static class ParamReader implements ScaleReader<Lookup.Type.TypeInfo.Param> {

                    @Override
                    public Lookup.Type.TypeInfo.Param read(ScaleCodecReader rdr) {
                        Lookup.Type.TypeInfo.Param result = new Lookup.Type.TypeInfo.Param();
                        result.setName(rdr.readString());

                        rdr.readOptional(ScaleCodecReader.COMPACT_BIGINT).ifPresent((v) -> {
                            result.setType(v.intValue());
                            result.setHasType(true);
                        });
                        return result;
                    }
                }

                static class DefinitionReader implements ScaleReader<Lookup.Type.TypeInfo.Definition> {

                    public static final CompositeReader COMPOSITE_READER = new CompositeReader();
                    public static final ArrayReader ARRAY_READER = new ArrayReader();
                    public static final PrimitiveReader PRIMITIVE_READER = new PrimitiveReader();
                    public static final CompactReader COMPACT_READER = new CompactReader();
                    public static final SequenceReader SEQUENCE_READER = new SequenceReader();
                    public static final VariantReader VARIANT_READER = new VariantReader();

                    public static final ListReader<Lookup.Type.TypeInfo.Definition.Composite.Field> COMPOSITE_FIELD_LIST_READER = new ListReader<>(new CompositeReader.CompositeFieldReader());


                    @Override
                    public Lookup.Type.TypeInfo.Definition read(ScaleCodecReader rdr) {
                        Lookup.Type.TypeInfo.Definition result = new Lookup.Type.TypeInfo.Definition();
                        byte defType = rdr.readByte();
                        switch (defType) {
                            case 0:
                                result.setComposite(COMPOSITE_READER.read(rdr));
                                break;
                            case 1:
                                result.setVariant(VARIANT_READER.read(rdr));
                                break;
                            case 2:
                                result.setSequence(SEQUENCE_READER.read(rdr));
                                break;
                            case 3:
                                result.setArray(ARRAY_READER.read(rdr));
                                break;
                            case 4:
                                result.setTuple(new Lookup.Type.TypeInfo.Definition.Tuple().setTypes(INT_LIST_READER.read(rdr)));
                                break;
                            case 5:
                                result.setPrimitive(PRIMITIVE_READER.read(rdr));
                                break;
                            case 6:
                                result.setCompact(COMPACT_READER.read(rdr));
                                break;
                            default:
                                throw new IllegalStateException("Unsupported type definition: " + defType);
                        }
                        return result;
                    }

                    static class CompositeReader implements ScaleReader<Lookup.Type.TypeInfo.Definition.Composite> {

                        @Override
                        public Lookup.Type.TypeInfo.Definition.Composite read(ScaleCodecReader rdr) {
                            Lookup.Type.TypeInfo.Definition.Composite result = new Lookup.Type.TypeInfo.Definition.Composite();
                            result.setFields(COMPOSITE_FIELD_LIST_READER.read(rdr));
                            return result;
                        }

                        static class CompositeFieldReader implements ScaleReader<Lookup.Type.TypeInfo.Definition.Composite.Field> {

                            @Override
                            public Lookup.Type.TypeInfo.Definition.Composite.Field read(ScaleCodecReader rdr) {
                                Lookup.Type.TypeInfo.Definition.Composite.Field result = new Lookup.Type.TypeInfo.Definition.Composite.Field();
                                rdr.readOptional(ScaleCodecReader.STRING).ifPresent((v) -> {
                                    result.setName(v);
                                    result.setHasName(true);
                                });
                                result.setType(rdr.read(ScaleCodecReader.COMPACT_BIGINT).intValue());
                                rdr.readOptional(ScaleCodecReader.STRING).ifPresent((v) -> {
                                    result.setTypeName(v);
                                    result.setHasTypeName(true);
                                });
                                result.setDocs(STRING_LIST_READER.read(rdr));
                                return result;
                            }
                        }
                    }

                    static class ArrayReader implements ScaleReader<Lookup.Type.TypeInfo.Definition.Array> {
                        @Override
                        public Lookup.Type.TypeInfo.Definition.Array read(ScaleCodecReader rdr) {
                            Lookup.Type.TypeInfo.Definition.Array result = new Lookup.Type.TypeInfo.Definition.Array();
                            result.setLen(rdr.readUint32());
                            result.setType(rdr.read(ScaleCodecReader.COMPACT_BIGINT).intValue());
                            return result;
                        }
                    }

                    static class PrimitiveReader implements ScaleReader<Lookup.Type.TypeInfo.Definition.Primitive> {
                        @Override
                        public Lookup.Type.TypeInfo.Definition.Primitive read(ScaleCodecReader rdr) {
                            Lookup.Type.TypeInfo.Definition.Primitive result = new Lookup.Type.TypeInfo.Definition.Primitive();
                            result.setPrimitive(rdr.readByte());
                            return result;
                        }
                    }

                    static class CompactReader implements ScaleReader<Lookup.Type.TypeInfo.Definition.Compact> {
                        @Override
                        public Lookup.Type.TypeInfo.Definition.Compact read(ScaleCodecReader rdr) {
                            Lookup.Type.TypeInfo.Definition.Compact result = new Lookup.Type.TypeInfo.Definition.Compact();
                            result.setType(rdr.read(ScaleCodecReader.COMPACT_BIGINT).intValue());
                            return result;
                        }
                    }

                    static class SequenceReader implements ScaleReader<Lookup.Type.TypeInfo.Definition.Sequence> {
                        @Override
                        public Lookup.Type.TypeInfo.Definition.Sequence read(ScaleCodecReader rdr) {
                            Lookup.Type.TypeInfo.Definition.Sequence result = new Lookup.Type.TypeInfo.Definition.Sequence();
                            result.setType(rdr.read(ScaleCodecReader.COMPACT_BIGINT).intValue());
                            return result;
                        }
                    }

                    static class VariantReader implements ScaleReader<Lookup.Type.TypeInfo.Definition.Variant> {

                        public static final ListReader<Lookup.Type.TypeInfo.Definition.Variant.TypeDefVariant> VARIANT_LIST_READER = new ListReader<>(new TypeDefVariantReader());
                        @Override
                        public Lookup.Type.TypeInfo.Definition.Variant read(ScaleCodecReader rdr) {
                            Lookup.Type.TypeInfo.Definition.Variant result = new Lookup.Type.TypeInfo.Definition.Variant();
                            result.setVariants(VARIANT_LIST_READER.read(rdr));
                            return result;
                        }

                        static class TypeDefVariantReader implements ScaleReader<Lookup.Type.TypeInfo.Definition.Variant.TypeDefVariant> {
                            @Override
                            public Lookup.Type.TypeInfo.Definition.Variant.TypeDefVariant read(ScaleCodecReader rdr) {
                                Lookup.Type.TypeInfo.Definition.Variant.TypeDefVariant result = new Lookup.Type.TypeInfo.Definition.Variant.TypeDefVariant();
                                result.setName(rdr.readString());
                                result.setFields(COMPOSITE_FIELD_LIST_READER.read(rdr));
                                result.setIndex(rdr.readByte());
                                result.setDocs(STRING_LIST_READER.read(rdr));
                                return result;
                            }
                        }
                    }

                }
            }
        }
    }

    static class PalletsReader implements ScaleReader<MetadataV14.Pallet> {

        public static final MetadataV14Reader.StorageReader STORAGE_READER = new MetadataV14Reader.StorageReader();
        public static final ListReader<MetadataV14.Call> CALL_LIST_READER = new ListReader<>(new MetadataV14Reader.CallReader());
        public static final ListReader<MetadataV14.Event> EVENT_LIST_READER = new ListReader<>(new MetadataV14Reader.EventReader());
        public static final ListReader<MetadataV14.Constant> CONSTANT_LIST_READER = new ListReader<>(new MetadataV14Reader.ConstantReader());
        public static final ListReader<MetadataV14.Error> ERROR_LIST_READER = new ListReader<>(new MetadataV14Reader.ErrorReader());

        @Override
        public MetadataV14.Pallet read(ScaleCodecReader rdr) {
            MetadataV14.Pallet result = new MetadataV14.Pallet();
            result.setName(rdr.readString());
            System.out.println("Pallet name: " + result.getName());
            rdr.readOptional(STORAGE_READER).ifPresent(result::setStorage);
            rdr.readOptional(CALL_LIST_READER).ifPresent(result::setCalls);
            rdr.readOptional(EVENT_LIST_READER).ifPresent(result::setEvents);
            result.setConstants(CONSTANT_LIST_READER.read(rdr));
            result.setErrors(ERROR_LIST_READER.read(rdr));
            result.setIndex(rdr.readUByte());
            return result;
        }
    }

    static class StorageReader implements ScaleReader<MetadataV14.Storage> {

        public static final ListReader<MetadataV14.Storage.Entry> ENTRY_LIST_READER = new ListReader<>(new MetadataV14Reader.StorageEntryReader());

        @Override
        public MetadataV14.Storage read(ScaleCodecReader rdr) {
            MetadataV14.Storage result = new MetadataV14.Storage();
            result.setPrefix(rdr.readString());
            result.setEntries(ENTRY_LIST_READER.read(rdr));
            return result;
        }
    }

    static class StorageEntryReader implements ScaleReader<MetadataV14.Storage.Entry> {

        public static final EnumReader<MetadataV14.Storage.Modifier> MODIFIER_ENUM_READER = new EnumReader<>(MetadataV14.Storage.Modifier.values());
        public static final MetadataV14Reader.TypeReader TYPE_READER = new MetadataV14Reader.TypeReader();

        @Override
        public MetadataV14.Storage.Entry read(ScaleCodecReader rdr) {
            MetadataV14.Storage.Entry result = new MetadataV14.Storage.Entry();
            String name = rdr.readString();
            System.out.println("Storage entry name: " + name);
            result.setName(name);
            MetadataV14.Storage.Modifier modifier = MODIFIER_ENUM_READER.read(rdr);
            System.out.println("Storage entry modifier: " + modifier);
            result.setModifier(modifier);
            MetadataV14.Storage.Type<?> type = rdr.read(TYPE_READER);
            System.out.println("Storage entry type: " + type);
            result.setType(type);
            byte[] defaults = rdr.readByteArray();
            System.out.println("Storage entry defaults: " + Arrays.toString(defaults));
            result.setDefaults(defaults);
            List<String> documentation = STRING_LIST_READER.read(rdr);
            System.out.println("Storage entry documentation: " + documentation);
            result.setDocumentation(documentation);
            return result;
        }
    }

    static class TypeReader implements ScaleReader<MetadataV14.Storage.Type<?>> {

        @SuppressWarnings("unchecked")
        private static final UnionReader<MetadataV14.Storage.Type<?>> TYPE_UNION_READER = new UnionReader<>(
                new MetadataV14Reader.TypePlainReader(),
                new MetadataV14Reader.TypeMapReader(),
                new MetadataV14Reader.TypeDoubleMapReader()
        );

        @Override
        public MetadataV14.Storage.Type<?> read(ScaleCodecReader rdr) {
            return TYPE_UNION_READER.read(rdr).getValue();
        }
    }

    static class TypePlainReader implements ScaleReader<MetadataV14.Storage.PlainType> {
        @Override
        public MetadataV14.Storage.PlainType read(ScaleCodecReader rdr) {
            return new MetadataV14.Storage.PlainType(rdr.readString());
        }
    }

    static class TypeMapReader implements ScaleReader<MetadataV14.Storage.MapType> {

        @Override
        public MetadataV14.Storage.MapType read(ScaleCodecReader rdr) {
            MetadataV14.Storage.MapDefinition definition = new MetadataV14.Storage.MapDefinition();
            definition.setHasher(HASHER_ENUM_READER.read(rdr));
            definition.setKey(rdr.readString());
            definition.setType(rdr.readString());
            definition.setIterable(rdr.readBoolean());
            return new MetadataV14.Storage.MapType(definition);
        }
    }

    static class TypeDoubleMapReader implements ScaleReader<MetadataV14.Storage.DoubleMapType> {

        @Override
        public MetadataV14.Storage.DoubleMapType read(ScaleCodecReader rdr) {
            MetadataV14.Storage.DoubleMapDefinition definition = new MetadataV14.Storage.DoubleMapDefinition();
            definition.setFirstHasher(HASHER_ENUM_READER.read(rdr));
            definition.setFirstKey(rdr.readString());
            definition.setSecondKey(rdr.readString());
            definition.setType(rdr.readString());
            definition.setSecondHasher(HASHER_ENUM_READER.read(rdr));
            return new MetadataV14.Storage.DoubleMapType(definition);
        }
    }

    static class CallReader implements ScaleReader<MetadataV14.Call> {

        public static final ListReader<MetadataV14.Call.Arg> ARG_LIST_READER = new ListReader<>(new MetadataV14Reader.ArgReader());

        @Override
        public MetadataV14.Call read(ScaleCodecReader rdr) {
            MetadataV14.Call result = new MetadataV14.Call();
            result.setName(rdr.readString());
            result.setArguments(ARG_LIST_READER.read(rdr));
            result.setDocumentation(STRING_LIST_READER.read(rdr));
            return result;
        }
    }

    static class ArgReader implements ScaleReader<MetadataV14.Call.Arg> {

        @Override
        public MetadataV14.Call.Arg read(ScaleCodecReader rdr) {
            MetadataV14.Call.Arg result = new MetadataV14.Call.Arg();
            result.setName(rdr.readString());
            result.setType(rdr.readString());
            return result;
        }
    }

    static class EventReader implements ScaleReader<MetadataV14.Event> {

        @Override
        public MetadataV14.Event read(ScaleCodecReader rdr) {
            MetadataV14.Event result = new MetadataV14.Event();
            result.setName(rdr.readString());
            result.setArguments(STRING_LIST_READER.read(rdr));
            result.setDocumentation(STRING_LIST_READER.read(rdr));
            return result;
        }
    }

    static class ConstantReader implements ScaleReader<MetadataV14.Constant> {

        @Override
        public MetadataV14.Constant read(ScaleCodecReader rdr) {
            MetadataV14.Constant result = new MetadataV14.Constant();
            result.setName(rdr.readString());
            result.setType(rdr.readString());
            result.setValue(rdr.readByteArray());
            result.setDocumentation(STRING_LIST_READER.read(rdr));
            return result;
        }
    }

    static class ErrorReader implements ScaleReader<MetadataV14.Error> {

        @Override
        public MetadataV14.Error read(ScaleCodecReader rdr) {
            MetadataV14.Error result = new MetadataV14.Error();
            result.setName(rdr.readString());
            result.setDocumentation(STRING_LIST_READER.read(rdr));
            return result;
        }
    }
}
