#!/bin/bash

# Exit on error
set -e

# Validate input
if [ "$#" -ne 1 ]; then
  echo "Usage: $0 [debug|release]"
  exit 1
fi

VARIANT="$1"

# Validate build variant
if [[ "$VARIANT" != "debug" && "$VARIANT" != "release" ]]; then
  echo "Invalid build variant: $VARIANT. Use 'debug' or 'release'."
  exit 1
fi

# Capitalize first letter for Gradle task
CAP_VARIANT="$(tr '[:lower:]' '[:upper:]' <<< "${VARIANT:0:1}")${VARIANT:1}"

# Clean project
./gradlew clean

# Assemble the APK
./gradlew "assemble${CAP_VARIANT}"

# Find the generated APK
APK_PATH=$(find "./app/build/outputs/apk/$VARIANT" -name "*.apk" | head -n 1)

if [ -z "$APK_PATH" ]; then
  echo "APK not found for variant '$VARIANT'."
  exit 1
fi

echo "Found APK: $APK_PATH"

# Install on connected device
adb install -r "$APK_PATH"

echo "✅ app-$VARIANT APK installed successfully"