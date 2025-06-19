#!/usr/bin/env sh

gradle clean test nativeCompile
docker build -f Dockerfile.native -t kane16/portfolio_server:1.0.0 .
docker push kane16/portfolio_server:1.0.0