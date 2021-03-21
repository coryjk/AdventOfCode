package org.coryjk.AdventOfCode.year2020.day14;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Slf4j
@NoArgsConstructor
public final class Memory {
    private static final char X = 'X';
    private static final char ONE = '1';

    private final Map<Long, BigInteger> memory = new HashMap<>();

    public long store(final long address, final long value) {
        return Optional.ofNullable(memory.put(address, BigInteger.valueOf(value)))
                .map(BigInteger::longValue)
                .orElse(-1L);
    }

    public long get(final long address) {
        return memory.getOrDefault(address, BigInteger.valueOf(-1)).longValue();
    }

    public Set<Long> getSetAddresses() {
        return memory.keySet();
    }

    @NoArgsConstructor
    public static final class Subroutine {
        @Getter private String mask;
        private List<String> operations = new LinkedList<>();

        public Subroutine withMask(final String mask) {
            this.mask = mask.split("=")[1].trim();
            return this;
        }

        public Subroutine withOperation(final String operation) {
            operations.add(operation);
            return this;
        }

        public void applyOperations(final Memory memory, final boolean isDecoderV2) {
            operations.forEach(operation -> {
                if (isDecoderV2) {
                    applyOperationV2(memory, operation);
                } else {
                    applyOperation(memory, operation);
                }
            });
        }

        private void applyOperation(final Memory memory, final String operation) {
            final long address = parseAddress(operation);
            long value = parseValue(operation);

            /*
             * For each bit (in Big Endian), either set or clear the nth bit
             * for 1 or 0 respectively. No-op on X.
             */
            for (int i = 0; i < mask.length(); i++) {
                if (mask.charAt(i) == X) {
                    continue;
                }
                value = applyMask(value, mask.charAt(i) == ONE, mask.length() - i - 1);
            }

            // write to memory
            memory.store(address, value);
        }

        private void applyOperationV2(final Memory memory, final String operation) {
            final long address = parseAddress(operation);
            final long value = parseValue(operation);
            final List<Integer> floatingBitOffsets = new LinkedList<>();
            long modifiedAddressBase = address;

            /*
             * For each bit, apply mask on 1 and ignore 0s. However, on X,
             * store the offset position of X.
             */
            for (int i = 0; i < mask.length(); i++) {
                final int n = mask.length() - i - 1;
                if (mask.charAt(i) == X) {
                    floatingBitOffsets.add(n);
                }
                if (mask.charAt(i) == ONE) {
                    modifiedAddressBase = applyMask(modifiedAddressBase, true, n);
                }
            }

            // clear target bits
            for (final int offset : floatingBitOffsets) {
                modifiedAddressBase &= applyMask(modifiedAddressBase, false, offset);
            }

            /*
             * Now must evaluate all possible values of floating bits. Given n floating
             * bit offset values, there will be 2^n = 1 << n writes.
             *
             * For each variation:
             * 1. since bits are being overwritten, must first clear target bits (done already)
             * 2. construct mask based on offsets, then apply OR mask
             * 3. store value in all evaluated memory addresses
             */
            final int writes = 1 << floatingBitOffsets.size();
            for (int i = 0; i < writes; i++) {
                // build mask by extracting jth bit and applying it in offset position
                long mask = 0;
                for (int j = 0; j < floatingBitOffsets.size(); j++) {
                    final int offset = floatingBitOffsets.get(j);
                    mask = applyMask(mask, ((i >> j) & 1) == 1, offset);
                }

                // store
                memory.store(modifiedAddressBase | mask, value);
            }
        }

        private long parseAddress(final String operation) {
            return Long.parseLong(operation.substring(
                    operation.indexOf('[')+1, operation.lastIndexOf(']')));
        }

        private long parseValue(final String operation) {
            return Long.parseLong(operation.split("=")[1].trim());
        }

        private long applyMask(final long value, final boolean setBit, final int n) {
            return setBit ? value | (1L << n) : value & ~(1L << n);
        }
    }
}
