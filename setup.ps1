# Create gradle wrapper directory if it doesn't exist
New-Item -ItemType Directory -Force -Path "gradle\wrapper"

# Download gradle-wrapper.jar
Invoke-WebRequest -Uri "https://raw.githubusercontent.com/gradle/gradle/master/gradle/wrapper/gradle-wrapper.jar" -OutFile "gradle\wrapper\gradle-wrapper.jar"

# Download gradlew
Invoke-WebRequest -Uri "https://raw.githubusercontent.com/gradle/gradle/master/gradlew" -OutFile "gradlew"

# Make gradlew executable (for Unix-like systems)
if (Test-Path "gradlew") {
    $content = Get-Content "gradlew" -Raw
    $content = $content -replace "`r`n", "`n"
    Set-Content "gradlew" -Value $content -NoNewline
} 