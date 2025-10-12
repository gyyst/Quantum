package com.lunarstra.quantum.key.generate;

import com.lunarstra.quantum.utils.UUIDV7Util;
import com.mybatisflex.core.keygen.IKeyGenerator;

public class UUIDKeyGenerator implements IKeyGenerator {

    @Override
    public Object generate(Object entity, String keyColumn) {
        return UUIDV7Util.generateString();
    }
}
