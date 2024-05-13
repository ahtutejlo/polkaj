package io.emeraldpay.polkaj.scale.writer;

import io.emeraldpay.polkaj.scale.ScaleCodecWriter;
import io.emeraldpay.polkaj.scale.ScaleWriter;
import io.emeraldpay.polkaj.scale.reader.UInt128Reader;
import io.emeraldpay.polkaj.scale.reader.UInt256Reader;

import java.io.IOException;
import java.math.BigInteger;

public class UInt256Writer implements ScaleWriter<BigInteger> {

    @Override
    public void write(ScaleCodecWriter wrt, BigInteger value) throws IOException {
        if (value.signum() < 0) {
            throw new IllegalArgumentException("Negative numbers are not supported by Uint256");
        }
        byte[] array = value.toByteArray();
        int pos = 0;
        // sometimes BigInteger gives an extra zero byte in the start of the array
        if (array[0] == 0) {
            pos++;
        }
        int len = array.length - pos;
        if (len > UInt256Reader.SIZE_BYTES) {
            throw new IllegalArgumentException("Value is to big for 256 bits. Has: " + len * 8 + " bits");
        }
        byte[] encoded = new byte[UInt256Reader.SIZE_BYTES];
        System.arraycopy(array, pos, encoded, encoded.length - len, len);
        UInt256Reader.reverse(encoded);
        wrt.directWrite(encoded, 0, UInt256Reader.SIZE_BYTES);
    }
}
