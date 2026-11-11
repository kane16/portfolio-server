#!/usr/bin/env bash
set -euo pipefail

IMAGE_NAME="kane16/portfolio-server-native"
TAG="${1:-latest}"
VARIANT="${VARIANT:-}"
PROD_AOT_PROFILES="prod"

build_args=()
case "${VARIANT}" in
  "") ;;
  prod)
    TAG="${TAG}-prod"
    build_args+=(--build-arg "AOT_PROFILES=${PROD_AOT_PROFILES}")
    ;;
  *)
    echo "Unknown VARIANT: ${VARIANT} (use prod or leave empty)" >&2
    exit 2
    ;;
esac

echo "Building GraalVM native image: ${IMAGE_NAME}:${TAG}${VARIANT:+ (AOT profiles: ${PROD_AOT_PROFILES})}"
docker build --build-context authplugin=../AuthPlugin ${build_args[@]+"${build_args[@]}"} -t "${IMAGE_NAME}:${TAG}" -f Dockerfile.native .

echo ""
echo "Done: ${IMAGE_NAME}:${TAG}"
