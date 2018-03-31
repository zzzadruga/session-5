# Session-5

1. Реализовать класс `SQLGenerator`, который генерирует SQL выражения для переданного на вход класса.
    * Значения заменяются на символ '?'.

    Пример SQL-запросов:

```
    INSERT INTO table1(id, value) VALUES(?, ?);

    SELECT value FROM table1 WHERE id = ?;

    UPDATE table1 SET value1 = ? WHERE id = ?;

    DELETE FROM table1 WHERE id = ?;

```

2. Реализовать класс, который переводит поданный обьект в json-формат.
    * Написать тесты.
    * Рекурсивно превращать предков.
    * Коллекции Set, List или массивы превращаются в json массив.
    * Примитивные типы и их обёртки превращаются в свои toString значения.
    * Date, Calendar форматируются в `dd.MM.yyyy`.
    * Предусмотреть возможность добавления новых простых типов данных.

    Пример json-обьекта:

```
{
    "field": "value",

    "field2": .1,

    "field3": 1234,

    "array": [
        "val1",
        "val2",
        "val3"
    ]

    "nestedObject": {
        "nestedField1": 1,
        "nestedField2": 2,
    }
}
```
