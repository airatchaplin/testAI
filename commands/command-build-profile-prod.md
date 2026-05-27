---
description: Build JAR with specific profile
agent: build
model: develonica/dvl-developer
---
use command ./gradlew clean build -Pprofile=prod
Build the project into a JAR file using the specified profile.
Check for profile-specific configuration issues and suggest fixes.