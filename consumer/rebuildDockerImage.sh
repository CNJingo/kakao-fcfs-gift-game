docker build -t consumer .
docker image tag consumer wlsdn2165/consumer:latest
docker image push wlsdn2165/consumer:latest
