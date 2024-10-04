package io.emeraldpay.polkaj.scale.writer;

import io.emeraldpay.polkaj.scale.ScaleCodecWriter;
import io.emeraldpay.polkaj.scale.ScaleWriter;

import java.io.IOException;
import java.util.List;

public class SequenceWriter<T> implements ScaleWriter<List<T>> {

    private ScaleWriter<T> scaleWriter;

    public SequenceWriter(ScaleWriter<T> scaleWriter) {
        this.scaleWriter = scaleWriter;
    }

    @Override
    public void write(ScaleCodecWriter wrt, List<T> value) throws IOException {
        for (T item: value) {
            scaleWriter.write(wrt, item);
        }
    }
}
