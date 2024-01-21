package assign0;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Assignment 0
 * Submitted by: 
 * Student 1. Itamar Abir 	ID# 
 * Student 2. Rami Abo Rabia	ID# 
 */

import base.Compressor;

public class StringEncoderDecoder2 implements Compressor {

	public StringEncoderDecoder2() {
	}

	//
	private void mapping(String[] input_names, HashMap<String, Integer> wordCountMap) {

		
		// System.out.println(input_names[0]);
		// hashingTwo(input_names[0], wordCountMap);
		hashingString(input_names, wordCountMap);

	}

	private void hashingString(String[] input_names, HashMap<String, Integer> wordCountMap) {
		Scanner scanner = new Scanner(input_names[0]);
		while (scanner.hasNext()) {
			String line = scanner.nextLine();
			line = line + " ";
			line = line.trim();
			//System.out.println(line);
			String[] part = line.split(" ");
			String runner = "";
			for (int i = 0; i < part.length; i++) {

				runner = part[i];
				if (wordCountMap.containsKey(runner)) {
					wordCountMap.put(runner, wordCountMap.get(runner) + 1);
				} else {
					wordCountMap.put(runner, 1);
				}
			}
		}
		// HashMap the value is the number of occurrences 
		//System.out.println("wordCountMap");
		//System.out.println(wordCountMap);
	}
//
//	private void hashingTwo(String line, HashMap<String, Integer> wordCountMap) {
//
//		for (int i = 0; i < line.length() - 1; i += 2) {
//			String runner = line.substring(i, i + 2);
//			if (wordCountMap.containsKey(runner)) {
//				// If char is present in wordCountMap,
//				// incrementing it's count by 1
//				wordCountMap.put(runner, wordCountMap.get(runner) + 1);
//			} else {
//				// If char is not present in wordCountMap,
//				// putting this char to wordCountMap with 1 as it's value
//				wordCountMap.put(runner, 1);
//			}
//		}
//		if (line.length() % 2 != 0) {// size of string is odd
//			String lastChar = line.charAt(line.length() - 1) + "";
//			wordCountMap.put(lastChar, 1);
//
//		}
//		// 
////		System.out.println("wordCountMap");
//		System.out.println(wordCountMap);
	// }

	@Override
	public void Compress(String[] input_names, String[] output_names) {

		HashMap<String, Integer> wordCountMap = new HashMap<>();// using hashmap to count the number of appearance of
																// st.
		mapping(input_names, wordCountMap);// given a string mapping convert it to hashmap with the number of appearance

		// list for sorting 200 most common strings;
		List<Map.Entry<String, Integer>> list = new ArrayList<>(wordCountMap.entrySet());

		sort(wordCountMap, list);// sorting the most common 200 st's
		print(list);// print after sort

		// a hashmap that has the value st and the key is the st encoded string by it's
		// sort
		HashMap<String, String> dictionary = new HashMap<>();
		// giving every st it's encoded string
		Decode(list, dictionary);
		// System.out.println("dictionary:");
		// System.out.println(dictionary); //
		// we are putting the dictionary and a 223 symbol twice to separate it from the
		// original text/string
		output_names[0] = dictionary.toString() + "" + ((char) 255 + "" + ((char) 255));
		// starting decoding the given text/string
		decodedInput(dictionary, input_names, output_names);
		

	}

	private void decodedInput(HashMap<String, String> dictionary, String[] input_names, String[] output_names) {
		String decodedInput = "";
		
		Scanner scanner = new Scanner(input_names[0]);
		while (scanner.hasNext()) {
			String line = scanner.nextLine();
			line = line + " ";
			line = line.trim();
			String[] part = line.split(" ");
			String runner = "";
			for (int i = 0; i < part.length; i++) {

				runner = part[i];
				decodedInput += dictionary.get(runner);

			}
		}
		//System.out.println(decodedInput.length());
		output_names[0] = output_names[0] += decodedInput;
		//System.out.println();
		//System.out.println(output_names[0]);
		
	}

	private void Decode(List<Map.Entry<String, Integer>> list, HashMap<String, String> dictionary) {

		int i = 0;
		while (i < list.size() - 1) {
			Map.Entry<String, Integer> e = list.get(i);
			if (i < 250) {
				if (e.getKey().length() >= 2) {
					dictionary.put(e.getKey(), ((char) (i) + ""));
				}
				else {
					dictionary.put(e.getKey(),  e.getKey() + "");

				}
			}

			else {
				dictionary.put(e.getKey(), e.getKey() + "");
			}
			i++;
		}
		//System.out.println("dic");
		//System.out.println(dictionary);

	}

	private void print(List<Entry<String, Integer>> list) {
		//System.out.println("print after sort:");
		for (Map.Entry<String, Integer> e : list) {
			//System.out.print(e.getKey() + ":" + e.getValue() + " ");
		}
		//System.out.println();
	}

	private void sort(HashMap<String, Integer> wordCountMap, List<Entry<String, Integer>> list) {
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
				return e2.getValue() - e1.getValue();
			}
		});
	}

	@Override
	public void Decompress(String[] input_names, String[] output_names) {
		Map<String, String> map = new HashMap<>();

		int index = input_names[0].indexOf("" + ((char) 255 + "" + ((char) 255)));
		String dictionary = input_names[0].substring(1, index - 1);// remove {} from hash map
		importDictionary(map, dictionary);

//		System.out.println("map:");
//		System.out.println(map.toString());

		String enc = input_names[0].substring(index + 2, input_names[0].length());
		//System.out.println("enc.lenght: "+enc.length());

		convertDecodeToString(enc, map, output_names);

	}

	private void importDictionary(Map<String, String> map, String dictionary) {

		// System.out.println("dictionary:");
		// System.out.println(dictionary.toString());

		String[] keysVal = dictionary.split(", ");// remove space,
		 //System.out.println("keysVal:");
		 for (String str : keysVal) {

		 //System.out.print(str + "");// test
		 }
		
		String str1 = "", str0 = "";
		for (int i = 0; i < keysVal.length; i++) {
			
			for (int j = 0; j < keysVal[i].length(); j++) {
				if (keysVal[i].charAt(j) == '=') {
					j++;
					while (j < keysVal[i].length() && keysVal[i].charAt(j) != '=') {
						str0 += keysVal[i].charAt(j);
						j++;
					}

					map.put(str0, str1);
					//System.out.println(runner[1] + " " + runner[0] + ",");
					str0 = "";
					str1 = "";

				}

				else {

					str1 += keysVal[i].charAt(j);
				}
			}
		}
	}

	private void convertDecodeToString(String enc, Map<String, String> map, String[] output_names) {
		String run = null;

		//System.out.println("enc:");
		//System.out.println(enc);
		for (int i = 0; i < enc.length(); i++) {
			if (enc.charAt(i) == (char) 222) {
				if (enc.charAt(i + 2) == (char) 223) {
					run = enc.charAt(i + 1) + "";
					output_names[0] += run;
					i = i + 2;
				} else {
					run = enc.charAt(i + 1) + "" + enc.charAt(i + 2);
					output_names[0] += run;
					i = i + 2;
				}
			} else {
				run = enc.charAt(i) + "";
//				if (map.get(run) == null)
//					output_names[0] += "\n";
//				else {
				output_names[0] += map.get(run);

			}

		}

	}

	@Override
	public byte[] CompressWithArray(String[] input_names, String[] output_names) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] DecompressWithArray(String[] input_names, String[] output_names) {

		return null;
	}

}
