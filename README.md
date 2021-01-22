# AutoCompleteSystem
 A search autocomplete system for a search engine. Users may input a sentence (at least one word and end with a special character '#').
 For each character they type except '#', it returns the top 3 historical hot sentences that have prefix the same as the part of sentence already typed.
 
 These are the specific rules:
 
    1.The hot degree for a sentence is defined as the number of times a user typed the exactly same sentence before.
    2.The returned top 3 hot sentences are sorted by hot degree (The first is the hottest one). If several sentences
      have the same degree of hot,      ASCII-code order is used (smaller one appears first).
    3.If less than 3 hot sentences exist, then it returns as many as possible.
    4.When the input is a special character, it means the sentence ends, and in this case, it returns an empty list.
