# Тест-кейсы для YouTrack

## Логин страница 

Запуск тест-сьюта

```
mvn test -Dtest=LoginTest
```

## Страница Issues 

Запуск тест-сьюта

```
mvn test -Dtest=IssuesTest
```

## Страница Dashboard

Запуск тест-сьюта

```
mvn test -Dtest=DashboardTest
```

Пример запуска теста с параметрами

```
mvn test -Dtest=IssuesTest#testFindTask "-Dsearch.query=Тестовый заголовок 20250406_011934"
```
