# Esittely

Esityksessä käsitellään miten kokonainen Fullstack projekti voidaan kontittaa ja miten sitä voidaan hyväksi käyttää ja julkaista Rahti 2 palveluun valmiiksi luoduilla imageilla.

Seminaarin esimerkki vaatii projektien omien vaatimusten lisäksi vähintään Dockerin, GitHub/DockerHub tilin, sekä pääsyn Rahti 2 palveluun.

### Sisältö
- SpringBoot(Maven) projektin kontitus
- NPM(Vite) projektin kontitus
- Docker imagen työntäminen DockerHubiin
- Imagen julkaisu Rahti 2 palveluun
- Hyödyt/haitat imagen julkaisusta Rahti 2 palveluun

# SpringBoot(Maven) projektin kontitus

### Valmistelu
SpringBoot projektin kontitusta varten tarvitsee selvittää 2/3 asiaa:
- Javan versio
- Projektin nimi ja versio
- Portti

Portti on vakiona 8080, mutta jos se on vaihdettu se voi löytöä application.properties, application.yml tai koodin sisältä.

Projektin Java version, sekä nimen ja projektin oman version löytää Maven projetkissa molemmat pom.xml tiedostosta.

Alla olevasta esimerkki pom.xml tiedostosta löytyy eri kohdista;
- Java versio = 17
- Projektin nimi = Juomapeli
- Projektin versio = 0.0.1-SNAPSHOT

<img src="./images/pom.png">

### Dockerfile luominen

Valmistelun jälkeen projektille on suhteellisen helppo luoda oma Dockerfile.

```
FROM openjdk:17
# Java + projektin java versio

EXPOSE 8080
# Portti

WORKDIR /usr/src/app
# kansio kontin sisällä

COPY . .
# Kopioidaan root kansiosta kaikki paitsi .dockerIgnore:n merkitsemät tiedostot konttiin

RUN ./mvnw package
# Ajetaan kontin sisällä maven wrapper, joka kokoaa projektin yhdeksi .jar tiedostoksi

CMD ["java", "-jar", "./target/JuomaPeli-0.0.1-SNAPSHOT.jar"]
# Kontin käynnistyessä ajettava komento, eli kutsutaan java-tulkki, määritetään että halutaan käynnistää jar tiedosto,
# Ja suoritetaan maven wrapperin kokoama tiedosto, jonka nimi muodostuu seuraavasti <projektin nimi>-<projektin versio>
# Eli JuomaPeli-0.0.1-SNAPSHOT
```

### Imagen luonti

Kun projektille on luotu dockerfile root kansioon, se voidaan muuntaa/rakentaa Imageksi, seuraavalla komennolla. Esimerkissä dockerfile on nimetty juomapeli.Dockerfile
```
docker build -f .\juomapeli.Dockerfile . -t ohke-be

# Build komento, -f eli file kertoo dockerille dockerFilen nimen, "." kertoo dockerille kansion jossa työskennellään,
# eli projektin Root kansio ja -t eli tag kertoo dockerille miten luotu image nimetään ja vaihtoehtoisesti millä tagillä.
```

Luotu image löytyy, joko docker images komennolla listasta tai graafisetsta liittymästä, jos docker desktop on asennettu.

# NPM(Vite) projektin kontitus

### Valmistelu

https://heidloff.net/article/deploying-nginx-on-openshift/
