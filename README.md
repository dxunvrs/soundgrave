## 🎵 Soundgrave - простой стриминг музыки 

Легковесный бэкенд на Spring Boot для стриминга музыки напрямую в VLC или любой другой плеер, поддерживающий сетевые плейлисты.

## 🚀 Как использовать?
1. Загрузите свою музыку в music/ и запустите контейнер.  
2. Откройте ссылку в VLC и наслаждайтесь.

## 🛠 Быстрый старт
### 1. Подготовка файлов
Создай папку music в корне проекта. Различные папки - разные плейлисты.

```
music/  
├── playlist1/  
│   ├── linkin_park_numb.mp3  
│   └── daft_punk_veridis_quo.flac  
└── playlist2/  
    └── kikoriki_theme.mp3   
```

### 2. Запуск через Docker
```bash
docker compose up -d --build
```

### 3. Подключение к VLC  
- Откройте VLC Media Player.  
- Нажмите Ctrl + N (Открыть сеть).
- Введите адрес для нужного плейлиста:
http://localhost:1220/api/playlists/playlist1

## 📡 API Эндпоинты

|Метод|Эндпоинт|Описание|
|-|-|-|
|GET|/api/playlists/{playlist-name}|	Получить плейлист для VLC|
|GET|/api/tracks/{track-id}/stream|Потоковая передача аудио (поддерживает Range)|

## 🏗 Технологический стек

- Java 17
- Spring Boot 4.05 (Web, Lombok)
- Docker & Docker Compose
- In-Memory Storage (ConcurrentHashMap) — для максимальной скорости и простоты.
