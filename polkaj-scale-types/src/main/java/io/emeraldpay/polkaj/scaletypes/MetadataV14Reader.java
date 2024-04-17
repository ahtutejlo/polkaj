package io.emeraldpay.polkaj.scaletypes;

import io.emeraldpay.polkaj.scale.ScaleCodecReader;
import io.emeraldpay.polkaj.scale.ScaleReader;
import io.emeraldpay.polkaj.scale.reader.EnumReader;
import io.emeraldpay.polkaj.scale.reader.ListReader;
import io.emeraldpay.polkaj.scale.reader.UnionReader;

import java.util.List;

public class MetadataV14Reader implements ScaleReader<MetadataV14> {


    public static final ListReader<MetadataV14.Pallet> PALLET_LIST_READER = new ListReader<>(new PalletsReader());
    public static final ListReader<String> STRING_LIST_READER = new ListReader<>(ScaleCodecReader.STRING);
    public static final EnumReader<MetadataV14.Storage.Hasher> HASHER_ENUM_READER = new EnumReader<>(MetadataV14.Storage.Hasher.values());

    @Override
    public MetadataV14 read(ScaleCodecReader rdr) {
        MetadataV14 result = new MetadataV14();
        result.setMagic(ScaleCodecReader.INT32.read(rdr));
        result.setVersion(rdr.readUByte());
        if (result.getVersion() != 14) {
            throw new IllegalStateException("Unsupported metadata version: " + result.getVersion());
        }
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
            result.setName(rdr.readString());
            result.setModifier(MODIFIER_ENUM_READER.read(rdr));
            result.setType(rdr.read(TYPE_READER));
            result.setDefaults(rdr.readByteArray());
            result.setDocumentation(STRING_LIST_READER.read(rdr));
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
