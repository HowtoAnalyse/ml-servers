```console
docker run -p 8501:8501 --mount 'type=bind,src=/home/sara/exportpb,dst=/models/textclf' -e MODEL_NAME=textclf -t tensorflow/serving &
```

