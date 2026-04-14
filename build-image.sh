#!/usr/bin/env bash
set -euo pipefail

IMAGE_NAME="kane16/portfolio-server"
TAG="${1:-latest}"

echo "Building JVM image: ${IMAGE_NAME}:${TAG}"
docker build -t "${IMAGE_NAME}:${TAG}" -f Dockerfile .

echo ""
echo "Done: ${IMAGE_NAME}:${TAG}"
