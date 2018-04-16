mvn clean package
docker build -t twitter-img .
docker run -p 8080:8080 -d --name app-twitter  twitter-img
