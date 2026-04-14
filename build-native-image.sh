#!/usr/bin/env bash
set -euo pipefail

IMAGE_NAME="kane16/portfolio-server-native"
TAG="${1:-latest}"

echo "Building GraalVM native image: ${IMAGE_NAME}:${TAG}"
docker build -t "${IMAGE_NAME}:${TAG}" -f Dockerfile.native .

echo ""
echo "Done: ${IMAGE_NAME}:${TAG}"
