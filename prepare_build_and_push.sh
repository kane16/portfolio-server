#!/usr/bin/env sh

gradle clean nativeCompile
docker build -t kane16/portfolio_server:1.0.0 .
docker push kane16/portfolio_server:1.0.0