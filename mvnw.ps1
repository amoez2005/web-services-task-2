$ErrorActionPreference = 'Stop'
$jdk = $env:JAVA_HOME
if (-not $jdk -or -not (Test-Path "$jdk\bin\javac.exe")) {
    $jdk = Split-Path (Split-Path (Get-Command javac.exe -ErrorAction Stop).Source)
}
$env:JAVA_HOME = $jdk
$env:PATH = "$jdk\bin;$env:PATH"
$version = '3.9.9'
$toolsDir = Join-Path $PSScriptRoot '.tools'
$maven = Join-Path $toolsDir "apache-maven-$version\bin\mvn.cmd"
if (-not (Test-Path $maven)) {
    New-Item -ItemType Directory -Force $toolsDir | Out-Null
    $archive = Join-Path $toolsDir 'maven.zip'
    $url = "https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/$version/apache-maven-$version-bin.zip"
    Invoke-WebRequest -UseBasicParsing $url -OutFile $archive
    $expected = (Invoke-WebRequest -UseBasicParsing "$url.sha512").Content.Trim().Split(' ')[0]
    if ((Get-FileHash $archive -Algorithm SHA512).Hash -ne $expected) { throw 'Maven checksum mismatch' }
    Expand-Archive -LiteralPath $archive -DestinationPath $toolsDir -Force
}
$mavenHome = Split-Path (Split-Path $maven)
$launcherJar = (Get-ChildItem "$mavenHome\boot\plexus-classworlds-*.jar" | Select-Object -First 1).FullName
& "$jdk\bin\java.exe" "-Dmaven.multiModuleProjectDirectory=$PSScriptRoot" "-Dmaven.home=$mavenHome" "-Dclassworlds.conf=$mavenHome\bin\m2.conf" -classpath $launcherJar org.codehaus.plexus.classworlds.launcher.Launcher @args
exit $LASTEXITCODE
