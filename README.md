# car-number-generator
REST-сервис, выдающий автомобильные номера.

Контекст приложения: /number

REST-сервис реализует два GET-метода: random и next

Пример вызова: 
Запрос: GET http://localhost:8080/number/random
Ответ: C399BA 116 RUS

Методы random и next генерируют и выводят строку формата A111AA 116 RUS,
где A - любой символ из списка ['А', 'В', 'Е', 'К', 'М', 'Н', 'О', 'Р', 'С', 'Т', 'У', 'Х'], 1 - любая цифра, 116 RUS - константа.

Используется русский алфавит.

Номера только с нолями быть не может, пример - A000AA 116 RUS

Реализовано два алгоритма генерации номера:

Рандомный - выдается произвольный номер, соответствующий формату при вызове метода random. При этом номер не совпадает с любым ранее выданным.

Последовательный - номера выдаются последовательно от меньшего к большему при вызове метода next. При этом сначала производится итерация цифровых символов, и только после этого итерация буквенных.

Номера, выданные методами random и next, не повторяются, при исчерпании лимита определенного 
по формуле "(количество символов из списка ^ количество символов в номере) * максимальное цифровое значение в номере"

При перезапуске сервис продолжает работу с того места, на котором закончил работу.

Номера сохраняются в БД. Sql скрипт находится в папке sql.

# Примеры

Пример последовательный 1: после номера C399BA 116 RUS должен идти номер C400BA 116 RUS

Пример последовательный 2: после номера A999BA 116 RUS должен идти номер A001BB 116 RUS
