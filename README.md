**Readme File for StringEncoderDecoder Compression Algorithm**

### Introduction
This Java code implements a basic data compression algorithm using a simple string encoding and decoding technique. The algorithm involves creating a mapping of substrings to their frequencies, sorting them based on their occurrences, and then assigning unique codes to the most common substrings. The code includes both a single-string encoder/decoder (`StringEncoderDecoder`) and a multi-string encoder/decoder (`StringEncoderDecoder2`) that works on multiple lines of input.

### Files
1. **StringEncoderDecoder.java:** This file contains the implementation of the single-string compression algorithm.
2. **StringEncoderDecoder2.java:** This file contains the implementation of the multi-string compression algorithm.
3. **Compressor.java:** This file defines the interface for compression algorithms.

### Usage
1. **StringEncoderDecoder:**
   - The `Compress` method takes an array of input strings and compresses them into a single output string.
   - The `Decompress` method takes the compressed string and decompresses it back to the original input.

2. **StringEncoderDecoder2:**
   - Similar to `StringEncoderDecoder`, but designed to work on multiple lines of input.

3. **Compressor Interface:**
   - The `Compressor` interface defines the methods that compression algorithms must implement. These methods include `Compress`, `Decompress`, `CompressWithArray`, and `DecompressWithArray`.

### How to Run
1. Compile the Java files using a Java compiler.
   ```bash
   javac StringEncoderDecoder.java
   javac StringEncoderDecoder2.java
   ```

2. Run the Java files with appropriate input.
   ```bash
   java StringEncoderDecoder
   java StringEncoderDecoder2
   ```

### Input Format
1. **StringEncoderDecoder:**
   - Provide input strings when prompted. The program will compress and decompress the given string.

2. **StringEncoderDecoder2:**
   - Input multiple lines of text. The program will compress and decompress the entire text.

### Output
1. **StringEncoderDecoder:**
   - The compressed string will be displayed after compression.
   - The decompressed string will be displayed after decompression.

2. **StringEncoderDecoder2:**
   - The compressed text will be displayed after compression.
   - The decompressed text will be displayed after decompression.

### Additional Notes
- The code uses a basic substring frequency mapping and sorting technique for compression. It may not be optimized for large datasets and is intended for educational purposes.
- Feel free to explore the code, modify it, and experiment with different inputs.

### Authors
- Rami Abo Rabia
- Itamar Abir

### Acknowledgments
This code was submitted as part of an assignment for a data compression course. It demonstrates a basic understanding of string compression techniques in Java.
