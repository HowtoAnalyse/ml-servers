# CoreNLP server using docker


```console
root@vmname:~# docker pull howtoanalyse/corenlp-chinese
root@vmname:~# docker run -p 9000:9000 --name coreNLP --rm -i -t howtoanalyse/corenlp-chinese
```

# test

```console
wget --post-data '金大中对克林顿的讲话报以掌声：克林顿总统在会谈中重申，他坚定地支持韩国摆脱经济危机。' '{SERVER_IP}:9000/?properties={"annotators":"tokenize,ssplit","outputFormat":"json"}' -O -
```

# Memory and time usage

The speed and memory usage of CoreNLP server depends on the annotators we are using. Parsing and coreference can be very expensive. By turning off unnecessary annotators and making choices about parameters wisely, we are able to host a fast and memory efficient server producing high quality annotations.

For example, CoreNLP can do sentence splitting and word tokenization on a 100K sample file while giving Java only 20MB of memory (-mx20m).

## Reducing memory usage

Where is the 20MB going for the aforementioned example?

While processing the file, a whole document is represented in memory. Each token is represented as an Object, which stores various token attributes, such as token offsets, which are themselves represented as Objects.

The other half goes to the tokenizer model. 

In summary, there are 2 main places that memory goes:

1. The document to be annotated is stored in memory. And strings are internally memory-expensive in Java.

2. Large machine learning models (mainly arrays and maps of Strings for features and floats or doubles for parameters) and large data structures that are used by some NLP algorithms are stored in memory.

So, by processing the whole document piece by piece (Place 1), turning of unecessary annotators or choosing smaller ones (Place 2), we are able to lessen memory use efficiently.

## Reducing time usage

The first thing we do to invoke CoreNLP server is to load an annotation pipeline using `new StanfordCoreNLP(props)`. It usually takes 10-40 seconds. By loading one pipeline and use it for everything, we are able to save a considerable amount of time. 

For example:
```console
root@vmname:~# ls -1 para*.txt > all-files.txt
root@vmname:~# java -cp "$STANFORD_CORENLP_HOME/*" edu.stanford.nlp.pipeline.StanfordCoreNLP -filelist all-files.txt -outputFormat json
```

will be faster than

```console
java -cp "$STANFORD_CORENLP_HOME/*" edu.stanford.nlp.pipeline.StanfordCoreNLP -file para1.txt -outputFormat json
java -cp "$STANFORD_CORENLP_HOME/*" edu.stanford.nlp.pipeline.StanfordCoreNLP -file para2.txt -outputFormat json
...
java -cp "$STANFORD_CORENLP_HOME/*" edu.stanford.nlp.pipeline.StanfordCoreNLP -file para100.txt -outputFormat json
```

# For particular annotators

It is important to know characteristics of annotators before choosing one. Fore example, Several annotators support a maximum sentence length property (`pos.maxlen`, `parse.maxlen`, etc.) and will skip sentences exceeding the maximum. CoreNLP splits sentence using terminators like '.' and '?' heuristically, that is why data preprocessing is necessary before feeding data to an annotation model.

# Reference
* [Stanford CoreNLP](https://stanfordnlp.github.io/CoreNLP/memory-time.html)


