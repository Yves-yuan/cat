cd /d %~dp0
bin\protoc.exe --java_out=../src/main/java *.proto
bin\protoc.exe -I ./v1/dto --java_out=../src/main/java ./v1/dto/*.proto