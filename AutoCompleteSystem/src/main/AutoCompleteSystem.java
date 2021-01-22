/**
 * A search autocomplete system for a search engine. Users may input a sentence (at least one word and end with a special character '#'). For each character they type except '#', it returns the top 3 historical
 * hot sentences that have prefix the same as the part of sentence already typed. These are the specific rules:

    1.The hot degree for a sentence is defined as the number of times a user typed the exactly same sentence before.
    2.The returned top 3 hot sentences are sorted by hot degree (The first is the hottest one). If several sentences have the same degree of hot, ASCII-code order is used (smaller one appears first).
    3.If less than 3 hot sentences exist, then it returns as many as possible.
    4.When the input is a special character, it means the sentence ends, and in this case, it returns an empty list.
    
 * @author J. Choker, jalal.choker@gmail.com, 20.1.2021
 */

package main;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AutoCompleteSystem {
	
	private final Map<String, Integer> counts;
	private final Trie trie;
	private String current;
	private static final int RESULT_COUNT = 3;
	
	//The input is historical data. Sentences is previously typed sentences. Frequency is the corresponding times a sentence has been typed.
	public AutoCompleteSystem(String[] sentences, int[] frequency) {		
	
		this.counts = new HashMap<>();
		this.trie = new Trie();
		this.current = "";
		
		for (int i = 0; i < sentences.length; i++) {
			var word = sentences[i];
			counts.put(word, frequency[i]);
			trie.insert(word);
		}
	}
	
	// The character will only be lower-case letters ('a' to 'z'), blank space (' ') or a special character ('#').
	public List<String> input(char c) {
		
		if(c == '#') // finish current search
		{
			if(!this.counts.containsKey(current))
			{
				this.trie.insert(this.current);
				this.counts.put(current, 1);
			}
			else
			{
				this.counts.put(current, this.counts.get(current) + 1); // increment
			}
			
			this.current = ""; // reset			
			return new ArrayList<String>(); // return empty
		}
		else
		{
			this.current += c; // append input character to ongoing search
			var node = this.trie.searchNode(current);
			return GetTopHistory(node);
		}		
	}
	
	// returns top 3 historical data in node if any
	private List<String> GetTopHistory(TrieNode node) {
		
		if(node == null) return new ArrayList<String>();		
		
		List<String> arr = new ArrayList<>(node.sentences);
		
		 // sort by frequency descending else by lexicographic order ascending
		Collections.sort(arr, (a,b) -> this.counts.get(a) != this.counts.get(b) ?
									   this.counts.get(b) - this.counts.get(a) : a.compareTo(b));
		
		var res = new ArrayList<String>();
		
		var i = 0;
		while(i < Math.min(RESULT_COUNT, arr.size()))		
			res.add(arr.get(i++));
		
		return res;
	}
	
	class Trie
	{
		private final TrieNode root;
		
		public Trie() {			
			this.root = new TrieNode();
		}
		
		// Inserts a word into trie
		public Boolean insert(String word) {
			
			var cur = this.root;
			
			for (int i = 0; i < word.length(); i++) {
				var c = word.charAt(i);
				
				var chldrn = cur.children;
				
				if(!chldrn.containsKey(c)) chldrn.put(c, new TrieNode());
				
				cur = chldrn.get(c);
				
				cur.sentences.add(word); // add word to each node in the hierarchy
			}
			
			if(cur.isEnd) return false; // word already existed in trie
			
			cur.isEnd=true; // word didn't exist in trie
			return true;
		}
		
		// Searches for a prefix in the trie and returns last node if exists

		public TrieNode searchNode(String prefix) {			
		
			var cur = this.root;
			
			for (int i = 0; i < prefix.length(); i++) {
				var c = prefix.charAt(i);
				
				if(!cur.children.containsKey(c)) return null; // prefix not found
					 
				cur = cur.children.get(c);
			}			
			
			return cur;
		}
	}
		
	class TrieNode 
	{
		public Boolean isEnd;
		public final Map<Character, TrieNode> children; // alternatively a TrieNode array of size 27 can be use with a prefixed location to # character
		public final Set<String> sentences;

		public TrieNode() {
			this.isEnd = false;
			this.children = new HashMap<>();
			this.sentences = new HashSet<>();
		}
	}
}
