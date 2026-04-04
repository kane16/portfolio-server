#!/usr/bin/env sh

gradle clean nativeCompile
docker build -f Dockerfile.native -t kane16/portfolio_server:1.0.0 .