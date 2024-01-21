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


public class StringEncoderDecoder implements Compressor {

	public StringEncoderDecoder() {
		// TODO Auto-generated method stub
	}

	private void mapping(String[] input_names, HashMap<String, Integer> wordCountMap) {
		//System.out.println("first text:");
		//System.out.println(input_names[0]);
		hashing(input_names[0], wordCountMap);

	}

	private void hashing(String line, HashMap<String, Integer> wordCountMap) {

		for (int i = 0; i < line.length() - 1; i += 2) {
			String runner = line.substring(i, i + 2);
			if (wordCountMap.containsKey(runner)) {
				//if the runner appeared before increment its count
				wordCountMap.put(runner, wordCountMap.get(runner) + 1);
			} else {
				//if its the first appearance of runner initialize its count to 1
				wordCountMap.put(runner, 1);
			}
		}
		if (line.length() % 2 != 0) {// size of string is odd - in any case its only 1
			String lastChar = line.charAt(line.length() - 1) + "";
			wordCountMap.put(lastChar, 1);

		}
		
		//System.out.println("wordCountMap");
		//System.out.println(wordCountMap);
	}

	@Override
	public void Compress(String[] input_names, String[] output_names) {
//		System.out.println(input_names[0].length());
		HashMap<String, Integer> wordCountMap = new HashMap<>();
		mapping(input_names, wordCountMap);

		// list for sorting 200 most common strings;
		List<Map.Entry<String, Integer>> list = new ArrayList<>(wordCountMap.entrySet());
		//sorting the list:
		sort(wordCountMap, list);
		//print(list);// print after sort

		// dictionary- hash map after decoding;
		HashMap<String, String> dictionary = new HashMap<>();

		Decode(list, dictionary);
		//System.out.println("dictionary:");
		//System.out.println(dictionary); 
		
		//saving the dictionary at the first part of the decoded string separating the content with twice char 223
		output_names[0] = dictionary.toString() + "" + ((char) 223 + "" + ((char) 223));
		//starting the decode on input_names[0]
		decodedInput(dictionary, input_names, output_names);
		//System.out.println(output_names[0].length());

	}

	private void decodedInput(HashMap<String, String> dictionary, String[] input_names, String[] output_names) {
		String decodedInput = "";//Chaining all the decoded strings to this variable
		for (int i = 0; i < input_names[0].length() - 1; i += 2) {
			String runner = input_names[0].substring(i, i + 2);
			decodedInput += dictionary.get(runner);//chaining by the appearance in the given text using the hashmap
		}

		if (input_names[0].length() % 2 != 0) {//if odd chain the last char - 3.3 statement
			String lastChar = input_names[0].charAt(input_names[0].length() - 1) + "";
			decodedInput += dictionary.get(lastChar);
		}
		//System.out.println("decodedInput-------:");
		//System.out.println(decodedInput.length());
		output_names[0] = output_names[0] += decodedInput;//putting the decoded string in output_names.
		
		//System.out.println("final: "+output_names[0].length());
	}

	private void Decode(List<Map.Entry<String, Integer>> list, HashMap<String, String> dictionary) {
		for (int i = 0; i < list.size(); i++) {
			Map.Entry<String, Integer> e = list.get(i);
			// TODO
			if (i <200) { // 3.1
				dictionary.put(e.getKey(), ((char) (i) + ""));
			} else {
				if (e.getKey().length() == 1) { // 3.3
					dictionary.put(e.getKey(), ((char) (222) + e.getKey() + "" + (char) (223)));
				} else { // 3.2 e.getKey().length()==1
					dictionary.put(e.getKey(), ((char) (222) + e.getKey() + ""));
					;
				}

			}
		}
	}
	//used for testing
	private void print(List<Entry<String, Integer>> list) {
		System.out.println("print after sort:");
		for (Map.Entry<String, Integer> e : list) {
			System.out.print(e.getKey() + ":" + e.getValue() + " ");
		}
		System.out.println();
	}
	//using Collections to be able to sort by value from the HashMap
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

		int index = input_names[0].indexOf("" + ((char) 223 + "" + ((char) 223)));//the separation index from the dictionary and the decoded text
		String dictionary = input_names[0].substring(1, index - 1);// remove {} from the toString of hash map
		//we need to flip the map to be able to use it on the decoded text - we can't use the map by value
		mappingDictionary(map, dictionary);

		//System.out.println("map:");
		//System.out.println(map.toString());

		String enc = input_names[0].substring(index + 2, input_names[0].length());//the values to encode without the dictionary

		convertDecodeToString(enc, map, output_names);

	}
	private void mappingDictionary(Map<String, String> map, String dictionary) {

		//System.out.println("dictionary:");
		//System.out.println(dictionary.toString());
		
		String[] keysVal = dictionary.split(", ");// remove space and , and separating them to an array of strings
		//System.out.println("keysVal:");
//		for (String str : keysVal) {
//
//			System.out.print(str + "");// test
//		}
		//{hh=jh, hjh=d, hjd=wd}
		//System.out.println("after =");
		String str1="", str0="";//in str1 and str2 we switch the value to be the key and the value to be keys to access and scan from the decoded input
		for (int i=0; i<keysVal.length; i++) {
			String runner[] = { "", "" };
			for (int j=0; j<keysVal[i].length(); j++) {
				if (keysVal[i].charAt(j)=='=') {//a key can be like this: char = =   --> = can be a value.
					j++; //we dont want the first = it came from the hashmap.toString
					while ( j<keysVal[i].length()&& keysVal[i].charAt(j)!='=') {
						str0+=keysVal[i].charAt(j);
						j++;
					}
					
					map.put(str0, str1);//we will use the key given from the decoded string and will know what its original value was
					//System.out.println(runner[1] + " " + runner[0]+",");
					str0="";
					str1="";

				}
				
				else {
					
					str1+=keysVal[i].charAt(j);
				}
			}
		}
	}


	private void convertDecodeToString(String enc, Map<String, String> map, String[] output_names) {
		String run = null;

		//System.out.println("enc:");
		//System.out.println(enc.length());
		for (int i = 0; i < enc.length(); i++) {
			if (enc.charAt(i) == (char) 222) {//translating as given in the algorithm
				if (enc.charAt(i + 2) == (char) 223) {
					run = enc.charAt(i + 1) + "";
					output_names[0] += run;
					i = i + 2;
				} else {
					run = enc.charAt(i + 1) + "" + enc.charAt(i + 2);
					output_names[0] += run;
					i = i + 2;
				}
			} else {//3.1
				run = enc.charAt(i) + "";

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
