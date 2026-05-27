---
description: Build JAR file without running tests
agent: build
model: develonica/dvl-developer
---
use command ./gradlew clean build -x test
Build the project into a JAR file, skipping tests to save time.
Focus on compilation errors and suggest fixes.