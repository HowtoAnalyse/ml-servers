# CoreNLP server using docker


```console
root@vmname:~# docker pull howtoanalyse/corenlp-chinese
root@vmname:~# docker run -p 9000:9000 --name coreNLP --rm -i -t howtoanalyse/corenlp-chinese
```
# test

```console
wget --post-data '金大中对克林顿的讲话报以掌声：克林顿总统在会谈中重申，他坚定地支持韩国摆脱经济危机。' '{SERVER_IP}:9000/?properties={"annotators":"tokenize,ssplit","outputFormat":"json"}' -O -
```

# Java client
